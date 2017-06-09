package ua.adeptius.model;


import ua.adeptius.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pending_user")
public class PendingUser implements Serializable {

    public PendingUser() {
    }

    public PendingUser(String login, String password, String email, int checkDelay) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.checkDelay = checkDelay;
        key = StringUtils.generateRandomKey();
        date = new Date();
    }

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "hash")
    private String key;

    @Column(name = "date")
    private Date date;

    @Column(name = "checkDelay")
    private int checkDelay;



    @Column(name = "emailDelay")
    private int emailDelay;



    @Override
    public String toString() {
        return "PendingUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", key='" + key + '\'' +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PendingUser that = (PendingUser) o;

        return login != null ? login.equals(that.login) : that.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
