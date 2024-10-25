package com.example.parkinglot.repo;

import com.example.parkinglot.IntegrationTest;
import com.example.parkinglot.config.EmbeddedSQL;
import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@EmbeddedSQL
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLogin("johndoe123");
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setEmail("johndoe@example.com");
        user.setCreatedBy("test");
        user = userRepository.save(user);
    }

    @Test
    @Rollback
    void findByUserFirstName() {
        Car car = new Car();
        car.setModel("Model S");
        car.setMake("Tesla");
        car.setColor("Red");
        car.setRegistration("ABC123");
        car.setUser(user);
        carRepository.save(car);

        List<Car> cars = carRepository.findByUserFirstName("John");

        assertThat(cars).isNotEmpty();
//        assertThat(cars.get(0).getUser().getFirstName()).isEqualTo("John");
        assertThat(cars.get(0).getModel()).isEqualTo("Model S");
    }

}