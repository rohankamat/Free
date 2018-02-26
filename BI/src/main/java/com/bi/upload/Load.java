package com.bi.upload;

import java.io.File;

import org.json.simple.JSONObject;

import com.bi.utility.ConnectionUtility;

public class Load {
	
	
	public String CsvLoad(File file,String tablename){
        ConnectionUtility connectioninfo=new ConnectionUtility();
        
    	CSVLoader loader = null;
		try {
			loader = new CSVLoader(connectioninfo.getConnection());
		} catch (Exception e) {
			JSONObject result=new JSONObject();
			result.put("status", "failed");
			result.put("errorMessage", e.getMessage());
			return result.toString();
		}
    	try {
			loader.loadCSV(file, tablename, true);
			JSONObject result=new JSONObject();
			result.put("status", "success");
			return result.toString();
		} catch (Exception e) {
			JSONObject result=new JSONObject();
			result.put("status", "failed");
			result.put("errorMessage", e.getMessage());
			return result.toString();
		}
    	
	}
	

}
