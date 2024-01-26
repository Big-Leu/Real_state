package application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class building implements Initializable {

	@FXML
	private Label Area;

	@FXML
	private Label Area12;

	@FXML
	private Label Area13;

	@FXML
	private Label BHK;

	@FXML
	private Label Bath;

	@FXML
	private Label Beds;

	@FXML
	private Label Price;

	@FXML
	private Label address;

	@FXML
	private Circle btnClose;

	@FXML
	private Label floor;

	@FXML
	private Rectangle red;

	@FXML
	private Label transctionType;
	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;
    private String h;
    private String h2;
    private String h3;
    private int h4;
	public void handleMouseEvent(MouseEvent event) throws IOException {
		if (event.getSource() == btnClose) {
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
		}
	}
  @FXML
  public void conform(ActionEvent event) throws IOException {
	  FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/transaction.fxml"));
		root = loader.load();
		controllTransaction s2 = loader.getController();
		s2.search(h,h2,h3,h4);
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
	public void pop(String property) {
		h=property;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("select * from property\n" + "where property_id='" + property + "';");
			while (set.next()) {
				Blob bolb = set.getBlob("image");
				h2=set.getString("agent_name");
				h3=set.getString("sale_or_rent");
				h4=set.getInt("agent_id");
				InputStream inputStream = bolb.getBinaryStream();
				Image image = new Image(inputStream);
				Price.setText("₹" + String.valueOf(set.getBigDecimal("price")));
				BHK.setText(set.getString("room") + " Builder Floor For Sale in ");
				address.setText(set.getString(2));
				red.setFill(new ImagePattern(image));
				String r = set.getString("room").substring(0, 1);
				Beds.setText(r + " Beds");
				Bath.setText(r + " Baths");
				Area.setText(set.getString(11));
				if (set.getString(7).compareToIgnoreCase("appartment/flat") == 0)
					floor.setText("1 Floor");
				else
					floor.setText(r + " Floor");
				transctionType.setText("For " + set.getString(9));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
