package Connection.Admin;

import Entity.ProfileEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AdminTask {
    public AdminTask() {
    }

    public boolean updateUser(String name,String newName, String newPassword) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manufacturer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        ProfileEntity p1 = entityManager.find(ProfileEntity.class, name);
        if (p1 == null) {
            entityManager.getTransaction().commit();
            return false;
        } else {
            ProfileEntity p2 = new ProfileEntity();
            p2.setName(newName);
            p2.setPassword(newPassword);
            entityManager.remove(p1);
            entityManager.persist(p2);
            entityManager.getTransaction().commit();
        }
        return true;
    }

    public List<ProfileEntity> getAllUser() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manufacturer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<ProfileEntity> profiles = entityManager.createNativeQuery("SELECT * FROM profileentity", ProfileEntity.class).getResultList();

        entityManager.getTransaction().commit();

        return profiles;
    }

    public boolean deleteUser(String Name) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manufacturer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        ProfileEntity profile = entityManager.find(ProfileEntity.class, Name);

        if (profile == null) {
            entityManager.getTransaction().commit();
            return false;
        } else {
            entityManager.remove(profile);
            entityManager.getTransaction().commit();
            return true;
        }
    }
    public boolean findUser(String Name)
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manufacturer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        ProfileEntity profile = entityManager.find(ProfileEntity.class, Name);
        entityManager.getTransaction().commit();

        if (profile == null) {
            return false;
        } else {
            return true;
        }
    }
    public boolean addUser(String name, String password) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manufacturer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        ProfileEntity p1 = entityManager.find(ProfileEntity.class, name);
        if (p1 != null) {
            entityManager.getTransaction().commit();
            return false;
        }

        entityManager.persist(new ProfileEntity(name, password));
        entityManager.getTransaction().commit();
        return true;
    }
}
