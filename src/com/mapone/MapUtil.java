package com.mapone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapUtil {
	public static String InputStreamToString(InputStream inputstream,String Encoding){
		String reString=new String();
		try {
			StringBuffer  sbuffer=new   StringBuffer();			
			InputStreamReader inReader=new InputStreamReader(inputstream,Encoding);
			BufferedReader reader=new BufferedReader(inReader);			
			String line;
			while ((line = reader.readLine()) != null) {
				sbuffer.append(line);
			}
			reString=sbuffer.toString();
			//System.out.println(reString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return reString;
	}
}
