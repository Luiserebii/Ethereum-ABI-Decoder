package util;

import org.bouncycastle.util.encoders.*;
import org.apache.commons.lang3.StringUtils;

public class ABIHexUtil {

	public static String stringToHex(String str){
		return new String(Hex.encode(str.getBytes()));
	}

	public static String padTo32Bytes(String hexStr, Direction direction){
		return padBytes(hexStr, 32, direction);
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
