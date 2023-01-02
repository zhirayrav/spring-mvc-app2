package com.company.springcourse.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>{
	List<Book> findByPersonId(int id);
	@Query("select p from Person left join fetch p.books where book.id=:bookId")
	Optional<Person> findOwnerByBookId(int bookId);
	@Modifying
	@Query("update Book set owner = :selectedPerson where id=:bookId")
	void assign(int bookId,Person selectedPerson);
	@Modifying
	@Query("update Book set owner = null where id=:bookId")
	void release(int bookId);
}
