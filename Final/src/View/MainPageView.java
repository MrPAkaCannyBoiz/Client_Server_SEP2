package View;

import Application.ViewFactory;
import ViewModel.VehicleBookingViewModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPageView {

    public Button employeePanelButton;
    public Button customerPanelButton;
    @FXML private Text timeText;
    @FXML private Text dateText;
    private ViewFactory viewFactory;


    public MainPageView ()
    {

    }

    public void setViewFactory(ViewFactory viewFactory)
    {
        this.viewFactory = viewFactory;
    }

    @FXML
    public void initialize() {
        startClock();
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime now = LocalDateTime.now();
            timeText.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            dateText.setText(now.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    @FXML
    private void onEmployeePanelButton() throws IOException
    {
        viewFactory.getChooseEmployeeView();
    }

    @FXML
    private void onCustomerPanelButton()
    {
        viewFactory.getChooseCustomerView();
    }

}
