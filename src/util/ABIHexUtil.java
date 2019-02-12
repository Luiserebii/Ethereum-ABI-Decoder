package util;

import org.bouncycastle.util.encoders.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class ABIHexUtil {


/*
 *
 * General functions
 *
 *
 * */

	
/*
 * 
 * 
 * 
 * 
 * */

	
	//Alias for Hex32ToSignedBigInt()
	public static BigInteger Hex32ToInt(String hex) {
		return Hex32ToSignedBigInt(hex);
	}
	
	//Alias for Hex32ToUnsignedBigInt
	public static BigInteger Hex32ToUInt(String hex) {
		return Hex32ToUnsignedBigInt(hex);
	}

	public static boolean Hex32ToBool(String hex) {
		int boolVal = Hex32ToInteger(hex);
		return boolVal == 1 ? true : false;
	}
	
	public static String Hex32ToString(String hex, int byteLength) {
		String hexParsed = hex.substring(0, byteLength * 2);
		//System.out.println("hexPARSED" + hexParsed);
		try {
			//System.out.println(Hex.decode(hexParsed.getBytes()));
			return new String(Hex.decode(hexParsed.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		//return null;
	}
	
	/*
	 * Utility functions for hex to type conversions
	 * 
	 * */
	public static BigInteger Hex32ToSignedBigInt(String hex) {
		return new BigInteger(Hex.decode(hex));
	}
	
	public static BigInteger Hex32ToUnsignedBigInt(String hex) {
		return new BigInteger(hex, 16);
	}	
	
	public static int Hex32ToInteger(String hex) {
		return Integer.parseInt(hex, 16);
	}
	

/*
 *
 * Short-hand type to 32-bit hex
 *
 *
 * */


	public static String intToHex32(BigInteger integer){
		return padTo32Bytes(intToHex(integer), Direction.LEFT);
	}

	public static String uIntToHex32(BigInteger integer){
		return padTo32Bytes(uIntToHex(integer), Direction.LEFT);
	}

	public static String boolToHex32(boolean bool){
		return padTo32Bytes(boolToHex(bool), Direction.LEFT);
	}

	public static String addressToHex32(String str){
		//Should perhaps add a "protection" clause here, should the string be over 66 chars, for example
		return bytesToHex32(str);
	}

	public static String bytesToHex32(String str){
		//Should perhaps add a "protection" clause here, should the string be over 66 chars, for example
		return str.indexOf("0x") == 0 ? padTo32Bytes(str.trim().substring(2), Direction.LEFT) : padTo32Bytes(str.trim(), Direction.LEFT);
	}

	public static String stringToHex32(String str){
		return padTo32Bytes(stringToHex(str), Direction.RIGHT);
	}


/*
 *
 *
 * TYPE TO HEX CONVERSION FUNCTIONS
 *
 *
 * */


	public static String intToHex(BigInteger integer){
		return signedBigIntToHex(integer);
	}

	public static String uIntToHex(BigInteger integer){
		return unsignedBigIntToHex(integer);
	}

	public static String boolToHex(boolean bool){
		return bool == true ? "1" : "0";
	}

	public static String stringToHex(String str){
		return new String(Hex.encode(str.getBytes()));
	}

	public static String signedBigIntToHex(BigInteger integer){
		return integer.toByteArray().toString();
	}

	public static String unsignedBigIntToHex(BigInteger integer){
		return new BigInteger(integer.toString(), 16).toString();
	}	
	
	
/*
 *
 *
 * BYTE PADDING FUNCTIONS
 *
 *
 * */


	public static String padTo32Bytes(String hexStr, Direction direction){
		return padToBytes(hexStr, 32, direction);
	}

	public static String padToBytes(String hexStr, int byteNum, Direction direction){
		return padBytes(hexStr, 2 * byteNum, direction);
	}

	public static String padBytes(String hexStr, int byteLength, Direction direction){
		int length = hexStr.length();
		int padding = byteLength - length;

		if(direction == Direction.LEFT){
			return StringUtils.repeat('0', padding) + hexStr;
		} else if(direction == Direction.RIGHT){
			return hexStr + StringUtils.repeat('0', padding);
		} else {
			return null;
		}
	}

}
