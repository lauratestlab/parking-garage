package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Price;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@EmbeddedSQL
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Price price;

    @Test
    void findAll() {
        Price price1 = new Price();
        price1.setDuration(3L);
        price1.setPrice(new BigDecimal("2"));

        entityManager.persist(price1);

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(5).contains(price1);
    }

    //test to check if an exception thrown when you try to save two record with the same duration

    @Test
    @Disabled
    void saveExceptionThrownWhenDurationIsDuplicated() {
        Price price2 = new Price();
        price2.setDuration(3L);
        price2.setPrice(new BigDecimal("2"));

        priceRepository.saveAndFlush(price2);

        Price price3 = new Price();
        price3.setPrice(new BigDecimal("2"));
        price3.setDuration(3L);

        assertThrows(DataIntegrityViolationException.class, () -> priceRepository.saveAndFlush(price3));
    }

    @Test
    void findFirstByDurationGreaterThanEqualOrOrderByDurationAsc() {
        Optional<Price> priceOptional = priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(4);
        assertThat(priceOptional).isNotNull();
        assertThat(priceOptional.get().getPrice()).isEqualTo(new BigDecimal("8.00"));
    }

    @Test
    void findFirstOrderByDurationDesc() {
        Optional<Long> priceOptional = priceRepository.maxDuration();
        assertThat(priceOptional).isNotNull();
        assertThat(priceOptional.get()).isEqualTo(24L);
    }

    /*
    LocalAccount account = new LocalAccount("Savings", new BigDecimal("100"), null, SORT_CODE);
        account.setNumber(request.fromAccount());

    when(accountRepository.findByIdNumberAndIdSortCodeAndIsClosedFalse(request.fromAccount(), request.fromAccountSortCode())).thenReturn(Optional.of(account));

    assertThrows(NotEnoughFundsException.class, () -> transactionService.withdraw(request));

    verify(accountRepository).findByIdNumberAndIdSortCodeAndIsClosedFalse(request.fromAccount(), request.fromAccountSortCode());

     */
}