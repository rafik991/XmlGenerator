package pl.echoweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.echoweb.dao.ITemplateDAO;
import pl.echoweb.model.dto.TemplateDTO;
import pl.echoweb.model.entity.TemplateEntity;

/**
 * Klasa implementujaca interfejs {@link ITemplateService}
 * 
 * @author rafal.machnik
 * 
 */
@Service("TemplateService")
@Transactional
public class TemplateService implements ITemplateService {

	@Autowired
	@Qualifier("TemplateDAO")
	ITemplateDAO myData;

	public void setDAO(ITemplateDAO dao) {
		this.myData = dao;
	}

	@Override
	public TemplateDTO getTemplate(Long id) {
		TemplateDTO templateDTO = new TemplateDTO();
		TemplateEntity templateEntity = myData.getTemplate(id);
		templateDTO.setId(templateEntity.getId());
		templateDTO.setTemplate(templateEntity.getTemplate());
		templateDTO.setTemplateName(templateEntity.getTemplateName());
		templateDTO.setExcel(templateEntity.getExcel());
		templateDTO.setHeader(templateEntity.getHeader());
		templateDTO.setFooter(templateEntity.getFooter());
		return templateDTO;
	}

	@Override
	public Long addTemplate(TemplateDTO template) {
		TemplateEntity templateEntity = new TemplateEntity();
		templateEntity.setExcel(template.getExcel());
		templateEntity.setTemplate(template.getTemplate());
		templateEntity.setTemplateName(template.getTemplateName());
		templateEntity.setHeader(template.getHeader());
		templateEntity.setFooter(template.getFooter());
		return myData.addTemplate(templateEntity);
	}

	@Override
	public List<TemplateDTO> getTemplates() {
		List<TemplateEntity> entityList = myData.getTemplates();
		List<TemplateDTO> dtoList = new ArrayList<TemplateDTO>();
		for (TemplateEntity ent : entityList) {
			TemplateDTO tmp = new TemplateDTO();
			tmp.setId(ent.getId());
			tmp.setTemplate(ent.getTemplate());
			tmp.setTemplateName(ent.getTemplateName());
			tmp.setExcel(ent.getExcel());
			tmp.setHeader(ent.getHeader());
			tmp.setFooter(ent.getFooter());
			dtoList.add(tmp);
		}
		return dtoList;
	}

	@Override
	public void updateTemplate(TemplateDTO templateDTO) {
		TemplateEntity templateEntity = new TemplateEntity();
		templateEntity.setId(templateDTO.getId());
		templateEntity.setTemplate(templateDTO.getTemplate());
		templateEntity.setTemplateName(templateDTO.getTemplateName());
		templateEntity.setExcel(templateDTO.getExcel());
		templateEntity.setHeader(templateDTO.getHeader());
		templateEntity.setFooter(templateDTO.getFooter());
		myData.updateTemplate(templateEntity);

	}

	@Override
	public void deleteTemplate(Long id) {
		myData.deleteTemplate(id);

	}

	@Override
	public String getHeader(Long id) {
		String header = myData.getHeader(id);
		return header;
	}

	@Override
	public String getFooter(Long id) {
		String footer = myData.getFooter(id);
		return footer;
	}

}
