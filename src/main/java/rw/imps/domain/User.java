package rw.imps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity(name="users")
public class User {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long userId;
    @Email(message = "Provided Email is invalid")
    private String email;
    private String phone;
    @NotBlank
    @NotEmpty(message = " Full Name should not be empty")
    private String fullName;

    @Transient
    private String userType;

    @NotEmpty(message = " User Name should not be empty")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "Password can not be null")
    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoleList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(userType, user.userType) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, phone, fullName, userType, username, password);
    }
}
