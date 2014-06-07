package pl.asseco.echoweb.dao;

import java.util.List;

import pl.asseco.echoweb.model.entity.TemplateEntity;

/**
 * Interfejs osblugujacy {@link TemplateEntity}.
 * 
 * @author rafal.machnik
 * 
 */
public interface ITemplateDAO {

	/**
	 * Metoda pobiera szablon po Id.
	 * 
	 * @param id
	 * @return encje pobranego szablonu.
	 */
	public TemplateEntity getTemplate(Long id);

	/**
	 * Metoda dodaje szablon do bazy.
	 * 
	 * @param template
	 * @return id dodanego szablonu
	 */
	public Long addTemplate(TemplateEntity template);

	/**
	 * Metoda zwraca liste dostepnych szablonow.
	 * 
	 * @return
	 */
	public List<TemplateEntity> getTemplates();

	/**
	 * Metoda aktualizujaca szablon.
	 * 
	 * @param template
	 * 
	 */
	public void updateTemplate(TemplateEntity template);

	/**
	 * Metoda usuwajaca dany szablon.
	 * 
	 * @param id
	 */
	public void deleteTemplate(Long id);

	/**
	 * Metoda zwracajaca Header szablonu.
	 * 
	 * @param id
	 * @return
	 */
	public String getHeader(Long id);

	/**
	 * Metoda zwracajaca Footer szablonu.
	 * 
	 * @param id
	 * @return
	 */
	public String getFooter(Long id);

}
