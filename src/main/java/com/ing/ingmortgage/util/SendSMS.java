package com.ing.ingmortgage.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {

	public static void main(String[] args) {
		sendSms();

	}

	public static String sendSms() {
		try {
			// Construct data
			String apiKey = "apikey=" + "jNZTppE/axs-wck2MWHQmmTC9OE4wlQdpfCX7n1O89";
			String message = "&message=" + "Hi, Succesfully registered";
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + "918884148999";

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			System.out.println("SMS sent");
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}
}
