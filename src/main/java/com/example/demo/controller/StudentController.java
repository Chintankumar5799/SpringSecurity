package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {

	private List<Student> students=new ArrayList<>(List.of(
				new Student (1,"Chintan",90),
				new Student (3,"Yash",81),
				new Student(2,"Harsh",89)
			));
	
	@GetMapping("/student")
	public List<Student> getStudents(){
		return students;
	}
	
	@GetMapping("/csrfToken")
	public CsrfToken getCsrfToken(HttpServletRequest request){
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	
	@PostMapping("/addStudent")
	public List<Student> addStudent(@RequestBody Student student) {
		students.add(student);
		return students;
	}
}
 