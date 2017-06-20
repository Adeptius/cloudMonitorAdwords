package ua.adeptius.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.adeptius.model.PendingUser;

import java.util.List;


@Repository
public class PendingUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(PendingUser user) {
        getSession().save(user);
    }

    @Transactional
    public void saveOrUpdate(PendingUser user) {
        getSession().saveOrUpdate(user);
    }

    @Transactional
    public void remove(PendingUser user) {
        getSession().remove(user);
    }

    @Transactional
    public PendingUser findByKey(String key) {
        return (PendingUser) getSession().createQuery("from PendingUser where hash = '" + key + "'").uniqueResult();
    }

    @Transactional
    public List<PendingUser> findAll() {
        Query query = getSession().createQuery("select p from PendingUser p");

        List<PendingUser> list = query.list();
        return list;
    }
}
