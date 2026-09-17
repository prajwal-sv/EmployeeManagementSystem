package main.java.com.acts.employeeai.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.com.acts.employeeai.exception.DuplicateEmailException;
import main.java.com.acts.employeeai.exception.EmployeeNotFoundException;
import main.java.com.acts.employeeai.model.Employee;

public class EmployeeRegistry {
    
    private final List<Employee> employees = new ArrayList<>();
    private final Set<Employee> uniqueEmployees = new HashSet<>();
    private final Map<Long , Employee> employeeById = new HashMap<>();

    public void  addEmployee(Employee employee){
        for( Employee existingEmployee : employees){

            if (existingEmployee.getEmail().equalsIgnoreCase(employee.getEmail())) {

                throw new DuplicateEmailException("Employee with email already exists: " + employee.getEmail());
                
            }
        }
        employees.add(employee);
        uniqueEmployees.add(employee);
        employeeById.put(employee.getId(), employee);
    }
    
    public List<Employee> getAllEmployees(){

        return List.copyOf(employees);

    }

    public Employee findById(Long id){
        Employee employee = employeeById.get(id);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
            
        }
        return employeeById.get(id);
    }

    public List<Employee> findByDepartment(String department){

        List<Employee> result = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getDepartment().equalsIgnoreCase(department)) {

                result.add(employee);

                
            }
            
        }

        return  result ;

    }

    public boolean removeById(long id){
        Employee employee = employeeById.remove(id);

        if (employee == null) {
            return false ;
            
        }

        employees.remove(employee);
        uniqueEmployees.remove(employee);

        return true;
    }

    public  int size(){
        return  employees.size();
    }

    public void displayAll(){

        for (Employee employee : employees) {

            employee.displayEmployee();
            System.err.println("----------------------------------");
            
        }
    }

    public List<Employee> findByName(String name){
        List<Employee> result = new ArrayList<>();

        for (Employee employee : employees) {

            if (employee.getName().equalsIgnoreCase(name)) {
                result.add(employee);
                
            }
            
        }

        return result ;

       
    }

}
