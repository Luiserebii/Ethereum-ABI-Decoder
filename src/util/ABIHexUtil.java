package util;

import org.bouncycastle.util.encoders.*;
import org.apache.commons.lang3.StringUtils;

public class ABIHexUtil {



/*
 *
 * Short-hand type to 32-bit hex
 *
 *
 * */


	public static String intToHex32(int integer){
		return padTo32Bytes(intToHex(integer), Direction.LEFT);
	}

	public static String boolToHex32(boolean bool){
		return padTo32Bytes(boolToHex(bool), Direction.LEFT);
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


	public static String intToHex(int integer){
		return Integer.toHexString(integer);
	}


	public static String boolToHex(boolean bool){
		return bool == true ? "1" : "0";
	}

	public static String stringToHex(String str){
		return new String(Hex.encode(str.getBytes()));
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
