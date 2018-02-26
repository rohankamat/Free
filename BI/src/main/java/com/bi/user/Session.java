package com.bi.user;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bi.utility.TemplateClasses;

public class Session {
	
	
	
	
	@SuppressWarnings("unchecked")
	public String login(String user ,String password) throws Exception{
		TemplateClasses template=new TemplateClasses();
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("name", user);
		params.addValue("password", password);
		JSONArray json=template.getJsonResults("SELECT id, name, description, role, isactive, contact_no, created_on, updated_on, "
				+ "password FROM biuser where name=:name and password=:password", params);
		if(!json.isEmpty()){
			JSONObject result=new JSONObject();
			result.put("session", json);
			return result.toString();
		}else{
			JSONObject errorMessage=new JSONObject();
			errorMessage.put("status", "failed");
			errorMessage.put("errorMessage", "please enter the correct password");
			return errorMessage.toString();
		}
		
	}
	
	
	
	
	

}
