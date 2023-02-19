package dao.impl;

import config.HibernateSessionFactoryUtil;
import dao.CityDAO;
import model.City;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CityDAOimpl implements CityDAO {

    @Override
    public Optional<City> create(City city) {
        if (IsCityInBase(city)) {
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
                Transaction transaction = session.beginTransaction();
                Serializable id = session.save(city);
                City cityNew = session.get(City.class, id);
                transaction.commit();
                return Optional.of(cityNew);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean IsCityInBase(City city) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery
                    ("from City where cityName = :cityName");
            query.setParameter("cityName", city.getCityName());
            List theSame = query.list();
            if (theSame.size() > 0) {
                return true;
            } else return false;
        }
    }

    @Override
    public Optional<City> delete(City city) {
        if (readById(city.getCityId()).isPresent()) {
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                city = session.load(City.class, city.getCityId());
                session.delete(city);
                transaction.commit();
                return Optional.of(city);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(City city) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            city = session.load(City.class, city.getCityId()); //чтобы работала каскадность
            session.update(city);
            transaction.commit();
        }
    }


    @Override
    public Optional<City> readById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()) {
            return Optional.ofNullable(session.get(City.class, id));
        }
    }


    @Override
    public List<City> readAll() {
        List<City> cityList =
                (List<City>) HibernateSessionFactoryUtil.getSessionFactory()
                        .openSession()
                        .createQuery("From City ").list();
        return cityList;
    }
}
