package dao.jaxb;

import java.util.ArrayList;

import dao.Dao;
import main.Shop;
import model.Employee;
import model.Product;
import model.Products;

public class DaoImplJaxb implements Dao {
	
	Shop shop;
	
    public DaoImplJaxb(Shop shop) {
        this.shop = shop;
    }
	
	@Override
	public ArrayList<Product> getInventory() {
	    
		// Create JaxbUnMashaller object
	    JaxbUnMarshaller JaxbUnmarshaller = new JaxbUnMarshaller();
	    
	    // Create inventory from XML
	    Products inventory = JaxbUnmarshaller.init();
	    
	    // Check if inventory was created
		if (inventory != null) {
			return new ArrayList<>(inventory.getProducts());
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {

		// Create JaxbMashaller object
		JaxbMarshaller marshaller = new JaxbMarshaller();

		// Get inventory with Products class
		Products allProducts = new Products();
		allProducts.setProducts(inventory);

		// Return inventory converted to XML
		return marshaller.init(allProducts);
	}

	@Override
	public void connect() {
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		return null;
	}

	@Override
	public void disconnect() {	
	}

	@Override
	public void addProduct(Product product) {
	}

	@Override
	public void updateProduct(Product product) {
	}

	@Override
	public void deleteProduct(Long productId) {
	}
}
