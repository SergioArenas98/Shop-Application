package model;

public abstract class Person {
	protected String name;
	
	public Person() {
		super();
	}

	protected Person(String name) {
		super();
		this.name = name;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
	
}
