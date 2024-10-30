package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final EntityManager em;

    public ReportRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long numberOfAvailableSports(LocalDateTime startTime, LocalDateTime endTime) throws NoResultException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Spot> root = cq.from(Spot.class);

        Join<Spot, Reservation> joinedRoot = root.join("reservations", JoinType.LEFT);

        joinedRoot.on(cb.lessThan(joinedRoot.get("startTime"), endTime), cb.greaterThan(joinedRoot.get("endTime"), startTime), cb.equal(joinedRoot.get("status"), Status.ACTIVE));

        cq.where(cb.isNull(joinedRoot.get("id")));

        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public Spot findFirstAvailableSpot(LocalDateTime startTime, LocalDateTime endTime) throws NoResultException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Spot> cq = cb.createQuery(Spot.class);

        Root<Spot> root = cq.from(Spot.class);

        Join<Spot, Reservation> joinedRoot = root.join("reservations", JoinType.LEFT);

        joinedRoot.on(cb.lessThan(joinedRoot.get("startTime"), endTime), cb.greaterThan(joinedRoot.get("endTime"), startTime), cb.equal(joinedRoot.get("status"), Status.ACTIVE));

        cq.where(cb.isNull(joinedRoot.get("id")));

        cq.orderBy(cb.asc(root.get("floor")), cb.asc(root.get("name")));

        return em.createQuery(cq).setFirstResult(0).setMaxResults(1).getSingleResult();
    }
}
