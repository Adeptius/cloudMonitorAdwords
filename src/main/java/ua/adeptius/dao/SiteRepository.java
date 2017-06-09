package ua.adeptius.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.adeptius.model.Site;
import ua.adeptius.model.User;

import java.util.List;


@Repository
public class SiteRepository {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(Site site) {
        getSession().save(site);
    }

    @Transactional
    public Site findById(String id) {
        return getSession().get(Site.class, id);
    }

    @Transactional
    public List<Site> findAll(){
        Query query = getSession().createQuery("select s from Site s");
        List<Site> list = query.list();
        return list;
    }
}
