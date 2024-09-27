package model;

import main.Payable;

public class premiumClient extends Client implements Payable{
	
	private static int points = 0;

	public premiumClient(int memberId, String name, Amount balance, int points) {
		super(memberId, name, balance);
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	@Override
	public boolean pay(Amount saleAmount) {
		double newBalance = this.getBalance().getValue() - saleAmount.getValue();
		this.setBalance(new Amount(newBalance, "€"));
		
		if(newBalance >= 0) {
			balance.setValue(newBalance);
			return true;
		} else {
			return false;
		}
	}
}
