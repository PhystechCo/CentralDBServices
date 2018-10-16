/**
 * 
 */
package co.phystech.aosorio.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author AOSORIO
 *
 */

@Entity("projects")
public class Projects {

	@Id
	private int number;
	
	private String projectId;
	private String title;
	private String description;
	private String startDate;
	private String endDate;
	private String division;
	private int year;
	private String target;
	private String owner;
	private String team;
	private String presentationDate;
	private String aprovalDate;
	private String document;
	
	@Embedded
    private List<TimeTrackerRegistry> timeTrackerRegistry;

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * @return the presentationDate
	 */
	public String getPresentationDate() {
		return presentationDate;
	}

	/**
	 * @param presentationDate the presentationDate to set
	 */
	public void setPresentationDate(String presentationDate) {
		this.presentationDate = presentationDate;
	}

	/**
	 * @return the aprovalDate
	 */
	public String getAprovalDate() {
		return aprovalDate;
	}

	/**
	 * @param aprovalDate the aprovalDate to set
	 */
	public void setAprovalDate(String aprovalDate) {
		this.aprovalDate = aprovalDate;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the timeTrackerRegistry
	 */
	public List<TimeTrackerRegistry> getTimeTrackerRegistry() {
		return timeTrackerRegistry;
	}

	/**
	 * @param timeTrackerRegistry the timeTrackerRegistry to set
	 */
	public void setTimeTrackerRegistry(List<TimeTrackerRegistry> timeTrackerRegistry) {
		this.timeTrackerRegistry = timeTrackerRegistry;
	}
	
		
}
