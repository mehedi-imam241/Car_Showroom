package Connection.Manufacturer;

import Entity.CarEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ManufacturerTask {
    public ManufacturerTask() {

    }

    public boolean addCar(String reg, String make, String model, int yearmade, String color, int price, String imageloc) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        CarEntity c1 = entityManager.find(CarEntity.class, reg);
        if (c1 != null) {
            entityManager.getTransaction().commit();
            return false;
        }

        entityManager.persist(new CarEntity(reg, make, model, yearmade, color, price, imageloc));
        entityManager.getTransaction().commit();
        return true;
    }

    public List<CarEntity> getAllCar() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<CarEntity> cars = entityManager.createNativeQuery("SELECT * FROM carentity", CarEntity.class).getResultList();

        entityManager.getTransaction().commit();

        return cars;
    }

    public CarEntity findCarWithReg(String reg) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        CarEntity c = entityManager.find(CarEntity.class, reg);
        entityManager.getTransaction().commit();
        return c;
    }

    public List<CarEntity> findCarWithMakeAndModel(String make, String model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        List<CarEntity> cars = entityManager.createNativeQuery("SELECT * FROM carentity WHERE BINARY make ='" + make + "' and BINARY model = '" + model + "'", CarEntity.class).getResultList();
        entityManager.getTransaction().commit();

        return cars;
    }

    public boolean updateCar(String reg, String newReg, String make, String model, int yearmade, String color, int price, String imageloc) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        CarEntity c = entityManager.find(CarEntity.class, reg);
        if (c == null) {
            entityManager.getTransaction().commit();
            return false;
        }
        entityManager.remove(c);
        entityManager.persist(new CarEntity(newReg, make, model, yearmade, color, price, imageloc));
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean deleteCar(String reg) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("carshowroom");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        CarEntity c = entityManager.find(CarEntity.class, reg);

        if (c == null) {
            entityManager.getTransaction().commit();
            return false;
        } else {
            entityManager.remove(c);
            entityManager.getTransaction().commit();
            return true;
        }
    }
}
