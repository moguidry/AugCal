package com.aug.site.cal.Models;

import com.aug.site.cms.ServiceImpls.RawHtmlPlugin;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.markdown4j.Markdown4jProcessor;

@XmlRootElement
public class Event {

	private int id;
        private Date startDate;
        private Date endDate;
        private String location;
        private String organizer;
        private String summary;
        private String description;
        
            
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
        
	// Empty constructor for Jersey to instantiate this class
	public Event() {
	}

	public Event(String location, String organizer, String summary, String description) {
		this.location = location;
		this.organizer = organizer;
		this.summary = summary;
                this.description = description;
		this.startDate = new Date();
		this.endDate = new Date();

	}



	@JsonIgnore
	public String s() {
		try {
			return new Markdown4jProcessor().registerPlugins(new RawHtmlPlugin())
					.process(this.description);
		} catch (Exception ex) {
			return this.description;
		}
	}
}
