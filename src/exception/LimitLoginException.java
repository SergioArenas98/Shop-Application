package exception;

public class LimitLoginException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int counter;

	public LimitLoginException(int counter) {
		this.counter = counter;
	}
	
	@Override
	public String toString() {
		return "Los intentos de login han superado el límite " + this.counter + " permitido";
	}
}