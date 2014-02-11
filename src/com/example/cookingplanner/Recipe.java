package com.example.cookingplanner;

import android.graphics.Bitmap;

public class Recipe {
	private long id;
	private String name;
	private String description;

	private Content content;

	private int time;
	private Bitmap image;
	
	public Recipe(String name, String description, Content content, int time, Bitmap image) {
		this.name = name;
		this.description = description;
		this.content = content;
		this.time = time;
		this.image = image;
	}
	
	public Recipe(long id, String name, String description, Content content, int time, Bitmap image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.content = content;
		this.time = time;
		this.image = image;
	}
	
	public long getId() {
		return id;
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
	public boolean InsertInDatabase() {
		return true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean GetFromDatabase() {
		return true;		
	}

}
