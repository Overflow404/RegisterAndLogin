package service.login;

import model.User;
import org.junit.*;
import service.registration.RegistrationService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static service.login.LoginResult.*;

public class LoginServiceTest {

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
    public void nullDataLoginTest() {
        LoginService service = new LoginService();

        LoginResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(NULL_DATA, result.getFailureReason());
    }

    @Test
    public void emptyDataLoginTest() {
        User emptyUser = new User("", "");

        LoginService service = new LoginService();
        service.setUser(emptyUser);

        LoginResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(EMPTY_DATA, result.getFailureReason());
    }

    @Test
    public void requireRegistrationToLoginTest() {
        User user = new User("inhexistentUser", "123456");

        LoginService service = new LoginService();
        service.setUser(user);
        service.setEntityManager(entityManager);

        LoginResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(NOT_REGISTERED, result.getFailureReason());
    }

    @Test
    public void wrongPasswordTest() {
        User user = new User("admin", "admin");

        registerUser(user);

        User userWithWrongPassword = new User("admin", "wrongPassword");

        LoginService service = new LoginService();
        service.setUser(userWithWrongPassword);
        service.setEntityManager(entityManager);

        LoginResult result = service.execute();

        Assert.assertFalse(result.success());
        Assert.assertEquals(WRONG_PASSWORD, result.getFailureReason());
    }

    @Test
    public void successfulLoginTest() {
        User user = new User("admin1", "admin1");
        registerUser(user);

        LoginService service = new LoginService();
        service.setUser(user);
        service.setEntityManager(entityManager);

        LoginResult result = service.execute();

        Assert.assertTrue(result.success());
        Assert.assertEquals(SUCCESS_LOGIN, result.getContent());
    }

    private void registerUser(User user) {
        RegistrationService service = new RegistrationService();
        service.setUser(user);
        service.setEntityManager(entityManager);

        entityManager.getTransaction().begin();
        service.execute();
        entityManager.getTransaction().commit();
    }

}
