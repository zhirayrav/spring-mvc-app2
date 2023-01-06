package com.company.springcourse.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.company.springcourse.models.Book;
import com.company.springcourse.models.Person;
import com.company.springcourse.services.BooksService;
import com.company.springcourse.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
	private final BooksService booksService;
	private final PeopleService peopleService;
	
	@Autowired
	public BooksController(BooksService booksService,PeopleService peopleService) {
		super();
		this.booksService = booksService;
		this.peopleService = peopleService;
	}
	@GetMapping()
	public String index(Model model,@RequestParam(value = "page",required = false) Integer page
			,@RequestParam(value = "books_per_page",required = false)Integer booksPerPage
			,@RequestParam(value = "sort_by_year",required = false) boolean sortByYear){
		
		if(page==null || booksPerPage==null)
			model.addAttribute("list",booksService.findAll(sortByYear));
		else  {
			model.addAttribute("list",booksService.findAllWithPagination(page,booksPerPage,sortByYear));
		}
			
		return "books/indexBook";
	}
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id,Model model,@ModelAttribute("person") Person person) {
		model.addAttribute("book",booksService.findOne(id));
		Person bookOwner = booksService.getBookOwner(id);
		if(bookOwner != null) {
			model.addAttribute("owner", bookOwner);
		}else
			model.addAttribute("people", peopleService.findAll());
		return "books/showBook";
	}
	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/newBook";
	}
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id,Model model) {
		model.addAttribute("book",booksService.findOne(id));
		return "books/editBook";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "books/newBook";
		booksService.save(book);
		return "redirect:/books";
	}
	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "books/editBook";
		booksService.update(id,book);
		return "redirect:/books";
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		booksService.deleteById(id);
		return "redirect:/books";
	}
	@PatchMapping("/{id}/release")
	public String release(@PathVariable("id") int id) {
		booksService.release(id);
		return "redirect:/books/" + id;
	}
	@PatchMapping("/{id}/assign")
	public String assign(@PathVariable("id") int bookId, Person selectedPerson) {
		booksService.assign(bookId,selectedPerson);
		return "redirect:/books/" + bookId;
	}
	
	@PostMapping("/search")
	public String search(@RequestParam("query") String query,Model model) {
		List<Book> foundBooks = booksService.search(query);
		model.addAttribute("foundBooks",foundBooks);
		foundBooks=null;
		return "books/indexBook";
	}
	
		
	

}
