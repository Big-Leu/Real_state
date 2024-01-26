package application;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class agentControll implements Initializable {
	@FXML
	private VBox chosenFruitCard;
	@FXML
	private Button search;
	@FXML
	private Circle btnClose;
	@FXML
	private Circle circle;
	@FXML
	private GridPane grid1;
	@FXML
	private Rectangle rec;
	@FXML
	private ScrollPane scroll;
	@FXML
	private Button buy;
	@FXML
	private Label agent;
	@FXML
	private Label type;
	@FXML
	private Label room;
	@FXML
	private TextField textsearch;
	private Parent root;
	private Stage stage;
	private Scene scene;
	private double xOffset;
	private double yOffset;
	private int row;
	private MyListener2 myListener;
	private List<Agent> fruits = new ArrayList<>();

	private int s1(List<Agent> fruits, String s) {
		for (int i = 0; i < fruits.size(); i++) {
			if (fruits.get(i).getName().compareToIgnoreCase(s) == 0) {
				return i;
			}
		}
		return -1;
	}

	private List<Agent> getDataRange(String q) {
		List<Agent> fruits = new ArrayList<>();
		Agent fruit;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root", "abhi9889");
			if (!con.isClosed()) {
				System.out.println("Connection is established ");
			}
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(q);
			while (set.next()) {
				Blob bolb = set.getBlob("image");
				InputStream inputStream = bolb.getBinaryStream();
				Image image = new Image(inputStream);
				fruit = new Agent();
				fruit.setImgSrc(image);
				fruit.setName(set.getString("name"));
				fruit.setAgent_address(set.getString("address"));
				fruit.setAgnet_age(set.getInt("age"));
				fruit.setid(set.getBigDecimal("agent_id"));
				fruit.setAgnet_phone(set.getString("phone_no"));
				Statement stmt2 = con.createStatement();
				ResultSet set2 = stmt2
						.executeQuery("select count(transaction_type) as sale\r\n" + "from transaction\r\n"
								+ "where agent_id=" + set.getBigDecimal(1) + " and transaction_type='sale' ;");
				int a = 0;
				while (set2.next()) {
					a = set2.getInt(1);
				}
				fruit.setrent(a);
				Statement stmt3 = con.createStatement();
				ResultSet set3 = stmt3
						.executeQuery("select count(transaction_type) as sale\r\n" + "from transaction\r\n"
								+ "where agent_id=" + set.getBigDecimal(1) + " and transaction_type='rent' ;");
				while (set3.next()) {
					a = set3.getInt(1);
				}
				fruit.setsold(a);
				double b = 0;
				Statement stmt4 = con.createStatement();
				ResultSet set4 = stmt4.executeQuery("select sum(price) as sale\r\n" + "from transaction\r\n"
						+ "where agent_id=" + set.getBigDecimal(1) + ";");
				while (set4.next()) {
					b = set4.getInt(1);
				}
				fruit.setSum(b);
				fruits.add(fruit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fruits;
	}

	public void setChosenFruit(Agent fruit) {
		agent.setText("" + fruit.getName());
		type.setText(String.valueOf(fruit.getrent() + fruit.getsold()));
		room.setText(fruit.getSum());
		rec.setFill(new ImagePattern(fruit.getImgSrc()));
		chosenFruitCard.setStyle("-fx-background-radius:30;");
	}

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

	public static int getRowIndexAsInteger(Node node) {
		final var a = GridPane.getRowIndex(node);
		if (a == null) {
			return 0;
		}
		return a;
	}

	public static void removeRow(GridPane grid, Integer targetRowIndexIntegerObject) {
		final int targetRowIndex = targetRowIndexIntegerObject == null ? 0 : targetRowIndexIntegerObject;

		// Remove children from row
		grid.getChildren().removeIf(node -> getRowIndexAsInteger(node) == targetRowIndex);

		// Update indexes for elements in further rows
		grid.getChildren().forEach(node -> {
			final int rowIndex = getRowIndexAsInteger(node);
			if (targetRowIndex < rowIndex) {
				GridPane.setRowIndex(node, rowIndex - 1);
			}
		});

		// Remove row constraints
//        grid.getRowConstraints().remove(targetRowIndex);
	}

	public void remove(int x) throws IOException {
		for (int i = 0; i < x; i++) {
			removeRow(grid1, 1);
		}
	}

	@FXML
	public void search(ActionEvent event) throws IOException {

		String line = textsearch.getText();
		String arr[] = line.split("@");
		switch (arr.length) {
		case 1:
			remove(row);
			Collections.sort(fruits, new Comparator<Agent>() {
				public int compare(Agent s1, Agent s2) {
					return ((int) Double.parseDouble(s2.getSum().replaceAll("LKH", ""))
							- (int) Double.parseDouble(s1.getSum().replaceAll("LKH", "")));
				}
			});
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener2() {

					@Override
					public void onClickListener(Agent fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/agentItem.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					agentp itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid1.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
				System.out.println(row);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			remove(row);
			ArrayList<BigDecimal> max = new ArrayList<BigDecimal>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://database2.c5dg3krqvrb1.ap-south-1.rds.amazonaws.com:3306/4diwar", "root",
						"abhi9889");
				if (!con.isClosed()) {
					System.out.println("Connection is established ");
				}
				Statement stmt = con.createStatement();
				ResultSet set = stmt.executeQuery("select t.agent_id,count(t.date) as n from transaction t,agent a\r\n"
						+ "where t.agent_id=a.agent_id and t.date like '" + arr[1] + "%'\r\ngroup by(t.agent_id)\n"
						+ "order by (n);");
				while (set.next()) {
					max.add(set.getBigDecimal(1));
				}
				System.out.println("Size of Max:=" + max.size());
				for (int i = 0; i < max.size(); i++) {
					for (int j = 0; j < fruits.size(); j++) {
						if (max.get(i).compareTo(fruits.get(j).getid()) == 0) {
							System.out.println("Value of i:=" + i + "  Value of j=" + j);
							Agent temp = fruits.get(j);
							fruits.set(j,fruits.get(i));
							fruits.set(i,temp);
							break;
						}
					}
				}
				for (int i = 0; i < fruits.size(); i++) {
					System.out.println(fruits.get(i).getName());
				}
				if (fruits.size() > 0) {
					setChosenFruit(fruits.get(0));
					myListener = new MyListener2() {

						@Override
						public void onClickListener(Agent fruit) {

							setChosenFruit(fruit);
						}

					};
				}
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/agentItem.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					agentp itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid1.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
				System.out.println(row);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			remove(row);
			String q1 = "select * from agent;";
			fruits.clear();
			fruits.addAll(getDataRange(q1));
			Collections.sort(fruits, new Comparator<Agent>() {
				public int compare(Agent s1, Agent s2) {
					return s1.getName().compareToIgnoreCase(s2.getName());

				}
			});
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener2() {

					@Override
					public void onClickListener(Agent fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/agentItem.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					agentp itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid1.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
				System.out.println(row);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	public void saleMouseEvent(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/sales.fxml"));
		root = loader.load();
		salecontroller s2 = loader.getController();
		System.out.println(agent.getText());
		int x = s1(fruits, agent.getText());
		s2.pop(fruits.get(x));
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fruits.addAll(getDataRange("select * from agent;"));
		if (fruits.size() > 0) {
			setChosenFruit(fruits.get(0));
			myListener = new MyListener2() {

				@Override
				public void onClickListener(Agent fruit) {

					setChosenFruit(fruit);
				}

			};
		}
		try {
			int column = 0;
			row = 1;
			for (int i = 0; i < fruits.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/application/agentItem.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				agentp itemController = fxmlLoader.getController();
				itemController.setData(fruits.get(i), myListener);
				if (column == 3) {
					column = 0;
					row++;
				}
				grid1.add(anchorPane, column++, row);
				GridPane.setMargin(anchorPane, new Insets(15.6));
			}
			System.out.println(row);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}