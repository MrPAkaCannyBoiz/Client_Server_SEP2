package Application;

import View.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory
{
  private ViewModelFactory viewModelFactory;
  private AddVehicleView addVehicleView;
  private BookingView bookingView;
  private ChooseCustomerView chooseCustomerView;
  private ChooseEmployeeView chooseEmployeeView;
  private CustomerPageView customerPageView;
  private EmployeePageView employeePageView;
  private MainPageView mainPageView;
  private NewBookingViewForCustomer newBookingViewForCustomer;
  private NewBookingViewForEmployee newBookingViewForEmployee;
  private NewCustomerView newCustomerView;


  private Stage primaryStage;
  private FXMLLoader fxmlLoader;

  public ViewFactory(ViewModelFactory viewModelFactory, Stage primaryStage)
  {
    this.viewModelFactory = viewModelFactory;
    this.primaryStage = primaryStage;
  }

  public void getMainPageView() throws IOException
  {
    fxmlLoader = new FXMLLoader(getClass().getResource("../View/MainPageView.fxml"));
    fxmlLoader.setControllerFactory(somethingidk ->
    {
      MainPageView controller = new MainPageView();
      controller.setViewFactory(this);
      return controller;
    });

    Scene scene = new Scene(fxmlLoader.load());
    primaryStage.setTitle("VIACarRental GUI");
    primaryStage.setScene(scene);
    primaryStage.show();
    mainPageView = fxmlLoader.getController();
  }


  public void getAddVehicleView()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AddVehicleView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      AddVehicleView controller = new AddVehicleView(viewModelFactory.getAddVehicleViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      //new window: use new stage
      Stage addNewVehicleStage = new Stage();
      Scene scene = new Scene(loader.load());
      addNewVehicleStage.setTitle("SEP2 VIACarRental Add new Vehicle page");
      addNewVehicleStage.setScene(scene);
      addNewVehicleStage.show();
      loader.getController();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void getChooseCustomerView()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ChooseCustomerView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      ChooseCustomerView controller = new ChooseCustomerView
          (viewModelFactory.getChooseCustomerViewModel(),viewModelFactory.getVehicleBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      //same window: use primary Stage
      Scene scene = new Scene(loader.load());
      primaryStage.setTitle("SEP2 VIACarRental choose exist customer page");
      primaryStage.setScene(scene);
      primaryStage.show();
      loader.getController();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void getEmployeePageView()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EmployeePageView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      EmployeePageView controller = new EmployeePageView
          (viewModelFactory.getVehicleBookingViewModel(),viewModelFactory.getNewBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      //same window: use primary Stage
      Scene scene = new Scene(loader.load());
      primaryStage.setTitle("SEP2 VIACarRental employee page");
      primaryStage.setScene(scene);
      primaryStage.show();
      loader.getController();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void getCustomerPageView()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CustomerPageView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      CustomerPageView controller = new CustomerPageView
          (viewModelFactory.getVehicleBookingViewModel(),viewModelFactory.getNewBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      //same window: use primary Stage
      Scene scene = new Scene(loader.load());
      primaryStage.setTitle("SEP2 VIACarRental customer page");
      primaryStage.setScene(scene);
      primaryStage.show();
      loader.getController();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }


  public void getNewBookingViewForCustomer()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewBookingViewForCustomer.fxml"));
    loader.setControllerFactory(parameter ->
    {
      NewBookingViewForCustomer controller =
          new NewBookingViewForCustomer(viewModelFactory.getNewBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      //new window: put new Stage
      Stage newBookingStage = new Stage();
      Scene scene = new Scene(loader.load());
      newBookingStage.setTitle("VIACarRental (Make new booking (Customer))");
      newBookingStage.setScene(scene);
      newBookingStage.show();
      loader.getController();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void getNewBookingViewForEmployee() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewBookingViewForEmployee.fxml"));
    loader.setControllerFactory(parameter ->
    {
      NewBookingViewForEmployee controller =
          new NewBookingViewForEmployee(viewModelFactory.getNewBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    //new window: put new Stage
    Stage newBookingStage = new Stage();
    Scene scene = new Scene(loader.load());
    newBookingStage.setTitle("VIACarRental make new booking");
    newBookingStage.setScene(scene);
    newBookingStage.show();
    loader.getController();
  }

  public void getChooseEmployeeView() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ChooseEmployeeView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      ChooseEmployeeView controller =
          new ChooseEmployeeView
              (viewModelFactory.getChooseEmployeeViewModel(),viewModelFactory.getVehicleBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    //same window: use primary stage
    Scene scene = new Scene(loader.load());
    primaryStage.setTitle("VIACarRental choose employee");
    primaryStage.setScene(scene);
    primaryStage.show();
    loader.getController();
  }

  public void getBookingView() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/BookingView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      BookingView controller =
          new BookingView(viewModelFactory.getVehicleBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });

    Stage bookingStage = new Stage();
    Scene scene = new Scene(loader.load());
    bookingStage.setTitle("Booking List");
    bookingStage.setScene(scene);
    bookingStage.show();
    loader.getController();
  }

  public void getNewEmployeeView() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewEmployeeView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      NewEmployeeView controller =
          new NewEmployeeView(viewModelFactory.getNewEmployeeViewModel());
      controller.setViewFactory(this);
      return controller;
    });

    Stage newEmployeeStage = new Stage();
    Scene scene = new Scene(loader.load());
    newEmployeeStage.setTitle("Booking List");
    newEmployeeStage.setScene(scene);
    newEmployeeStage.show();
    loader.getController();
  }

  public void getCustomerBookingManagerView() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CustomerBookingManagerView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      CustomerBookingManagerView controller =
          new CustomerBookingManagerView(viewModelFactory.getVehicleBookingViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    //new window
    Stage stage = new Stage();
    Scene scene = new Scene(loader.load());
    stage.setTitle("Booking List");
    stage.setScene(scene);
    stage.show();
    loader.getController();
  }

  public void getEditingCustomerView() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditingCustomerView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      EditingCustomerView controller =
          new EditingCustomerView(viewModelFactory.getVehicleBookingViewModel(),viewModelFactory.getNewCustomerViewModel());
      controller.setViewFactory(this);
      return controller;
    });

    Stage editingCustomerStage = new Stage();
    Scene scene = new Scene(loader.load());
    editingCustomerStage.setTitle("Edit customer for employee");
    editingCustomerStage.setScene(scene);
    editingCustomerStage.show();
    loader.getController();
  }

  public void getNewCustomerViewAsNewPage()
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewCustomerView.fxml"));
    loader.setControllerFactory(parameter ->
    {
      NewCustomerView controller = new NewCustomerView(viewModelFactory.getNewCustomerViewModel());
      controller.setViewFactory(this);
      return controller;
    });
    try
    {
      Stage stage = new Stage();
      //offset a little bit
      stage.setX(primaryStage.getX() + primaryStage.getWidth()/4);
      stage.setY(primaryStage.getY() + primaryStage.getHeight()/10);
      Scene scene = new Scene(loader.load());
      stage.setTitle("SEP2 VIACarRental new customer page");
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }






}
