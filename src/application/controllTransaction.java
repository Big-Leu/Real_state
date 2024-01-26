package application;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class controllTransaction {

	@FXML
	private Label agent;
	@FXML
	private Image img1;
	@FXML
	private Image img2;
	@FXML
	private Circle btnClose;
	@FXML
	private DatePicker date;
	@FXML
	private TextField mode;

	@FXML
	private Label property;

	@FXML
	private Label seller;

	@FXML
	private HBox t1;

	@FXML
	private HBox t2;

	@FXML
	private HBox t3;

	@FXML
	private TextField textb;
    @FXML
    private Rectangle rec;
    @FXML
    private Rectangle rec1;
	@FXML
	private TextField type;
	@FXML
	private ImageView img;
	private int sellerid;
	private int agentid;

	public void search(String pro, String agent1, String t, int a) throws IOException {
		property.setText(pro);
		agent.setText(agent1);
		type.setText(t);
		agentid = a;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("with tab1(t1,t2) as(select * from owns where property_id='" + pro + "')\r\n"
					+ "select seller.name,tab1.t2 from seller,tab1\r\n" + "where seller.seller_id=tab1.t2;");
			Statement stmt2 = con.createStatement();
			ResultSet set2 = stmt2.executeQuery("with tab1(t1) as(select agent_id from property where property_id='"
					+ pro + "')\r\n" + "select a.image from agent a,tab1\r\n" + "where a.agent_id=tab1.t1;");
			Statement stmt3 = con.createStatement();
			ResultSet set3 = stmt3.executeQuery("select * from property where property_id='"+pro+"';");
			while(set3.next()) {
				Blob bolb = set3.getBlob("image");
				InputStream inputStream = bolb.getBinaryStream();
				img2 = new Image(inputStream);
			}
			rec1.setFill(new ImagePattern(img2));
			while (set2.next()) {
				Blob bolb = set2.getBlob(1);
				InputStream inputStream = bolb.getBinaryStream();
				img1 = new Image(inputStream);
			
			}
			while (set.next()) {
				sellerid = set.getInt(2);
				seller.setText(set.getString(1));
			}
			rec.setFill(new ImagePattern(img1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleMouseEvent(MouseEvent event) {
		if (event.getSource() == btnClose) {
			System.exit(0);
		}
	}

	@FXML
	public void complete(ActionEvent event) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("select * from property where property_id='" + property.getText() + "';");
			BigDecimal big2 = new BigDecimal("696969");
			String Address = "";
			String PropertySize = "";
			String date2 = "";
			String room2 = "";
			while (set.next()) {
				big2 = set.getBigDecimal(4);
				Address = set.getString(2);
				PropertySize = set.getString(11);
				date2 = set.getString(10);
				room2 = set.getString(3);

			}
			BigDecimal big = new BigDecimal(agentid);
			PreparedStatement pstmt = con.prepareStatement("insert into transaction values (?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, property.getText());
			pstmt.setInt(2, sellerid);
			pstmt.setString(3, textb.getText());
			pstmt.setBigDecimal(4, big);
			pstmt.setString(5, type.getText());
			pstmt.setString(6, mode.getText());
			pstmt.setString(7, date.getValue().toString());
			pstmt.setBigDecimal(8, big2);
			pstmt.setString(9, Address);
			pstmt.setString(10, PropertySize);
			pstmt.setString(11, date2);
			pstmt.setString(12, room2);
			pstmt.executeUpdate();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			textb.setStyle(
					"-fx-background-color:#2d3136;\n-fx-background-radius:5px 20px 5px 20px;\n-fx-border-radius:5px 20px 5px 20px;\n-fx-border-color:RED;");
		}
	}

}
