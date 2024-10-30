package com.example.parkinglot.service;

import com.example.parkinglot.entity.Car;
import com.example.parkinglot.exception.CarNotFoundException;
import com.example.parkinglot.repo.CarRepository;
import com.example.parkinglot.dto.CarDTO;
import com.example.parkinglot.mapper.CarMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    /**
     * Save a car.
     *
     * @param carDTO the entity to save.
     * @return the persisted entity.
     */
    public CarDTO save(CarDTO carDTO) {
        LOG.debug("Request to save Car : {}", carDTO);
        Car car = carMapper.toEntity(carDTO);
        car = carRepository.save(car);
        return carMapper.toDto(car);
    }

    public Car findOrSaveCar(Long carId, CarDTO carDTO) {
        if (carDTO != null) {
            return carMapper.toEntity(save(carDTO));
        } else {
            return carRepository.findByUserIsCurrentUserAndId(carId)
                    .orElseThrow(() -> new CarNotFoundException("Car with id " + carId + " was not found in the database"));
        }
    }

    /**
     * Update a car.
     *
     * @param carDTO the entity to save.
     * @return the persisted entity.
     */
    public CarDTO update(CarDTO carDTO) {
        LOG.debug("Request to update Car : {}", carDTO);
        Car car = carMapper.toEntity(carDTO);
        car = carRepository.save(car);
        return carMapper.toDto(car);
    }

    /**
     * Partially update a car.
     *
     * @param carDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CarDTO> partialUpdate(CarDTO carDTO) {
        LOG.debug("Request to partially update Car : {}", carDTO);

        return carRepository
            .findById(carDTO.getId())
            .map(existingCar -> {
                carMapper.partialUpdate(existingCar, carDTO);

                return existingCar;
            })
            .map(carRepository::save)
            .map(carMapper::toDto);
    }

    /**
     * Get all the cars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Cars");
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }

    /**
     * Get all the cars with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CarDTO> findAllWithEagerRelationships(Pageable pageable) {
        return carRepository.findAllWithEagerRelationships(pageable).map(carMapper::toDto);
    }

    /**
     * Get one car by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarDTO> findOne(Long id) {
        LOG.debug("Request to get Car : {}", id);
        return carRepository.findOneWithEagerRelationships(id).map(carMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Car : {}", id);
        carRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CarDTO> findAllByUser(Pageable pageable, String userLogin) {
        LOG.debug("Request to get all Cars for user: {}", userLogin);
        return carRepository.findAllByUser(pageable, userLogin).map(carMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CarDTO> findAllWithEagerRelationshipsByUser(Pageable pageable, String userLogin) {
        LOG.debug("Request to get all Cars with eager load for user: {}", userLogin);
        return carRepository.findAllWithEagerRelationshipsByUser(pageable, userLogin).map(carMapper::toDto);
    }

    public List<CarDTO> searchCars(String model, String make, String color, String registration, Long userId) {
        List<Car> cars = carRepository.searchCars(model, make, color, registration, userId);
        return cars.stream().map(carMapper::toDto).collect(Collectors.toList());
    }


}
