package com.company.springcourse.services;



import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;
import com.company.springcourse.repositories.BooksRepository;
import com.company.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	private final BooksRepository booksRepository;
	private final PeopleService peopleService;
	private final PeopleRepository peopleRepository;
	
	@Autowired
	public BooksService(BooksRepository booksRepository,PeopleService peopleService,PeopleRepository peopleRepository) {
		super();
		this.booksRepository = booksRepository;
		this.peopleService = peopleService;
		this.peopleRepository = peopleRepository;
	}
	public List<Book> findAll(boolean sortByYear){
		if(sortByYear)
			return booksRepository.findAll(Sort.by("year"));
		else
			return booksRepository.findAll();
	}
	public Page<Book> findAllWithPagination(Integer page,Integer booksPerPage,boolean sortByYear){
		if(sortByYear)
		return booksRepository.findAll(PageRequest.of(page, booksPerPage,Sort.by("year")));
		else
			return booksRepository.findAll(PageRequest.of(page, booksPerPage));
	}
	public List<Book> findAll(Sort sort){
		return booksRepository.findAll(sort);
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
		Book bookToBeUpdated = booksRepository.findById(id).get();
		updatedBook.setId(id);
		updatedBook.setOwner(bookToBeUpdated.getOwner());
		booksRepository.save(updatedBook);
	}
	@Transactional
	public void deleteById(int id) {
		booksRepository.deleteById(id);
	}
	public List<Book> getBooksByPersonId(int id){
		Optional<Person> person = peopleRepository.findById(id);
		if(person.isPresent()) {
		Hibernate.initialize(person.get().getBooks());
		person.get().getBooks().forEach(book -> {
			Period dif = Period.between(book.getTakenAt().toLocalDate(),LocalDate.now());
			if(dif.get(ChronoUnit.DAYS)>10)
				book.setExpired(true);
		});
		return person.get().getBooks();
		
	}else 
		return Collections.EMPTY_LIST;
	}
	
	public Person getBookOwner(int bookId){
		return booksRepository.findById(bookId).map(Book::getOwner).orElse(null);
		
//		return booksRepository.findOwnerByBookId(bookId); // @Query(value = "select p from Person p left join fetch p.books b where b.id = ?1")
	}
	@Transactional
	public void assign(int bookId,Person person) {
		booksRepository.findById(bookId).ifPresent(book -> {
			book.setOwner(person);
			book.setTakenAt(LocalDateTime.now());
		});
//		booksRepository.assign(bookId, person);
		
	}
	@Transactional
	public void release(int bookId) {
		booksRepository.findById(bookId).ifPresent(book -> {
			book.setOwner(null);
			book.setTakenAt(null);
		});
//		booksRepository.release(bookId);
	}
	public List<Book> search(String str){
		return booksRepository.findByNameStartingWith(str);
	}
	
}
