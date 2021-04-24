
public class SyntaxErrorException extends Exception{
	String message;
	public SyntaxErrorException() {
		message="Syntax Error";
	}
	public SyntaxErrorException(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
}
