package main;


import util.Direction;
import util.SolidityType;
import util.ABIUtil;
import util.ABIHexUtil;

import org.bouncycastle.util.encoders.*;
import org.apache.commons.lang3.StringUtils;

public class ABIDecoder {

	public ABIDecoder(){

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

		    String abc = ABIHexUtil.stringToHex32("abc");
			System.out.println("ENCODED   " + abc);
			System.out.println(abc.length());

			System.out.println(ABIHexUtil.intToHex32(-2));
			System.out.println("Testing bool");
			System.out.println(ABIHexUtil.boolToHex32(true));


	}


	public static SolidityType parseParameterToEnum(String str){

		if(str.equals("int")){
			return SolidityType.INTEGER;
		} else if(str.equals("uint")){
			return SolidityType.UINTEGER;
		} else if(str.equals("bool")){
			return SolidityType.BOOL;
		} else if(str.equals("")){

		}
		return SolidityType.INTEGER;


	}

	/*
	 *
	 * END OF ABI PARAMETER FUNCTIONS
	 *
	 *
	 * */


}
