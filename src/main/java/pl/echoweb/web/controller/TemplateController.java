package pl.echoweb.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.echoweb.model.dto.TemplateDTO;
import pl.echoweb.service.ICsvService;
import pl.echoweb.service.ITemplateService;
import pl.echoweb.service.IXmlService;
import pl.echoweb.web.model.TemplateListModel;
import pl.echoweb.web.model.TemplateModel;
import pl.echoweb.web.model.validator.GenerateValidator;
import pl.echoweb.web.model.validator.TemplateModelValidator;

/**
 * Kontroler obslugujacy dodawanie szablonu
 * 
 * @author rafal.machnik
 * 
 */
@Controller
@RequestMapping("/app")
public class TemplateController {

	@Autowired
	@Qualifier("TemplateService")
	ITemplateService templateService;

	@Autowired
	@Qualifier("XmlService")
	IXmlService xmlService;

	@Autowired
	@Qualifier("CsvService")
	ICsvService csvService;

	TemplateModelValidator validator = new TemplateModelValidator();
	GenerateValidator generateValidator = new GenerateValidator();

	@RequestMapping(value = "/addTemplate", method = RequestMethod.GET)
	public String showForm(Model model) {
		model.addAttribute("Template", new TemplateModel());
		return "addTemplate";
	}

	@RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
	public String handleFormUpload(
			@ModelAttribute("Template") TemplateModel template,
			BindingResult result) throws IOException {
		validator.validate(template, result);
		if (!result.hasErrors()) {
			TemplateDTO templateDTO = new TemplateDTO();
			templateDTO.setTemplateName(template.getTemplateName());
			templateDTO.setTemplate(template.getFileData().getBytes());
			templateDTO.setExcel(template.getExcelData().getBytes());
			templateDTO.setHeader(template.getHeader());
			templateDTO.setFooter(template.getFooter());
			templateService.addTemplate(templateDTO);
			return "redirect:/templateList";
		} else {

			return "addTemplate";
		}

	}

	@RequestMapping(value = "/templateList", method = RequestMethod.GET)
	public String getTemplates(Model model) {
		List<TemplateDTO> templateDTOList = templateService.getTemplates();
		List<TemplateListModel> templateModelList = new ArrayList<TemplateListModel>();
		for (TemplateDTO dto : templateDTOList) {
			TemplateListModel mod = new TemplateListModel(dto.getId(),
					dto.getTemplateName());
			templateModelList.add(mod);
		}
		model.addAttribute("TemplateList", templateModelList);
		return "templateList";
	}

	@RequestMapping(value = "/editTemplate/{templateId}", method = RequestMethod.GET)
	public String showEditTemplate(
			@PathVariable(value = "templateId") Long templateId, Model model) {
		TemplateDTO templateDTO = templateService.getTemplate(templateId);
		TemplateModel templateModel = new TemplateModel();
		String templateContent = new String(templateDTO.getTemplate());
		templateModel.setTemplateName(templateDTO.getTemplateName());
		templateModel.setTemplate(templateContent);
		templateModel.setId(templateId);
		templateModel.setHeader(templateDTO.getHeader());
		templateModel.setFooter(templateDTO.getFooter());
		model.addAttribute("Template", templateModel);
		return "editTemplate";

	}

	@RequestMapping(value = "/editTemplate/{templateId}", method = RequestMethod.POST)
	public String editTemplate(
			@ModelAttribute("Template") TemplateModel templateModel) {
		TemplateDTO templateDTO = templateService.getTemplate(templateModel
				.getId());
		templateDTO.setTemplate(templateModel.getTemplate().getBytes());
		templateDTO.setTemplateName("");
		templateDTO.setTemplateName(templateModel.getTemplateName());
		templateDTO.setHeader(templateModel.getHeader());
		templateDTO.setFooter(templateModel.getFooter());
		templateService.updateTemplate(templateDTO);
		return "redirect:/templateList";

	}

	@RequestMapping(value = "/templateDetails/{templateId}", method = RequestMethod.GET)
	public String showDetails(
			@PathVariable(value = "templateId") Long templateId, Model model) {
		TemplateDTO templateDTO = templateService.getTemplate(templateId);
		TemplateModel templateModel = new TemplateModel();
		templateModel.setTemplateName(templateDTO.getTemplateName());
		templateModel.setExcel(templateDTO.getExcel());
		templateModel.setTemplateName(templateDTO.getTemplateName());
		templateModel.setId(templateDTO.getId());
		templateModel.setHeader(templateDTO.getHeader());
		templateModel.setFooter(templateDTO.getFooter());
		String templateContent = templateDTO.getHeader()
				+ System.getProperty("line.separator");
		templateContent += new String(templateDTO.getTemplate())
				+ System.getProperty("line.separator");
		templateContent += templateDTO.getFooter();
		templateModel.setTemplate(templateContent);
		model.addAttribute("Template", templateModel);
		return "templateDetails";

	}

	@RequestMapping(value = "/getExcelTemplate/{templateId}", method = RequestMethod.GET)
	@ResponseBody
	public String getExcelTemplate(
			@PathVariable(value = "templateId") Long templateId,
			HttpServletResponse response) throws IOException {
		response.setHeader("Content-Disposition", "attachment;filename="
				+ templateService.getTemplate(templateId).getTemplateName()
				+ ".xls");
		response.setContentType("application/vnd.ms-excel");
		InputStream is = new ByteArrayInputStream(templateService.getTemplate(
				templateId).getExcel());
		OutputStream out = response.getOutputStream();
		try {
			IOUtils.copy(is, out);
			out.flush();

		} finally {
			out.close();
			is.close();
		}
		return "templateList";

	}

	@RequestMapping(value = "/templateDetails/{templateId}", method = RequestMethod.POST)
	public void getExcelTemplatePost(
			@ModelAttribute("Template") TemplateModel model,
			HttpServletResponse response) throws IOException {
		response.setHeader("Content-Disposition",
				"attachment;filename=template.xls");
		response.setContentType("application/vnd.ms-excel");
		InputStream is = new ByteArrayInputStream(templateService.getTemplate(
				model.getId()).getExcel());
		OutputStream out = response.getOutputStream();
		try {
			IOUtils.copy(is, out);
			out.flush();
		} finally {
			out.close();
			is.close();
		}

	}

	@RequestMapping(value = "/deleteTemplate/{templateId}", method = RequestMethod.GET)
	public String deleteTemplate(@PathVariable("templateId") Long templateId,
			Model model) {
		templateService.deleteTemplate(templateId);
		List<TemplateDTO> templateDTOList = templateService.getTemplates();
		List<TemplateModel> templateModelList = new ArrayList<TemplateModel>();
		for (TemplateDTO dto : templateDTOList) {
			TemplateModel mod = new TemplateModel();
			mod.setExcel(dto.getExcel());
			mod.setId(dto.getId());
			mod.setTemplateName(dto.getTemplateName());
			mod.setTemplate(new String(dto.getTemplate()));
			templateModelList.add(mod);
		}
		model.addAttribute("TemplateList", templateModelList);
		return "redirect:/templateList";
	}

	@RequestMapping(value = "/generateXml/{templateId}", method = RequestMethod.GET)
	public String generateXml(Model model,
			@PathVariable("templateId") Long templateId) {
		TemplateDTO dto = templateService.getTemplate(templateId);
		TemplateModel templateModel = new TemplateModel();
		templateModel.setId(templateId);
		templateModel.setTemplateName(dto.getTemplateName());
		templateModel.setTemplate(new String(dto.getTemplate()));
		templateModel.setHeader(dto.getHeader());
		templateModel.setFooter(dto.getFooter());
		model.addAttribute("Template", templateModel);
		return "generateXml";
	}

	@RequestMapping(value = "/generateXml/{templateId}", method = RequestMethod.POST)
	public String generateXmlPost(
			@ModelAttribute("Template") TemplateModel template,
			@PathVariable("templateId") Long templateId, BindingResult result,
			HttpServletResponse response) throws BiffException, Exception {
		GenerateValidator myNewValidator = new GenerateValidator();
		myNewValidator.validate(template, result);
		generateValidator.validate(template, result);

		if (result.hasErrors()) {

			return "generateXml";

		} else {
			if (template.getExcelData().getOriginalFilename().contains(".xls")) {

				response.setContentType("application/xml");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ template
										.getExcelData()
										.getOriginalFilename()
										.subSequence(
												0,
												template.getExcelData()
														.getOriginalFilename()
														.indexOf(".")) + ".xml");
				ServletOutputStream writer = response.getOutputStream();

				try {

					xmlService.XlsToXml(template.getExcelData()
							.getInputStream(), templateId, writer);
				}

				finally {

					writer.flush();
					writer.close();
				}
			} else if (template.getExcelData().getOriginalFilename()
					.contains(".csv")) {

				response.setContentType("application/xml");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ template
										.getExcelData()
										.getOriginalFilename()
										.subSequence(
												0,
												template.getExcelData()
														.getOriginalFilename()
														.indexOf(".")) + ".xml");
				ServletOutputStream writer = response.getOutputStream();
				try {

					csvService.generateXml(template.getExcelData()
							.getInputStream(), templateId, writer);
				}

				finally {

					writer.flush();
					writer.close();
				}

			}
			return null;
		}

	}
}
