package com.company.springcourse.controllers;
import com.company.springcourse.models.Person;
import com.company.springcourse.services.BooksService;
import com.company.springcourse.services.PeopleService;
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
	private final PeopleService peopleService;
	private final PersonValidator personValidator;
	private final BooksService booksService;
	
	@Autowired
	public PeopleController(PeopleService peopleService,BooksService booksService, PersonValidator personValidator) {
		super();
		this.peopleService = peopleService;
		this.booksService = booksService;
		this.personValidator = personValidator;
	}


	@GetMapping()
	public String index(Model model) {
		model.addAttribute("list",peopleService.findAll());
		return "people/indexPerson";
	}
	
		
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id,Model model) {
		model.addAttribute("person",peopleService.findOne(id));
		model.addAttribute("books",booksService.getBooksByPersonId(id));
		return "people/showPerson";
	}
	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "people/newPerson";
	}
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("person",peopleService.findOne(id));
		return "people/editPerson";
	}
	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/newPerson";
		peopleService.save(person);
		return "redirect:/people";
	}
	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id,@ModelAttribute("person") @Valid Person person,BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/editPerson";
		peopleService.update(id,person);
		return "redirect:/people";
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		peopleService.deleteById(id);
		return "redirect:/people";
	}
	
	
		
}
