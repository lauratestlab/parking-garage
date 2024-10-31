package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class CriteriaBasedRepositoryImpl implements CriteriaBasedRepository {

    private final EntityManager em;

    public CriteriaBasedRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    private static <T> Root<Spot> availableSpots(CriteriaBuilder cb, CriteriaQuery<T> cq, LocalDateTime startTime, LocalDateTime endTime, Long maxDurationInHours) {

        Root<Spot> root = cq.from(Spot.class);

        Join<Spot, Reservation> joinedRoot = root.join("reservations", JoinType.LEFT);

        CriteriaBuilder.Coalesce<LocalDateTime> coalesce = cb.coalesce();
        coalesce.value(joinedRoot.get("endTime"));
        coalesce.value(cb.function("add_hours", LocalDateTime.class, joinedRoot.get("startTime"), cb.literal(maxDurationInHours)));

        joinedRoot.on(
                cb.lessThan(joinedRoot.get("startTime"), endTime),
                cb.greaterThan(coalesce, startTime),
                cb.in(joinedRoot.get("status")).value(Status.STARTED).value(Status.ORDERED)
        );

        cq.where(cb.isNull(joinedRoot.get("id")));

        return root;
    }

    @Override
    public Long numberOfAvailableSports(LocalDateTime startTime, LocalDateTime endTime, Long maxDurationInHours) throws NoResultException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        var root = availableSpots(cb, cq, startTime, endTime, maxDurationInHours);

        cq.select(cb.count(root));
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public Spot findFirstAvailableSpot(LocalDateTime startTime, Long maxDurationInHours) throws NoResultException {
        LocalDateTime endTime = startTime.plusHours(maxDurationInHours);
        return findFirstAvailableSpot(startTime, endTime, maxDurationInHours);
    }

    @Override
    public Spot findFirstAvailableSpot(LocalDateTime startTime, LocalDateTime endTime, Long maxDurationInHours) throws NoResultException {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Spot> cq = cb.createQuery(Spot.class);

        var root = availableSpots(cb, cq, startTime, endTime, maxDurationInHours);

        cq.orderBy(cb.asc(root.get("floor")), cb.asc(root.get("name")));
        return em.createQuery(cq).setFirstResult(0).setMaxResults(1).getSingleResult();
    }
}
