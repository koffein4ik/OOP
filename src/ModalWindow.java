import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class ModalWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button close = new Button("Close");
        close.setOnAction(event->window.close());
        pane.getChildren().add(close);
        Scene scene = new Scene(pane, 100, 100);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void newWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button close = new Button("Close");
        close.setOnAction(event->window.close());
        pane.getChildren().add(close);
        Scene scene = new Scene(pane, 100, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
