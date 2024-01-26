package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class itemControl {
	@FXML
	private ImageView img;

	@FXML
	private Label nameLabel;

	@FXML
	private Label priceLabel;

	@FXML
	private void click(MouseEvent mouseEvent) {
		myListener.onClickListener(fruit);
	}

	private Fruit fruit;
	private MyListener myListener;

	public void setData(Fruit fruit, MyListener myListener) {
		this.fruit = fruit;
		this.myListener = myListener;
		nameLabel.setText(fruit.getName());
		priceLabel.setText("$" + fruit.getprice());
		img.setImage(fruit.getImgSrc());

	}
}
