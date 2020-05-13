package ua.training.constants;

public final class Regex {
	
	private Regex() {
				
	}
	
	public static String EMAIL_REGEX ="^[\\\\w-\\\\+]+(\\\\.[\\\\w-]+)*@[A-Za-z\\\\d-]+(\\\\.[A-Za-z\\\\d]+)*(\\\\.[A-Za-z]{2,})$" ;
	public static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d]{8,14}$";

}
