package com.tts.umap.ui.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

import com.tts.umap.ui.Activator;
import com.tts.umap.ui.preferences.PreferenceConstants;


public class UmapRestClient { 

	private HttpURLConnection connection;
	private String baseUrl;
	private String username;
	private String password;
	
	public UmapRestClient(){
		
	}
	public UmapRestClient(String baseUrl){
		this.baseUrl = baseUrl;
	}
	
	public void getDefaultSettings(){
		baseUrl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_URI);
		username = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_NAME);
		password = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PWD);
	}
	
	public String testService() throws IOException{
			
		openConnection( "test", "GET", null );
		connection.connect();
		String code = "HTTP return code: " + connection.getResponseCode() + " " + connection.getResponseMessage();
		connection.disconnect();
		return code;
	}
	
	public String[] queryTransportRequest() throws IOException {
		openConnection( "trsp", "GET", null );
		connection.connect();
		if (connection.getResponseCode() != 200) {
			throw new IOException();
		}
		
		String tmp = getContent().toString();

        connection.disconnect();
        
        return tmp.split(";");
	}
	
	/**
	 * Liefert den HTTP Body als Stringbuffer
	 * @return
	 * @throws IOException
	 */
	private StringBuffer getContent() throws IOException{
		int read;
        byte buffer[] = new byte[8192];
         
        InputStream responseBodyStream = connection.getInputStream();
        StringBuffer responseBody = new StringBuffer();
        
        while ((read = responseBodyStream.read(buffer)) != -1)
        {
            responseBody.append(new String(buffer, 0, read));
        }
        return responseBody;
	}

	public static void main(String[] args) throws IOException {
		UmapRestClient client = new UmapRestClient("http://ttstti.sap.itulm.lan:8000/sap/zumap");
		client.setPassword("Bruno123");
		client.setUsername("luthermi");
		System.out.println("test service " + client.testService());
		
		client.publishModel(new File("C:/Dokumente und Einstellungen/MaerkerMa/workspace/com.tts.abap2uml.models/p1.umap"));
		
		
	}
	
	public String[] queryPackage(String query) throws IOException{
		String param = "name=" + query;
		openConnection( "devp", "GET", param);
		connection.connect();
		if (connection.getResponseCode() != 200) {
			throw new IOException();
		}
		String reult = getContent().toString();
		
		return reult.split(";");
	}
	/**
	 * 
	 * @param path
	 * @param method
	 * @throws IOException
	 */
	private void openConnection(String path, String method, String query) throws IOException{
		String url;
//		Attache the REST path
		if (baseUrl.endsWith("/")) {
			url = baseUrl + path;
		}
		else{
			url = baseUrl + "/" + path;	
			};
		if ( query != null){
			url = url + "?" + query;
		}
		URL uri = new URL(url);
		connection = (HttpURLConnection) uri.openConnection();
		connection.setRequestMethod(method);
		//Set user and password in BASE64 encoding
		encode();
	}
	
	public boolean publishModel(File file) throws IOException{
//		getDefaultSettings();
		openConnection("publ", "PUT", null);
		try {
			@SuppressWarnings("resource")
			FileInputStream putBody = new FileInputStream(file);
			byte buffer[] = new byte[8192];
	        int read = 0;
	        if (putBody != null)
	        {
	            connection.setDoOutput(true);
	            
	            java.io.OutputStream output = connection.getOutputStream();
	            while ((read = putBody.read(buffer)) != -1)
	            {
	                output.write(buffer, 0, read);
	            }
	        }
	        connection.connect();
	        
	        if (connection.getResponseCode() != 200) return false;
	        connection.disconnect();
	        return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * BASE64 encode für Username und Passwort
	 */
	private void encode(){
		byte[]  encode = new String(username + ":" + password).getBytes();
		String enc = new String(Base64.encodeBase64(encode));
		connection.setRequestProperty("Authorization", "Basic " + enc );
				
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
