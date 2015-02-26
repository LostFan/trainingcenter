package finalproject.form;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class CourseForm {
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	private int categoryId;
	
	private String links;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

}
