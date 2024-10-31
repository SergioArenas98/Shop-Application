package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wholesaler_price", propOrder = { "value", "currency" })
public class Amount {
	
	private static final String CURRENCY = "€";
	
	@XmlAttribute(name = "value")
    private double value;
	
    private String currency;

    public Amount(double value, String currency) {
        this.value = value;
        this.currency = CURRENCY;
    }
    
    // Constructor for JAXB
    public Amount() {
        this.currency = CURRENCY;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return value + currency;
    }
}