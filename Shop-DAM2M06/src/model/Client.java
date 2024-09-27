package model;

import main.Payable;

public class Client extends Person implements Payable{
	
	private static final int MEMBERID = 456;
	protected static final Amount BALANCE = new Amount(150.00, "€");
	protected Amount balance;
	
	
	public Client(int memberId, String name, Amount balance) {
		super(name);
		this.balance = BALANCE;
	}
	
	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}

	@Override
	public boolean pay(Amount saleAmount) {
		double newBalance = this.getBalance().getValue() - saleAmount.getValue();
		
		if(newBalance >= 0) {
			balance.setValue(newBalance);
			return true;
		} else {
			return false;
		}
	}
}