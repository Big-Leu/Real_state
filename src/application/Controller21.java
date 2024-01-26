package application;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller21 implements Initializable {

	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;
	@FXML
	private TableView<Property> table;
	@FXML
	private TableColumn<Property, String> propertyIdColumn;
	@FXML
	private TableColumn<Property, String> addressColumn;
	@FXML
	private TableColumn<Property, String> roomColumn;
	@FXML
	private TableColumn<Property, String> propertyTypeColumn;
	@FXML
	private TableColumn<Property, Long> priceColumn;
	@FXML
	private TableColumn<Property, String> saleOrRentColumn;
	@FXML
	private TableColumn<Property, String> propertySizeColumn;

	@FXML
	private TableView<soldOrRent> table2;
	@FXML
	private TableColumn<soldOrRent, String> rspropertyIdColumn;
	@FXML
	private TableColumn<soldOrRent, String> rsaddressColumn;
	@FXML
	private TableColumn<soldOrRent, String> rsroomColumn;
	@FXML
	private TableColumn<soldOrRent, String> rssoldDateColumn;
	@FXML
	private TableColumn<soldOrRent, Long> rspriceColumn;
	@FXML
	private TableColumn<soldOrRent, String> rssaleOrRentColumn;
	@FXML
	private TableColumn<soldOrRent, String> rspropertySizeColumn;

	@FXML
	private TableView<Transactions> table3;
	@FXML
	private TableColumn<Transactions, String> transPropertyIDColumn;
	@FXML
	private TableColumn<Transactions, String> transBuyerIDColumn;
	@FXML
	private TableColumn<Transactions, Integer> transSellerIDColumn;
	@FXML
	private TableColumn<Transactions, String> transModeOfTransColumn;
	@FXML
	private TableColumn<Transactions, String> transTypeColumn;
	@FXML
	private TableColumn<Transactions, String> transDateColumn;

	@FXML
	private TableView<List<String>> searchTable;
	@FXML
	private TextField searchTF;
	@FXML
	private Button mySold, myPro, mySearch, myHome, myRefresh, transButton1, transButton2, refreshButton, addProButton;
	@FXML
	private Text proText, soldText, searchText;
	@FXML
	private MenuItem myLogout;
	@FXML
	private AnchorPane pnSold, pnHome, pnPro, pnSearch, pnMain, transAnchorPane;
	@FXML
	private Label cum, aname;
	@FXML
	private Rectangle pic;

	@FXML
	private Label exp, sold, rent;
	private BigDecimal id1;

	public void getAgentID(BigDecimal id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			System.out.println("Connection Established");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from agent where agent_id=" + id + ";");
			Statement statement2 = connection.createStatement();
			ResultSet resultSet2 = statement2.executeQuery(
					"select count(agent_id) from transaction where agent_id=" + id + " and transaction_type='sale'; ");
			Statement statement3 = connection.createStatement();
			ResultSet resultSet3 = statement3.executeQuery(
					"select count(agent_id) from transaction where agent_id=" + id + " and transaction_type='rent'; ");
			Statement statement4 = connection.createStatement();
			ResultSet resultSet4 = statement4
					.executeQuery("select sum(price) from transaction where agent_id=" + id + ";");
			while (resultSet2.next()) {
				sold.setText(resultSet2.getString(1));
			}
			while (resultSet3.next()) {
				rent.setText(resultSet3.getString(1));
			}
			while (resultSet4.next()) {
				cum.setText(String.valueOf(resultSet4.getInt(1)/1000000)+"M");
			}
			while (resultSet.next()) {
				Blob bolb = resultSet.getBlob("image");
				InputStream inputStream = bolb.getBinaryStream();
				Image image = new Image(inputStream);
				pic.setFill(new ImagePattern(image));
				aname.setText(resultSet.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.id1 = id;

		table.setItems(getProperty());
		table2.setItems(getSoldOrRent());
		table3.setItems(getTransactions());
	}

	@FXML
	private void handleRefreshAction(ActionEvent event) {
		table.setItems(getProperty());
		table2.setItems(getSoldOrRent());
		table3.setItems(getTransactions());
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource() == mySold) {
			pnSold.toFront();
		}

		else if (event.getSource() == mySearch) {
			pnSearch.toFront();
		}

		else if (event.getSource() == myPro) {
			pnPro.toFront();
		}

		else if (event.getSource() == transButton1 || event.getSource() == transButton2) {
			transAnchorPane.toFront();
		}
	}
    @FXML
    private void homepage(ActionEvent e) {
    	pnHome.toFront();
    }
	@FXML
	public void handleAddProAction(ActionEvent event) {

	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/p1.fxml"));
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

	@FXML
	private void handleSearchButton(ActionEvent event) {
		Connection connection = null;
		searchTable.getColumns().clear();
		searchTable.getItems().clear();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			System.out.println("Connection Established");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(searchTF.getText());
//			int columnCount = resultSet.getMetaData().getColumnCount();
//			for (int i = 1; i <= columnCount; i++) {
//				String columnName = resultSet.getMetaData().getColumnName(i);
//				if (columnName.compareTo("image") != 0) {
//					TableColumn<List<String>, String> column = new TableColumn<>(columnName);
//					searchTable.getColumns().addAll(column);
//				}
//			}
			ObservableList<List<String>> row = FXCollections.observableArrayList();
			while (resultSet.next()) {

				List<String> unit = new ArrayList<>();
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int columnType = resultSetMetaData.getColumnType(i);
					if (columnType == -5) {
						BigDecimal ob = resultSet.getBigDecimal(i);
						String s = ob.toString();
						System.out.print(s + "  ");
						unit.add(s);
					}
					if (columnType == 12) {
						String s = resultSet.getString(i);
						System.out.print(s + "  ");
						unit.add(s);
					}
					if (columnType == 3) {
						Double s1 = resultSet.getDouble(i);
						String s = String.valueOf(s1);
						System.out.print(s + "  ");
						unit.add(s);
					}
					if (columnType == 4) {
						int x = resultSet.getInt(i);
						String s = String.valueOf(x);
						System.out.print(s + "  ");
						unit.add(s);
					}
				}
				row.add(unit);
				System.out.println();
			}
			List<List<String>> result = row;

			// Clear existing columns and items in the TableView

			if (result != null && !result.isEmpty()) {
				// Create columns dynamically based on the number of columns in the result
				int columnCount1 = resultSet.getMetaData().getColumnCount();
				for (int i = 1; i <= columnCount1; i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);
					int columnIndex = i - 1;
					if (columnName.compareTo("image") != 0) {
						TableColumn<List<String>, String> column = new TableColumn<>(columnName);
						column.setCellValueFactory(
								param -> new SimpleStringProperty(param.getValue().get(columnIndex)));
						searchTable.getColumns().add(column);
					}
				}

				// Add rows to the TableView
				for (List<String> r : result) {
					ObservableList<String> observableRow = FXCollections.observableArrayList(r);
					searchTable.getItems().add(observableRow);
				}
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		pnHome.toFront();

		propertyIdColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("property_id"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("room"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Property, Long>("price"));
		propertyTypeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("property_type"));
		saleOrRentColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("sale_or_rent"));
		propertySizeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("property_size"));

		rspropertyIdColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("property_id"));
		rsaddressColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("address"));
		rsroomColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("room"));
		rspriceColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, Long>("price"));
		rssoldDateColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("soldDate"));
		rssaleOrRentColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("sale_or_rent"));
		rspropertySizeColumn.setCellValueFactory(new PropertyValueFactory<soldOrRent, String>("property_size"));

		transPropertyIDColumn.setCellValueFactory(new PropertyValueFactory<Transactions, String>("property_id"));
		transBuyerIDColumn.setCellValueFactory(new PropertyValueFactory<Transactions, String>("buyer_id"));
		transSellerIDColumn.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("seller_id"));
		transDateColumn.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
		transTypeColumn.setCellValueFactory(new PropertyValueFactory<Transactions, String>("transaction_type"));
		transModeOfTransColumn
				.setCellValueFactory(new PropertyValueFactory<Transactions, String>("mode_of_transaction"));

		table.setItems(getProperty());
		table2.setItems(getSoldOrRent());
		table3.setItems(getTransactions());

	}

	public ObservableList<Property> getProperty() {
		ObservableList<Property> properties = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from property where agent_id=" + id1);
			while (resultSet.next()) {
				String property_id = resultSet.getString("property_id");
				long price = resultSet.getLong("price");
				String property_type = resultSet.getString("property_type");
				String address = resultSet.getString("address");
				String room = resultSet.getString("room");
				String property_size = resultSet.getString("property_size");
				String sell_or_rent = resultSet.getString("sale_or_rent");
				properties.add(
						new Property(property_id, address, room, price, property_type, sell_or_rent, property_size));
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return properties;

	}

	public ObservableList<Transactions> getTransactions() {
		ObservableList<Transactions> transactions = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from transaction where agent_id=" + id1);
//	            statement.setInt(1,id1);
			while (resultSet.next()) {
				String property_id = resultSet.getString("property_id");
				String buyer_id = resultSet.getString("buyer_id");
				int seller_id = resultSet.getInt("seller_id");
				String modeOfTrans = resultSet.getString("mode_of_transaction");
				String transType = resultSet.getString("transaction_type");
				String date = resultSet.getString("date");
				transactions.add(new Transactions(property_id, seller_id, buyer_id, date, transType, modeOfTrans));
//	                System.out.println(room);
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return transactions;
	}

	public ObservableList<soldOrRent> getSoldOrRent() {
		ObservableList<soldOrRent> properties = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from property_rented where agent_id=" + id1);
			while (resultSet.next()) {
				String property_id = resultSet.getString("property_id");
				long price = resultSet.getLong("rent_price");
				String property_size = resultSet.getString("property_size");
				String address = resultSet.getString("property_address");
				String room = resultSet.getString("no_of_room");
				String rentdate = resultSet.getString("rent_date");
				properties.add(new soldOrRent(property_id, address, room, price, rentdate, "Rent", property_size));
			}

			resultSet = statement.executeQuery("select * from property_sold where agent_id=" + id1);
			while (resultSet.next()) {
				String property_id = resultSet.getString("property_id");
				long price = resultSet.getLong("sold_price");
				String property_size = resultSet.getString("property_size");
				String address = resultSet.getString("property_address");
				String room = resultSet.getString("no_of_room");
				String solddate = resultSet.getString("sold_date");
				properties.add(new soldOrRent(property_id, address, room, price, solddate, "Sold", property_size));
			}

			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return properties;
	}

}
