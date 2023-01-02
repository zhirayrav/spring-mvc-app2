package com.company.springcourse.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;
import com.company.springcourse.repositories.BooksRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	private final BooksRepository booksRepository;
	
	@Autowired
	public BooksService(BooksRepository booksRepository) {
		super();
		this.booksRepository = booksRepository;
	}
	public List<Book> findAll(){
		return booksRepository.findAll();
	}
	public Book findOne(int id) {
		return booksRepository.findById(id).orElse(null);
	}
	@Transactional
	public void save(Book book) {
		booksRepository.save(book);
	}
	@Transactional
	public void update(int id,Book updatedBook) {
		updatedBook.setId(id);
		booksRepository.save(updatedBook);
	}
	@Transactional
	public void deleteById(int id) {
		booksRepository.deleteById(id);
	}
	public List<Book> getBooksByPersonId(int id){
		return booksRepository.findByPersonId(id);
	}
	public Optional<Person> getBookOwner(int bookId){
		return booksRepository.findOwnerByBookId(bookId);
	}
	public void assign(int bookId,Person person) {
		booksRepository.assign(bookId, person);
	}
	public void release(int bookId) {
		booksRepository.release(bookId);
	}
}
