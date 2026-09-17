package main.java.com.acts.employeeai.model;

public class ContractEmployee extends Employee {

    private double contractBonus;

    public  ContractEmployee(
                    Long id,
                    String name,
                    String email,
                    String department,
                    double salary,
                    boolean active,
                    double contractBonus
    ){
        super(id, name, email, department, salary, active);
        this.contractBonus = contractBonus;
                    
    }
    @Override 
     public String getRoleDescription() {
        return "Contract employee in " + getDepartment() + " department";
    }
    @Override 
     public double calculateAnnualCompensation(){
        return  super.calculateAnnualCompensation()+contractBonus;

     }
     public double getContractBonus(){
        return contractBonus;
     }
     public void setContractBonus(double contractBonus) {
        this.contractBonus = contractBonus;
    }
    
}
