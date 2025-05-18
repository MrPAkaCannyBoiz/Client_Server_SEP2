package Application;

import Client.ClientSocketHandler;
import Shared.RemoteModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class MainLauncher extends Application
{
  public void start(Stage primaryStage) throws Exception
  {
    // Connect to the server
    //Socket socket = new Socket("localhost", 12345); // Make sure this matches the server port
    System.out.println("[Launcher] Starting MainLauncher...");
    try
    {
      RemoteModel remoteModel = new ClientSocketHandler("localhost",
          12345); //  Proxy
      System.out.println("[Client] Socket connection attempted");
      ViewModelFactory viewModelFactory = new ViewModelFactory(remoteModel);
      ViewFactory viewFactory = new ViewFactory(viewModelFactory, primaryStage);
      viewFactory.getMainPageView();
    }
    catch (IOException e)
    {
      System.err.println(
          "[Launcher] Could not connect to server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
//  @Override public void start(Stage primaryStage) throws Exception
//  {
//    ModelFactory modelFactory = new ModelFactory();
//    ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
//    Customer testCustomer = new Customer("1234567890", "AN445886",
//        "Dominus", "6666666", "Tomertor@getmyball.com",
//        new DriverLicense("dupa667", true, true, true, true, true),
//        "Martian", "1999-02-12");
//    Customer testCustomer1 = new Customer("1234567891", "AN445885",
//        "Hata", "6666666", "Roshan@getmyball.com",
//        new DriverLicense("dupa888", true, true, true, true, true),
//        "Thailand", "1999-02-17");
//
//    viewModelFactory.getAddVehicleViewModel().getModel().addCustomer(testCustomer);
//    modelFactory.getModel().addCustomer(testCustomer1);
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Car(
//      "Toyota", "Corolla", "Red", "Hybrid", "AB12345",
//      25.0, 100.0, 50.0, 4, 5));
//    modelFactory.getModel().addEmployees(new Employee("dupa","sigma"));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Truck("Chevrolet", "Elantra", "White", "Diesel", "AB44188", 44, 183, 32, 2, 7, 3105,true));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Truck("Volkswagen", "Rio", "Green", "Petrol", "AB84147", 39, 130, 45, 2, 3, 3732,false));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new MotorCycle("Chevrolet", "Elantra", "Silver", "Diesel", "AB14969", 31, 82, 36, 6, 3, false));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Van("Volkswagen", "Civic", "Brown", "Electric", "AB98666", 21, 101, 42, 2, 7, 1000,true));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Truck("Nissan", "Golf", "Silver", "Hybrid", "AB75784", 50, 162, 35, 6, 6, 1037,false));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Ufo("Kia", "Corolla", "White", "Petrol", "AB99679", 29, 192, 66, 2, 5, true));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Truck("Hyundai", "Civic", "Silver", "Diesel", "AB53788", 38, 163, 44, 2, 2, 1047,true));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new SportsCar("Kia", "Malibu", "Brown", "Petrol", "AB56028", 33, 127, 61, 6, 7, 224, true));
//    viewModelFactory.getAddVehicleViewModel().getModel().addVehicle(new Ufo("Volkswagen", "Golf", "Silver", "Hybrid", "AB78499", 25, 84, 39, 4, 3, false));
//
////    viewModelFactory.getAddVehicleViewModel().getModel().makeBookingByCustomer(
////        LocalDate.of(2025, 7, 3),
////        LocalDate.of(2025, 7, 9),
////        viewModelFactory.getAddVehicleViewModel().getModel().getCustomers().get(0),
////        viewModelFactory.getAddVehicleViewModel().getModel().getVehicles().get(0)
////    );
//    viewModelFactory.getAddVehicleViewModel().getModel().makeBookingByCustomer(
//        LocalDate.of(2025, 7, 3),
//        LocalDate.of(2025, 7, 9),
//        testCustomer,
//        viewModelFactory.getAddVehicleViewModel().getModel().getVehicles().get(0)
//    );
//
//    Catalogue.getInstance().logUsage(
//        testCustomer,
//        viewModelFactory.getAddVehicleViewModel().getModel().getVehicles().get(0),
//        "booked"
//    );
//    ViewFactory viewFactory = new ViewFactory(viewModelFactory,primaryStage);
//    viewFactory.getMainPageView();
//  }
}
