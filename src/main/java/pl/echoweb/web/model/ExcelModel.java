package pl.echoweb.web.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ExcelModel {

	@NotEmpty
	private String excelName;
	private String excel;
	private CommonsMultipartFile fileData;

	public String getExelName() {
		return excelName;
	}

	public void setExcelName(String exelName) {
		this.excelName = exelName;
	}

	public String getExel() {
		return excel;
	}

	public void setExel(String exel) {
		this.excel = exel;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

}
