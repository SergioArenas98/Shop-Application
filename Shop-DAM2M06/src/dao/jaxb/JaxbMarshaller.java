package dao.jaxb;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import model.Products;

public class JaxbMarshaller {
	
    public boolean init(Products products) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            // Set date
    	    LocalDate currentDate = LocalDate.now();
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	    String formattedDate = currentDate.format(formatter);
    	    
    	    // Set route
    	    String filePath = "C:/Users/sejum/git/Shop-DAM2M06/Shop-DAM2M06/jaxb/inventory_" + formattedDate + ".xml";
            
            jaxbMarshaller.marshal(products, new File(filePath));
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }

    }

}
