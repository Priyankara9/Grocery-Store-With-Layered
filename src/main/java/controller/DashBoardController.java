package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardController {
    public AnchorPane pane;
    public Label lblTime;
    public void initialize(){
        setTimeOnScreen();

    }

    private void setTimeOnScreen() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO,e->
                lblTime.setText(LocalDateTime.now().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd  HH:mm:ss")))),new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void customerButtonOnAction(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void itemsButtonOnAction(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Item.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
