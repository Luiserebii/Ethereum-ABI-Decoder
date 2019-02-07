package main;

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
			toCleanFunctionSig(t);

		}
	}

	public String toCleanFunctionSig(String functionStr){

	    String cleanSig = "";
	    String functionName = parseFunctionName(functionStr);
	    String[] parameterTypes = parseParameterTypes(functionStr);


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
		
		
		System.out.println("Function Name: " + functionName);
		
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
	    	System.out.println(paramType);
	    }
	    return parameterTypes;

	}

}