package ua.adeptius.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sites")
public class Site implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "login", insertable = false, updatable = false)
    private String login;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "login")
    private User user;

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", url='" + url + '\'' +
                ", user=" + (user!=null?user.getLogin():"NULL!!!") +
                '}';
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String userName) {
        this.login = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
