package com.cpms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.pojos.Course;
import com.cpms.pojos.Technology;
import com.cpms.services.ITechnologyService;

/**
 * @author karun
 *
 */
@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	ITechnologyService service;

	@GetMapping("course/list")
	public ResponseEntity<?>  listCourses() {
		List<String> courses = new ArrayList<>();
		for (Course course : Course.values()) {
			courses.add(course.toString());
		}
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	@GetMapping("technology/list")
	public ResponseEntity<?> listTechnologies() {
		List <Technology> technologies = service.listTechnologies();
		if(technologies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(technologies, HttpStatus.OK);
		}
	}
}
