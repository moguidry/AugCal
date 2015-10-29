package com.aug.site.cms.Models;

import com.aug.site.cms.ServiceImpls.RawHtmlPlugin;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.markdown4j.Markdown4jProcessor;

@XmlRootElement
public class Article {

	private int id;
	private String title;
	private String content;
	private Date dateCreated;
	private Date lastModified;
	private boolean active;
	private String authorId;
	private String miniContent;

	// Empty constructor for Jersey to instantiate this class
	public Article() {
	}

	public Article(String title, String content, String miniContent) {
		this.title = title;
		this.content = content;
		this.miniContent = miniContent;
		this.dateCreated = new Date();
		this.lastModified = new Date();
		this.active = true;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMiniContent() {
		return miniContent;
	}

	public void setMiniContent(String miniContent) {
		this.miniContent = miniContent;
	}

	@JsonIgnore
	public String getPretty() {
		try {
			return new Markdown4jProcessor().registerPlugins(new RawHtmlPlugin())
					.process(this.content);
		} catch (Exception ex) {
			return this.content;
		}
	}
}
