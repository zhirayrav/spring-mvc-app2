package com.company.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
public class Book {
	private int id;
	@NotEmpty(message = "name shoud not be empty")
	@Size(min=2, max=100, message="size shoud be 2-100 characters")
	private String name;
	@NotEmpty(message="author shoud not be empty")
	private String author;
	@Min(value=1900,message="enter year since 1900")
	private int year;
	public Book() {}
	public Book(int id,String name, String author, int year) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.year = year;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
