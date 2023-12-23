import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("GroceryStore");
        primaryStage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("view/DashBoardForm.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}