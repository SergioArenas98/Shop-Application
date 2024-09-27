package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {
    
	void connect() throws SQLException;
    
	Employee getEmployee(int employeeId, String password);
	
	//ArrayList<Product> loadInventory();
	
	//void exportInventory(ArrayList<Product> inventory);
    
	void disconnect() throws SQLException;
	
	ArrayList<Product> getInventory();
	
	boolean writeInventory(ArrayList<Product> inventory);
}