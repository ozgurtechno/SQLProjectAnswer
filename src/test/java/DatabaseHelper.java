import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    protected static Connection con;
    protected static Statement st;
    protected static ResultSet rs;
    private static final String hostURL = "jdbc:mysql://db-technostudy.ckr1jisflxpv.us-east-1.rds.amazonaws.com";
    private static final String dbSchema = hostURL + "/employees";
    private static final String username = "root";
    private static final String password = "'\"-LhCB'.%k[4S]z";

    @BeforeClass
    public void setUp() {

        con = null;
        try {
            System.out.println(">>> Connecting to Database...");
            con = DriverManager.getConnection(dbSchema, username, password);
            if (con != null) {
                System.out.println(">|< Connected to the Database...");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @AfterClass
    public void tearDown() {
        if (con != null) {
            try {
                System.out.println("::: Closing Database Connection...");
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static List<List<String>> getDataList(String sql) {

        List<List<String>> dataList = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                List<String> rowList = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    rowList.add(rs.getString(i));
                }

                dataList.add(rowList);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return dataList;
    }

    protected static void executeAverageSalaryQuery(String query) throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        // Print result
        System.out.printf("%-10s %-16s%n", "Gender", "Average Salary");
        while (rs.next()) {
            System.out.printf("%-10s %-16s%n", rs.getString("gender"), rs.getString("avg_salary"));
        }
    }

    // Helper method to execute query and print results
    protected void executeEmployeeQueryUnique(String query) throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        // Print column headers
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.printf("%-16s", metaData.getColumnName(i));
        }
        System.out.println();
        // Print result
        String previousName = "";
        int rowCount = 0;
        while (rs.next() && rowCount < 100) {  // Limit to 100 records
            String currentName = rs.getString("first_name") + " " + rs.getString("last_name");
            if (!currentName.equals(previousName)) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.printf("%-16s", rs.getString(i));
                }
                System.out.println();
            }
            previousName = currentName;
            rowCount++;
        }
    }

    // Helper method to execute query and print results
    protected void executeEmployeeQuery(String query) throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        // Print column headers
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.printf("%-16s", metaData.getColumnName(i));
        }
        System.out.println();
        // Print result
        int rowCount = 0;
        while (rs.next() && rowCount < 100) {  // Limit to 100 records
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.printf("%-16s", rs.getString(i));
            }
            System.out.println();
            rowCount++;
        }
    }

    protected void executeDepartmentQuery(String query) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-12s %-16s%n", "Dept No", "Average Salary");
            System.out.println("----------------------------");

            while (rs.next()) {
                String deptNo = rs.getString("dept_no");
                double avgSalary = rs.getDouble("average_salary");
                System.out.printf("%-12s %-16.2f%n", deptNo, avgSalary);
            }
        }
    }

    protected void executeDepartmentQueryByName(String query) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.printf("%-20s %-16s%n", "Dept Name", "Average Salary");
            System.out.println("----------------------------------");

            while (rs.next()) {
                String deptName = rs.getString("dept_name");
                double avgSalary = rs.getDouble("avg_salary");
                System.out.printf("%-20s %-16.2f%n", deptName, avgSalary);
            }
        }
    }

    protected void executeDepartmentQueryWithNames(String query) throws SQLException {
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            // Print column headers
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeDepartmentQueryAndPrintResults(String query) throws SQLException {
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String department = resultSet.getString("department");
                double avgSalary = resultSet.getDouble("avg_salary");
                System.out.printf("%-30s %.2f%n", department, avgSalary);
            }
        }
    }

    // Metod to execute a query and print the results
    protected void executeQueryAndPrintResults(String query) {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-16s", metaData.getColumnName(i));
            }
            System.out.println();

            // Print query results
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

    protected int executeSingleValueQuery(String query) {
        int result = 0;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String executeSingleValueQueryStr(String query) throws SQLException {
        StringBuilder result = new StringBuilder();

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String deptNo = rs.getString(1); // dept_no sütununu al
                int employeeCount = rs.getInt(2); // employee_count sütununu al
                result.append(String.format("Department No: %s, Employee Count: %d%n", deptNo, employeeCount));
            }
        }

        return result.toString();
    }
}


