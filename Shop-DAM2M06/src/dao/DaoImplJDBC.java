package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao{
	
	private Connection connection;
	
    public DaoImplJDBC() {
    }
	
    /**
     * Create connection to SQL database
     */
	@Override
	public void connect() {

		// Create variables with database information
        String url = "jdbc:mysql://localhost:3306/Shop";
        String user = "root";
        String password = "Sergio14Sejuma18";

        try {
        	// Create connection to database
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Employee getEmployee(int employeeId, String password) {
		
		Employee employee = null;
		
		// Prepare SELECT query
		String query = "SELECT * FROM employee WHERE employeeId = ? AND employeePassword = ?;";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			
			// Set the variable to search for the employee
			ps.setInt(1,employeeId);
			ps.setString(2,password);
		  	
			// If employeeId is found, create employee
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		employee =  new Employee(rs.getInt(1), rs.getString(2));            		            				
	        	}
	        }
	    } catch (SQLException e) {
			e.printStackTrace();
			
		}
		return employee;
	}

    /**
     * Import inventory from SQL database
    
    
	@Override
	public ArrayList<Product> loadInventory(){

		ArrayList<Product> inventory = new ArrayList<>();

		// Prepare SELECT query
		String query = "SELECT * FROM products;";

		try (
			PreparedStatement ps = connection.prepareStatement(query);

			ResultSet rs = ps.executeQuery()) {
			
			// Get data until find empty row
			while (rs.next()) {
				String productName = rs.getString("productName");
                double wholesalerPrice = rs.getDouble("wholesalerPrice");
                boolean available = rs.getBoolean("available");
                int stock = rs.getInt("stock");
				
				// Create product
                Product product = new Product(productName, new Amount(wholesalerPrice, "€"), available, stock);
                
                // Add to inventory
				inventory.add(product);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inventory;
	}*/
	

    /**
    * Export inventory to SQL database
    
	
	@Override
	public void exportInventory(ArrayList<Product> inventory) {
        String query = "INSERT INTO products (name, wholesalerPrice, available, stock) VALUES (?, ?, ?, ?);";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Product product : inventory) {
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getWholesalerPrice().getValue());
                ps.setBoolean(3, product.isAvailable());
                ps.setInt(4, product.getStock());
                ps.addBatch();
            }
            ps.executeBatch();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

	@Override
	public void disconnect() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	@Override
	public ArrayList<Product> getInventory() {
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		return false;
	}
}