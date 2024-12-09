package dao.jaxb;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Products;

public class JaxbUnMarshaller {

	public Products init() {

		try {
			// Set import route
			File filePath = new File(System.getProperty("user.dir") + File.separator + "inputInventory.xml");
			
			// Create UnMarshaller object
			JAXBContext context = JAXBContext.newInstance(Products.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Return XML converted to Products
			return (Products) unmarshaller.unmarshal(filePath);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}