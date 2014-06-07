package pl.asseco.echoweb.util;

public class TokenGenerator {

	public final static int TOKEN_SIZE = 20;
	public static String RandomAlphaNumericString(){
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuilder ret = new StringBuilder();
	    int length = chars.length();
	    for (int i = 0; i < TOKEN_SIZE; i ++){
	        ret.append(chars.split("")[ (int) (Math.random() * (length - 1)) ]);
	    }
	    return ret.toString();
	}
}
