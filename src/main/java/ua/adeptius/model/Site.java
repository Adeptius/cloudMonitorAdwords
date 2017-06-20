package ua.adeptius.model;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "sites")
public class Site implements Serializable {


    public Site() {
    }

    public Site(@Nonnull String login, @Nonnull String url, @Nonnull User user) {
        this.login = login;
        this.url = url;
        this.user = user;
        checkDelay = 20;
        triesToEmail = 1;
    }

    public Site(@Nonnull String login, @Nonnull String url, @Nonnull User user, int checkDelay, int triesToEmail) {
        this.login = login;
        this.url = url;
        this.user = user;
        this.checkDelay = checkDelay;
        this.triesToEmail = triesToEmail;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "login", insertable = false, updatable = false)
    private String login;

    @Column(name = "url")
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "login")
    private User user;

    @Column(name = "checkDelay")
    private int checkDelay;

    @Column(name = "triesToEmail")
    private int triesToEmail;

    @Column(name = "lookingWord")
    private String lookingWord;

    @Transient
    AtomicInteger fails = new AtomicInteger(0);

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", url='" + url + '\'' +
                ", user=" + user.getLogin() +
                ", checkDelay=" + checkDelay +
                ", triesToEmail=" + triesToEmail +
                ", lookingWord='" + getLookingWord() + '\'' +
                '}';
    }




    @Nonnull
    public String getLookingWord() {
        return lookingWord == null? "NULL!!":lookingWord;
    }

    public void setLookingWord(@Nonnull String lookingWord) {
        this.lookingWord = lookingWord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nonnull
    public String getLogin() {
        return login;
    }

    public void setLogin(@Nonnull String userName) {
        this.login = userName;
    }

    @Nonnull
    public String getUrl() {
        return url==null? "NULL!!": url;
    }

    public void setUrl(@Nonnull String url) {
        this.url = url;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public void setUser(@Nonnull User user) {
        this.user = user;
    }

    public int getCheckDelay() {
        return checkDelay;
    }

    public void setCheckDelay(int checkDelay) {
        this.checkDelay = checkDelay;
    }

    public int getTriesToEmail() {
        return triesToEmail;
    }

    public void setTriesToEmail(int triesToEmail) {
        this.triesToEmail = triesToEmail;
    }
}
