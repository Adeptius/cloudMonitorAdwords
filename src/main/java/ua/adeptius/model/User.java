package ua.adeptius.model;


import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public User() {
    }

    public User(@Nonnull String login, @Nonnull String password, @Nonnull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "login", referencedColumnName = "login")
    private List<Site> sites;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", sites=" + sites +
                '}';
    }

    @Nonnull
    public String getLogin() {
        return login;
    }

    public void setLogin(@Nonnull String login) {
        this.login = login;
    }

    @Nonnull
    public List<Site> getSites() {
        return sites==null? new ArrayList<>(): sites;
    }

    public void setSites(@Nonnull List<Site> sites) {
        this.sites = sites;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nonnull String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull String email) {
        this.email = email;
    }
}
