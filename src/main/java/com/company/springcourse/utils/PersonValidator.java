package com.company.springcourse.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.company.springcourse.dao.PersonDAO;
import com.company.springcourse.models.Person;
@Component
public class PersonValidator implements Validator{
	private final PersonDAO personDAO;
	
	@Autowired
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
			Person person = (Person) o;
			if(personDAO.getPersonByName(person.getName()).isPresent()) {
				errors.rejectValue("name", "", "This name is already taken");
			}
		
	}

}

