package com.bi.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class TemplateClasses {
	
	
	private static final Logger logger = Logger.getLogger(TemplateClasses.class);
	private JdbcTemplate template;
	private NamedParameterJdbcTemplate NamedParameterTemplate;
	private SimpleJdbcTemplate simpletemplate;
	private BasicDataSource datasource;

	
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template,BasicDataSource datasource) throws Exception {
		if (template == null) {
			template = new JdbcTemplate(datasource);
		}
		template = template;
	}

	
	public boolean check(String query, MapSqlParameterSource params) throws Exception {
		BasicDataSource DataSource = null;
		boolean result = false;
		try {

			ConnectionUtility con = new ConnectionUtility();
			DataSource = con.getDataSource();
			NamedParameterTemplate = new NamedParameterJdbcTemplate(DataSource);
			int output = NamedParameterTemplate.queryForInt(query, params);
			if (output > 0) {
				result = true;
			}
			NamedParameterTemplate = null;
		} catch (Exception exception) {
			logger.error("Issue", exception);
			return false;
		} finally {
			if (DataSource != null) {
				DataSource.close();
				DataSource = null;
			}
		}
		return result;
	}

	public JSONArray getJsonResults(String query, MapSqlParameterSource params) throws Exception {
		JSONArray result = null;
		BasicDataSource DataSource = null;
		try {
			ConnectionUtility con = new ConnectionUtility();
			DataSource = con.getDataSource();

			NamedParameterTemplate = new NamedParameterJdbcTemplate(DataSource);
			result = NamedParameterTemplate.query(query, params, new ListResultExtractorjson());

			// simple.
			NamedParameterTemplate = null;
		} catch (Exception exception) {
			logger.error("Issue", exception);
			return null;
		} finally {
			if (DataSource != null) {
				DataSource.close();
				DataSource = null;
			}
		}
		return result;
	}

}

class ListResultExtractorjson implements ResultSetExtractor <JSONArray> {

	@Override
	public JSONArray extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		JSONArray result = new JSONArray();
		JSONObject resultobject=null;
		while (rs.next()) {
			 resultobject = new JSONObject();
			java.sql.ResultSetMetaData resultSetMetaData = rs
					.getMetaData();
			int numColumns = 0;
			numColumns = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String colName = resultSetMetaData
						.getColumnName(i);
				try{
					resultobject.put(resultSetMetaData
							.getColumnName(i).toLowerCase(), (rs.getString(colName) == null ? 	null :(JSONArray) (new JSONParser().parse(rs.getString(colName)))));
				}catch(Exception exception){
					try{
						resultobject.put(resultSetMetaData
								.getColumnName(i).toLowerCase(), (rs.getString(colName) == null ? 	null :(JSONObject) (new JSONParser().parse(rs.getString(colName)))));
					}catch(Exception exc){
						resultobject.put(resultSetMetaData
								.getColumnName(i).toLowerCase(), (rs.getString(colName) == null ? 	null :rs.getString(colName).toString()));
					}
				}
				
			}
			result.add(resultobject);
		}
		return result;
	}
}
