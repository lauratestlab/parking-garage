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
    @Query("select paymentMethod from PaymentMethod paymentMethod where paymentMethod.user.login = ?#{authentication.name}")
    List<PaymentMethod> findByUserIsCurrentUser();

    default Optional<PaymentMethod> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PaymentMethod> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PaymentMethod> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select paymentMethod from PaymentMethod paymentMethod left join fetch paymentMethod.user",
        countQuery = "select count(paymentMethod) from PaymentMethod paymentMethod"
    )
    Page<PaymentMethod> findAllWithToOneRelationships(Pageable pageable);

    @Query("select paymentMethod from PaymentMethod paymentMethod left join fetch paymentMethod.user")
    List<PaymentMethod> findAllWithToOneRelationships();

    @Query("select paymentMethod from PaymentMethod paymentMethod left join fetch paymentMethod.user where paymentMethod.id =:id")
    Optional<PaymentMethod> findOneWithToOneRelationships(@Param("id") Long id);
}
