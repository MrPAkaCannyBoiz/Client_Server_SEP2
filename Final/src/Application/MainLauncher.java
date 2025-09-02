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

}
