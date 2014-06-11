package pl.echoweb.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.echoweb.model.entity.TemplateEntity;
import pl.echoweb.util.CachedProvider;

import java.util.List;

/**
 * Klasa implementujaca interfejs {@link ITemplateDAO}.
 *
 * @author rafal.machnik
 */
@Repository("TemplateDAO")
public class TemplateDAO implements ITemplateDAO {

    @Autowired
    private SessionFactory mySessionFactory;

    @Autowired
    CachedProvider memCachedProvider;

    @Override
    public TemplateEntity getTemplate(Long id) {
        Session session = mySessionFactory.getCurrentSession();
        TemplateEntity template = (TemplateEntity) memCachedProvider.getObjectFromMemCache(String.valueOf(id));
        if (template == null)
            template = (TemplateEntity) session.get(
                    TemplateEntity.class, id);
        return template;
    }

    @Override
    public Long addTemplate(TemplateEntity template) {
        Session session = mySessionFactory.getCurrentSession();
        final Long id = (Long) session.save(template);
        memCachedProvider.storeObjectInMemCache(id.toString(), template);
        return id;
    }

    @Override
    public List<TemplateEntity> getTemplates() {
        Session session = mySessionFactory.getCurrentSession();
        Criteria myCriteria = session.createCriteria(TemplateEntity.class);
        List<TemplateEntity> list = (List<TemplateEntity>) memCachedProvider.getObjectFromMemCache("templateEntityList");
        if (list == null || list.isEmpty())
            list = myCriteria.list();
        return list;
    }

    @Override
    public void updateTemplate(TemplateEntity template) {
        Session session = mySessionFactory.getCurrentSession();
        session.update(template);
        memCachedProvider.storeObjectInMemCache(template.getId().toString(), template);
    }

    @Override
    public void deleteTemplate(Long id) {
        Session session = mySessionFactory.getCurrentSession();
        TemplateEntity ent = getTemplate(id);
        session.delete(ent);
    }

    @Override
    public String getHeader(Long id) {
        Session session = mySessionFactory.getCurrentSession();
        TemplateEntity template = (TemplateEntity) session.get(
                TemplateEntity.class, id);
        return template.getHeader();
    }

    @Override
    public String getFooter(Long id) {
        Session session = mySessionFactory.getCurrentSession();
        TemplateEntity template = (TemplateEntity) session.get(
                TemplateEntity.class, id);
        return template.getFooter();
    }
}
