package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
public class Main extends Application {
	private double xOffset=0;
	private double yOffset=0;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root=FXMLLoader.load(getClass().getResource("login.fxml"));
		root.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
			
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX()-xOffset);
				stage.setY(event.getScreenY()-yOffset);
			}
			
		});
		Scene scene=new Scene(root,Color.TRANSPARENT);
		String css=this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Login Demo");
		stage.initStyle(StageStyle.TRANSPARENT);
		Image icon=new Image("p69.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.setFullScreen(false);
		stage.show();
	}
}
