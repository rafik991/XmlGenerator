package pl.asseco.echoweb.web.model;

public class TemplateListModel {

	private String details;
	private String edition;
	private String excel;
	private String generation;
	private Long id;
	private String name;
	private String delete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TemplateListModel(Long a, String m_name) {
		id = a;
		name = m_name;
		details = "<div class=\"btn\"><a href=\"templateDetails/" + a
				+ "\">Szczegoly</a></div>";
		edition = "<div class=\"btn\"><a href=\"editTemplate/" + a
				+ "\">Edycja</a></div>";
		excel = "<div class=\"btn\"><a href=\"getExcelTemplate/" + a
				+ "\">Pobierz Excel</a></div>";
		generation = "<div class=\"btn\"><a href=\"generateXml/" + a
				+ "\">Generuj Xml</a></div>";
		delete = "<div class=\"btn\"><a href=\"deleteTemplate/" + a
				+ "\">Usun Szablon</a></div>";

	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

}
