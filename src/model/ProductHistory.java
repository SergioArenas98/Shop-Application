package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long idHistory;
	
	@Column(name = "available")
    private boolean available = true;
	
	@Column(name = "created_at")
    private LocalDateTime createAt;

	@Column(name = "id_product")
    private Long idProduct;

	@Column(name = "name")
    private String productName;

	@Column(name = "price")
    private Double price;

	@Column(name = "stock")
    private int stock;
	
	public ProductHistory (Long idHistory, Long idProduct, String productName, Double price, boolean available, int stock, LocalDateTime createAt) {
		this.idHistory = idHistory;
		this.idProduct = idProduct;
		this.productName = productName;
		this.price = price;
		this.available = available;
		this.stock = stock;
		this.createAt = createAt;
	}

	public Long getIdHistory() {
		return idHistory;
	}

	public void setIdHistory(Long idHistory) {
		this.idHistory = idHistory;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setWholesalerPrice(Double price) {
		this.price = price;
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

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

}
