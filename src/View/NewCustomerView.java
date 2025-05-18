package View;

import Application.ViewFactory;
import ViewModel.NewCustomerViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class NewCustomerView
{
  private NewCustomerViewModel viewModel;
  private ViewFactory viewFactory;

  @FXML TextField nameTextField;
  @FXML TextField phoneNoTextField;
  @FXML TextField emailTextField;
  @FXML ComboBox<String> nationalityComboBox;
  @FXML DatePicker dobDatePicker;
  @FXML TextField cprTextField;
  @FXML TextField passNoTextField;
  @FXML TextField licenceNoTextField;
  @FXML CheckBox categoryACheckBox;
  @FXML CheckBox categoryBCheckBox;
  @FXML CheckBox categoryCCheckBox;
  @FXML CheckBox categoryDCheckBox;
  @FXML CheckBox categoryXCheckBox;
  @FXML Button backButton;
  @FXML Button signInAndSignUpButton;

  public NewCustomerView(NewCustomerViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void initialize()
  {
    nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
    phoneNoTextField.textProperty().bindBidirectional(viewModel.phoneNoProperty());
    emailTextField.textProperty().bindBidirectional(viewModel.emailProperty());
    cprTextField.textProperty().bindBidirectional(viewModel.cprProperty());
    passNoTextField.textProperty().bindBidirectional(viewModel.passNoProperty());
    licenceNoTextField.textProperty().bindBidirectional(viewModel.licenceNumberProperty());
    categoryACheckBox.selectedProperty().bindBidirectional(viewModel.isCategoryAProperty());
    categoryBCheckBox.selectedProperty().bindBidirectional(viewModel.isCategoryBProperty());
    categoryCCheckBox.selectedProperty().bindBidirectional(viewModel.isCategoryCProperty());
    categoryDCheckBox.selectedProperty().bindBidirectional(viewModel.isCategoryDProperty());
    categoryXCheckBox.selectedProperty().bindBidirectional(viewModel.isCategoryXProperty());
    dobDatePicker.valueProperty().bindBidirectional(viewModel.dobProperty());

    nationalityComboBox.setItems(viewModel.getNationalityList());
    nationalityComboBox.valueProperty().bindBidirectional(viewModel.selectedNationalityProperty());

    phoneNoTextField.setTextFormatter(allowOnlyNumber());
    cprTextField.setTextFormatter(allowOnlyNumber());
    this.backButton.setDisable(false);
    this.signInAndSignUpButton.setDisable(false);
  }

  public void setViewFactory(ViewFactory viewFactory)
  {
    this.viewFactory = viewFactory;
  }

  public void setButtonsDisabledForNewTab()
  {
    this.backButton.setDisable(true);
    this.signInAndSignUpButton.setDisable(true);
  }

  private TextFormatter<Integer> allowOnlyNumber()
  {
    return new TextFormatter<Integer>(change ->
    {
      String text = change.getControlNewText();
      if (text.matches("\\d*"))
      {
        return change;
      }
      return null;
    });
  }

  public void onSignUpButtonClicked()
  {
    try
    {
      viewModel.addCustomer();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText("Signed up");
      alert.setContentText("Customer is now added");
      alert.showAndWait();
    }
    catch (IllegalArgumentException | NullPointerException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText("Information is not filled correctly");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  public void onSignInAndSignUpButtonClicked()
  {
    try
    {
      viewModel.addCustomerAndSetToCurrentCustomer();
      viewFactory.getCustomerPageView();
    }
    catch (IllegalArgumentException | NullPointerException e)
    {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText("Information is not filled correctly");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  public void onBackToMainButtonClicked() throws IOException
  {
    viewFactory.getMainPageView();
  }

}
