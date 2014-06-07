package pl.echoweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.echoweb.model.entity.TemplateEntity;

/**
 * Klasa implementujaca interfejs {@link ITemplateDAO}.
 * 
 * @author rafal.machnik
 * 
 */
@Repository("TemplateDAO")
public class TemplateDAO implements ITemplateDAO {

	@Autowired
	private SessionFactory mySessionFactory;

	@Override
	public TemplateEntity getTemplate(Long id) {
		Session session = mySessionFactory.getCurrentSession();
		TemplateEntity template = (TemplateEntity) session.get(
				TemplateEntity.class, id);
		return template;
	}

	@Override
	public Long addTemplate(TemplateEntity template) {
		Session session = mySessionFactory.getCurrentSession();
		return (Long) session.save(template);
	}

	@Override
	public List<TemplateEntity> getTemplates() {
		Session session = mySessionFactory.getCurrentSession();
		Criteria myCriteria = session.createCriteria(TemplateEntity.class);
		@SuppressWarnings("unchecked")
		List<TemplateEntity> list = myCriteria.list();
		return list;
	}

	@Override
	public void updateTemplate(TemplateEntity template) {
		Session session = mySessionFactory.getCurrentSession();
		session.update(template);
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
