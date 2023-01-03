package com.company.springcourse.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Table(name = "Person")
@Entity
public class Person {
	public Person() {}
	public Person(int id,String name, int age) {
		super();
		this.id=id;
		this.name = name;
		this.age = age;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@NotEmpty(message="name shoud not be empty")
	@Size(min=2,max=100,message="name shoud be 2-100 character")
	@Column(name = "name")
	private  String name;
	@Min(value=6,message="age not mapping because <6")
	@Max(value=150,message="enter real age of person")
	@Column(name = "age")
	private int age;
	@OneToMany(mappedBy = "owner")
	private List<Book> books;
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
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
}
