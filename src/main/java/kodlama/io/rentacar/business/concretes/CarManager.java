package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.dto.requests.create.CreateCarRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateCarResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllCarsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateCarResponse;
import kodlama.io.rentacar.entities.Car;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private final CarRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<GetAllCarsResponse> getAll(boolean showMaintenance) {
        List<Car> cars = repository.findAll();
        cars= checkIfWithoutMaintenanceIsTrue(cars,showMaintenance);
        List<GetAllCarsResponse> response = cars
                .stream()//map diye bir fonksiyon kullanmamızı sağlıyor
                .map(car ->  mapper.map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }
    @Override
    public GetCarResponse getById(int id) {
        Car car = repository.findById(id).orElseThrow();
        GetCarResponse response = mapper.map(car, GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        Car car = mapper.map(request, Car.class);
        //requestteki bilgileri brand classına  dönüştür ***newlemedik
        car.setId(0); //başka id lerle karıştırmasın
        repository.save(car);
        CreateCarResponse response = mapper.map(car, CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {

        Car car = mapper.map(request, Car.class);
        car.setId(id);
        repository.save(car);
        UpdateCarResponse response = mapper.map(car, UpdateCarResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);

    }

    private  List<Car> checkIfWithoutMaintenanceIsTrue(List<Car> cars, boolean isMaintenance) {
        List<Car> cars1 = new ArrayList<>();
        for (Car car:cars) {
            cars1.add(car);
        }
        if (isMaintenance) {
            for (Car car : cars) {
                if (car.getState() == State.MAINTENANCE) {
                    cars1.remove(car);
                }
            }
        }
        return cars1;
    }






}