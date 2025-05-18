package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.util.Objects;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainPageView.fxml"));
    Parent root = loader.load();

    primaryStage.setTitle("VIACarRental");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
