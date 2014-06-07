package pl.asseco.echoweb.web.model.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.asseco.echoweb.web.model.TemplateModel;

public class GenerateValidator implements Validator {

	String feach = "";

	@Override
	public boolean supports(Class<?> clazz) {
		return TemplateModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TemplateModel model = (TemplateModel) target;

		if (model.getExcelData().isEmpty()) {
			errors.rejectValue("excelData", "excelData.Empty",
					"Brak pliku Excela");
		}
		if (!model.getExcelData().getOriginalFilename().contains(".xls")
				&& !model.getExcelData().getOriginalFilename().contains(".csv")) {
			errors.rejectValue("excelData", "excelData.WrongFormat",
					"Niewłaściwy format Excela dopuszczalny jedynie .xls lub .csv");
		} else {
			if (model.getExcelData().getOriginalFilename().contains(".xls")) {
				if (model.getExcelData().getSize() > new Long(45000000)) {
					errors.rejectValue("excelData", "excelData.TooBig",
							"Plik Excela jest za duzy!");
				} else {

					if (!model.getExcelData().isEmpty()
							&& !model.getTemplate().isEmpty()) {
						String veloContent = new String(model.getTemplate());
						try {
							List<String> mes = validateExcel(model
									.getExcelData().getInputStream(),
									veloContent);
							String all = "";
							if (mes.size() > 0) {

								for (String s : mes)
									all += s
											+ System.getProperty("line.separator");
								errors.rejectValue("excel", "excelNotMatch",
										all);
							}

						} catch (BiffException e) {
							errors.rejectValue("excel", "parseExcelFail",
									"Bład parsowania excela.");
							e.printStackTrace();
						} catch (IOException e) {
							errors.rejectValue("excel", "parseExcelFail",
									"Błąd parsowania excela.");
							e.printStackTrace();
						}
					}
				}
			} else if (model.getExcelData().getOriginalFilename()
					.contains(".csv")) {
				if (!model.getExcelData().isEmpty()
						&& !model.getTemplate().isEmpty()) {
					String veloContent = new String(model.getTemplate());
					try {
						List<String> mes = validateCsv(model.getExcelData()
								.getInputStream(), veloContent);
						String all = "";
						if (mes.size() > 0) {

							for (String s : mes)
								all += s + System.getProperty("line.separator");
							errors.rejectValue("excel", "excelNotMatch", all);
						}

					} catch (IOException e) {
						errors.rejectValue("excel", "parseExcelFail",
								"Błąd parsowania CSV.");
						e.printStackTrace();
					}
				}
			}
		}
	}

	public List<String> validateExcel(InputStream xls, String template)
			throws BiffException, IOException {
		Workbook workbook = null;
		Sheet sheet;
		List<String> headers;
		try {

			// inicjalizacja
			workbook = Workbook.getWorkbook(xls);
			sheet = workbook.getSheet(0);
			headers = new ArrayList<String>();

			// wypelnainie naglowkow
			for (int i = 0; i < sheet.getColumns(); i++) {
				headers.add(sheet.getCell(i, 0).getContents());
			}

			String[] lines = template.split(System
					.getProperty("line.separator"));

			List<String> velocity = new ArrayList<String>();
			for (String st : lines) {
				if (st.contains("$"))
					velocity.add(st);
			}

			List<String> errors = new ArrayList<String>();

			// gdy ilosc naglowkow excela jest mniejsza
			if (headers.size() <= velocity.size()) {
				String[] headers1 = new String[velocity.size()];
				for (int k = 0; k < headers.size(); k++) {
					headers1[k] = headers.get(k);
				}
				if (headers.size() < velocity.size()) {
					errors.add("Zbyt mało nagłówków w excelu. ");
				}
				for (int k = 0; k < headers.size(); k++) {
					if (!velocity.get(k).contains(headers1[k])) {
						errors.add("Brak zgodności przy nagłówku: "
								+ headers1[k] + "! ");

					}
				}
				return errors;
			}// ilosc naglowkow excela jest wieksza
			else {
				for (int k = 0; k < velocity.size(); k++) {
					if (!velocity.get(k).contains(headers.get(k))) {
						errors.add("Brak zgodności przy nagłówku: "
								+ headers.get(k) + "! ");
					}
				}
				return errors;

			}
		} finally {
			if (workbook != null)
				workbook.close();

		}

	}

	public static List<String> validateCsv(InputStream csv, String template)
			throws IOException {
		BufferedReader reader = null;
		List<String> headers;
		try {
			reader = new BufferedReader(new InputStreamReader(csv));
			String headerLine = reader.readLine();

			headers = new ArrayList<String>();
			if (headerLine != null) {
				String[] hD = headerLine.split(";");
				for (int i = 0; i < hD.length; i++) {
					headers.add(hD[i]);
				}
			}
			String[] lines = template.split(System
					.getProperty("line.separator"));
			List<String> velocity = new ArrayList<String>();
			for (String st : lines) {
				if (st.contains("$"))
					velocity.add(st);
			}
			List<String> errors = new ArrayList<String>();
			// gdy ilosc naglowkow excela jest mniejsza
			if (headers.size() <= velocity.size()) {
				String[] headers1 = new String[velocity.size()];
				for (int k = 0; k < headers.size(); k++) {
					headers1[k] = headers.get(k);
				}
				if (headers.size() < velocity.size()) {
					errors.add("Zbyt mało nagłówków w excelu. ");
				}
				for (int k = 0; k < headers.size(); k++) {
					if (!velocity.get(k).contains("$"+headers1[k])) {
						errors.add("Brak zgodności przy nagłówku: "
								+ headers1[k] + "! ");

					}
				}
				return errors;
			}// ilosc naglowkow excela jest wieksza
			else {
				for (int k = 0; k < velocity.size(); k++) {
					if (!velocity.get(k).contains(headers.get(k))) {
						errors.add("Brak zgodności przy nagłówku: "
								+ headers.get(k) + "! ");
					}
				}
				return errors;

			}
		} finally {
			csv.close();
			reader.close();
		}
	}
}
