package dao;

import model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeDAO {

    Optional<Employee> create(Employee employee);

    Optional<Employee> readById(int id);

    List<Employee> readAll();

    Employee update(Employee employee);

    boolean IsEmployeeInBase(Employee employee);

    Optional<Employee> delete(Employee employee);
}
