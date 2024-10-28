package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
        user.setPassword("$2y$10$iaLUe2YNU6aKWR6vxVc7CO5VBdMBC2ZeC0aCYr88xEdyD4xlrkdwe");
        user.setEmail("johndoe@example.com");
        user.setCreatedBy("test");

        userRepository.save(user);
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
        assertThat(cars.get(0).getUser().getFirstName()).isEqualTo("John");
        assertThat(cars.get(0).getModel()).isEqualTo("Model S");
    }

}