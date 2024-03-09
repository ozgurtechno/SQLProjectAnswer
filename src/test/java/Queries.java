import org.testng.annotations.Test;

import java.sql.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Queries extends DatabaseHelper {

    /** Q01 */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesInDepartmentD001() {

        String query =
                "SELECT * FROM employees " +
                        "INNER JOIN dept_emp ON employees.emp_no = dept_emp.emp_no " +
                        "WHERE dept_no = 'D001';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-16s", metaData.getColumnName(i));
            }
            System.out.println();
            // Print 100 rows
            int rowCount = 0;
            while (rs.next() && rowCount<100) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-16s", rs.getString(i));
                }
                System.out.println();
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q02 */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesInHumanResourcesDepartment() {

        String query =
                "SELECT employees.* FROM employees " +
                        "INNER JOIN dept_emp ON employees.emp_no = dept_emp.emp_no " +
                        "INNER JOIN departments ON dept_emp.dept_no = departments.dept_no " +
                        "WHERE departments.dept_name = 'Human Resources';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-16s", metaData.getColumnName(i));
            }
            System.out.println();
            // Print 100 rows
            int rowCount = 0;
            while (rs.next() && rowCount < 100) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-16s", rs.getString(i));
                }
                System.out.println();
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q03 */
    @Test(groups = "SalaryQueries")
    public void calculateAverageSalary() {

        String query = "SELECT AVG(salary) AS average_salary FROM salaries;";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-16s", metaData.getColumnLabel(i));
            }
            System.out.println();
            // Print result
            if (rs.next()) {
                System.out.printf("%-16s%n", rs.getString("average_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q04 */
    @Test(groups = "SalaryQueries")
    public void calculateAverageSalaryForMaleEmployees() {

        String query = "SELECT AVG(salary) AS avg_salary FROM employees e " +
                "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE e.gender = 'M';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            // Print result
            if (rs.next()) {
                System.out.printf("%-16s%n", "Average Salary (M)");
                System.out.printf("%-16s%n", rs.getString("avg_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q04, Q05 */
    @Test(groups = "SalaryQueries")
    public void calculateAverageSalaryForFemaleEmployees() {

        String query = "SELECT AVG(salary) AS avg_salary FROM employees e " +
                "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE e.gender = 'F';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            // Print result
            if (rs.next()) {
                System.out.printf("%-16s%n", "Average Salary (F)");
                System.out.printf("%-16s%n", rs.getString("avg_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q03, Q04 & Q05 */
    @Test(groups = "SalaryQueries")
    public void calculateAverageSalaries() {

        String overallQuery = "SELECT 'Overall' AS gender, AVG(s.salary) AS avg_salary FROM employees e INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "UNION " +
                "SELECT 'Male' AS gender, AVG(s.salary) AS avg_salary FROM employees e INNER JOIN salaries s ON e.emp_no = s.emp_no WHERE e.gender = 'M' " +
                "UNION " +
                "SELECT 'Female' AS gender, AVG(s.salary) AS avg_salary FROM employees e INNER JOIN salaries s ON e.emp_no = s.emp_no WHERE e.gender = 'F';";

        try {
            System.out.println("Average Salaries:");
            executeAverageSalaryQuery(overallQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /** Q06 */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void listEmployeesInSalesDepartmentWithHighSalary() {

        String query =
                "SELECT e.*, s.salary " +
                        "FROM employees e " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE d.dept_name = 'Sales' AND s.salary > 70000 " +
                        "LIMIT 100;";  // Limit to 100 records

        try {
            System.out.println("Employees in Sales Department with Salary > $70,000:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q07 */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void listEmployeesWithSalaryBetween50KAnd100K() {

        String query =
                "SELECT e.* " +
                        "FROM employees e " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE s.salary BETWEEN 50000 AND 100000 " +
                        "LIMIT 100;";  // Limit to 100 records

        try {
            System.out.println("Employees with Salary Between $50,000 and $100,000:");
            executeEmployeeQueryUnique(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q08 by Depatrment No */
    @Test(groups = {"SalaryQueries"})
    public void calculateAverageSalaryForEachDepartment() {

        String query =
                "SELECT d.dept_no, AVG(s.salary) AS average_salary " +
                        "FROM dept_emp de " +
                        "JOIN salaries s ON de.emp_no = s.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "GROUP BY d.dept_no;";

        try {
            System.out.println("Average Salary for Each Department:");
            executeDepartmentQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q08 by Depatrment Name */
    @Test(groups = {"DepartmentQueries", "SalaryQueries"})
    public void calculateAverageSalaryForEachDepartmentByName() {
        String query =
                "SELECT dept_name, AVG(salary) AS avg_salary " +
                        "FROM departments " +
                        "JOIN dept_emp ON departments.dept_no = dept_emp.dept_no " +
                        "JOIN salaries ON dept_emp.emp_no = salaries.emp_no " +
                        "GROUP BY departments.dept_no;";

        try {
            System.out.println("Average Salary for Each Department (by department name):");
            executeDepartmentQueryByName(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Q09. Calculate the average salary for each department, including department names */
    @Test(groups = {"DepartmentQueries", "SalaryQueries"})
    public void calculateAverageSalaryForEachDepartmentWithNames() {
        String query =
                "SELECT d.dept_no, d.dept_name, AVG(s.salary) AS average_salary " +
                        "FROM employees e " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "GROUP BY d.dept_no, d.dept_name;";

        try {
            System.out.println("Average Salary for Each Department (including department names):");
            executeDepartmentQueryWithNames(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q10 */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void findSalaryChangesForEmployee() {
        String query =
                "SELECT emp_no, salary, from_date, to_date " +
                        "FROM salaries " +
                        "WHERE emp_no = '10102' " +
                        "ORDER BY from_date;";

        try {
            System.out.println("Salary changes for employee with emp. no '10102':");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q11 */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void findSalaryIncreasesForEmployee() {
        String query =
                "SELECT emp_no, salary, to_date " +
                        "FROM salaries " +
                        "WHERE emp_no = '10102' " +
                        "ORDER BY to_date;";

        try {
            System.out.println("Salary increases for employee with employee number '10102':");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q12 */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void findEmployeeWithHighestSalary() {
        String query =
                "SELECT * FROM employees " +
                        "JOIN salaries ON employees.emp_no = salaries.emp_no " +
                        "ORDER BY salary DESC " +
                        "LIMIT 1;";

        try {
            System.out.println("Employee with the highest salary:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q13 Find the latest salaries for each employee */
    @Test(groups = {"SalaryQueries"})
    public void findLatestSalariesForEmployees() {
        String query =
                "SELECT emp_no, salary, to_date " +
                        "FROM salaries " +
                        "WHERE (emp_no, to_date) IN " +
                        "(SELECT emp_no, MAX(to_date) FROM salaries GROUP BY emp_no);";

        try {
            System.out.println("Latest salaries for each employee:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q14. List the first name, last name, and highest salary of employees in the "`sales`" department.
     * Order the list by highest salary descending and only show the employee with the highest salary */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void listHighestSalaryInSalesDepartment() {
        String query =
                "SELECT e.first_name, e.last_name, MAX(s.salary) AS highest_salary " +
                        "FROM employees e " +
                        "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE d.dept_name = 'Sales' " +
                        "GROUP BY e.first_name, e.last_name " +
                        "ORDER BY highest_salary DESC " +
                        "LIMIT 1;";

        try {
            System.out.println("Employee with the highest salary in the Sales department:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q15. Find the Employee with the Highest Salary Average in the Research Department */
     @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void findEmployeeWithHighestAverageSalaryInResearchDepartment() {
        String query =
                "SELECT e.first_name, e.last_name, MAX(s.salary) AS max_salary " +
                        "FROM employees e " +
                        "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE d.dept_name = 'Research' " +
                        "GROUP BY e.first_name, e.last_name " +
                        "ORDER BY max_salary DESC " +
                        "LIMIT 1;";

        try {
            System.out.println("Employee with the highest average salary in the Research department:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** ##	16. For each department, identify the employee with the highest single salary ever recorded.
     * List the department name, employee's first name, last name, and the peak salary amount.
     * Order the results by the peak salary in descending order. */
     @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void identifyEmployeeWithHighestSalaryInEachDepartment() {
        String query =
                "SELECT d.dept_name AS department, e.first_name, e.last_name, MAX(s.salary) AS max_salary " +
                        "FROM employees e " +
                        "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "GROUP BY d.dept_name " +
                        "ORDER BY max_salary DESC;";

        try {
            System.out.println("Employees with the highest single salary in each department:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**  Q17. Identify the employees in each department who have the highest average salary.
     * List the department name, employee's first name, last name, and the average salary.
     * Order the results by average salary in descending order, showing only those with the highest average salary
     * within their department. */
    @Test(groups = {"EmployeeQueries", "SalaryQueries", "DepartmentsQueries"})
    public void identifyEmployeesWithHighestAverageSalaryInEachDepartment() {
        String query =
                "SELECT d.dept_name AS department, e.first_name, e.last_name, AVG(s.salary) AS avg_salary " +
                        "FROM employees e " +
                        "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "GROUP BY department, e.first_name, e.last_name " +
                        "ORDER BY department, avg_salary DESC;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-20s %-20s %-20s %-16s%n", "Department", "First Name", "Last Name", "Average Salary");
            System.out.println("------------------------------------------------------------------------");

            int rowCount = 0;
            while (rs.next() && rowCount < 100) {
                String department = rs.getString("department");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                double avgSalary = rs.getDouble("avg_salary");
                System.out.printf("%-20s %-20s %-20s %-16.2f%n", department, firstName, lastName, avgSalary);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Q18. List the names, last names, and hire dates in alphabetical order of all employees hired
     *  before `January 01, 1990`. */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesHiredBefore1990() {
        String query =
                "SELECT first_name, last_name, hire_date " +
                        "FROM employees " +
                        "WHERE hire_date < '1990-01-01' " +
                        "ORDER BY first_name ASC, last_name ASC";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-20s %-20s %-12s%n", "First Name", "Last Name", "Hire Date");
            System.out.println("--------------------------------------------------");

            int rowCount = 0;
            while (rs.next() && rowCount < 500) { // Limit to 500 records
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String hireDate = rs.getString("hire_date");
                System.out.printf("%-20s %-20s %-12s%n", firstName, lastName, hireDate);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q19. List the names, last names, hire dates of all employees hired
     * between `January 01, 1985` and `December 31, 1989`, sorted by hire date. */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesHiredBetween1985And1989() {
        String query =
                "SELECT first_name, last_name, hire_date " +
                        "FROM employees " +
                        "WHERE hire_date BETWEEN '1985-01-01' AND '1989-12-31' " +
                        "ORDER BY hire_date ASC;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-20s %-20s %-12s%n", "First Name", "Last Name", "Hire Date");
            System.out.println("--------------------------------------------------");

            int rowCount = 0;
            while (rs.next() && rowCount < 100) { // Limit to 100 records
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String hireDate = rs.getString("hire_date");
                System.out.printf("%-20s %-20s %-12s%n", firstName, lastName, hireDate);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** ##	20. List the names, last names, hire dates, and salaries of all employees in the Sales department
     *  who were hired between January 01, 1985 and December 31, 1989, sorted by salary in descending order. */
    @Test(groups = "EmployeeQueries")
    public void listSalesEmployeesHiredBetween1985And1989WithSalary() {
        String query =
                "SELECT e.first_name, e.last_name, e.hire_date, s.salary " +
                        "FROM employees e " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "WHERE e.hire_date BETWEEN '1985-01-01' AND '1989-12-31' " +
                        "AND d.dept_name = 'Sales' " +
                        "ORDER BY s.salary DESC " +
                        "LIMIT 100;";  // Limit to 100 records

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-20s %-20s %-12s %-10s%n", "First Name", "Last Name", "Hire Date", "Salary");
            System.out.println("-----------------------------------------------------------------");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String hireDate = rs.getString("hire_date");
                double salary = rs.getDouble("salary");
                System.out.printf("%-20s %-20s %-12s %-10.2f%n", firstName, lastName, hireDate, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q21.a.b.c.d */
    @Test(groups = "EmployeeQueries")
    public void employeeStatistics() {
        String query = "SELECT\n" +
                "    'Male' AS gender,\n" +
                "    COUNT(CASE WHEN gender = 'M' THEN 1 END) AS Male_Count,\n" +
                "    COUNT(CASE WHEN gender = 'F' THEN 1 END) AS Female_Count,\n" +
                "    COUNT(*) AS Total_Employees\n" +
                "FROM\n" +
                "    employees;";

        System.out.println("Employee Statistics:");
        System.out.println("--------------------");
        executeQueryAndPrintResults(query);
    }

    /** Q21.a: Find the count of male employees / EXPECTED :179973 */
    @Test(groups = "EmployeeQueries")
    public void countMaleEmployees() {
        String query = "SELECT COUNT(gender) AS Male_Count FROM employees WHERE gender = 'M';";
        int maleCount = 0;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                maleCount = rs.getInt("Male_Count");
                System.out.println("Count of male employees: " + maleCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(
                maleCount,
                179973,
                "Unique count does not match expected value."
        );
    }

    /** Q21.b: Determine the count of female employees / EXPECTED :120050 */
    @Test(groups = "EmployeeQueries")
    public void countFemaleEmployees() {
        String query = "SELECT COUNT(gender) AS Female_Count FROM employees WHERE gender = 'F';";
        int femaleCount = 0;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                femaleCount = rs.getInt("Female_Count");
                System.out.println("Count of female employees: " + femaleCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(
                femaleCount,
                120050,
                "Unique count does not match expected value."
        );
    }

    /** Q21.c: Find the number of male and female employees by grouping */
    @Test(groups = "EmployeeQueries")
    public void countEmployeesByGender() {
        String query = "SELECT gender, COUNT(*) AS count FROM employees GROUP BY gender;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            System.out.println("Employee count by gender:");
            System.out.println("-------------------------");
            while (rs.next()) {
                String gender = rs.getString("gender");
                int count = rs.getInt("count");
                System.out.println("Gender: " + gender + ", Count: " + count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q21.d: Calculate the total number of employees in the company / EXPECTED :300023 */
    @Test(groups = "EmployeeQueries")
    public void totalEmployeeCount() {
        String query = "SELECT COUNT(emp_no) AS Total_Employees FROM employees;";
        int totalEmployees = 0;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                totalEmployees = rs.getInt("Total_Employees");
                System.out.println("Total number of employees: " + totalEmployees);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(
                totalEmployees,
                300023,
                "Total count does not match expected value."
        );
    }

    /** Q.22.a: Find out how many employees have unique first names /  EXPECTED :1275 */
    @Test(groups = {"EmployeeQueries"})
    public void testUniqueFirstNames() {
        String query = "SELECT COUNT(DISTINCT first_name) AS Unique_Names FROM employees;";
        System.out.println("Number of Employees with Unique First Names:");
        int uniqueEmployeesCount = executeSingleValueQuery(query);
        System.out.println("uniqueEmployeesCount = " + uniqueEmployeesCount);
        assertEquals(
                uniqueEmployeesCount,
                1275,
                "Unique department count does not match expected value."
        );
    }

    /** Q22.b: Identify the number of distinct department names / EXPECTED :9 */
    @Test(groups = "EmployeeQueries")
    public void testUniqueDepartmentCount() {
        String query = "SELECT COUNT(DISTINCT dept_name) AS Unique_Departments FROM departments;";
        int uniqueDepartmentsCount = 0;
        uniqueDepartmentsCount= executeSingleValueQuery(query);
        System.out.println("uniqueDepartmentsCount = " + uniqueDepartmentsCount);
        assertEquals(
                uniqueDepartmentsCount,
                9,
                "Unique department count does not match expected value."
        );
    }

    /** Q23. List the number of employees in each department */
    @Test(groups = {"EmployeeQueries", "DepartmentQueries"})
    public void listEmployeeCountInEachDepartment() {
        String query = "SELECT de.dept_no, COUNT(*) AS employee_count " +
                "FROM dept_emp de " +
                "GROUP BY de.dept_no;";
        System.out.println("Department Employee Counts:");
        String result = null;
        try {
            result = executeSingleValueQueryStr(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Total departments:\n" + result);
    }

    /** Q24. List all employees hired within the `last 5 years` from `February 20, 1990` */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesHiredWithinLast5YearsFrom1990_02_20() {
        String query =
                "SELECT * FROM employees " +
                        "WHERE hire_date <= DATE_SUB('1990-02-20', INTERVAL 5 YEAR);";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-16s", metaData.getColumnName(i));
            }
            System.out.println();
            // Print results
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-16s", rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q25. List the information (employee number, date of birth, first name, last name, gender, hire date) of the employee named "`Annemarie Redmiles`". */
     @Test(groups = "EmployeeQueries")
    public void testListEmployeeInformationByName_Annemarie_Redmiles() {
        String query =
                "SELECT emp_no, birth_date, first_name, last_name, gender, hire_date " +
                        "FROM employees " +
                        "WHERE first_name = 'Annemarie' AND last_name = 'Redmiles';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            boolean found = false;

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                assertEquals(firstName, "Annemarie");
                assertEquals(lastName, "Redmiles");

                System.out.println("Employee Information:");
                System.out.println("Employee Number: " + rs.getInt("emp_no"));
                System.out.println("Date of Birth: " + rs.getString("birth_date"));
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Hire Date: " + rs.getString("hire_date"));

                found = true;
            }

            assertTrue(found, "Employee Annemarie Redmiles not found in the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q26. List all information (employee number, date of birth, first name, last name, gender, hire date, salary, department, and title) for the employee named "`Annemarie Redmiles`". */
    @Test(groups = "EmployeeQueries")
    public void testListEmployeeInformationWithSalaryAndTitleByName_Annemarie_Redmiles() {
        String query =
                "SELECT e.emp_no, e.birth_date, e.first_name, e.last_name, e.gender, e.hire_date, " +
                        "s.salary, " +
                        "t.title " +
                        "FROM employees e " +
                        "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                        "INNER JOIN titles t ON e.emp_no = t.emp_no " +
                        "WHERE e.first_name = 'Annemarie' AND e.last_name = 'Redmiles';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            boolean found = false;

            while (rs.next()) {
                System.out.println("Employee Information:");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(columnName);
                    System.out.println(columnName + ": " + value);
                }

                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                assertEquals(firstName, "Annemarie");
                assertEquals(lastName, "Redmiles");

                found = true;
            }

            assertTrue(found, "Employee Annemarie Redmiles not found in the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q27. List all employees and managers in the `D005` department */
    @Test(groups = {"EmployeeQueries", "DepartmentQueries"})
    public void testListManagersInDepartmentD005() {
        String query =
                "SELECT e.emp_no, e.first_name, e.last_name, e.birth_date, e.gender, e.hire_date, " +
                        "d.dept_name AS department_name, t.title, s.salary " +
                        "FROM employees e " +
                        "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
                        "JOIN departments d ON dm.dept_no = d.dept_no " +
                        "JOIN titles t ON e.emp_no = t.emp_no " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE d.dept_no = 'D005';";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-12s", metaData.getColumnName(i));
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-12s", rs.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q28. List all employees hired after '`1994-02-24`' and earning more than `50,000` */
    @Test(groups = {"EmployeeQueries"})
    public void testListEmployeesHiredAfterDateWithSalaryAbove50K() {
        String query =
                "SELECT e.emp_no, e.first_name, e.last_name, e.birth_date, e.gender, e.hire_date, t.title, s.salary\n" +
                        "FROM employees e\n" +
                        "JOIN titles t ON e.emp_no = t.emp_no\n" +
                        "JOIN salaries s ON e.emp_no = s.emp_no\n" +
                        "WHERE e.hire_date > '1994-02-24' AND s.salary > 50000\n" +
                        "LIMIT 1000;";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Print column headers
            System.out.printf("%-10s %-15s %-15s %-12s %-7s %-12s %-25s %-10s%n",
                    "Emp No", "First Name", "Last Name", "Birth Date", "Gender", "Hire Date", "Title", "Salary");
            System.out.println("--------------------------------------------------------------------------------------" +
                    "-------------------------------------------------------------");

            while (rs.next()) {
                double salary = rs.getDouble("salary");
                // Print employee information
                System.out.printf("%-10d %-15s %-15s %-12s %-7s %-12s %-25s %-10.2f%n",
                        rs.getInt("emp_no"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        rs.getString("hire_date"),
                        rs.getString("title"),
                        rs.getDouble("salary"));
                assertTrue(salary > 50000, "Salary should be greater than 50000");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/** Q29. List all employees working in the "`sales`" department with the title "`Manager`" */
@Test(groups = {"EmployeeQueries", "ManagerQueries"})
public void listSalesManagers() {
    String query =
            "SELECT e.emp_no, e.first_name, e.last_name, e.birth_date, e.gender, e.hire_date " +
            "FROM employees e " +
            "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
            "JOIN departments d ON dm.dept_no = d.dept_no " +
            "JOIN titles t ON e.emp_no = t.emp_no " +
            "WHERE d.dept_name = 'Sales' AND t.title = 'Manager';";

    try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println("Sales Managers:");
        while (rs.next()) {
            int empNo = rs.getInt("emp_no");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String birthDate = rs.getString("birth_date");
            String gender = rs.getString("gender");
            String hireDate = rs.getString("hire_date");

            System.out.println("Employee No: " + empNo);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Birth Date: " + birthDate);
            System.out.println("Gender: " + gender);
            System.out.println("Hire Date: " + hireDate);
            System.out.println("-----------------------------------------");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

	/** 30. Find the department where employee with '`10102`' has worked the longest */
    @Test(groups = {"EmployeeQueries", "DepartmentQueries"})
    public void findDepartmentWithLongestEmployee() {
        String query = "SELECT employees.emp_no, departments.dept_name, DATEDIFF(MAX(dept_emp.to_date), MIN(dept_emp.from_date)) AS work_duration " +
                "FROM employees " +
                "JOIN dept_emp ON employees.emp_no = dept_emp.emp_no " +
                "JOIN departments ON dept_emp.dept_no = departments.dept_no " +
                "GROUP BY employees.emp_no " +
                "ORDER BY work_duration DESC " +
                "LIMIT 1;";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String departmentName = rs.getString("dept_name");
                int workDuration = rs.getInt("work_duration");

                System.out.println("Department with Longest Employee Work Duration:");
                System.out.println("Department Name: " + departmentName);
                System.out.println("Work Duration: " + workDuration + " days");

                // Assert that the department name is not null
                // and the work duration is greater than 0
                assertTrue(departmentName != null && !departmentName.isEmpty());
                assertTrue(workDuration > 0);
            } else {
                System.out.println("No result found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 31. Find the highest paid employee in department `D004`: */
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void findHighestPaidEmployeeInDepartmentD004() {
        String query = "SELECT employees.first_name, employees.last_name, MAX(salaries.salary) AS max_salary " +
                "FROM employees " +
                "JOIN salaries ON employees.emp_no = salaries.emp_no " +
                "JOIN dept_emp ON employees.emp_no = dept_emp.emp_no " +
                "WHERE dept_emp.dept_no = 'D004';";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                double maxSalary = rs.getDouble("max_salary");

                System.out.println("Highest Paid Employee in Department D004:");
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Max Salary: $" + maxSalary);

                // Assert that the first name and last name are not null or empty
                // and the max salary is greater than 0
                assertTrue(firstName != null && !firstName.isEmpty());
                assertTrue(lastName != null && !lastName.isEmpty());
                assertTrue(maxSalary > 0);
            } else {
                System.out.println("No result found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**  Q32. Find the entire position history for employee with emp. no '`10102`':*/
     @Test(groups = "EmployeeQueries")
    public void findPositionHistoryForEmployee() {
        String employeeNo = "10102";
        String query =
                "SELECT emp_no, title, from_date, to_date " +
                        "FROM titles " +
                        "WHERE emp_no = '" + employeeNo + "' " +
                        "ORDER BY from_date;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-12s %-40s %-12s %-12s%n", "Employee No", "Title", "From Date", "To Date");
            System.out.println("------------------------------------------------------------");

            while (rs.next()) {
                String empNo = rs.getString("emp_no");
                String title = rs.getString("title");
                String fromDate = rs.getString("from_date");
                String toDate = rs.getString("to_date");
                System.out.printf("%-12s %-40s %-12s %-12s%n", empNo, title, fromDate, toDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q33. Finding the average "`employee age`":*/
    @Test(groups = "EmployeeQueries")
    public void findAverageEmployeeAge() {
        String query = "SELECT AVG" +
                "(TIMESTAMPDIFF" +
                "(YEAR, birth_date, CURDATE())" +
                ") " +
                "AS average_age FROM employees;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Average Employee Age:");
            System.out.println("---------------------");

            while (rs.next()) {
                double averageAge = rs.getDouble("average_age");
                System.out.printf("Average Age: %.2f%n", averageAge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Q34. Finding the number of employees per department:*/
    @Test(groups = "EmployeeQueries")
    public void findNumberOfEmployeesPerDepartment() {
        String query = "SELECT dept_no, COUNT(*) AS employee_count FROM dept_emp GROUP BY dept_no;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Number of Employees per Department:");
            System.out.println("-----------------------------------");
            System.out.printf("%-10s %-15s%n", "Dept No", "Employee Count");

            while (rs.next()) {
                String deptNo = rs.getString("dept_no");
                int employeeCount = rs.getInt("employee_count");
                System.out.printf("%-10s %-15s%n", deptNo, employeeCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q35. Finding the managerial history of employee with ID (emp. no) `110022`:*/
    @Test(groups = "EmployeeQueries")
    public void findManagerialHistoryOfEmployee110022() {
        String query = "SELECT employees.first_name, employees.last_name, dept_manager.from_date, dept_manager.to_date " +
                "FROM employees " +
                "JOIN dept_manager ON employees.emp_no = dept_manager.emp_no " +
                "WHERE employees.emp_no = '110022';";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Managerial History of Employee 110022:");
            System.out.println("--------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s%n", "First Name", "Last Name", "From Date", "To Date");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String fromDate = rs.getString("from_date");
                String toDate = rs.getString("to_date");
                System.out.printf("%-15s %-15s %-15s %-15s%n", firstName, lastName, fromDate, toDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q36. Find the duration of employment for each employee _with name_ : */
    @Test(groups = "EmployeeQueries")
    public void findEmploymentDurationForEachEmployee() {
        String query =
                "SELECT e.emp_no, e.first_name, e.last_name, DATEDIFF(MAX(t.to_date), MIN(t.from_date)) AS work_duration " +
                "FROM employees e " +
                "JOIN titles t ON e.emp_no = t.emp_no " +
                "GROUP BY e.emp_no, e.first_name, e.last_name " +
                "LIMIT 1000;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Employment Duration for Each Employee:");
            System.out.println("--------------------------------------");
            System.out.printf("%-10s %-20s %-20s %-15s%n", "Emp No", "First Name", "Last Name", "Work Duration");

            while (rs.next()) {
                String empNo = rs.getString("emp_no");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int workDuration = rs.getInt("work_duration");
                System.out.printf("%-10s %-20s %-20s %-15d%n", empNo, firstName, lastName, workDuration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Q37. Find the latest title information for each employee:*/
    @Test(groups = "EmployeeQueries")
    public void findLatestTitleForEachEmployeeLimited() {
        String query = "SELECT e.emp_no, e.first_name, e.last_name, t.title " +
                "FROM employees e " +
                "JOIN titles t ON e.emp_no = t.emp_no " +
                "WHERE t.to_date = (SELECT MAX(to_date) FROM titles WHERE emp_no = e.emp_no) " +
                "LIMIT 1000;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Latest Title Information for Each Employee (Limited to 1000):");
            System.out.println("-----------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-20s %-30s%n", "Emp No", "First Name", "Last Name", "Latest Title");

            int count = 0;
            while (rs.next() && count < 1000) {
                String empNo = rs.getString("emp_no");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String latestTitle = rs.getString("title");
                System.out.printf("%-10s %-20s %-20s %-30s%n", empNo, firstName, lastName, latestTitle);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q38. Find the first and last names of managers in department '`D005`': */
    @Test(groups = {"EmployeeQueries", "DepartmentQueries", "ManagerQueries"})
    public void findManagersInDepartmentD005() {
        String query = "SELECT e.first_name, e.last_name " +
                "FROM employees e " +
                "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
                "WHERE dm.dept_no = 'D005';";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Managers in Department 'D005':");
            System.out.println("--------------------------------");
            System.out.printf("%-20s %-20s%n", "First Name", "Last Name");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                System.out.printf("%-20s %-20s%n", firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q39. Sort employees by their birth dates : */
    @Test(groups = "EmployeeQueries")
    public void sortEmployeesByBirthDate() {
        String query =
                "SELECT * FROM employees " +
                        "ORDER BY birth_date " +
                        "LIMIT 1000;";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Employees Sorted by Birth Date:");
            System.out.println("--------------------------------");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date birthDate = rs.getDate("birth_date");
                System.out.printf("%-20s %-20s %s%n", firstName, lastName, birthDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q40. List employees hired in `April 1992`: */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesHiredInApril1992() {
        String query = "SELECT * FROM employees " +
                "WHERE hire_date BETWEEN '1992-04-01' AND '1992-04-30';";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Employees Hired in April 1992:");
            System.out.println("--------------------------------");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date hireDate = rs.getDate("hire_date");
                System.out.printf("%-20s %-20s %s%n", firstName, lastName, hireDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Q41. Find all departments that employee '`10102`' has worked in: */
    @Test(groups = "EmployeeQueries")
    public void findDepartmentsForEmployee10102() {
        String query = "SELECT DISTINCT dept_no " +
                "FROM dept_emp " +
                "WHERE emp_no = '10102';";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Departments for Employee 10102:");
            System.out.println("------------------------------");

            while (rs.next()) {
                String deptNo = rs.getString("dept_no");
                System.out.println(deptNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Q41X. Find employees who have worked in multiple departments, and
     * list their first name, last name, and the number of departments they have worked in. */
    @Test(groups = "EmployeeQueries")
    public void listEmployeesInMultipleDepartments() {
        String query = "SELECT e.first_name, e.last_name, COUNT(de.dept_no) AS department_count " +
                "FROM employees e " +
                "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "GROUP BY e.emp_no " +
                "HAVING COUNT(de.dept_no) > 1;";

        try {
            System.out.println("Employees Working in Multiple Departments:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

