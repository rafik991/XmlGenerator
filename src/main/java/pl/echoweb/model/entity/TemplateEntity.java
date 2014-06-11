package pl.echoweb.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Encja zawierajaca szablony.
 *
 * @author rafal.machnik
 */

@Entity
@Table(name = "TEMPLATES")
public class TemplateEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "template_seq", sequenceName = "template_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "Template_Name")
    private String templateName;

    @Lob
    @Column(name = "Template")
    private byte[] template;

    @Lob
    @Column(name = "Excel")
    private byte[] excel;

    @Column(name = "Header")
    private String header;

    @Column(name = "Footer")
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

    public void setTemplate(byte[] template) {
        this.template = template;
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
