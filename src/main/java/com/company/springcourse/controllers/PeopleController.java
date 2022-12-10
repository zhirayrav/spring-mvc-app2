package com.company.springcourse.controllers;
import com.company.springcourse.dao.PersonDAO;
import com.company.springcourse.models.Person;
import com.company.springcourse.utils.PersonValidator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private final PersonDAO personDAO;
	private final PersonValidator personValidator;
	

	@Autowired
	public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
		super();
		this.personDAO = personDAO;
		this.personValidator = personValidator;
	}


	@GetMapping()
	public String index(Model model) {
		model.addAttribute("list",personDAO.index());
		return "people/indexPerson";
	}
	
		
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id,Model model) {
		model.addAttribute("person",personDAO.show(id));
		model.addAttribute("books",personDAO.getBooksByPersonId(id));
		return "people/showPerson";
	}
	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "people/newPerson";
	}
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("person",personDAO.show(id));
		return "people/editPerson";
	}
	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/newPerson";
		personDAO.save(person);
		return "redirect:/people";
	}
	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id,@ModelAttribute("person") @Valid Person person,BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/editPerson";
		personDAO.update(id,person);
		return "redirect:/people";
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDAO.delete(id);
		return "redirect:/people";
	}
	
	
		
}
