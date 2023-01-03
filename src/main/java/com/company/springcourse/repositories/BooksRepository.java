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
	
	List<Book> findBooksByOwner(Person person);
	@Query(value = "select p from Person p left join fetch p.books b where b.id = ?1")
	Optional<Person> findOwnerByBookId(int bookId);
	@Modifying
	@Query("update Book set owner = ?2 where id= ?1")
	void assign(int bookId,Person selectedPerson);
	@Modifying
	@Query("update Book set owner = null where id=?1")
	void release(int bookId);
}
