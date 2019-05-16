package ma.shop.database.model;

import ma.shop.utils.RandomGenerator;

import java.util.Objects;

public class User {
    private long id;
    private String email;
    private String password;
    private String salt;
    private String address;
    private int good;
    private Role role;

    public User(String email, String password, String address, Role role) {
        this.email = email;
        this.password = password;
        this.salt = RandomGenerator.randomSalt();
        this.address = address;

        if (role == null) {
            role = Role.USER;
        }
        this.role = role;
    }

    public User(long id, String email, String password, String address, int good, Role role, String salt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.good = good;
        this.role = role;
        this.salt = salt;
    }

    public User(long id, String email, String password, String address, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                good == user.good &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(address, user.address) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, salt, address, good, role);
    }
}
