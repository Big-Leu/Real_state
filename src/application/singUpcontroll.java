package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class singUpcontroll implements Initializable {

	@FXML
	private Button Sumit;

	@FXML
	private AnchorPane a1;

	@FXML
	private AnchorPane a2;

	@FXML
	private AnchorPane a21;

	@FXML
	private TextField address;

	@FXML
	private Button b2;

	@FXML
	private Button back;

	@FXML
	private Circle btnClose;

	@FXML
	private ImageView danger;

	@FXML
	private TextField email;

	@FXML
	private Label error;

	@FXML
	private TextField name;

	@FXML
	private PasswordField pass;

	@FXML
	private TextField phonenumber;

	@FXML
	private TextField userid;
	@FXML
	private Label suc;
	@FXML
	private ChoiceBox<String> usertype;
	private String[] type= {"Buyer","Seller"};
	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;

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
	public void next(ActionEvent event) {
		a21.toFront();
	}

	@FXML
	public void back(ActionEvent event) {
		a2.toFront();
	}

	@FXML
	public void sumit(ActionEvent event) throws SQLException {
		if (usertype.getValue().compareToIgnoreCase("seller") == 0) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
				if (!con.isClosed()) {
					System.out.println("Connection is established ");
				}
				BigDecimal big = new BigDecimal(phonenumber.getText());
				PreparedStatement pstmt = con.prepareStatement("insert into seller values (?,?,?,?,?,?)");
				pstmt.setInt(1, Integer.parseInt(userid.getText()));
				pstmt.setString(2, name.getText());
				pstmt.setString(3, address.getText());
				pstmt.setString(4, pass.getText());
				pstmt.setBigDecimal(5, big);
				pstmt.setString(6, email.getText());
				pstmt.executeUpdate();
				suc.setStyle("-fx-opacity:1;");
			} catch (Exception e) {
				e.printStackTrace();
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				userid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}
		} else if (usertype.getValue().compareToIgnoreCase("buyer") == 0) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
				if (!con.isClosed()) {
					System.out.println("Connection is established ");
				}
				BigDecimal big = new BigDecimal(phonenumber.getText());
				PreparedStatement pstmt = con.prepareStatement("insert into buyer values (?,?,?,?,?,?)");
				pstmt.setInt(5, Integer.parseInt(userid.getText()));
				pstmt.setString(1, name.getText());
				pstmt.setString(6, address.getText());
				pstmt.setString(2, pass.getText());
				pstmt.setBigDecimal(4, big);
				pstmt.setString(3, email.getText());
				pstmt.executeUpdate();
				suc.setStyle("-fx-opacity:1;");
			} catch (Exception e) {
				a2.toFront();
				e.printStackTrace();
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				userid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}

		} else if (usertype.getValue().compareToIgnoreCase("Agent") == 0) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
				if (!con.isClosed()) {
					System.out.println("Connection is established ");
				}
				BigDecimal big = new BigDecimal(phonenumber.getText());
				PreparedStatement pstmt = con.prepareStatement("insert into agent values (?,?,?,?,?,?)");
				pstmt.setInt(1, Integer.parseInt(userid.getText()));
				pstmt.setString(2, name.getText());
				pstmt.setString(5, address.getText());
				pstmt.setString(6, pass.getText());
				pstmt.setBigDecimal(4, big);
				pstmt.setString(3, email.getText());
				pstmt.executeUpdate();
				suc.setStyle("-fx-opacity:1;");

			} catch (Exception e) {
				a2.toFront();
				e.printStackTrace();
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				userid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}
		} else if (usertype.getValue().compareToIgnoreCase("realstateoffice") == 0) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
				if (!con.isClosed()) {
					System.out.println("Connection is established ");
				}
				BigDecimal big = new BigDecimal(phonenumber.getText());
				PreparedStatement pstmt = con.prepareStatement("insert into realstateoffice values (?,?,?,?,?,?)");
				pstmt.setInt(1, Integer.parseInt(userid.getText()));
				pstmt.setString(2, name.getText());
				pstmt.setString(5, address.getText());
				pstmt.setString(6, pass.getText());
				pstmt.setBigDecimal(4, big);
				pstmt.setString(3, email.getText());
				pstmt.executeUpdate();
				suc.setStyle("-fx-opacity:1;");
			} catch (Exception e) {
				a2.toFront();
				e.printStackTrace();
				danger.setStyle("-fx-opacity:1");
				error.setStyle("-fx-opacity:1;");
				userid.setStyle("-fx-background-radius:50px;\n-fx-border-radius:50px;\n-fx-border-color:RED;");
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usertype.getItems().addAll(type);
		danger.setStyle("-fx-opacity:0;");
		error.setStyle("-fx-opacity:0;");
		suc.setStyle("-fx-opacity:0;");
	}

}
