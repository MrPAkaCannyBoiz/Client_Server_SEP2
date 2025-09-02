package Dao;

import Application.DatabaseConnection;
import Model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private Connection connection;

    public EmployeeDAOImpl() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (employeeid, name, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("[DAO] Inserting employee with ID: " + employee.getId());

            stmt.setInt(1, employee.getId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding employee", e);
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employee WHERE employeeid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getString("name"), rs.getString("role"), rs.getInt("employeeid")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employee", e);
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getString("name"), rs.getString("role"), rs.getInt("employeeid")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employees", e);
        }
        return employees;
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name=?, role=? WHERE employeeid=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getRole());
            stmt.setInt(3, employee.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }

    @Override
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE employeeid=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee", e);
        }
    }
}
