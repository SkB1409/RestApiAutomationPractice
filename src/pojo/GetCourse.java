package pojo;

public class GetCourse {

	private String services;
	private String Expertise;
	private Courses courses;
	private String instructor;
	private String linkedIn;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getExpertise() {
		return Expertise;
	}

	public void setExpertise(String expertise) {
		Expertise = expertise;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getlinkedIn() {
		return linkedIn;
	}

	public void setlinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
