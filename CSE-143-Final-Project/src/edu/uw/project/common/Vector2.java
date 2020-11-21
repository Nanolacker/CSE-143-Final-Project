package edu.uw.project.common;

/**
 * Represents a 2-dimensional vector with an x-component and a y-component.
 * 
 * @author Connor Reinholdtsen
 */
public class Vector2 {

	private double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public Vector2 setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public Vector2 setY(double y) {
		this.y = y;
		return this;
	}

	public Vector2 add(Vector2 other) {
		return add(other.x, other.y);
	}

	public Vector2 add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2 subtract(Vector2 other) {
		return subtract(other.x, other.y);
	}

	public Vector2 subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2 multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2 clone() {
		return new Vector2(x, y);
	}

}