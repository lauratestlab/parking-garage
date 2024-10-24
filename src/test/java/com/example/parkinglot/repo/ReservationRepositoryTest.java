//package com.example.parkinglot.repo;
//
//import com.example.parkinglot.IntegrationTest;
//import com.example.parkinglot.config.EmbeddedSQL;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//@EmbeddedSQL
////@IntegrationTest
//class ReservationRepositoryTest {
//
//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    void existsAvailableSpots() {
//        var startTime = LocalDateTime.of(2024, 10 ,22 ,10 ,45);
//        var endTime = LocalDateTime.of(2024, 10 ,23 ,10 ,45);
//        boolean result = reservationRepository.existsAvailableSpots(startTime, endTime);
//        assertTrue(result);
//    }
//}