package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount {
    
    public static final String CURRENCY = "€";
    private double value;
    private String currency;

    public Amount(double value, String currency) {
        this.value = value;
        this.currency = CURRENCY;
    }

    public Amount() {
        this.currency = CURRENCY;
    }

    @XmlValue
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @XmlAttribute(name = "currency")
    public String getCurrency() {
        return CURRENCY;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
