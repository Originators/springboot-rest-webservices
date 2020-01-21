package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "user")
@Table(name = "user_details")
@JsonIgnoreProperties({"firstName", "lastName"})    // it is same as json ignore only diff is it is applied on class level
                                                    // and causes multiple properties to be json ignored
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "username is mandatory. Please provide a user name")
    private String userName;

    @Column(name = "first_name", length = 50, nullable = false)
    @Size(min = 2, message = "first name should have a min of 2 characters")
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    @Column(name = "SSN", length = 50, nullable = false, unique = true)
    @JsonIgnore // it is used to ignore the property from json, once this is done ssn part from json will be ignored
    // for put and post
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    // no-arg constructor

    public User() {
    }


    // fields constructor

    public User(String userName, String firstName, String lastName, String email, String role, String ssn) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.ssn = ssn;
    }


    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // to_string


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}
