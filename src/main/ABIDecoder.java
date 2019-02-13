package main;

import util.Direction;
import util.SolidityType;
import util.ABIUtil;
import util.ABIHexUtil;

import org.bouncycastle.util.encoders.*;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class ABIDecoder {

	
//	private int ABIPointer;
	
	public ABIDecoder(){
//		ABIPointer = 0;
	}


	public void run(){
		
		realTest();
		//noobTest();
		
	}

	
	
	
	
	public String decode(String rawFunction, String abi) {
		
		String total = decodeParams(ABIUtil.parseParameterTypes(rawFunction), ABIUtil.parseABI(abi), 0);
		//Mini-test to tell if we're decoding our params correctly
//		for(String s : ABIUtil.parseABI(abi)) {
//			System.out.print(s + "|||");
//		}
		if(total.indexOf(',') == -1) { total = total.substring(1, total.length() - 1); }
		
		return total;
	}
	
	public String decodeParams(String[] parsedParams, String[] parsedABI, int ABIPointer) {
		String total = "[";
		
		
		for(String param : parsedParams) {
			if(param.contains("uint")) {
				//convert thing at ABIPointer
				BigInteger integer = ABIHexUtil.Hex32ToUInt(parsedABI[ABIPointer]);
				
				//add to total
				total += integer.toString();
				
				//move forward
				ABIPointer++;
	
			} else if(param.contains("int")) {
				//convert thing at ABIPointer
				BigInteger integer = ABIHexUtil.Hex32ToInt(parsedABI[ABIPointer]);
				
				//add to total
				total += integer.toString();
				
				//move forward
				ABIPointer++;
	
			} else if(param.contains("string")) {
				
				//Get length  TODO: Consider using Hex32ToInt instead
				System.out.println(parsedABI[ABIPointer]);
				int strLength = ABIHexUtil.Hex32ToInteger(parsedABI[ABIPointer]);
				System.out.println(strLength);
				//For each byte, convert the hex into string
				StringBuilder paramStr = new StringBuilder();
				for(int i = 0; i < strLength; i++) {
					paramStr.append(ABIHexUtil.Hex32ToString(parsedABI[ABIPointer+1], 32));
				}
				//Concatenate strings
				total += paramStr.toString();
				//Move forward 1 + string.length
				ABIPointer += 1 + strLength;
			}			
			
			
		}
		
		
		//Close string
		total += "]";
		return total;
	}
	
	
	
	
	public void realTest() {
		
		
		ArrayList<String[]> testCases = new ArrayList<String[]>();
		
		String[] test1 = {"function baz(int8)", "0xfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe", "-2"};
		String[] test2 = {"function baz(int80)", "0x0000000000000000000000000000000000000000000000000000b29c26f344fe", "196383738119422"};
		String[] test3 = {"function baz(uint32)", "0xfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe", "4294967294"};
		String[] test4 = {"function baz(string)", "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000b68656c6c6f20776f726c64000000000000000000000000000000000000000000", "hello world"};
		String[] test5 = {"function baz(bytes[] a, bytes32 b)", "0x0000000000000000000000000000000000000000000000000000000000000040cb93e7ddea88eb37f5419784b399cf13f7df44079d05905006044dd14bb898110000000000000000000000000000000000000000000000000000000000000003000bf9f2adc93a1da7b9e61f44ee6504f99c467a2812b354d70a07f0b3cdc58c0007cc5734453f8d7bbacd4b3a8e753250dc4a432aaa5be5b048c59e0b5ac5fc00120aa407bdbff1d93ea98dafc5f1da56b589b427167ec414bccbe0cfdfd573", "[0x000bf9f2adc93a1da7b9e61f44ee6504f99c467a2812b354d70a07f0b3cdc58c, 0x0007cc5734453f8d7bbacd4b3a8e753250dc4a432aaa5be5b048c59e0b5ac5fc, 0x00120aa407bdbff1d93ea98dafc5f1da56b589b427167ec414bccbe0cfdfd573], 0xcb93e7ddea88eb37f5419784b399cf13f7df44079d05905006044dd14bb89811"};
		String[] test6 = {"function baz(int[3])", "0x000000000000000000000000000000000000000000000000000000000000002afffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb", "[42, -3, -5]"};
		String[] test7 = {"function baz(uint128[2][3], uint)", "0x000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000a", "[[1, 2, 3], [4, 5, 6]], 10"};
		String[] test8 = {"function baz(uint128[2][3][2], uint)", "0x000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000a", "[[[1, 2], [3, 4], [5, 6]], [[1, 2], [3, 4], [5, 6]]], 10"};
		String[] test9 = {"function baz(uint[3][], uint)", "0x0000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000000a0000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006", "[[1, 2], [3, 4], [5, 6]], 10"};		
		String[] test10 = {"function baz(uint[][3],uint)", "0x000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000e00000000000000000000000000000000000000000000000000000000000000140000000000000000000000000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000030000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006", "[[1, 2], [3, 4], [5, 6]], 10"};
		String[] test11 = {"function baz(uint256[] a,uint[] b,uint256[] c)", "0x000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000c0000000000000000000000000000000000000000000000000000000000000012000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000015af1d78b58c400000000000000000000000000000000000000000000000000015af1d78b58c4000000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000001bc16d674ec800000000000000000000000000000000000000000000000000001bc16d674ec80000", "[6, 5], [25000000000000000000, 25000000000000000000], [2000000000000000000, 2000000000000000000]"};				
		
		
		testCases.add(test1);
		testCases.add(test2);
		testCases.add(test3);
		testCases.add(test4);
		testCases.add(test5);
		testCases.add(test6);		
		testCases.add(test7);
		testCases.add(test8);
		testCases.add(test9);
		testCases.add(test10);
		testCases.add(test11);		
		
		/*
		 * Iterate through test cases, print expected values, and whether succeeds or fails
		 * 
		 * */
		for(String[] test : testCases) {
			
			System.out.println("=============================================================");
			System.out.println("FUNCTION INPUT: " + test[0]); 
			System.out.println("ABI: " + test[1]);
			System.out.println("EXPECTING: " + test[2]); 
			String res = decode(test[0], test[1]);
			if(res == null) { res = "null"; } else if(res.equals("")) { res = "empty value"; }
			System.out.println("\n" + res + "\n\n");
			if(res.equals(test[2])) {
				System.out.println("     1 TEST PASSED: SUCCESS!!!");
			} else {
				System.out.println("     0 FAILED!");
			}
			System.out.println("=============================================================");
			System.out.println("\n\n");
		}		
		
	}
	
	
	public void noobTest() {
		
		String[] testFunctionStrings = {
				"function baz(int8)",
				"function baz(uint32)",
				"function baz(bytes[] a, bytes32 b)",
				"function baz(uint128[2][3], uint)"
					};

		for(String t : testFunctionStrings){
			System.out.println("Test function string: " + t);
		
			String[] res = ABIUtil.parseParameterTypes(t);
			for(String str : res) {
				System.out.print("  " + str);
			}
			System.out.println();

		}

		    String abc = ABIHexUtil.stringToHex32("abc");
			System.out.println("ENCODED   " + abc);
			System.out.println(abc.length());

			System.out.println(ABIHexUtil.intToHex32(BigInteger.valueOf(-2)));
			System.out.println("Testing bool");
			System.out.println(ABIHexUtil.boolToHex32(true));

			System.out.println(ABIHexUtil.addressToHex32("0x965D1C9987BD2c34e151E63d60AFf8E9dB6b1561"));
			System.out.println(ABIHexUtil.addressToHex32("965D1C9987BD2c34e151E63d60AFf8E9dB6b1561"));
			System.out.println(ABIHexUtil.bytesToHex32("0x123"));
			System.out.println(ABIHexUtil.Hex32ToBool("0000000000000000000000000000000000000000000000000000000000000001"));
			System.out.println(ABIHexUtil.Hex32ToSignedBigInt("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe"));
			System.out.println(ABIHexUtil.Hex32ToString("6162630000000000000000000000000000000000000000000000000000000000", 3));

			System.out.println(ABIHexUtil.stringToHex32("abc"));	
			
		
		
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
		return null;


	}

	/*
	 *
	 * END OF ABI PARAMETER FUNCTIONS
	 *
	 *
	 * */


}
