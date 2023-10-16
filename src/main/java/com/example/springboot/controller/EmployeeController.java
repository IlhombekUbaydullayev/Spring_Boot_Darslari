package com.example.springboot.controller;

import com.example.springboot.model.Employee;
import com.example.springboot.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity create(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @PutMapping("/employees/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(employee, id));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping("/employees/{name}")
    public ResponseEntity findByName(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.findByName(name));
    }

    @GetMapping("/employees/search")
    public ResponseEntity findByParam(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByParam(name));
    }

    @DeleteMapping("/employees/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}