package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PaneContoll implements Initializable {

	@FXML
	private Button agentLogin;

	@FXML
	private TextField agentid;

	@FXML
	private PasswordField agentpass;

	@FXML
	private Pane agnet_pane;

	@FXML
	private Button b1;

	@FXML
	private Circle btnClose;

	@FXML
	private Button btn_agentLogin;

	@FXML
	private Button btn_real;

	@FXML
	private ImageView danger;

	@FXML
	private ImageView danger1;

	@FXML
	private Label error;

	@FXML
	private Label error1;

	@FXML
	private Pane realState;

	@FXML
	private TextField realid;

	@FXML
	private PasswordField realpass;

	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;

	@FXML
	public void agent(ActionEvent event) {
		try {
			danger.setStyle("-fx-opacity:0");
			error.setStyle("-fx-opacity:0;");
			agentid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:TRANSPARENT;");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("select agent_id,agent_pass from agent");
			int agentID = Integer.parseInt(agentid.getText());
			String agentPASS = agentpass.getText();
			agentid.setText("");
			agentpass.setText("");
			int flag = 0;
			while (set.next()) 
			{
				if ((set.getInt(1) == agentID) && (set.getString(2).compareTo(agentPASS) == 0)) 
				{
					flag = 1;
					break;
				}
			}
			System.out.println(flag);
			if (flag == 1) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AgentProfile.fxml"));
				root = loader.load();
			Controller21 s2 = loader.getController();
			BigDecimal b2=new BigDecimal(agentID);
			s2.getAgentID(b2);
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
			} else {
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				agentid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}

		} catch (Exception e) {
			e.printStackTrace();
			danger.setStyle("-fx-opacity:1");
			error.setStyle("-fx-opacity:1;");
			agentid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
		}
	}

	@FXML
	public void handleButtonAction(ActionEvent event) {
		if (event.getSource() == btn_real) {
			realState.toFront();
		} else if (event.getSource() == btn_agentLogin) {
			agnet_pane.toFront();
		}
	}

	@FXML
	public void handleMouseEvent(MouseEvent event) throws IOException {
		if (event.getSource() == btnClose) {
			root = FXMLLoader.load(getClass().getResource("/application/login.fxml"));
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
	}

	@FXML
	public void realstateOffice(ActionEvent event) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("select realAgent_id,realagentpass from realstateoffice");
			int agentID = Integer.parseInt(realid.getText());
			String agentPASS = realpass.getText();
			realid.setText("");
			realpass.setText("");
			int flag = 0;
			while (set.next()) {
				if ((set.getInt(1) == agentID) && (set.getString(2).compareTo(agentPASS) == 0)) {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				System.out.println("Opening");
				root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
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
			} else {
				danger1.setStyle("-fx-opacity:1");
				error1.setStyle("-fx-opacity:1;");
				realid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}

		} catch (Exception e) {
			danger1.setStyle("-fx-opacity:1");
			error1.setStyle("-fx-opacity:1;");
			realid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		realState.toFront();
		danger.setStyle("-fx-opacity:0");
		error.setStyle("-fx-opacity:0;");
		danger1.setStyle("-fx-opacity:0");
		error1.setStyle("-fx-opacity:0;");
	}
}
