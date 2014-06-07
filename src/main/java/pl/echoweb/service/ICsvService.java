package pl.echoweb.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Interfejs do obslugi plikow CSV
 * 
 * @author rafal.machnik
 * 
 */
public interface ICsvService {

	public void generateXml(InputStream csv, Long templateId,
			OutputStream outStream) throws IOException, Exception;

	public String makeTemplate(String template);

	public Boolean checkEndExcel(Map<String, String> map);

}
