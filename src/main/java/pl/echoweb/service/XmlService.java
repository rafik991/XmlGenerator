package pl.echoweb.service;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.echoweb.dao.ITemplateDAO;
import pl.echoweb.model.entity.TemplateEntity;

/**
 * Implementacja interfejsu {@link IXmlService}
 * 
 * @author rafal.machnik
 * 
 */
@Service("XmlService")
@Transactional
public class XmlService implements IXmlService {

	@Autowired
	@Qualifier("TemplateDAO")
	ITemplateDAO myData;

	@Override
	public void XlsToXml(InputStream xls, Long templateId,
			ServletOutputStream outStream) throws Exception {

		Workbook workbook = null;
		Sheet sheet;
		List<String> headers;
		VelocityEngine ve;
		ArrayList<Map<String, String>> list;
		Map<String, String> map;

		// inicjalizacja
		try {
			workbook = Workbook.getWorkbook(xls);
			sheet = workbook.getSheet(0);
			headers = new ArrayList<String>();
			list = new ArrayList<Map<String, String>>();
			ve = new VelocityEngine();
			ve.init();

			// inicjalizacja szablonu
			TemplateEntity ent = myData.getTemplate(templateId);
			String finalTemplate = makeTemplate(ent.getHeader(),
					ent.getFooter(), new String(ent.getTemplate()));

			// wypelnainie naglowkow
			for (int i = 0; i < sheet.getColumns(); i++) {
				headers.add(sheet.getCell(i, 0).getContents());
			}
			outStream.write((ent.getHeader() + System
					.getProperty("line.separator")).getBytes());
			// zczytywanie mapy danych
			if (sheet.getRows() > 1) {
				for (int i = 1; i < sheet.getRows(); i++) {
					map = new HashMap<String, String>();
					for (int k = 0; k < headers.size(); k++) {

						map.put(headers.get(k), sheet.getCell(k, i)
								.getContents());
					}
					if (!checkEndExcel(map)) {
						list.add(map);
					} else {
						break;
					}
					if (list.size() > 20) {
						// silnik velocity
						StringWriter writer = new StringWriter();
						VelocityContext context = new VelocityContext();
						context.put("dataList", list);
						Velocity.evaluate(context, writer, "velocity",
								finalTemplate);
						outStream.write(writer.toString().getBytes());
						outStream.flush();
						list.clear();
						list = new ArrayList<Map<String, String>>();

						writer.close();

					}
				}
			}
			if (list.size() > 0) {
				StringWriter writer = new StringWriter();
				VelocityContext context = new VelocityContext();
				context.put("dataList", list);
				Velocity.evaluate(context, writer, "velocity", finalTemplate);
				writer.append(ent.getFooter());
				outStream.write(writer.toString().getBytes());
				outStream.flush();
				writer.close();
			}
		} finally {
			workbook.close();
			xls.close();
			outStream.close();
		}

	}

	@Override
	public String makeTemplate(String header, String footer, String template) {
		String out = "";

		String[] lines = template.split(System.getProperty("line.separator"));
		List<String> linesNew = new ArrayList<String>();
		linesNew.add("#foreach ($item in $dataList) "
				+ System.getProperty("line.separator"));
		for (String s : lines) {
			if (s.contains("$")) {
				linesNew.add(s.replace("$", "$item.")
						+ System.getProperty("line.separator"));
			} else {
				linesNew.add(s + System.getProperty("line.separator"));
			}
		}
		linesNew.add("#end" + System.getProperty("line.separator"));
		for (String s : linesNew) {
			out += s;
		}
		return out;

	}

	// zwraca false jesli nie stwierdzi pustosci wiersza
	public Boolean checkEndExcel(Map<String, String> map) {
		int i = 0;
		for (String s : map.keySet()) {
			if (map.get(s).isEmpty())
				i++;
		}
		if (i == map.keySet().size())
			return true;
		else
			return false;

	}
}
