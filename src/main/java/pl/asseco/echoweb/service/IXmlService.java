package pl.asseco.echoweb.service;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import jxl.read.biff.BiffException;

/**
 * Interfejs serwisu do konwersji xls do xml na podstawie szablonu.
 * 
 * @author rafal.machnik
 * 
 */
public interface IXmlService {

	/**
	 * Usluga przeksztalcajaca xls do xmla na podstawie wybranego szablonu.
	 * 
	 * @param xls
	 * @param templateId
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 * @throws Exception
	 */
	public void XlsToXml(InputStream xls, Long templateId,
			ServletOutputStream outStream) throws BiffException, IOException,
			Exception;

	/**
	 * Generuje poprawny szablon velocity.
	 * 
	 * @param header
	 * @param footer
	 * @param template
	 * @return
	 */
	public String makeTemplate(String header, String footer, String template);

}
