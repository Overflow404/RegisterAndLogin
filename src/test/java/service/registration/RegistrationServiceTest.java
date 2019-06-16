package service.registration;

import model.User;
import org.junit.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static model.User.QUERY_FIND_USER;
import static service.registration.RegistrationResult.*;

public class RegistrationServiceTest {

    private static EntityManagerFactory entityManagerFactory;
    private final static String PERSISTENCE_UNIT = "testUnit";
    private EntityManager entityManager;

    @BeforeClass
    public static void setupClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @Before
    public void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void teardown() {
        entityManager.close();
    }

    @AfterClass
    public static void teardownClass() {
        entityManagerFactory.close();
    }

    @Test
    public void nullDataRegistrationTest() {
        RegistrationService service = new RegistrationService();

        RegistrationResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(NULL_DATA, result.getFailureReason());
    }

    @Test
    public void emptyDataRegistrationTest() {
        User emptyUser = new User("", "");

        RegistrationService service = new RegistrationService();
        service.setUser(emptyUser);

        RegistrationResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(EMPTY_DATA, result.getFailureReason());
    }

    @Test
    public void alreadyRegisteredTest() {
        String username = "admin";
        String password = "admin";
        User user = new User(username, password);

        RegistrationService service = new RegistrationService();
        service.setUser(user);
        service.setEntityManager(entityManager);

        entityManager.getTransaction().begin();
        RegistrationResult result = service.execute();
        entityManager.getTransaction().commit();

        Assert.assertTrue(result.success());
        Assert.assertEquals(SUCCESS_REGISTRATION, result.getContent());

        List<User> users = entityManager
                .createNamedQuery(QUERY_FIND_USER, User.class)
                .setParameter("username", user.getUsername())
                .getResultList();

        User actualUser = users.get(0);
        Assert.assertEquals(user.getUsername(), actualUser.getUsername());

        entityManager.getTransaction().begin();
        result = service.execute();
        entityManager.getTransaction().commit();

        Assert.assertFalse(result.success());
        Assert.assertEquals(ALREADY_REGISTERED, result.getFailureReason());
    }

    @Test
    public void successfulRegistrationTest() {
        String username = "admin1";
        String password = "admin1";
        User user = new User(username, password);

        RegistrationService service = new RegistrationService();
        service.setUser(user);
        service.setEntityManager(entityManager);

        entityManager.getTransaction().begin();
        RegistrationResult result = service.execute();
        entityManager.getTransaction().commit();
        Assert.assertTrue(result.success());

        List<User> users = entityManager
                .createNamedQuery(QUERY_FIND_USER, User.class)
                .setParameter("username", user.getUsername())
                .getResultList();

        User actualUser = users.get(0);

        Assert.assertEquals(user.getUsername(), actualUser.getUsername());
        Assert.assertEquals(SUCCESS_REGISTRATION, result.getContent());
    }
}
