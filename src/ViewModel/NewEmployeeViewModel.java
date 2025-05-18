package ViewModel;


import Model.Employee;
import Shared.RemoteModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class NewEmployeeViewModel
{
  private RemoteModel model;
  private StringProperty name;
  //for comboBox
  //to binding
  private StringProperty selectedRole;
  //to making a list of role
  private ObservableList<String> roleList;

  public NewEmployeeViewModel(RemoteModel model)
  {
    this.model = model;
    this.name = new SimpleStringProperty("");
    this.selectedRole = new SimpleStringProperty("");
    this.roleList = FXCollections.observableArrayList(
        "Fleet Manager","Front Desk", "Vehicle Detailer",
        "Logistics", "Mechanic", "Manager", "Sales Agent", "IT/System Admin",
        "Marketing & Social Media Manager");
  }

  public void addEmployee() throws IOException
  {
    model.addEmployees(new Employee(name.get(), selectedRole.get()));
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty selectedRoleProperty()
  {
    return selectedRole;
  }

  public ObservableList<String> getRoleList()
  {
    return roleList;
  }
}
