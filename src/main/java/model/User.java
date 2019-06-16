package model;

import javax.persistence.*;
import java.util.Objects;

import static model.User.QUERY_FIND_USER;
import static model.User.QUERY_GET_PASSWORD_FROM_USERNAME;

@Entity
@NamedQuery(
        name = QUERY_FIND_USER,
        query = "SELECT c FROM User c WHERE c.username = :username")
@NamedQuery(
        name = QUERY_GET_PASSWORD_FROM_USERNAME,
        query = "SELECT password FROM User c WHERE c.username = :username"
)
public class User {

    public static final String QUERY_FIND_USER = "findUserByName";
    public static final String QUERY_GET_PASSWORD_FROM_USERNAME = "getPasswordFromUsername";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
