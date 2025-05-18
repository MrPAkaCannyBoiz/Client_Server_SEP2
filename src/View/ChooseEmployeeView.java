package View;

import Application.ViewFactory;
import Model.Employee;
import ViewModel.ChooseEmployeeViewModel;
import ViewModel.VehicleBookingViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ChooseEmployeeView
{
  private ChooseEmployeeViewModel chooseEmployeeViewModel;
  private VehicleBookingViewModel employeePageVM;
  @FXML private ListView<Employee> employeeListView;
  @FXML private TextField passwordField;
  private static String fixedPassword = "VIA1234";
  private ViewFactory viewFactory;

  public ChooseEmployeeView(ChooseEmployeeViewModel viewModel, VehicleBookingViewModel employeePageVM)
  {
    this.chooseEmployeeViewModel = viewModel;
    this.employeePageVM = employeePageVM;
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void initialize()
  {
    employeeListView.setItems(chooseEmployeeViewModel.getEmployees());
  }

  public void onBackButtonClicked() throws IOException
  {
    viewFactory.getMainPageView();
  }

  public void onSignInButtonClicked()
  {
    if (passwordField.getText().equals(fixedPassword))
    {
      Employee selectedEmployee = employeeListView.getSelectionModel()
          .getSelectedItem();
      try
      {
        chooseEmployeeViewModel.selectAndSetToCurrentEmployee(selectedEmployee);
        employeePageVM.setSelectedEmployee(selectedEmployee);
        viewFactory.getEmployeePageView();
      }
      catch (NullPointerException e)
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Something's wrong");
        alert.setContentText("Please select employee before sign in");
        alert.showAndWait();
      }
    }
    else
    {
      new Alert(Alert.AlertType.WARNING,
          "Wrong password, ask your boss if you didn't know").showAndWait();
    }
  }

  public void onAddNewEmployeeClicked() throws IOException
  {
    viewFactory.getNewEmployeeView();
  }

  public void onRemoveEmployeeButtonClicked()
  {
    if (passwordField.getText().equals(fixedPassword))
    {
      Employee selectedEmployee = employeeListView.getSelectionModel()
          .getSelectedItem();
      if (selectedEmployee == null)
      {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Please select employee");
        alert.setContentText("Please select employee before sign in");
        alert.showAndWait();
      }
      try
      {
        chooseEmployeeViewModel.removeEmployee(selectedEmployee);
      }
      catch (NullPointerException e)
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Something's wrong");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    else
    {
      new Alert(Alert.AlertType.WARNING,
          "Wrong password, ask your boss if you didn't know").showAndWait();
    }
  }

}
