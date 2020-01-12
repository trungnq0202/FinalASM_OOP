import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main_client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("resources/view/main.fxml"));
        primaryStage.setTitle("Co Ca Ngua");
        primaryStage.setScene(new Scene(root, 1400, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}