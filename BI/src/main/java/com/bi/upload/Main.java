package com.bi.upload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.bi.utility.ConnectionUtility;
 
public class Main {
	
 
    public static void main(String[] args) throws Exception {
 
    	ConnectionUtility connectioninfo=new ConnectionUtility();
        
    	CSVLoader loader = new CSVLoader(connectioninfo.getConnection());
    	
    	
        
    	//For Country Table
    	//loader.loadCSV("Country.csv", "COUNTRY", true);
    	
    	
    	System.out.println("Data loaded successfully!");
    }
}
