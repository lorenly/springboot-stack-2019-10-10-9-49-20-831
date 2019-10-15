package com.tw.apistackbase.controller;

import com.tw.apistackbase.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class EmployeeController {
    List<Employee> employeeList = new ArrayList<>();
    private final String NEWLINE = "\n";

    @GetMapping(path = "/employees", produces = {"application/json"})
    public List<Employee> getAll() {

        return employeeList;
    }

    @GetMapping(path = "/employee/{id}", produces = {"application/json"})
    public List<Employee> searchEmployee(@PathVariable("id") int id) {
        return employeeList.stream()
                .filter(emp -> emp.getId() == id)
                .collect(Collectors.toList());
    }

    @PostMapping("/employee")
    public ResponseEntity createEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employee")
    public ResponseEntity updateEmployee(@RequestBody Employee employee){
        boolean isExistEmployee = false;
        for(Employee emp : employeeList){
            if(emp.getId() == employee.getId()){
                isExistEmployee = true;
                emp.setAge(employee.getAge());
                emp.setGender(employee.getGender());
                emp.setName(employee.getName());
            }
        }
        String msg = isExistEmployee ? "-----Updated-----" +
                NEWLINE + "id: " + employee.getId() +
                NEWLINE + "name: " + employee.getName() +
                NEWLINE + "age: " + employee.getAge() +
                NEWLINE + "gender: " + employee.getGender() :
                "Employee not Exist";
        return ResponseEntity.ok(msg);
    }

    @RequestMapping(value= "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable("id") int id){
        boolean isExistEmployee = false;
        for (int objIndex = 0; objIndex <= employeeList.size(); objIndex++) {
            if (employeeList.get(objIndex).getId() == id) {
                employeeList.remove(objIndex);
                isExistEmployee = true;
            }
        }
        String msg = isExistEmployee == true ? "Removed Employee with Employee ID: " + id : "Employee not Exist";
        return ResponseEntity.ok(msg);
    }

}
