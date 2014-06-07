package pl.echoweb.web.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class TemplateModel {

	@NotEmpty(message = "nazwa nie moze byc pusta")
	private String templateName;
	private String template;
	private CommonsMultipartFile excelData;
	private CommonsMultipartFile fileData;
	private byte[] excel;
	private Long id;
	private String header;
	private String footer;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public CommonsMultipartFile getExcelData() {
		return excelData;
	}

	public void setExcelData(CommonsMultipartFile excelData) {
		this.excelData = excelData;
	}

	public byte[] getExcel() {
		return excel;
	}

	public void setExcel(byte[] excel) {
		this.excel = excel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
