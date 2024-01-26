package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class agentp {
	@FXML
	private ImageView img;

	@FXML
	private Label nameLabel;
	@FXML
	private Rectangle rec;

	@FXML
	private void click(MouseEvent mouseEvent) {
		myListener.onClickListener(agent);
	}

	private Agent agent;
	private MyListener2 myListener;

	public void setData(Agent agent, MyListener2 myListener) {
		this.agent = agent;
		this.myListener = myListener;
		nameLabel.setText(agent.getName());
        rec.setFill(new ImagePattern(agent.getImgSrc()));

	}
}
