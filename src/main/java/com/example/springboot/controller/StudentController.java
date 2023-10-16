package com.example.springboot.controller;

import com.example.springboot.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class StudentController { 

    @GetMapping("/student")
    private ResponseEntity getAll(){
        Student student = new Student(1L,"Ilhombek","Ubaydullayev","Java");
        Student student2 = new Student(2L,"Ilhombek2","Ubaydullayev2","Kotlin");
        Student student3 = new Student(3L,"Ilhombek3","Ubaydullayev3","Python");
        ArrayList<Student> arrayList = new ArrayList<>();
        arrayList.add(student);
        arrayList.add(student2);
        arrayList.add(student3);
        return ResponseEntity.ok(arrayList);
    }

    @GetMapping("/student/{id}")
    private ResponseEntity getId(@PathVariable Long id) {
        Student student = new Student(id,"Ilhombek","Ubaydullayev","Java");
        return ResponseEntity.ok(student);
    }

    @GetMapping("/students")
    private ResponseEntity getId(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String lastName,
                                 @RequestParam String course) {
        Student student = new Student(id,name,lastName,course);
        return ResponseEntity.ok(student);
    }


    @PostMapping("student/create")
    public ResponseEntity create(@RequestBody Student student) {
        return ResponseEntity.ok(student);
    }

    @PutMapping("student/update/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody Student student) {
        Student student2 = new Student(2L,"Ilhombek2","Ubaydullayev2","Kotlin");
        student2.setId(id);
        student2.setName(student.getName());
        student2.setLatName(student.getLatName());
        student2.setCourse(student.getCourse());
        return ResponseEntity.ok(student2);
    }
}
