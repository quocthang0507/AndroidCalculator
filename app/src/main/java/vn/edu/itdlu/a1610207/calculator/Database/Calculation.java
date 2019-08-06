package vn.edu.itdlu.a1610207.calculator.Database;

import java.io.Serializable;

public class Calculation implements Serializable {
	private int id;
	private String expression;
	private String result;
	
	public Calculation() {
	}
	
	public Calculation(String expression, String result) {
		this.expression = expression;
		this.result = result;
	}
	
	public Calculation(int id, String expression, String result) {
		this.id = id;
		this.expression = expression;
		this.result = result;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return this.expression;
	}
}
