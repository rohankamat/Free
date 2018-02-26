package com.bi.upload;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


public class CleanResourceUtility {
	private static Logger logger = Logger.getLogger(CleanResourceUtility.class);
	/**
	 * @param args
	 */
	public static void closeInputStream(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
				inputStream=null;
			}
			catch (Exception ex) {
				logger.debug("Could not close inputStream ", ex);
			}
			catch (Throwable ex) {
			
				logger.debug("Unexpected exception on inputstream ", ex);
			}
		}
	}
	
	public static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
				reader=null;
			}
			catch (Exception ex) {
				logger.debug("Could not close reader ", ex);
			}
			catch (Throwable ex) {
			
				logger.debug("Unexpected exception on reader ", ex);
			}
		}
	}
	public static void closeWriter(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
				writer=null;
			}
			catch (Exception ex) {
				logger.debug("Could not close writer ", ex);
			}
			catch (Throwable ex) {
			
				logger.debug("Unexpected exception on writer ", ex);
			}
		}
	}
	public static void closeOutputStream(OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
				outputStream=null;
			}
			catch (Exception ex) {
				logger.debug("Could not close outputStream ", ex);
			}
			catch (Throwable ex) {
			
				logger.debug("Unexpected exception on outputStream ", ex);
			}
		}
	}
	public static void nullifyObject(Object object) {
		if (object != null) {
			try {
				object=null;
			}
			catch (Exception ex) {
				logger.debug("Could not close object ", ex);
			}
			catch (Throwable ex) {
			
				logger.debug("Unexpected exception on object ", ex);
			}
		}
	}

public static void clear(Map<?, ?> map) {	
		if (map != null) {
			try {
				map.clear();
				map=null;
			}
			catch (Exception ex) {
				logger.debug("Could not clear Map<?, ?> ", ex);
			}
			catch (Throwable ex) {
				logger.debug("Unexpected exception on Map<?, ?>", ex);
			}
		}	
	}
public static void clear(List<?> list) {	
	if (list != null) {
		try {
			list.clear();
			list=null;
		}
		catch (Exception ex) {
			logger.debug("Could not clear List<?> ", ex);
		}
		catch (Throwable ex) {
			logger.debug("Unexpected exception on List<?>", ex);
		}
	}	
}
public static void delete(File file){
	if(file!=null&&file.exists()){
		try {
			file.delete();
			file.deleteOnExit();
			file=null;
		}
		catch (Exception ex) {
			logger.debug("Could not delete File ", ex);
		}
		catch (Throwable ex) {
			logger.debug("Unexpected delete on File ", ex);
		}
	}
}
}
