package ViewModel;

import Model.Employee;
import Shared.RemoteModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;

public class ChooseEmployeeViewModel
{
  private RemoteModel model;
  private ObservableList<Employee> employees;
  private Employee selectedEmployee;

  public ChooseEmployeeViewModel(RemoteModel model)
  {
    this.model = model;
    this.employees = FXCollections.observableArrayList(model.getEmployees());

    model.addPropertyChangeListener("EmployeeEvent",this::updateEmployees);
    List<Employee> loadedEmployees = model.getEmployees();
    employees.setAll(loadedEmployees);
  }

  private void updateEmployees(PropertyChangeEvent event)
  {
    List<Employee> newEmployee = (List<Employee>) event.getNewValue();
    Platform.runLater(()-> employees.setAll(newEmployee));
  }

  public void selectAndSetToCurrentEmployee(Employee selectedEmployee)
  {
    setSelectedEmployee(selectedEmployee);
  }

  public void removeEmployee(Employee employeeToRemove) throws IOException
  {
    model.removeEmployees(employeeToRemove);
  }

  public ObservableList<Employee> getEmployees()
  {
    return employees;
  }


  public Employee getSelectedEmployee()
  {
    return selectedEmployee;
  }

  public void setSelectedEmployee(Employee selectedEmployee)
  {
    this.selectedEmployee = selectedEmployee;
  }
}
