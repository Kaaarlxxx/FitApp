package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Design.fxml"));
        primaryStage.setTitle("Fit App");
        Scene scene = new Scene(root, 600, 820);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}


















