package main;

import org.bouncycastle.util.encoders.*;
import org.apache.commons.lang3.StringUtils;

public class ABIDecoder {

	public ABIDecoder(){

	}

	public enum solidityTypes{
		UINTEGER, INTEGER, ADDRESS, BOOL, BYTES, STRING
	}

	public enum Direction {
		LEFT, RIGHT
	}

	public void run(){

		String[] testFunctionStrings = {
				"function baz(int8)",
				"function baz(uint32)",
				"function baz(bytes[] a, bytes32 b)",
				"function baz(uint128[2][3], uint)"
					};

		for(String t : testFunctionStrings){
			System.out.println("Test function string: " + t);
			System.out.println(toCleanFunctionSig(t));

		}

		    String abc = padTo32Bytes(stringToHex("abc"), Direction.RIGHT);
			System.out.println("ENCODED   " + abc);
			System.out.println(abc.length());

	}

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

	/*
	 *
	 * ABI Function hashing functions
	 *
	 *
	 * */

	public String toCleanFunctionSig(String functionStr){

	    String cleanSig;
	    String functionName = parseFunctionName(functionStr);
	    String[] parameterTypes = parseParameterTypes(functionStr);
	    //Build the first piece of the clean signature
	    cleanSig = functionName + "(" + parameterTypes[0];
	    //If we have more than 1 parameter, lets cycle through them and add commas...
	    if(parameterTypes.length >= 2){
	    	for(int i = 1; i < parameterTypes.length; i++){
	    		cleanSig += "," + parameterTypes[i];
	    	}
	    }
	    cleanSig += ")";

		return cleanSig;
	}

	public String toMethodID(String functionSig){

		String methodID = "";

		return methodID;
	}

	public String parseFunctionName(String str){

		String functionName;

		int indexEnd = str.indexOf("(");
		int indexStart = 0;
		//If our function name has the function keyword specified...
		if(str.contains("function")){
			indexStart = str.indexOf("function") + 8;
		}
		functionName = str.substring(indexStart, indexEnd).trim();


		//System.out.println("Function Name: " + functionName);

		return functionName;
	}

	public String[] parseParameterTypes(String str){

		String[] parameterTypes;

	    int indexStart = str.indexOf("(") + 1;
	    int indexEnd = str.indexOf(")");
	    parameterTypes = (str.substring(indexStart, indexEnd)).split(",");

	    for(int i = 0; i < parameterTypes.length; i++){
	    	//Trim the extra whitespace
	    	String paramType = parameterTypes[i].trim();
	    	//Remove all but the type, if we have a var explicitly named
	    	if(paramType.indexOf(" ") != -1){
	    		paramType = paramType.substring(0, paramType.indexOf(" "));
	    	}
	    	parameterTypes[i] = paramType;
	    	//System.out.println(paramType);
	    }
	    return parameterTypes;
	}

	public solidityTypes parseParameterToEnum(String str){


		if(str.equals("int")){
			return solidityTypes.INTEGER;
		} else if(str.equals("uint")){
			return solidityTypes.UINTEGER;
		} else if(str.equals("bool")){
			return solidityTypes.BOOL;
		} else if(str.equals("")){

		}


		return solidityTypes.INTEGER;


	}

	/*
	 *
	 * END OF ABI PARAMETER FUNCTIONS
	 *
	 *
	 * */


}
