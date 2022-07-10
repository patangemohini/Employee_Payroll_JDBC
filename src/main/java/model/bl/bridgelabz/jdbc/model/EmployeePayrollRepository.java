package model.bl.bridgelabz.jdbc.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class EmployeePayrollRepository {

    public Connection getConnection() {
        Connection connection = null;
        try {
            String JDBCURL = "jdbc:mysql://localhost:3306/emp_payroll";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBCURL, "root", "MySql@123");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Driver not loaded");
        }
        return connection;
    }

    public List<EmployeePayrollData> retrieveData() {
        List<EmployeePayrollData> employeeInfo = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = "select * from employee";
            ResultSet resultset = statement.executeQuery(sqlQuery);
            while (resultset.next()) {
                EmployeePayrollData employeePayrollData = new EmployeePayrollData();
                employeePayrollData.setId(resultset.getInt("id"));
                employeePayrollData.setName(resultset.getString("name"));
                employeePayrollData.setGender(resultset.getString("gender").charAt(0));
                employeePayrollData.setSalary(resultset.getString("salary"));
                employeePayrollData.setAddress(resultset.getString("address"));
                employeePayrollData.setPhone_number(resultset.getString("phone_number"));
                employeePayrollData.setStartDate(Date.valueOf(resultset.getDate("startDate").toLocalDate()));
                employeeInfo.add(employeePayrollData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeInfo;
    }

    public void updateSalary(String name, int salary) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = format("update employee set salary = %d where name = '%s'", salary, name);
            int result = statement.executeUpdate(sqlQuery);
            if (result >= 1) {
                System.out.println("salary updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSalaryUsingPreparedStatement(String name, int salary) {
        try (Connection connection = getConnection()) {
            String query = "update employee set salary = ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, salary);
            preparedStatement.setString(2, name);
            int result = preparedStatement.executeUpdate();
            if (result >= 1) {
                System.out.println("salary updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EmployeePayrollData> retrieveDataByDateRange() {
        List<EmployeePayrollData> employeeInfo = new ArrayList<>();
        try (Connection connection = getConnection()) {

            Statement statement = connection.createStatement();
            String sqlQuery = "select * from employee where startDate between '2021-04-10' and date(now())";
            ResultSet resultset = statement.executeQuery(sqlQuery);
            while (resultset.next()) {
                EmployeePayrollData employeePayrollData = new EmployeePayrollData();
                employeePayrollData.setId(resultset.getInt("id"));
                employeePayrollData.setName(resultset.getString("name"));
                employeePayrollData.setGender(resultset.getString("gender").charAt(0));
                employeePayrollData.setAddress(resultset.getString("address"));
                employeePayrollData.setPhone_number(resultset.getString("phone_number"));
                employeePayrollData.setStartDate(Date.valueOf(resultset.getDate("startDate").toLocalDate()));
                employeePayrollData.setSalary(resultset.getString("salary"));
                employeeInfo.add(employeePayrollData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeInfo;
    }

    //Sum of salary by gender;
    public Map<String, Double> getSumOfSalaryByGender() {
        Map<String, Double> sumOfSalary = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select gender,sum(salary) from employee group by gender";
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                String gender = String.valueOf(resultset.getString("gender").charAt(0));
                Double salary = Double.valueOf(resultset.getString("Sum_salary"));
                sumOfSalary.put(gender, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumOfSalary;
    }

    public Map<String, Double> getAverageSalaryByGender() {
        Map<String, Double> avgSalary = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select gender,avg(salary) as avg_salary from employee group by gender;";
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                String gender = String.valueOf(resultset.getString("gender").charAt(0));
                Double avg_salary = Double.valueOf(resultset.getString("avg_salary"));
                avgSalary.put(gender, avg_salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgSalary;
    }

    //maximum salary by gender
    public Map<String, Double> getMaxSalaryByGender() {
        Map<String, Double> maxSalary = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select gender,max(salary) as max_salary from employee group by gender;";
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                String gender = String.valueOf(resultset.getString("gender").charAt(0));
                Double max_salary = Double.valueOf(resultset.getString("max_salary"));
                maxSalary.put(gender, max_salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxSalary;
    }

    public Map<String, Double> getMinSalaryByGender() {
        Map<String, Double> minSalary = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select gender,min(salary) as min_salary from employee group by gender;";
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                String gender = String.valueOf(resultset.getString("gender").charAt(0));
                Double min_salary = Double.valueOf(resultset.getString("min_salary"));
                minSalary.put(gender, min_salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minSalary;
    }

    public Map<String, Integer> getCountByGender() {
        Map<String, Integer> countByGender = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select gender,count(*) as count from employee group by gender";
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                String gender = String.valueOf(resultset.getString("gender").charAt(0));
                Integer count = Integer.valueOf(resultset.getString("count"));
                countByGender.put(gender, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countByGender;
    }

    public String addEmployeeData() throws SQLException {
        int empId = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //insert employee data
        try (Statement statement1 = connection.createStatement()) {
            String sql = format("insert into employee(name,gender,startDate,phone_number,address) " +
                    "values ('%s','%s','%s',%s,'%s') ", "Testing1", "M", "2021-01-10", "8895465656", "Pune");
            int rowsAffected = statement1.executeUpdate(sql, statement1.RETURN_GENERATED_KEYS);
            if (rowsAffected == 1) {
                System.out.println("data added");
                System.out.println(statement1.getGeneratedKeys());
                ResultSet resultSet = statement1.getGeneratedKeys();
                if (resultSet.next()) {
                    empId = resultSet.getInt(1);
                }
            }
            System.out.println(empId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }

        // insert payroll data
        double salary = 5200000;
        try (Statement statement1 = connection.createStatement()) {
            double deductions = salary * 0.2;
            double taxablePay = salary - deductions;
            double tax = taxablePay * 0.1;
            double netPay = salary - tax;
            String sql = format("insert into payroll(basic_pay,deductions,taxable_pay,income_tax,net_pay,emp_ID) " +
                    "values ('%f','%f','%f','%f','%f','%d')", salary, deductions, taxablePay, tax, netPay, empId);
            int rowAffected = statement1.executeUpdate(sql);
            if (rowAffected >= 1) {
                System.out.println("Payroll data added");
            }
        } catch (SQLException e) {
            System.out.println("exception" + e.getMessage());
            connection.rollback();
        } catch (Exception e) {
            System.out.println("exception" + e.getMessage());
        }
        try {
            connection.commit();
        } finally {
            connection.close();
        }


        return null;
    }
}