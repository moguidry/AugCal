package com.aug.site.cal.Models;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LightArticle {

	private final int id;
	private final String miniContent;
	private final String title;
	private final Date dateCreated;

	public LightArticle(Article article) {
		id = article.getId();
		miniContent = article.getMiniContent();
		title = article.getTitle();
		dateCreated = article.getDateCreated();
	}

	public int getId() {
		return id;
	}

	public String getMiniContent() {
		return miniContent;
	}

	public String getTitle() {
		return title;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}
