package dao.xml;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import model.Product;
import view.FolderNameView;

public class DomWriter {
	
	private Document document;
	
	public DomWriter() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("Error al generar el documento");
		}
	}
	
	public void generateDocument(ArrayList<Product> inventory) {
		
		try {

			int totalCounter = inventory.size();
			int idCounter = 1;
			
			// PARENT NODE
			// root node
			Element productsInventory = document.createElement("products");
			productsInventory.setAttribute("total", String.valueOf(totalCounter));
			document.appendChild(productsInventory);
				
			for (Product product : inventory){
			
				// CHILD NODES 
				// product nodes
				Element productInventory = document.createElement("product");
				productInventory.setAttribute("id", String.valueOf(idCounter++));
				productsInventory.appendChild(productInventory);
				
				// FINAL NODES
				// name nodes
				Element nameInventory = document.createElement("name");
				nameInventory.setTextContent(product.getName());
				productInventory.appendChild(nameInventory);
				
				// price nodes
				Element priceInventory = document.createElement("wholesaler_price");
				priceInventory.setAttribute("currency", "€");
				priceInventory.setTextContent(String.valueOf(product.getWholesalerPrice().getValue()));
				productInventory.appendChild(priceInventory);
		
				// stock nodes
				Element stockInventory = document.createElement("stock");
				stockInventory.setTextContent(String.valueOf(product.getStock()));
				productInventory.appendChild(stockInventory);
			}
		  
		} catch(Exception e) {
			  e.printStackTrace();
		}
	}
	
	public void generateXml(){
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
	        }
	    }

	    // Get the current date and format it as yyyy-mm-dd
	    LocalDate currentDate = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String formattedDate = currentDate.format(formatter);
	    
	    // Set the file's name
	    File inventoryFile = new File(newFolder, "inventory_" + formattedDate + ".xml");
	    
	    // Check if file doesn't exists
	    if (!inventoryFile.exists()) {
	    	// If doesn't exist, create new file
	        try {
	            inventoryFile.createNewFile();
	        // If can't create new file, return false
	        } catch (IOException e) {
	            e.printStackTrace();
	        }   
         }
	    
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(inventoryFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}