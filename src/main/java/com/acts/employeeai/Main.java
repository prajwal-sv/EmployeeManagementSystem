package main.java.com.acts.employeeai;


import main.java.com.acts.employeeai.model.Employee;
import main.java.com.acts.employeeai.service.EmployeeRegistry;


public class Main {
    
    public static void main(String[] args) {

        

        
        Employee employee1 = new Employee(
                101L,
                "Amit Patil",
                "amit@example.com",
                "AI",
                85000.0,
                true
        );

        Employee employee2 = new Employee(
                102L,
                "Priya Sharma",
                "priya@example.com",
                "Java",
                75000.0,
                true
        );

        Employee employee3 = new Employee(
                103L,
                "Rahul Joshi",
                "rahul@example.com",
                "AI",
                90000.0,
                false
        );

        EmployeeRegistry registry = new EmployeeRegistry();

        registry.addEmployee(employee1);
        registry.addEmployee(employee2);
        registry.addEmployee(employee3);

        System.out.println("\nAll Employees:");

        registry.displayAll();


        System.out.println("\nSearching employee with ID 102:");

        Employee found = registry.findById(102L);

        if (found != null) {

            found.displayEmployee();
            
        }
         System.out.println("\nEmployees in AI department:");

        for (Employee employee : registry.findByDepartment("AI")) {
            System.out.println(employee);
        }


        // Total employees
        System.out.println("\nTotal Employees: " + registry.size());


        // Delete employee
        System.out.println("\nRemoving employee 103...");

        boolean removed = registry.removeById(103L);

        System.out.println("Removed: " + removed);
        System.out.println("Employees after removal: " + registry.size());


        System.out.println("\nSearching by name:");

        for (Employee employee : registry.findByName("Amit Patil")) {
            System.out.println(employee);
}




        

        

    }
}
