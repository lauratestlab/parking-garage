package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Price price;

    @Test
    void findAll() {
        Price price1 = new Price();
        price1.setDuration(2);
        price1.setPrice(2);

        entityManager.persist(price1);

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(3).contains(price1);
    }

    //test to check if an exception thrown when you try to save two record with the same duration

    @Test
    void saveExceptionThrownWhenDurationIsDuplicated() {
        Price price2 = new Price();
        price2.setDuration(2);
        price2.setPrice(2);
        

        Price price3 = new Price();
        price3.setPrice(2);
        price3.setDuration(2);

        assertThrows(DataIntegrityViolationException.class, () -> priceRepository.save(price2));
    }
    /*
    LocalAccount account = new LocalAccount("Savings", new BigDecimal("100"), null, SORT_CODE);
        account.setNumber(request.fromAccount());

    when(accountRepository.findByIdNumberAndIdSortCodeAndIsClosedFalse(request.fromAccount(), request.fromAccountSortCode())).thenReturn(Optional.of(account));

    assertThrows(NotEnoughFundsException.class, () -> transactionService.withdraw(request));

    verify(accountRepository).findByIdNumberAndIdSortCodeAndIsClosedFalse(request.fromAccount(), request.fromAccountSortCode());

     */
}