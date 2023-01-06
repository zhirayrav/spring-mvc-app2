package com.company.springcourse.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Table(name = "Book")
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@NotEmpty(message = "name shoud not be empty")
	@Size(min=2, max=100, message="size shoud be 2-100 characters")
	@Column(name = "name")
	private String name;
	@NotEmpty(message="author shoud not be empty")
	@Column(name = "author")
	private String author;
	@Min(value=1900,message="enter year since 1900")
	@Column(name = "year")
	private int year;
	@ManyToOne
	@JoinColumn(name = "person_id",referencedColumnName = "id")
	private Person owner;
	@Column(name = "data")
//	@Temporal(TemporalType.TIME)
	private LocalDateTime takenAt;
	@Transient
	private boolean expired; 
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
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public LocalDateTime getTakenAt() {
		return takenAt;
	}
	public void setTakenAt(LocalDateTime takenAt) {
		this.takenAt = takenAt;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	
}
