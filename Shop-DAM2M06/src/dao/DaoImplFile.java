package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;
import view.FolderNameView;

public class DaoImplFile implements Dao {
	
    Shop shop;
	
    public DaoImplFile(Shop shop) {
    	this.shop = shop;
    }

	@Override
	public ArrayList<Product> getInventory() {
	    // Set the file's route
	    File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
	    ArrayList<Product> products = new ArrayList<>();

	    try {
	        FileReader fr = new FileReader(f);
	        BufferedReader br = new BufferedReader(fr);

	        // Read first line
	        String line = br.readLine();

	        // Read every line until the file is finished
	        while (line != null) {
	            // Divide into sections
	            String[] sections = line.split(";");

	            String name = "";
	            double wholesalerPrice = 0.0;
	            int stock = 0;

	            // Reed every section
	            for (String section : sections) {
	                // Divide into keys and values
	                String[] data = section.split(":");

	                switch (data[0]/*.trim()*/) {
	                	// Product name
	                    case "Product":
	                        name = data[1]/*.trim()*/;
	                        break;

	                    // Price
	                    case "Wholesaler Price":
	                        wholesalerPrice = Double.parseDouble(data[1]);
	                        break;

	                    // Stock
	                    case "Stock":
	                        stock = Integer.parseInt(data[1]);
	                        break;

	                    default:
	                        break;
	                }
	            }

	            // Add product to the list
	            products.add(new Product(name, new Amount(wholesalerPrice, "€"), true, stock));

	            // Read next line
	            line = br.readLine();
	        }
	        fr.close();
	        br.close();

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // Return list of products
	    return products; 
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// Set the file's route
	    File newFolder = new File(System.getProperty("user.dir") + File.separator + "files");
	    if (!newFolder.exists()) {
	    	FolderNameView folderNameView = new FolderNameView(null);
	        folderNameView.setVisible(true);
	        
	        // Get folder's name
	        String folderName = folderNameView.getFolderName();
	        
	        if (folderName != null && !folderName.trim().isEmpty()) {
	            // If folder's name is valid, create folder
	            newFolder = new File(System.getProperty("user.dir") + File.separator + folderName);
	            newFolder.mkdir();
	        } else {
	            // If folder's name not valid or cancel, return false 
	            JOptionPane.showMessageDialog(null, "El nombre de la carpeta no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	    }

	    // Get the current date and format it as yyyy-mm-dd
	    LocalDate currentDate = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String formattedDate = currentDate.format(formatter);
	    
	    // Set the file's name
	    File inventoryFile = new File(newFolder, "inventory_" + formattedDate + ".txt");
	    
	    // Check if file doesn't exists
	    if (!inventoryFile.exists()) {
	    	// If doesn't exist, create new file
	        try {
	            inventoryFile.createNewFile();
	        // If can't create new file, return false
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    try (FileWriter fw = new FileWriter(inventoryFile, false);
	         PrintWriter pw = new PrintWriter(fw)) {

	        // Iterate on inventory and write in the file
	        for (Product product : inventory) {
	            pw.println("Product:" + product.getName() + ";Stock:" + product.getStock() + ";");
	        }
	        
	        pw.print("\n"+ "Número total de productos:" + inventory.size());

	        // If successful, return true
	        return true;

	    // If exception occurs, return false
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public void connect() throws SQLException {	
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		return null;
	}

	@Override
	public void disconnect() throws SQLException {	
	}
}