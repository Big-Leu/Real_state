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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MarketContoller implements Initializable {
	@FXML
	private VBox chosenFruitCard;
	@FXML
	private Label fruitNameLabel;
	@FXML
	private Label fruitPriceLabel;
	@FXML
	private Button Agent;
	@FXML
	private Button search;
	@FXML
	private Circle btnClose;
	@FXML
	private Circle circle;
	@FXML
	private GridPane grid;
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
	@FXML
	private Circle full;
	@FXML
	private Circle mini;

	private MyListener myListener;
	private List<Fruit> fruits = new ArrayList<>();

	public void fullscreen(MouseEvent event) {
		if (event.getSource() == full) {
		   ((Stage)((Node)event.getSource()).getScene().getWindow()).setIconified(true);;
		}
	}


	private List<Fruit> getDataRange(String q) {
		List<Fruit> fruits = new ArrayList<>();
		Fruit fruit;
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
				fruit = new Fruit();
				fruit.setImgSrc(image);
				fruit.setName(set.getString("property_id"));
				fruit.setprice((set.getInt("price")) / 80);
				fruit.setAgent(set.getString("agent_name"));
				fruit.setRoom(set.getString("room"));
				fruit.setType(set.getString("property_type"));
				fruit.setinList(set.getString(8));
				fruit.setAddress(set.getString("address"));
				fruits.add(fruit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fruits;
	}

	private void setChosenFruit(Fruit fruit) {
		fruitNameLabel.setText("" + fruit.getName());
		fruitPriceLabel.setText("$" + fruit.getprice());
		agent.setText(fruit.getAgent());
		type.setText(fruit.getType());
		room.setText(fruit.getRoom());
		circle.setFill(new ImagePattern(fruit.getImgSrc()));
		chosenFruitCard.setStyle("-fx-background-radius:30;");

	}

	public void agentEvent(ActionEvent event) throws IOException {

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

	public void handleMouseEvent(MouseEvent event) throws IOException {
		if (event.getSource() == btnClose) {
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
			removeRow(grid, 1);
		}
	}

	@FXML
	public void search(ActionEvent event) throws IOException {

		String line = textsearch.getText();
		String arr[] = line.split("@");
		switch (arr.length) {
		case 1:
			remove(row);
			String q2 = "select * from property;";
			fruits.clear();
			fruits.addAll(getDataRange(q2));
			Collections.sort(fruits, new Comparator<Fruit>() {
				public int compare(Fruit s1, Fruit s2) {
					return (int) s2.getprice() - (int) s1.getprice();
				}
			});
//     	   System.out.println(fruits.get(0).getName());
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener() {

					@Override
					public void onClickListener(Fruit fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					itemControl itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			remove(row);
			String q6 = "select * from property\nwhere sale_or_rent='" + arr[0] + "';";

			fruits.clear();
			fruits.addAll(getDataRange(q6));
			for (int i = 0; i < fruits.size(); i++) {
				int x = fruits.get(i).getinList1();
				int y = Integer.parseInt(arr[1]);
				if (x < y) {
					fruits.remove(i);
					i--;
				}
			}
			Collections.sort(fruits, new Comparator<Fruit>() {
				public int compare(Fruit s1, Fruit s2) {
					return Integer.parseInt(s1.getName().replaceAll("[^0-9]", ""))
							- Integer.parseInt(s2.getName().replaceAll("[^0-9]", ""));
				}
			});
//     	   System.out.println(fruits.get(0).getName());
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener() {

					@Override
					public void onClickListener(Fruit fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					itemControl itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			int a = Integer.parseInt(arr[1]);
			int b = Integer.parseInt(arr[2]);
			int max = Math.max(a, b);
			int min = Math.min(a, b);
			remove(row);
			String q = "select * from property\n" + "where price between " + String.valueOf(min) + " and "
					+ String.valueOf(max) + ";";
			fruits.clear();
			fruits.addAll(getDataRange(q));
			Collections.sort(fruits, new Comparator<Fruit>() {
				public int compare(Fruit s1, Fruit s2) {
					return Integer.parseInt(s1.getName().replaceAll("[^0-9]", ""))
							- Integer.parseInt(s2.getName().replaceAll("[^0-9]", ""));
				}
			});
//          	   System.out.println(fruits.get(0).getName());
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener() {

					@Override
					public void onClickListener(Fruit fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					itemControl itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case 5:
			remove(row);
			String s = "";
			if (Integer.parseInt(arr[4]) == 1)
				s = ">=";
			else
				s = "<=";
			String q21 = "select * from property\nwhere sale_or_rent='" + arr[0] + "' and room='" + arr[1] + "bhk"
					+ "' and price" + s + arr[2] + ";";
			System.out.println(q21);
			fruits.clear();
			fruits.addAll(getDataRange(q21));
			Collections.sort(fruits, new Comparator<Fruit>() {
				public int compare(Fruit s1, Fruit s2) {
					return Integer.parseInt(s1.getName().replaceAll("[^0-9]", ""))
							- Integer.parseInt(s2.getName().replaceAll("[^0-9]", ""));
				}
			});
			for (int i = 0; i < fruits.size(); i++) {
				String s21 = fruits.get(i).getAddress();
				s21 = s21.toUpperCase();
				if (s21.contains(arr[3].toUpperCase()) == false) {
					fruits.remove(i);
					i--;
				}
			}
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener() {

					@Override
					public void onClickListener(Fruit fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					itemControl itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			break;
		default:
			remove(row);
			String q1 = "select * from property;";
			fruits.clear();
			fruits.addAll(getDataRange(q1));
			Collections.sort(fruits, new Comparator<Fruit>() {
				public int compare(Fruit s1, Fruit s2) {
					return Integer.parseInt(s1.getName().replaceAll("[^0-9]", ""))
							- Integer.parseInt(s2.getName().replaceAll("[^0-9]", ""));
				}
			});
//     	   System.out.println(fruits.get(0).getName());
			if (fruits.size() > 0) {
				setChosenFruit(fruits.get(0));
				myListener = new MyListener() {

					@Override
					public void onClickListener(Fruit fruit) {

						setChosenFruit(fruit);
					}

				};
			}
			try {
				int column = 0;
				row = 1;
				for (int i = 0; i < fruits.size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					itemControl itemController = fxmlLoader.getController();
					itemController.setData(fruits.get(i), myListener);
					if (column == 3) {
						column = 0;
						row++;
					}
					grid.add(anchorPane, column++, row);
					GridPane.setMargin(anchorPane, new Insets(15.6));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	@FXML
	public void Buy(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/buildingP.fxml"));
		root = loader.load();
		building s2 = loader.getController();
		System.out.println(fruitNameLabel.getText());
		s2.pop(fruitNameLabel.getText());
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
//		Image img=new Image("/application/p11.jpg");
//		circle.setFill(new ImagePattern(img));
		fruits.addAll(getDataRange("select * from property;"));
		Collections.sort(fruits, new Comparator<Fruit>() {
			public int compare(Fruit s1, Fruit s2) {
				return Integer.parseInt(s1.getName().replaceAll("[^0-9]", ""))
						- Integer.parseInt(s2.getName().replaceAll("[^0-9]", ""));
			}
		});
//	   System.out.println(fruits.get(0).getName());
		if (fruits.size() > 0) {
			setChosenFruit(fruits.get(0));
			myListener = new MyListener() {

				@Override
				public void onClickListener(Fruit fruit) {

					setChosenFruit(fruit);
				}

			};
		}
		try {
			int column = 0;
			row = 1;
			for (int i = 0; i < fruits.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/application/item.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				itemControl itemController = fxmlLoader.getController();
				itemController.setData(fruits.get(i), myListener);
				if (column == 3) {
					column = 0;
					row++;
				}
				grid.add(anchorPane, column++, row);
				GridPane.setMargin(anchorPane, new Insets(15.6));
			}
			System.out.println(row);

		    grid.setOnScroll(new EventHandler<ScrollEvent>() {
		        @Override
		        public void handle(ScrollEvent event) {
		            double deltaY = event.getDeltaY()*0.5; // *6 to make the scrolling a bit faster
		            double width = scroll.getContent().getBoundsInLocal().getWidth();
		            double vvalue = scroll.getVvalue();
		            scroll.setVvalue(vvalue + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
		        }
		    });
		    scroll.setContent(grid);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}