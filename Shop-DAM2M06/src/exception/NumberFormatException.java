package exception;

public class NumberFormatException extends Exception {
	
    private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Error login, el empleado debe ser numérico";
	}
}