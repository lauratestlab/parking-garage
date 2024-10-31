package com.example.parkinglot.repo;

import com.example.parkinglot.entity.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query("select paymentMethod from PaymentMethod paymentMethod where paymentMethod.user.login = :login")
    Page<PaymentMethod> findByUserIsCurrentUser(Pageable pageable, String login);

    default Page<PaymentMethod> findAllWithEagerRelationships(Pageable pageable, String login) {
        return this.findAllWithToOneRelationships(pageable, login);
    }

    @Query(
        value = "select paymentMethod from PaymentMethod paymentMethod left join fetch paymentMethod.user where paymentMethod.user.login = :login",
        countQuery = "select count(paymentMethod) from PaymentMethod paymentMethod where paymentMethod.user.login = :login"
    )
    Page<PaymentMethod> findAllWithToOneRelationships(Pageable pageable, String login);

    @Query("select paymentMethod from PaymentMethod paymentMethod left join fetch paymentMethod.user where paymentMethod.id =:id and paymentMethod.user.login = :login")
    Optional<PaymentMethod> findOne(@Param("id") Long id, String login);

    boolean existsByIdAndUserLogin(Long id, String login);
}
