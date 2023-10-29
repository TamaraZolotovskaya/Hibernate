import dao.CityDAO;
import dao.EmployeeDAO;
import dao.impl.CityDAOimpl;
import dao.impl.EmployeeDAOImpl;
import model.City;
import model.Employee;

import java.util.List;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        CityDAO cityDAO = new CityDAOimpl();

        City tomsk = new City(1, "Omsk2");
        Employee employeeNew = new Employee("John", "Smith", "male", 57, tomsk);

        Optional<Employee> employeeNewWithId = employeeDAO.create(employeeNew);
        System.out.println(employeeNewWithId.orElse(employeeNew));

        if (employeeNewWithId.isPresent()) {
            employeeNewWithId.get().setAge(58);
            employeeDAO.update(employeeNewWithId.get());
        }

        Optional<Employee> employeeById = employeeDAO.readById(4);
        System.out.println(employeeById);

        List<Employee> eList = employeeDAO.readAll();
        eList.forEach(System.out::println);

        employeeDAO.delete(employeeNew);

        City omsk = new City("Omsk");
        Optional<City> cityNewWithId = cityDAO.create(omsk);
        System.out.println(cityNewWithId);

        omsk.setCityName("Kazan");
        cityDAO.update(omsk);

        Optional<City> cityById = cityDAO.readById(1);
        System.out.println(cityById);

        List<City> cityList = cityDAO.readAll();
        cityList.forEach(System.out::println);

        cityDAO.delete(omsk);

        //Проверяю работу полной каскадности

        City moscow = new City(2, "Moscow");
        Optional<City> moskowWithId = cityDAO.create(moscow);
        Employee test = new Employee("test", "test", "male", 39, moscow);
        Optional<Employee> testWithId = employeeDAO.create(test);
        System.out.println(testWithId);
        moscow.setCityName("Vasuki");
        cityDAO.update(moscow);
        System.out.println(testWithId);
        cityDAO.delete(moscow);


    }
}


