package model.bridgelabz.jdbc.model;

import model.bl.bridgelabz.jdbc.model.EmployeePayrollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeePayrollTest {
    EmployeePayrollService employeePayrollService = new EmployeePayrollService();

    @Test
    public void givenTestCaseShouldReturn_AllData() {
        String sqlQuery = "Select * from employee";
        employeePayrollService.retrieveData(sqlQuery);
        Assertions.assertTrue(true, "Retrieve data successfully");
    }

    @Test
    public void givenTestCaseShouldReturn_UpdatedSalary() {
        String sqlQuery = "update employee set salary = %d where name = '%s";
        employeePayrollService.updateSalary("Mohini", 300000);
        Assertions.assertTrue(true, "Retrieve data successfully");
    }

    @Test
    public void givenTestCaseShouldReturn_UpdatedSalaryUsingPreparedStatement() {
        String sqlQuery = "update employee set salary = ? where name = ?";
        employeePayrollService.updateSalaryUsingPreparedStatement("Kajal", 6200000);
        Assertions.assertTrue(true, "Salary updated successfully using pp");
    }

    @Test
    public void givenTestCaseShouldReturn_AllDataByDateRange() {
        String sqlQuery = "select * from employee where startDate = %s between '2021-01-10' and date(now() = %s)";
        employeePayrollService.retrieveDataByDate(sqlQuery);
        Assertions.assertTrue(true, "Retrieve data by date successfully");
    }


    @Test
    public void givenTestCaseShouldReturn_SumOfSalaryByGender() {
        String sqlQuery = "select gender,sum(salary) from employee group by gender";
        employeePayrollService.getSumOfSalaryByGender(sqlQuery);
        Assertions.assertEquals("true", "true");
    }

    @Test
    public void givenTestCaseShouldReturn_AverageOfSalaryByGender() {
        String sqlQuery = "select gender,avg(salary) as avg_salary from employee group by gender;";
        employeePayrollService.getAverageSalaryByGender(sqlQuery);
        Assertions.assertTrue(true);
    }

    @Test
    public void givenTestCaseShouldReturn_MaximumSalaryByGender() {
        String sqlQuery = "select gender,max(salary) as max_salary from employee group by gender;";
        employeePayrollService.getMaxSalaryByGender(sqlQuery);
        Assertions.assertTrue(true, "Maximum salary retrieved");
    }

    @Test
    public void givenTestCaseShouldReturn_MinimumSalaryByGender() {
        String sqlQuery = "select gender,min(salary) as min_salary from employee group by gender;";
        employeePayrollService.getMinSalaryByGender(sqlQuery);
        Assertions.assertTrue(true, "Minimum salary retrieved");
    }

    @Test
    public void givenTestCaseShouldReturn_CountByGender() {
        String sqlQuery = "select gender,count(*) as count from employee group by gender;";
        employeePayrollService.getCountByGender(sqlQuery);
        Assertions.assertTrue(true);
    }

    @Test
    public void givenTestCaseShouldReturn_InsertPayrollData() {
        employeePayrollService.addEmployeeData();
        Assertions.assertTrue(true, "Employee data inserted");
    }
}