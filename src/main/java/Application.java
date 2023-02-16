
import dao.EmployeeDAO;
import dao.impl.EmployeeDAOImpl;

import model.Employee;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        Employee employeeNew = new Employee("John", "Doe", "male", 57, 2);

        Employee employeeNewWithId=employeeDAO.create(employeeNew);
        System.out.println(employeeNewWithId);

        employeeNew.setAge(58);
        employeeDAO.update(employeeNew);

        Employee employeeById = employeeDAO.readById(4);
        System.out.println(employeeById);

        List<Employee> eList = employeeDAO.readAll();
        eList.forEach(System.out::println);

        employeeDAO.delete(employeeNew);

    }
}


