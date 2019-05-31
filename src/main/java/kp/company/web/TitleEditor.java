package kp.company.web;

import java.beans.PropertyEditorSupport;

import kp.company.Company;
import kp.company.Title;

/**
 */
public class TitleEditor extends PropertyEditorSupport {

	private final Company company;

	/**
	 * Constructor.
	 * 
	 * @param company the company
	 */
	public TitleEditor(Company company) {

		this.company = company;
	}

	/**
	 * Sets title value from text parameter.
	 * 
	 * @param name the name
	 */
	@Override
	public void setAsText(String name) throws IllegalArgumentException {

		for (Title title : this.company.getTitles()) {
			if (title.getName().equals(name)) {
				setValue(title);
				break;
			}
		}
	}

}
