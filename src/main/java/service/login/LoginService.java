package service.login;

import model.User;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static model.User.QUERY_FIND_USER;
import static model.User.QUERY_GET_PASSWORD_FROM_USERNAME;
import static service.login.LoginResult.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LoginService {

    @PersistenceContext(unitName = "userUnit")
    private EntityManager entityManager;
    private User user;

    LoginService() {

    }

    public LoginResult execute() {
        if (dataIsNull()) {
            return LoginResult.failure(NULL_DATA);
        }

        if (dataIsEmpty()) {
            return LoginResult.failure(EMPTY_DATA);
        }

        if (notRegistered()) {
            return LoginResult.failure(NOT_REGISTERED);
        }

        if (wrongPassword()) {
            return LoginResult.failure(WRONG_PASSWORD);
        }

        return LoginResult.success(SUCCESS_LOGIN);
    }

    private boolean dataIsNull() {
        return user == null || user.getUsername() == null || user.getPassword() == null;
    }

    private boolean dataIsEmpty() {
        return user.getUsername().isBlank() || user.getPassword().isBlank();
    }

    private boolean notRegistered() {
        List<User> users = entityManager
                .createNamedQuery(QUERY_FIND_USER, User.class)
                .setParameter("username", user.getUsername())
                .getResultList();
        return users.isEmpty();
    }

    private boolean wrongPassword() {
        String password = (String)entityManager
                .createNamedQuery(QUERY_GET_PASSWORD_FROM_USERNAME)
                .setParameter("username", user.getUsername()).getSingleResult();
        return !password.equals(user.getPassword());
    }

    public void setUser(User user) {
        this.user = user;
    }

    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
