package service.registration;

import model.User;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static model.User.QUERY_FIND_USER;
import static service.registration.RegistrationResult.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RegistrationService {

    @PersistenceContext(unitName = "userUnit")
    private EntityManager entityManager;
    private User user;

    public RegistrationService() {

    }

    public RegistrationResult execute() {
        if (dataIsNull()) {
            return RegistrationResult.failure(NULL_DATA);
        }

        if (dataIsEmpty()) {
            return RegistrationResult.failure(EMPTY_DATA);
        }

        if(alreadyRegistered()) {
            return RegistrationResult.failure(ALREADY_REGISTERED);
        }

        registerUser();
        return RegistrationResult.success(SUCCESS_REGISTRATION);
    }

    private boolean dataIsNull() {
        return user == null || user.getUsername() == null || user.getPassword() == null;
    }

    private boolean dataIsEmpty() {
        return user.getUsername().isBlank() || user.getPassword().isBlank();
    }

    private boolean alreadyRegistered() {
        List<User> users = entityManager
                .createNamedQuery(QUERY_FIND_USER, User.class)
                .setParameter("username", user.getUsername())
                .getResultList();
        return !users.isEmpty();
    }

    private void registerUser() {
        entityManager.persist(user);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
