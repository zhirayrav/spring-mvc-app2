package com.company.springcourse.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {
	@GetMapping("/hello")
	public String helloPage(@RequestParam(value ="name",required = false) String name,
			@RequestParam(value = "surname",required = false) String surname,Model model) {
		model.addAttribute("message","Hello " + name + " " + surname);
		return "first/hello";
	}
	
		
	@GetMapping("/goodbye")
	public String goodByePage() {
		return "first/goodbye";
	}
	@GetMapping("/calculate")
	public String calc(@RequestParam("a") int a, 
			@RequestParam("b") int b,
			@RequestParam(value = "action",required = false) String action,Model model) {
		double result;
		switch(action) {
		case "addition" :
			result = a+b;
			break;
		case "subtraction":
			result = a-b;
			break;
		case "multiplication":
			result = a*b;
			break;
		case "division":
			result = a/(double)b;
			break;
		default: 
			result = 0;
			break;
		}
		model.addAttribute("result", result);
		return "first/calculate";
		}
}
