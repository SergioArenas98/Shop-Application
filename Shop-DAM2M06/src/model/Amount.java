package model;

public class Amount {
	
	private static final String CURRENCY = "€";
	
    private double value;
    private String currency;

    public Amount(double value, String currency) {
        this.value = value;
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