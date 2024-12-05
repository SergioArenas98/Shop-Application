package dao;

import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {
    
	void connect();
    
	ArrayList<Product> getInventory();
	
	boolean writeInventory(ArrayList<Product> inventory);
	
	Employee getEmployee(int employeeId, String password);
    
	void disconnect();
	
	void addProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(Long productId);
}