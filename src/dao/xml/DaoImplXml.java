package dao.xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dao.Dao;
import main.Shop;
import model.Employee;
import model.Product;

public class DaoImplXml implements Dao {
	
    Shop shop;
    private DomWriter domWriter;
    private SaxReader reader;

	public DaoImplXml(Shop shop) {
    	this.shop = shop;
    	this.domWriter = new DomWriter();
    	this.reader = new SaxReader();
	}

	@Override
	public ArrayList<Product> getInventory() {
	    try {
	    	// Create SAXParserFactory object
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();

	        // Define path to XML file
	        File filePath = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.xml");
	        
	        // Parse XML file
	        saxParser.parse(filePath, reader);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	 
	    // Return inventory
	    return reader.returnInventory();
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    try {
	        // Generate the XML with DomWriter
	        domWriter.generateDocument(inventory);
	        return domWriter.generateXml();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
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