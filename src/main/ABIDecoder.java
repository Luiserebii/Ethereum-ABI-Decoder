package main;


import util.Direction;
import util.ABIUtil;
import util.ABIHexUtil;

import org.bouncycastle.util.encoders.*;
import org.apache.commons.lang3.StringUtils;

public class ABIDecoder {

	public ABIDecoder(){

	}

	public enum solidityTypes{
		UINTEGER, INTEGER, ADDRESS, BOOL, BYTES, STRING
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
			System.out.println(ABIUtil.toCleanFunctionSig(t));

		}

		    String abc = ABIHexUtil.padTo32Bytes(ABIHexUtil.stringToHex("abc"), Direction.RIGHT);
			System.out.println("ENCODED   " + abc);
			System.out.println(abc.length());

			System.out.println(ABIHexUtil.padTo32Bytes(Integer.toHexString(-2), Direction.LEFT));

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
