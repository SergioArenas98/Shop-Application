package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = { "available", "wholesalerPrice", "publicPrice", "stock" })
@Entity
@Table(name = "inventory")
public class Product {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long productId;
	
	@Column(name = "productName")
    private String name;
	
	@Transient
    private Amount publicPrice;
    
	@Transient
    private Amount wholesalerPrice;
	
	@Column(name = "available")
    private boolean available = true;
	
	@Column(name = "stock")
    private int stock;
	
	@Transient
    private static int totalProducts;
	
	@Transient
    final static double EXPIRATION_RATE = 0.60;
	
	@Column(name = "wholesalerPrice")
	private Double price;
	
    public Product() {
    }
    
	public Product(String name, Double price, boolean available, int stock) {
		super();
		this.name = name;
		this.price = price;
		this.wholesalerPrice = new Amount(price, "€");
		this.publicPrice = new Amount(price * 2, "€");
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}
	
	@XmlAttribute(name = "id")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "publicPrice")
	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	@XmlElement(name = "wholesalerPrice")
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}
	
	@XmlElement(name = "available")
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	@XmlElement(name = "stock")
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
	
	
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
    	this.price = price;
    }

	// Reduce product price by 0.60 when soon to expire
	public void expire() {
		this.publicPrice.setValue(getPublicPrice().getValue() * EXPIRATION_RATE);
	}

	@Override
	public String toString() {
	    return "\nProduct --> ID = " + productId + 
	           ", Name = " + name + 
	           ", Public Price = " + publicPrice.getValue() + 
	           ", Wholesaler Price = " + wholesalerPrice.getValue() + 
	           ", Available = " + available + 
	           ", Stock = " + stock + "\n";
	}
}