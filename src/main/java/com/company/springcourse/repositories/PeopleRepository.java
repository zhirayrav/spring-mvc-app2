package com.company.springcourse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.springcourse.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
	Optional<Person> findByName(String name);
}
