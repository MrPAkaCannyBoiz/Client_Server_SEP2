package View;

import Application.ViewFactory;
import ViewModel.NewEmployeeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewEmployeeView
{
  private NewEmployeeViewModel newEmployeeViewModel;
  @FXML private TextField nameTextField;
  @FXML private TextField passwordTextField;
  @FXML private ComboBox<String> roleComboBox;
  private static String fixedPassword = "VIA1234";
  private ViewFactory viewFactory;

  public NewEmployeeView(NewEmployeeViewModel viewModel)
  {
    this.newEmployeeViewModel = viewModel;
  }

  public void initialize()
  {
    nameTextField.textProperty().bindBidirectional(newEmployeeViewModel.nameProperty());
    //set independent ComboBox
    roleComboBox.setItems(newEmployeeViewModel.getRoleList());
    //bind comboBox
    roleComboBox.valueProperty().bindBidirectional(
        newEmployeeViewModel.selectedRoleProperty());
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void addEmployee()
  {
    try
    {
      if (passwordTextField.getText().equals(fixedPassword))
      {
        newEmployeeViewModel.addEmployee();
      }
      else
      {
        throw new IllegalArgumentException("Wrong password");
      }
    }
    catch (IllegalArgumentException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText("Something's wrong");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

}
