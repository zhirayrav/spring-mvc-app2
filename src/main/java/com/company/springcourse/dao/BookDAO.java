package com.company.springcourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;

@Component
public class BookDAO {
	private final JdbcTemplate jdbcTemplate;

	public BookDAO(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	public List<Book> index(){
		return jdbcTemplate.query("select id,name,author,year from book", new BeanPropertyRowMapper<>(Book.class));
	}
	public Book show(int id) {
		return jdbcTemplate.query("select id,name,author,year from book where id=?",new BeanPropertyRowMapper<>(Book.class),id)
				.stream().findAny().orElse(null);
			
	}
	public void save(Book book) {
		jdbcTemplate.update("insert into book (name,author,year) values (?,?,?)",book.getName(),book.getAuthor(),book.getYear());
	}
	public void update(int id,Book updatedBook) {
		jdbcTemplate.update("update book set name=?,author=?,year=? where id=?",updatedBook.getName(),updatedBook.getAuthor(),
				updatedBook.getYear(),id);
	}
	public void delete(int id) {
		jdbcTemplate.update("delete from book where id=?",id);
	}
	public Optional<Person> getBookOwner(int bookId) {
		return jdbcTemplate.query("select person.* from book join person on book.person_id=person.id where book.id=?", new BeanPropertyRowMapper<>(Person.class),bookId)
		.stream().findAny();
	}
	public void assign(int bookId,Person person) {
		jdbcTemplate.update("update book set person_id=? where id=?",person.getId(),bookId);
	}
	public void release(int id) {
		jdbcTemplate.update("update book set person_id=null where id=?",id);
		
	}
	
}
