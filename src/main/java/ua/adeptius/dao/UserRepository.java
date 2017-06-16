package ua.adeptius.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.adeptius.model.User;

import javax.annotation.Nonnull;
import java.util.List;


@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(@Nonnull User user) {
        getSession().save(user);
    }

    @Transactional
    public void update(User user) {
        getSession().update(user);
    }

  @Transactional
    public void remove(User user) {
        getSession().remove(user);
    }



    @Transactional
    public User findByName(String name) {
        return getSession().get(User.class, name);
    }

    @Transactional
    public User findByPassword(String pass){
        return (User) getSession().createQuery("from User where password = '"+pass+"'").uniqueResult();
    }

    @Transactional
    public List<User> findAll(){

        Query query = getSession().createQuery("select u from User u");

        List<User> list = query.list();
        return list;
    }
}
