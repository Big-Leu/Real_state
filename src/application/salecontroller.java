package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class salecontroller {

	@FXML
	private Label Cash;

	@FXML
	private Label address;

	@FXML
	private Label address2;

	@FXML
	private Circle btnClose;

	@FXML
	private Label date;

	@FXML
	private Label date2;

	@FXML
	private Label date21;

	@FXML
	private Label date211;

	@FXML
	private Label date2111;

	@FXML
	private Label price;

	@FXML
	private Label price2;

	@FXML
	private Label rent;

	@FXML
	private Label sold;
	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;

	public void pop(Agent agent) {
		Cash.setText(agent.getSum());
		rent.setText(String.valueOf(agent.getrent()));
		sold.setText(String.valueOf(agent.getsold()));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(
					"select * from transaction where agent_id=" + agent.getid() + " and transaction_type='sale';");
			String solddate = "Sale Date:";
			String Soldprice = "Price:";
			String Soldaddress = "Address:";
			while (set.next()) {
				date2.setText(solddate + " " + set.getString(7));
				price2.setText(Soldprice + " " + String.valueOf(set.getBigDecimal(8)));
				address2.setText(Soldaddress + " " + set.getString(9));
			}
			Statement stmt2 = con.createStatement();
			ResultSet set2 = stmt2.executeQuery(
					"select * from transaction where agent_id=" + agent.getid() + " and transaction_type='rent';");
			while (set2.next()) {
				date.setText(solddate + " " + set2.getString(7));
				price.setText(Soldprice + " " + String.valueOf(set2.getBigDecimal(8)));
				address.setText(Soldaddress + " " + set2.getString(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleMouseEvent(MouseEvent event) throws IOException {
		if (event.getSource() == btnClose) {
			root = FXMLLoader.load(getClass().getResource("/application/AGENT.fxml"));
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

}
