package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "wholesalerPrice", "stock", "available"})
public class Product {
	
	@XmlTransient
	private int id;
	
	@XmlAttribute(name = "name")
    private String name;
	
	@XmlTransient
    private Amount publicPrice;
    
	@XmlElement(name = "wholesaler_price")
    private Amount wholesalerPrice;
    
	@XmlElement(name = "available")
    private boolean available = true;
    
	@XmlElement(name = "stock")
    private int stock;
    
    private static int totalProducts;
    final static double EXPIRATION_RATE=0.60;
    
	public Product(String name, Amount wholesalerPrice, boolean available, int stock) {
		super();
		this.id = totalProducts + 1;
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
		this.publicPrice = new Amount(0.0, "");
		this.publicPrice.setValue(getWholesalerPrice().getValue() * 2);
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}
	
	public Product() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}
	
	// Reduce product price by 0.60 when soon to expire
	public void expire() {
		this.publicPrice.setValue(getPublicPrice().getValue() * EXPIRATION_RATE);
	}
}