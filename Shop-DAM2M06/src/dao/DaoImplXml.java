package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import dao.xml.DomWriter;
import dao.xml.SaxReader;
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
	        String filePath = "C:/Users/sejum/git/Shop-DAM2M06/Shop-DAM2M06/files/inputInventory.xml";
	        
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
	        domWriter.generateXml();
	        
	        // Return true if successful
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        // Return true if fails
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