package com.appdirect.integration.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User extends AbstractModel<User> {
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String openId;
    @Basic
    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @Basic
    @Column
    @JsonIgnore
    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
