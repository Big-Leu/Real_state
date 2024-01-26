package application;

import java.math.BigDecimal;

import javafx.scene.image.Image;

public class Agent {
	private String name;
	private Image ImgSrc;
	private BigDecimal Agent_id;
	private String Agent_phone;
	private String Agent_address;
	private int Agent_age;
	private int rent;
	private int sold;
	private String Sum;

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

	public BigDecimal getid() {

		return Agent_id;
	}

	public void setid(BigDecimal price) {

		this.Agent_id = price;
	}

	public String getAgnet_phone() {

		return Agent_phone;
	}

	public void setAgnet_phone(String name) {

		this.Agent_address = name;
	}

	public String getAgnent_address() {

		return Agent_address;
	}

	public void setAgent_address(String name) {

		this.Agent_address = name;
	}

	public void setAgnet_age(int name) {

		this.Agent_age = name;
	}

	public int getAgnet_age() {
		return Agent_age;
	}

	public void setrent(int a) {
		this.rent = a;
	}

	public int getrent() {
		return rent;
	}

	public void setsold(int a) {
		this.sold = a;
	}

	public int getsold() {
		return sold;
	}

	public void setSum(double a) {
		a=(a/1000000);
		this.Sum = String.valueOf(a)+"M";
	}

	public String getSum() {
		return Sum;
	}
}
