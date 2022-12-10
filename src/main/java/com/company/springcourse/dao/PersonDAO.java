package com.company.springcourse.dao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;
@Component
public class PersonDAO {
	private final JdbcTemplate jdbcTemplate;
	@Autowired
	public PersonDAO(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	public List<Person> index(){
		return jdbcTemplate.query("select * from person",new BeanPropertyRowMapper<>(Person.class));
	}
	public Person show(int id) {
		return jdbcTemplate.query("select * from person where id=?",new BeanPropertyRowMapper<>(Person.class),
				id).stream().findAny().orElse(null);
	}
	public void save(Person person) {
		jdbcTemplate.update("insert into person(name,age) values(?,?)",person.getName(),person.getAge());
	}
	public void update(int id,Person updatedPerson) {
		jdbcTemplate.update("update person  set name=?,age=? where id=?",updatedPerson.getName(),updatedPerson.getAge(),id);
		
	}
	public void delete(int id) {
		jdbcTemplate.update("delete from person where id=?",id);
		
	}
	public List<Book> getBooksByPersonId(int id) {
		return jdbcTemplate.query("select * from book where person_id=?",new BeanPropertyRowMapper<>(Book.class),id);
	}
	public Optional<Person> getPersonByName(String name) {
		return jdbcTemplate.query("select * from person where name=?",new BeanPropertyRowMapper<>(Person.class),name)
				.stream().findAny();
		
	}
}
 