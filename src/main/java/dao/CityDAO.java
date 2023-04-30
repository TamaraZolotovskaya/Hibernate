package dao;

import model.City;

import java.util.List;
import java.util.Optional;

public interface CityDAO {

    Optional<City> create(City city);

    boolean IsCityInBase(City city);

    Optional<City> delete(City city);

    void update(City city);

    Optional<City> readById(int id);

    List<City> readAll();
}
