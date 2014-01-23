package com.rebeccamills.loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UserBean {
	String strain_id, subtype, state;
	int year;
	Calendar collection_date;
	int age;

	public String getStrain_id() {
		return strain_id;
	}

	public String getSubtype() {
		return subtype;
	}

	public String getState() {
		return state;
	}

	public int getAge() {
		return age;
	}

	public void setPassword(String strain_id) {
		this.strain_id = strain_id;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setAge(String age) {
		if (age.equalsIgnoreCase("-N/A-")) {
			this.age = 0;
		} else {
			this.age = Integer.parseInt(age);
		}

	}

	public Calendar getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(String collection_date) throws Exception {

		Calendar parsedDate = Calendar.getInstance();
		String[] d = collection_date.split("/");
		int size = d.length;
		if (size == 2) {
			collection_date = "01/" + collection_date;
			final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			parsedDate.setTime(formatter.parse(collection_date));
			this.collection_date = parsedDate;
			setYear();

		} else if (size == 1) {
			collection_date = "01/01/" + collection_date;
			final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			parsedDate.setTime(formatter.parse(collection_date));
			this.collection_date = parsedDate;
			setYear();
		} else {
			final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			parsedDate.setTime(formatter.parse(collection_date));
			this.collection_date = parsedDate;
			setYear();
		}

	}

	public void setStrain_id(String strain_id) {
		this.strain_id = strain_id;
	}

	public int getYear() {

		return year;
	}

	public void setYear(int year) {

		this.year = year;

	}

	public void setYear() {
		this.year = collection_date.get(Calendar.YEAR);
	}

	Calendar cal = Calendar.getInstance();

	public String toString() {
		return "UserBean [strain_id=" + strain_id + ", subtype=" + subtype
				+ ", state=" + state + ", year=" + getYear() + ", collection_date="
				+ collection_date.get(Calendar.YEAR) + collection_date.get(Calendar.MONTH) + collection_date.get(Calendar.DAY_OF_MONTH) +" age=" + age + "]";
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setCollection_date(Calendar collection_date) {
		this.collection_date = collection_date;
	}

}
