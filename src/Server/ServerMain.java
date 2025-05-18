package Server;

import Shared.RemoteModel;
import Model.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
  private static final int PORT = 12345;
  private static List<ObjectOutputStream> clientOutputs = new ArrayList<>();


  public static void main(String[] args) {
    Model rawModel = new Model();
    RemoteModelManager modelWithBroadcast = new RemoteModelManager(rawModel);
    Employee employee1 = new Employee("dupa","sigma");
    DriverLicense driverLicense1 = new DriverLicense("1234567891",true,true,true,true,true);
    Customer customer1 = new Customer("1234567891", "AN445885",
        "Hata", "6666666", "Roshan@getmyball.com",
        new DriverLicense("dupa888", true, true, true, true, true),
        "Thailand", "1999-02-17");
    Vehicle vehicle1 = new Van("Volkswagen", "Civic", "Brown", "Electric", "AB98666", 21, 101, 42, 2, 7, 1000,true);
    Vehicle vehicle2 = new Ufo("Volkswagen", "Golf", "Silver", "Hybrid", "AB78499", 25, 84, 39, 4, 3, false);
    Booking bookingTest = new Booking(1,customer1.getVIAId(),employee1.getId(),
        LocalDate.now().plusDays(1),LocalDate.now().plusDays(5),vehicle1);
    rawModel.addVehicle(vehicle1);
    rawModel.addVehicle(vehicle2);
    rawModel.addEmployees(employee1);
    rawModel.addCustomer(customer1);
    rawModel.addTheBooking(bookingTest,customer1);
    rawModel.addPropertyChangeListener(evt -> {
      synchronized (clientOutputs) {
        for (ObjectOutputStream out : clientOutputs) {
          try {
            out.writeObject(evt);
            out.flush();
          } catch (IOException e) {
            System.err.println("[Server] Failed to send event: " + e.getMessage());
          }
        }
      }
    });

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server is running on port " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        synchronized (clientOutputs) {
          clientOutputs.add(out);
        }

        new Thread(new ServerHandler(clientSocket, modelWithBroadcast, in, out)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}