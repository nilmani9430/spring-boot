package com.luv2code.demo.rest;

import com.luv2code.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudent;

    @PostConstruct
    public void loadData(){
        theStudent = new ArrayList<>();
        theStudent.add(new Student("Poornima","Patel"));
        theStudent.add(new Student("Mario","Rossi"));
        theStudent.add(new Student("Mary","Smith"));
    }

    //define endpoint for "/student" return a list of student
    @GetMapping("/students")
    public List<Student> getStudents(){
        return theStudent;
    }

    // define endpoint for "/students/{studentId} - return student at index of arrayList
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        // check studentId against the list size
        if( (studentId>=theStudent.size()) || (studentId<0)){
            throw new StudentNotFoundException("Student id not found - "+studentId);
        }
        return theStudent.get(studentId);
    }

    // add an exception handler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){

        // create a studentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        //return response entity
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    // add another exception handler .. to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){

        // create a studentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        //return response entity
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
