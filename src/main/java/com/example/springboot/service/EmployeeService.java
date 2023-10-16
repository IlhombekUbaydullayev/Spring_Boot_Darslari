package com.example.springboot.service;

import com.example.springboot.model.Employee;
import com.example.springboot.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee, Long id) {
        Employee employee1 = new Employee();
        Optional<Employee> byId = employeeRepository.findById(id);
        if (!byId.isPresent()) {
            return employee1;
        }
        Employee employee2 = byId.get();
        employee2.setName(employee.getName());
        employee2.setLastName(employee.getLastName());
        return employeeRepository.save(employee2);
    }


    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findAllByLike(name);
    }

    public List<Employee> findByParam(String name) {
        return employeeRepository.findAllNativeLike(name);
    }

    public String delete(Long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (!byId.isPresent()) {
            return "Id not present!";
        }
        employeeRepository.delete(byId.get());
        return "Deleted succefully!!";
    }

    @Scheduled(cron = "0 46 18 * * *")
    public void saveSchedule() {
        Employee employee = new Employee();
        employee.setName("Laylo");
        employee.setLastName("Saidazimova");
        Employee save = employeeRepository.save(employee);
        System.out.println(save);
    }
}
