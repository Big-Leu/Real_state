package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class loginControll implements Initializable {
	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;
	@FXML
	private Circle btnClose;
	@FXML
	private ImageView danger;
	@FXML
	TextField user;
	@FXML
	TextField pass;
	@FXML
	private Label error;
	@FXML
	private Button loginb;

	@FXML
	public void login(ActionEvent event) {
		try {
			String username = user.getText();
			String password = pass.getText();
			if (!(username.compareToIgnoreCase("root") == 0 && password.compareToIgnoreCase("@Abhi9889") == 0)) {
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				user.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			} else {
				Parent root;

				root = FXMLLoader.load(getClass().getResource("/application/p1.fxml"));

				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
						stage.setX(event.getScreenX() - xOffset);
						stage.setY(event.getScreenY() - yOffset);
					}

				});

				Scene scene = new Scene(root, Color.TRANSPARENT);
				stage.setScene(scene);
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void signUP(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/signUP.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}

		});

		scene = new Scene(root, Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	public void handleMouseEvent(MouseEvent event) {
		if (event.getSource() == btnClose) {
			System.exit(0);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		danger.setStyle("-fx-opacity:0;");
		error.setStyle("-fx-opacity:0;");

	}
}
