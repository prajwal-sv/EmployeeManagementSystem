package main.java.com.acts.employeeai.model;

import java.util.Objects;

public class Employee extends Person implements Payable {

    private Long id;
    private String name;
    private String email;
    private String department;
    private double salary;
    private boolean active;

    public Employee(
                    Long id,
                    String name,
                    String email,
                    String department,
                    double salary,
                    boolean active

    ){
        super(id, name, email);
        this.id = id; 
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.active = active;

    }

     // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for department
    public String getDepartment() {
        return department;
    }

    // Setter for department
    public void setDepartment(String department) {
        this.department = department;
    }

    // Getter for salary
    public double getSalary() {
        return salary;
    }

    // Setter for salary
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Getter for active
    public boolean isActive() {
        return active;
    }

    // Setter for active
    public void setActive(boolean active) {
        this.active = active;
    }

    public  double calculateAnnualCompensation(){
        return  salary*12;
    }


    public String getRoleDescription(){
        return "Regular employee in " + department + " department";
    }

    @Override
    public String toString() {
    return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + '\'' +
            ", email='" + getEmail() + '\'' +
            ", department='" + department + '\'' +
            ", salary=" + salary +
            ", active=" + active +
            '}';
        }

    @Override 
    public boolean equals(Object obj){
        if (this == obj) {
            return true ;
            
        }
        if (!(obj instanceof Employee other)) {
            return false;
            
        }

        return Objects.equals(getEmail(),other.getEmail());
    }

    @Override 
    public int hashCode(){
        return Objects.hash(getEmail());
    }




     public void displayEmployee() {

        System.out.println("Employee ID   : " + id);
        System.out.println("Name          : " + name);
        System.out.println("Email         : " + email);
        System.out.println("Department    : " + department);
        System.out.println("Salary        : " + salary);
        System.out.println("Active        : " + active);
        System.out.println("Role             : " + getRoleDescription());
        System.out.println("Annual Salary    : " + calculateAnnualCompensation());
    }




    
}
