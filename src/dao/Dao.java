package dao;

import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {
    
	void connect();
    
	Employee getEmployee(int employeeId, String password);
    
	void disconnect();
	
	ArrayList<Product> getInventory();
	
	boolean writeInventory(ArrayList<Product> inventory);
}