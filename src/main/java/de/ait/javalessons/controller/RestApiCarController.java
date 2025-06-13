package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class RestApiCarController {

    private List<Car> carList = new ArrayList<>();

    public RestApiCarController() {
        carList.addAll(List.of(
                new Car("1","Audi A4"),
                new Car("2", "BMW M5"),
                new Car("3", "Kia XCEED"),
                new Car("4", "Mazda 6"),
                new Car("5", "Mercedes Benz CLX"),
                new Car("6", "Skoda Octavia")
        ));
    }

    //@RequestMapping(value = "/cars", method = RequestMethod.GET)
    @GetMapping("/cars")
    private Iterable<Car> getCars() {
        log.info("Getting all cars");
        return carList;
    }

    @GetMapping("/cars/{id}")
    Optional<Car> getCarById(@PathVariable String id) {
        for(Car car: carList){
            if(car.getId().equals(id)){
                log.info("Car with id {} found", id);
                return Optional.of(car);
            }
        }
        log.info("Car with id {} not found", id);
        return Optional.empty();
    }

    @PostMapping("/cars")
    private Car postCar(@RequestBody Car car) {
        carList.add(car);
        log.info("Car with id {} posted", car.getId());
        return car;
    }

    @PutMapping("/cars/{id}")
    private ResponseEntity<Car> putCar(@PathVariable String id, @RequestBody Car car) {
        int carIndex = -1;
        for(Car carInList: carList){
            if(carInList.getId().equals(id)){
                carIndex = carList.indexOf(carInList);
                carList.set(carIndex, car);
                log.info("Car with id {} updated", id);
            }
        }
        return (carIndex == -1) ? new ResponseEntity<>(postCar(car), HttpStatus.CREATED)
                : new ResponseEntity<>(car, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    private void deleteCar(@PathVariable String id) {
        carList.removeIf(car -> car.getId().equals(id));
        log.info("Car with id {} deleted", id);
    }

}
