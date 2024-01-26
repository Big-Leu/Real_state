package application;

import javafx.scene.image.Image;

public class Fruit {
	private String name;
	private double price;
	private Image ImgSrc;
	private String Agent;
	private String room;
	private String type;
	private int inList;
	private String Address;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Image getImgSrc() {

		return ImgSrc;
	}

	public void setImgSrc(Image img) {

		this.ImgSrc = img;
	}

	public double getprice() {

		return price;
	}

	public void setprice(double price) {

		this.price = price;
	}

	public String getAgent() {

		return Agent;
	}

	public void setAgent(String name) {

		this.Agent = name;
	}

	public String getRoom() {

		return room;
	}

	public void setRoom(String name) {

		this.room = name;
	}

	public String getType() {

		return type;
	}

	public void setType(String name) {

		this.type = name;
	}

	public int getinList1() {

		return inList;
	}

	public void setinList(String name) {

		this.inList = Integer.parseInt(name.substring(6));
	}

	public String getAddress() {

		return Address;
	}

	public void setAddress(String name) {

		this.Address = name;
	}

}
