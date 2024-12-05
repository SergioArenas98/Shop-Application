package dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.Dao;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	
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

        // Create connection to database
        try {
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

    // Import inventory from SQL database
	public ArrayList<Product> getInventory(){

		ArrayList<Product> inventory = new ArrayList<>();

		// Prepare SELECT query
		String query = "SELECT * FROM products;";

		try (
			PreparedStatement ps = connection.prepareStatement(query);

			ResultSet rs = ps.executeQuery()) {
			
			// Get data until find empty row
			while (rs.next()) {
				Long productId = rs.getLong("id");
				String productName = rs.getString("productName");
                double wholesalerPrice = rs.getDouble("wholesalerPrice");
                boolean available = rs.getBoolean("available");
                int stock = rs.getInt("stock");
				
				// Create product
                Product product = new Product(productName, new Amount(wholesalerPrice, "€"), available, stock);
                
                // Set product ID
                product.setProductId(productId);
                
                // Add to inventory
				inventory.add(product);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inventory;
	}
	
    // Export inventory to SQL database
	public boolean writeInventory(ArrayList<Product> inventory) {
		
		String query = "INSERT INTO historical_inventory (id, id_product, productName, wholesalerPrice, available, stock, created_at) VALUES (?, ?, ?, ?);";
			
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Product product : inventory) {
            	// TODO el id supongo que será autoincrement, preguntar si la tabla ya debe estar creada
            	ps.setLong(2, product.getProductId());
                ps.setString(3, product.getName());
                ps.setDouble(4, product.getWholesalerPrice().getValue());
                ps.setBoolean(5, product.isAvailable());
                ps.setInt(6, product.getStock());
                // TODO ps.setInt(7, product.getDate());
                ps.addBatch();
            }
            ps.executeBatch();
            
            return true;
        
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
	public void addProduct(Product product) {
		String query = "INSERT INTO products (productName, wholesalerPrice, available, stock) VALUES (?, ?, ?, ?);";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getWholesalerPrice().getValue());
            ps.setBoolean(3, product.isAvailable());
            ps.setInt(4, product.getStock());
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get generated ID
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Set product ID
                        product.setProductId(generatedKeys.getLong(1));
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void updateProduct(Product product) {
		String query = "UPDATE products SET productName = ?, wholesalerPrice = ?, available = ?, stock = ? WHERE productName = ?;";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getWholesalerPrice().getValue());
            ps.setBoolean(3, product.isAvailable());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getName());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void deleteProduct(Long productId) {
		String query = "DELETE FROM products WHERE id =  ?;";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, productId);
			ps.executeUpdate();
		
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}