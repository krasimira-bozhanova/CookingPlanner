package com.example.cookingplanner;

public class Content {
	private long id;
	private long[] ingredients;
	private double[] amounts;
	private long[] units;
	
	public Content(long[] ingredients, long[] units, double[] amounts) {
		this.ingredients = ingredients;
		this.units = units;
		this.amounts = amounts;
	}
	
	public Content(long id, long[] ingredients, long[] units, double[] amounts) {
		this.id = id;
		this.ingredients = ingredients;
		this.units = units;
		this.amounts = amounts;
	}
	
	public long getId() {
		return id;
	}

	public long[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(long[] ingredients) {
		this.ingredients = ingredients;
	}
	
	public long[] getUnits() {
		return units;
	}

	public void setUnits(long[] units) {
		this.units = units;
	}

	public double[] getAmounts() {
		return amounts;
	}

	public void setAmounts(double[] amounts) {
		this.amounts = amounts;
	}
}
