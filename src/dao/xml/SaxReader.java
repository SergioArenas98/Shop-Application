package dao.xml;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {

    private List<Product> productList = null;
    private Product product = null;
    private StringBuilder data = null;

    @Override
    public void startDocument() {
        productList = new ArrayList<>();
    }
 
    // Called when a start element is found
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data = new StringBuilder();
        
        // Check if start element is "product"
        if (qName.equalsIgnoreCase("product")) {
        	 // Create a new Product
            product = new Product(attributes.getValue("name"), new Amount(0.0, ""), true, 0);
        }
    }

    // Called when character data is found within an element
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    // Called when an end element is found
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	// Check if end element is "wholesaler_price"
    	if (qName.equalsIgnoreCase("wholesalerPrice")) {
            product.setWholesalerPrice(new Amount(Double.parseDouble(data.toString()), "Euro"));
        // Check if end element is "stock"
        } else if (qName.equalsIgnoreCase("stock")) {
            product.setStock(Integer.parseInt(data.toString()));
        // Check if end element is "product"
        } else if (qName.equalsIgnoreCase("product")) {
            productList.add(product);
        }
    }
    
    // Method to return the list of products as an ArrayList
    public ArrayList<Product> returnInventory() {
    	return (ArrayList<Product>) productList;
    }
}
