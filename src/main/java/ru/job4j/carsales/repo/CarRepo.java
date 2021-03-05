package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import ru.job4j.carsales.model.Car;

@RequiredArgsConstructor(staticName = "of")
class CarRepo {
    private final Store store;

    Car createOrGet(Car car) {
        return store.findOrCreate(Car.class, car, "car_vin", car.getVin());
    }
}
