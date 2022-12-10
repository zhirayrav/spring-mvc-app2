package com.company.springcourse.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
public class Person {
	public Person() {}
	public Person(int id,String name, int age) {
		super();
		this.id=id;
		this.name = name;
		this.age = age;
	}
	private int id;
	@NotEmpty(message="name shoud not be empty")
	@Size(min=2,max=100,message="name shoud be 2-100 character")
	private  String name;
	@Min(value=6,message="age not mapping because <6")
	@Max(value=150,message="enter real age of person")
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
