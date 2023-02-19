package dao.impl;

import dao.EmployeeDAO;
import config.HibernateSessionFactoryUtil;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public Optional<Employee> create(Employee employee) {
        if (IsEmployeeInBase(employee)) {
            // В ресурсах блока try создаем объект сессии с помощью нашего конфиг-файл и открываем сессию
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
                // Создаем транзакцию и начинаем ее
                Transaction transaction = session.beginTransaction();
                // вызываем на объекте сессии метод save
                // данный метод внутри себя содержит необходимый запрос к базе для создания новой строки
                Serializable id = session.save(employee);
                Employee employeeNew = session.get(Employee.class, id);
                // Выполняем коммит, то есть сохраняем изменения,которые совершили в рамках транзакции
                transaction.commit();
                return Optional.of(employeeNew);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean IsEmployeeInBase(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery
                    ("from Employee where firstName = :employeeFirstName " +
                            "and lastName = :employeeLastName and gender = :employeeGender " +
                            "and age = :employeeAge and city = :employeeCity");
            query.setParameter("employeeFirstName", employee.getFirstName())
                    .setParameter("employeeLastName", employee.getLastName())
                    .setParameter("employeeGender", employee.getGender())
                    .setParameter("employeeAge", employee.getAge())
                    .setParameter("employeeCity", employee.getCity());
            List theSame = query.list();
            if (theSame.size() > 0) {
                return true;
            } else return false;
        }
    }

    @Override
    public Optional<Employee> delete(Employee employee) {
        if (readById(employee.getId()).isPresent()) {
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                // Для удаления объекта из таблицы нужно передать его в метод delete
                session.delete(employee);
                transaction.commit();
                return Optional.of(employee);
            }
        } else return Optional.empty();
    }

    @Override
    public Employee update(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // Для обновления данных нужно передать в конструктор
            // объект с актуальными данными
            session.update(employee);
            transaction.commit();
            return employee;
        }
    }


    @Override
    public Optional<Employee> readById(int id) {
        // С помощью конфиг-файла получаем сессию, открываем ее
        // и через метод get получаем объект
        // В параметре метода get нужно указать объект какого класса нам нужен и его id
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Employee.class, id));
        }
    }


    @Override
    public List<Employee> readAll() {
        List<Employee> employeeList =
                (List<Employee>) HibernateSessionFactoryUtil.getSessionFactory()
                        .openSession()
                        .createQuery("From Employee ").list();
        return employeeList;
    }


}
