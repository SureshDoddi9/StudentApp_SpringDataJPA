package com.suresh.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.bean.Student;
import com.suresh.repo.StudentRepo;

@RestController
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>>  getStudents(){
		try {
		    List<Student> stdlist = studentRepo.findAll();
		    if(stdlist.isEmpty())
		    	 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    return new ResponseEntity<>(stdlist, HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@PostMapping("/student")
	public ResponseEntity<Student> createStudent(@RequestBody Student std) {
		try {
			Student student =studentRepo.save(std);
			if(!std.getSname().equals(student.getSname()))
				return new ResponseEntity<>(student, HttpStatus.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	@GetMapping("/student/{id}")
	public ResponseEntity<Optional<Student>> getStudent(@PathVariable Integer id) {
		try {
			Optional<Student> std= studentRepo.findById(id);
			if(std.isEmpty())
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    return new ResponseEntity<>(std, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Integer id,@RequestBody Student std)
	{
		 Optional<Student> student = studentRepo.findById(id);
		 if(student.isPresent())
		 {
			 Student student_new= student.get();
			 student_new.setSid(id);
			 student_new.setSname(std.getSname());
			 student_new.setSaddr(std.getSaddr());
			 
			 return new ResponseEntity<>(studentRepo.save(student_new), HttpStatus.OK);
		 }	
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable Integer id){
		try {
			studentRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}





