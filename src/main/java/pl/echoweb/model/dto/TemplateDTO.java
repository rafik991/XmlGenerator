package pl.echoweb.model.dto;

import pl.echoweb.model.entity.TemplateEntity;

/**
 * Data transfer object dla klasy {@link TemplateEntity}.
 * 
 * @author rafal.machnik
 * 
 */
public class TemplateDTO {

	private Long id;
	private byte[] template;
	private String templateName;
	private byte[] excel;
	private String header;
	private String footer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getTemplate() {
		return template;
	}

	public void setTemplate(byte[] bs) {
		this.template = bs;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public byte[] getExcel() {
		return excel;
	}

	public void setExcel(byte[] excel) {
		this.excel = excel;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

}
