package pl.echoweb.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.echoweb.dao.ITemplateDAO;
import pl.echoweb.model.entity.TemplateEntity;
import pl.echoweb.service.ICsvService;

@Service("CsvService")
@Transactional
public class CsvService implements ICsvService {

	@Autowired
	@Qualifier("TemplateDAO")
	ITemplateDAO myData;

	@Override
	public void generateXml(InputStream csv, Long templateId,
			OutputStream outStream) throws Exception {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		BufferedReader reader = null;
		List<String> headers;
		VelocityEngine ve;
		try {
			ve = new VelocityEngine();
			ve.init();
			reader = new BufferedReader(new InputStreamReader(csv));
			headers = new ArrayList<String>();
			String headerLine = reader.readLine();

			TemplateEntity ent = myData.getTemplate(templateId);
			String finalTemplate = makeTemplate(new String(ent.getTemplate()));
			outStream.write((ent.getHeader() + System
					.getProperty("line.separator")).getBytes());

			if (!headerLine.isEmpty()) {
				String[] hd = headerLine.split(";");
				for (String s : hd) {
					headers.add(s);
				}
			}
			for (String line = ""; (line = reader.readLine()) != null;) {
				String[] data = line.split(";");
				map = new HashMap<String, String>();
				int emptyCheck = 0;
				for (int i = 0; i < headers.size(); i++) {
					map.put(headers.get(i), data[i]);
					if (data[i].isEmpty())
						emptyCheck++;
				}
				if (emptyCheck != headers.size()) {
					list.add(map);

				} else {
					break;
				}
				if (list.size() > 100) {
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
			if (list.size() > 0) {
				StringWriter writer = new StringWriter();
				VelocityContext context = new VelocityContext();
				context.put("dataList", list);
				Velocity.evaluate(context, writer, "velocity", finalTemplate);
				outStream.write(writer.toString().getBytes());
				outStream.flush();
				writer.close();
			}
			outStream.write(ent.getFooter().getBytes());
		} finally {
			if (reader != null)
				reader.close();
			csv.close();
			outStream.close();
		}

	}

	@Override
	public String makeTemplate(String template) {
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

	@Override
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
