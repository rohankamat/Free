package com.bi.upload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 * @author Rohan Kamat(EmpId:1015)
 * @version 1.0
 * @organization NanoBi Analytics
 * @Date Jun 20, 2012
 */
/**
 * @author chandra shekhar yadav(EmpId:1021)
 * @version 1.0
 * @organization NanoBi Analytics
 * @Date nov 20, 2012
 */
public class FileUtility {
	private static Logger NANOBILOGGER = Logger
			.getLogger(FileUtility.class);
	private static final String LINE_SEPARATOR = System.lineSeparator();
	public InputStream convertStringToInputStream(String stringData) {
		InputStream is = new ByteArrayInputStream(stringData.getBytes());
		return is;
	}
	public InputStream getInputStream(ByteArrayOutputStream baos) {
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}
	public  ByteArrayOutputStream getByteArrayOutputStream(InputStream inputStream) throws Exception{
		 ByteArrayOutputStream baos = null;
		 try{
		 baos=new ByteArrayOutputStream();
		    byte[] buffer = new byte[1024];
		    int len;
		    while ((len = inputStream.read(buffer)) > -1 ) {
		        baos.write(buffer, 0, len);
		    }
		 }catch(Exception e){
			 throw e;
		 }finally{
			    baos.flush();
		 }
		 return baos;
	}
	public String getStringFromInputStream(InputStream is) throws Exception{
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line).append(LINE_SEPARATOR);
			}
 
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
			   CleanResourceUtility.closeReader(br);	
			}
		}
 
		return sb.toString();
 
	}
	public String readFileToString(String url) {

		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		StringBuilder nanomartschemaurl = null;
		try {
			inputStream = FileUtility.class.getResourceAsStream(url);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream));

		} catch (Exception ex) {

		}
		String bufferline = null;
		nanomartschemaurl = new StringBuilder();
		try {
			while ((bufferline = bufferedReader.readLine()) != null) {
				nanomartschemaurl.append(bufferline);
			}

		} catch (IOException ex) {

		}

		return nanomartschemaurl.toString();

	}

	public String convertFileToString(File afile) {

		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		StringBuilder data = null;

		try {
			fileReader = new FileReader(afile);
			bufferedReader = new BufferedReader(fileReader);
			String bufferline = null;
			data = new StringBuilder();

			while ((bufferline = bufferedReader.readLine()) != null) {
				data.append(bufferline);
			}

		} catch (Exception ex) {

		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				
			}
		}

		return data.toString();
	}

	public void writeFile(String data, File afile) {
		BufferedWriter bufferedWriter=null;
		try {
			 bufferedWriter = new BufferedWriter(new FileWriter(afile));
			bufferedWriter.write(data);

		} catch (IOException e) {

		}finally {
		

			try {

				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
					bufferedWriter = null;
				}

			} catch (IOException ex) {

			}
		}
	}

	public File writeFile(String data, String fullFilePath) {

		File afile = new File(fullFilePath);
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(afile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(data);

		} catch (IOException exception) {
			NANOBILOGGER.error(exception);//exception.printStackTrace();

		} finally {
		

			try {

				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
					bufferedWriter = null;
				}

			} catch (IOException ex) {

			}

			try {

				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
					fileWriter = null;
				}

			} catch (IOException ex) {

			}
			
			
			
		}
		return afile;
	}

	public File readAttachedFiles(List<Attachment> fileattachments) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		FileWriter fileWriter = null;
		File file = null;
		String filename = null;
		BufferedReader bufferedReader = null;
		for (Attachment attr : fileattachments) {
			MultivaluedMap map = attr.getHeaders();
			DataHandler handler = attr.getDataHandler();
			filename = getFileName(map);
			if (filename != null) {

				int pos = filename.lastIndexOf('.');
				String fileextension = filename.substring(pos + 1);

				try {
					inputStream = handler.getInputStream();

					file = new File( UUID.randomUUID().toString()
							+ "." + fileextension);

					fileWriter = new FileWriter(file);

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					StringBuilder fileContents = new StringBuilder();
					String fileLine = null;
					while ((fileLine = reader.readLine()) != null) {
						fileContents.append(fileLine);
						fileContents.append("\n");
					}

					fileWriter.write(fileContents.toString());

				} catch (Exception ex) {

				} finally {
					try {
						if (inputStream != null) {
							inputStream.close();
							inputStream = null;
						}
					} catch (Exception exception) {

					}
					try {
						if (fileWriter != null) {
							fileWriter.flush();
							fileWriter.close();
							fileWriter = null;
						}
					} catch (Exception exception) {

					}
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
							bufferedReader = null;
						}
					} catch (Exception exception) {

					}
				}
			}

		}

		return file;

	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return null;
	}

	/*
	 * public static List<String> readAllFilesFromDirectory(String apath) { File
	 * dir = new File(apath); Collection<File> fileLists =
	 * org.apache.commons.io.FileUtils.listFiles( dir, null,
	 * DirectoryFileFilter.DIRECTORY); Iterator<File> fileIterator =
	 * fileLists.iterator(); LinkedList<String> fileNames = new
	 * LinkedList<String>();
	 * 
	 * while (fileIterator.hasNext()) { File file = (File) fileIterator.next();
	 * fileNames.add(file.getName()); }
	 * 
	 * return fileNames; }
	 */
	public boolean isFileExists(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	public String saveFile(File afile, String basepath, String filereferencename) {

		StringBuilder stringBuilder = null;
		BufferedWriter bufferedWriter = null;
		File destination = null;
		BufferedReader bufferedReader = null;
		stringBuilder = new StringBuilder();
		String filename = null;
		if (filereferencename == null) {
			filename = afile.getName();
		}
		try {
			bufferedReader = new BufferedReader(new FileReader(afile));

		} catch (FileNotFoundException ex) {

		}

		String bufferline = null;
		try {
			while ((bufferline = bufferedReader.readLine()) != null) {

				stringBuilder.append(bufferline);
				stringBuilder.append("\n");
			}

		} catch (IOException ex) {

		} finally {

		}

		try {
			destination = new File(basepath + filename);
			bufferedWriter = new BufferedWriter(new FileWriter(destination));
			bufferedWriter.write(stringBuilder.toString());
		} catch (IOException ex) {

		} finally {

			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {

			}

		}

		return destination.getAbsolutePath();
	}

	/*public File copyFileFromPath(String filePath) {

		InputStream inStream = null;
		OutputStream outStream = null;
		File sourceFile = null;
		File destFile = null;
		try {

			sourceFile = new File(filePath);
			destFile = new File("mart.xml");
			inStream = new FileInputStream(sourceFile);
			outStream = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

		} catch (IOException e) {

		}
		return destFile;
	}
*/
	public File readAttachedExcelFile(List<Attachment> fileattachments) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		String filename = null;

		for (Attachment attr : fileattachments) {
			DataHandler handler = attr.getDataHandler();
			try {
				inputStream = handler.getInputStream();
				MultivaluedMap map = attr.getHeaders();
				filename = getFileName(map);
				file = new File(filename);
				outputStream = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int read = 0;
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
					outputStream = null;
				}

			} catch (Exception ex) {

			}

		}

		return file;
	}
	public List<File> readAttachedFile(List<Attachment> fileattachments) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		String filename = null;
       List<File> listFile=new ArrayList<File>();
		for (Attachment attr : fileattachments) {
			File file = null;
			DataHandler handler = attr.getDataHandler();
			try {
				inputStream = handler.getInputStream();
				MultivaluedMap map = attr.getHeaders();
				filename = getFileName(map);
				file = new File(filename);
				outputStream = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int read = 0;
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
					outputStream = null;
				}

			} catch (Exception ex) {

			}
			listFile.add(file);
		}

		return listFile;
	}
	
	public File readFilefromURL(String uri) throws Exception{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		String fileext=null;
		try{
			fileext = (uri.split("\\.(?=[^\\.]+$)"))[1];
		}catch(Exception exception){
			fileext="xlsx";
		}
		 
		String filename =  UUID.randomUUID().toString()+"."+fileext;

		try {
			URL fileurl = new URL(URIUtil.encodeQuery(uri, "UTF-8"));
			file = new File(filename);
			outputStream = new FileOutputStream(file);
			inputStream = fileurl.openStream();
			byte[] bytes = new byte[1024];
			int read = 0;

			// BufferedReader reader=new BufferedReader(new
			// InputStreamReader(fileurl.openStream())) ;
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (Exception ex) {
			throw ex;
		}finally{
			CleanResourceUtility.closeInputStream(inputStream);
			CleanResourceUtility.closeOutputStream(outputStream);
		}
		return file;
	}
	public File readFilefromURL(String uri,String filename) throws Exception{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		//String filename =  UUID.randomUUID().toString(;

		try {
			URL fileurl = new URL(uri);
			URLConnection urlConn = fileurl.openConnection();
			file = new File(filename);
			outputStream = new FileOutputStream(file);
			inputStream = fileurl.openStream();
			byte[] bytes = new byte[1024];
			int read = 0;

			// BufferedReader reader=new BufferedReader(new
			// InputStreamReader(fileurl.openStream())) ;
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Exception ex) {
			throw ex;
		}finally{
			CleanResourceUtility.closeInputStream(inputStream);
			CleanResourceUtility.closeOutputStream(outputStream);
		}
		return file;
	}
	public File readFileFromResource(InputStream inputStream) {
		String filename = UUID.randomUUID().toString();
		File tempJsonFile = new File(filename);
		//File tempJsonFile = new File("tempfile.json");
		OutputStream out = null;
		try {
			out = new FileOutputStream(tempJsonFile);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception exception) {

			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception inputex) {

				}
			}
		}

		return tempJsonFile;
	}
	public File readFileFromResource(InputStream inputStream,String fileName) {
 		
		File tempJsonFile = new File(fileName);
		OutputStream out = null;
		try {
			out = new FileOutputStream(tempJsonFile);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception exception) {

			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception inputex) {

				}
			}
		}
		return tempJsonFile;
	}
	
	public File readFileFromResource(InputStream inputStream,String fileName,String ext) {

		File tempJsonFile = new File(fileName+"."+ext);
		OutputStream out = null;
		try {
			out = new FileOutputStream(tempJsonFile);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception exception) {

			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception inputex) {

				}
			}
		}

		return tempJsonFile;
	}
	public File writeFileWithAppend(String filename, String data) throws Exception {
		File errorFile = new File(filename);
		return writeFileWithAppend(errorFile, data);
	}
	public File writeFileWithAppend(File file, String data) throws Exception {
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {

			fstream = new FileWriter(file, true);
	      	out = new BufferedWriter(fstream);
	      	out.write(data);
			out.newLine();
		
			
		} catch (Exception e) {// Catch exception if any
			throw e;
		} finally {
			try {
				out.flush();
				out.close();
				out = null;
			} catch (Exception ex) {
			}

			try {
				fstream.close();
				fstream = null;
			} catch (Exception ex) {
			}
		}

		return file;
	}

	public URI writeString(String data, String basepath,
			String filereferencename) {

		File afile = new File(basepath + filereferencename);
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(afile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(data);

		} catch (IOException e) {

		} finally {

			try {

				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
					bufferedWriter = null;
				}
			} catch (IOException ex) {

			}
		}
		return afile.toURI();
	}
/**
 * it returns multi-line if source has multi line
 * @param filePath
 * @return
 * @throws java.io.IOException
 */
	public String readFileAsString(String filePath) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
	
	public  String getFileExtension(File file)
    {
        String name = file.getName();
        int k = name.lastIndexOf(".");
        String ext = null;
        if(k != -1)
            ext = name.substring(k + 1, name.length());
        return ext;
    }
    
	public File readAttachedFilesWithReference(List<Attachment> fileattachments,String fileRefName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		FileWriter fileWriter = null;
		File file = null;
		String filename = null;
		BufferedReader bufferedReader = null;
		for (Attachment attr : fileattachments) {
			MultivaluedMap map = attr.getHeaders();
			DataHandler handler = attr.getDataHandler();
			filename = getFileName(map);
			if (filename != null) {

				int pos = filename.lastIndexOf('.');
				//String fileextension = filename.substring(pos + 1);

				try {
					inputStream = handler.getInputStream();

				//	file = new File( UUID.randomUUID().toString(+ "." + fileextension);
					file = new File(fileRefName);
					fileWriter = new FileWriter(file);

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					StringBuilder fileContents = new StringBuilder();
					String fileLine = null;
					while ((fileLine = reader.readLine()) != null) {
						fileContents.append(fileLine);
						fileContents.append("\n");
					}

					fileWriter.write(fileContents.toString());

				} catch (Exception ex) {

				} finally {
					try {
						if (inputStream != null) {
							inputStream.close();
							inputStream = null;
						}
					} catch (Exception exception) {

					}
					try {
						if (fileWriter != null) {
							fileWriter.flush();
							fileWriter.close();
							fileWriter = null;
						}
					} catch (Exception exception) {

					}
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
							bufferedReader = null;
						}
					} catch (Exception exception) {

					}
				}
			}

		}

		return file;

	}
	
	public InputStream convertAttachmentstoInputStream(List<Attachment> fileattachments) throws DataAccessException{
		InputStream inputStream = null;
		
		String filename = null;
	
		for (Attachment attr : fileattachments) {
			MultivaluedMap map = attr.getHeaders();
			DataHandler handler = attr.getDataHandler();
			filename = getFileName(map);
			if (filename != null) {
				
				int pos = filename.lastIndexOf('.');
				//String fileextension = filename.substring(pos + 1);

				try {
					inputStream = handler.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					org.apache.commons.io.IOUtils.copy(inputStream, baos);
					byte[] bytes = baos.toByteArray();			
				
					ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
					BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
					if(reader.readLine() == null){
						NANOBILOGGER.error(filename+" : file is empty");
						throw new DataAccessException("No records fond in the file") {
						};
					}
					reader.close();
					bais.close();
					inputStream = new ByteArrayInputStream(bytes);
				} catch (Exception ex) {
					NANOBILOGGER.error(ex);
					if(ex instanceof DataAccessException){
						throw (DataAccessException)ex;
					}
                    } 	
				}
			}
		return inputStream;
		}
	
	public ByteArrayOutputStream convertAttachmentstoByteArrayInputStream(List<Attachment> fileattachments) throws DataAccessException{
		
		String filename = null;
		InputStream inputStream = null;
		for (Attachment attr : fileattachments) {
			MultivaluedMap map = attr.getHeaders();
			DataHandler handler = attr.getDataHandler();
			filename = getFileName(map);
			if (filename != null) {
				
				int pos = filename.lastIndexOf('.');
				//String fileextension = filename.substring(pos + 1);

				try {
					inputStream = handler.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					org.apache.commons.io.IOUtils.copy(inputStream, baos);
					byte[] bytes = baos.toByteArray();			
				
					ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
					BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
					if(reader.readLine() == null){
						NANOBILOGGER.error(filename+" : file is empty");
						throw new DataAccessException("No records fond in the file") {
						};
					}
					reader.close();
					bais.close();
				//	inputStream = new ByteArrayInputStream(bytes);
					
					return baos;
					
					
				} catch (Exception ex) {
					NANOBILOGGER.error(ex);
					if(ex instanceof DataAccessException){
						throw (DataAccessException)ex;
					}
                    } 	
				}
			}
		return null;
		}
	
	public String getFileName(List<Attachment> fileattachments){
		String filename = null;
	
		for (Attachment attr : fileattachments) {
			MultivaluedMap map = attr.getHeaders();
			DataHandler handler = attr.getDataHandler();
			
			if (getFileName(map) != null) {
				filename=getFileName(map);
				}
			}
		return filename;
		}
	public InputStream convertfileURLtoInputStream(String fileUrl) throws MalformedURLException, IOException{
		
		InputStream is = new URL(fileUrl).openStream();
		return is;
	}
	 public String getTextFromURL(String url) throws Exception {
	        URL website = new URL(url);
	        URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));

	        StringBuilder response = new StringBuilder();
	        String inputLine;

	        while ((inputLine = in.readLine()) != null)response.append(inputLine);
            if(in!=null)in.close();
	        return response.toString();
	    }
	 
	

/*	public static void unzip(String zipFilename, String destDirname)
			throws IOException {
		
		final Path destDir = Paths.get(destDirname);
		// if the destination doesn't exist, create it
		if (Files.notExists(destDir)) {
			//System.out.println(destDir + " does not exist. Creating...");
			Files.createDirectories(destDir);
		}

		try {

			FileSystem zipFileSystem = createZipFileSystem(zipFilename, false);

			final Path root = zipFileSystem.getPath("/");

			// walk the zip file tree and copy files to the destination
			Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					final Path destFile = Paths.get(destDir.toString(),
							file.toString());
					System.out.printf("Extracting file %s to %s\n", file,
							destFile);
					Files.copy(file, destFile,
							StandardCopyOption.REPLACE_EXISTING);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) throws IOException {
					final Path dirToCreate = Paths.get(destDir.toString(),
							dir.toString());
					if (Files.notExists(dirToCreate)) {
						System.out.printf("Creating directory %s\n",
								dirToCreate);
						Files.createDirectory(dirToCreate);
					}
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (Exception ex) {

		}
		
		
	}

	private static FileSystem createZipFileSystem(String zipFilename,
			boolean create) throws IOException {
		 
		final Path path = Paths.get(zipFilename);
		final URI uri = URI.create("jar:file:" + path.toUri().getPath());

		final Map<String, String> env = new HashMap<String, String>();
		if (create) {
			env.put("create", "true");
		}
		return FileSystems.newFileSystem(uri, env);
		
		
	
	}
	*/
	

}