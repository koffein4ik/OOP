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
        //Button close = new Button("Close");
        //close.setOnAction(event->window.close());
        //pane.getChildren().add(close);
        Scene scene = new Scene(pane, 100, 100);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void newWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        //Button close = new Button("Close");
        Button fact1 = new Button("Human");
        fact1.setLayoutX(10);
        fact1.setLayoutY(10);
        fact1.setPrefWidth(100);
        //fact1.setOnAction(event->CreateClass.createWindow(humanFactory.HumanType.values()));
        Button fact2 = new Button("Plane");
        fact2.setLayoutX(130);
        fact2.setLayoutY(10);
        fact2.setPrefWidth(100);
        Button fact3 = new Button("Airport");
        fact3.setLayoutX(250);
        fact3.setLayoutY(10);
        fact3.setPrefWidth(100);
        //close.setOnAction(event->window.close());
        pane.getChildren().addAll(fact1, fact2, fact3);
        Scene scene = new Scene(pane, 360, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
