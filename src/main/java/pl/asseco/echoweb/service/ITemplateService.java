package pl.asseco.echoweb.service;

import java.util.List;

import pl.asseco.echoweb.model.dto.TemplateDTO;

/**
 * Interfejs uslugi konwersji plikow xls do xml.
 * 
 * @author rafal.machnik
 * 
 */
public interface ITemplateService {

	/**
	 * Pobiera szablon po id.
	 * 
	 * @param id
	 *            szablonu do pobrania
	 * @return obiekt {@link TemplateDTO}
	 */
	public TemplateDTO getTemplate(Long id);

	/**
	 * Dodaje szablon bo bazy
	 * 
	 * @param template
	 *            obiekt klasy {@link TemplateDTO}
	 * @return id obiektu dodanego
	 */
	public Long addTemplate(TemplateDTO template);

	/**
	 * Metoda zwraca wszystkie szablony.
	 * 
	 * @return lista szablonow typu {@link TemplateDTO}
	 */
	public List<TemplateDTO> getTemplates();

	/**
	 * Aktualizuje szablon.
	 * 
	 * @param templateDTO
	 */
	public void updateTemplate(TemplateDTO templateDTO);

	/**
	 * Metoda usuwajaca szablon.
	 * 
	 * @param id
	 */
	public void deleteTemplate(Long id);

	/**
	 * Metoda zwracajaca Header.
	 * 
	 * @param id
	 * @return
	 */
	public String getHeader(Long id);

	/**
	 * Metoda zwracajaca Footer.
	 * 
	 * @param id
	 * @return
	 */
	public String getFooter(Long id);

}
