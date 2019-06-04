package ma.shop.database.model;

import ma.shop.utils.RandomGenerator;
import ma.shop.utils.SHA512SecureUtil;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String email, String password, String address, Role role) {
        this.email = email;
        this.salt = RandomGenerator.randomSalt();
        this.password = SHA512SecureUtil.getSecurePassword(password, this.salt);
        this.address = address;
        this.order = new Order(this);
        if (role == null) {
            this.role = Role.USER;
        } else {
            this.role = role;
        }
    }

    public User(long id, String email, String password, String address, Order order, Role role, String salt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.order = order;
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

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

}
