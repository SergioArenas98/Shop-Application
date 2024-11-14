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
			String filePath = "C:/Users/sejum/git/Shop-DAM2M06/Shop-DAM2M06/jaxb/inputInventory.xml";
			
			// Create UnMarshaller object
			JAXBContext context = JAXBContext.newInstance(Products.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Return XML converted to Products
			return (Products) unmarshaller.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}
}