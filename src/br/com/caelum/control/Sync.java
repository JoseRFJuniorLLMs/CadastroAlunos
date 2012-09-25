package br.com.caelum.control;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Sync {

	private String end = "http://www.caelum.com.br/mobile?dado=";

	public String enviarDado(String dado) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String encode = end + URLEncoder.encode(dado);
		Log.i("envio", encode);
		HttpGet httpGet = new HttpGet(encode);
		HttpResponse response;
		InputStream is = null;
		StringBuffer sb = new StringBuffer();

		try {
			response = httpClient.execute(httpGet);
			HttpEntity ent = response.getEntity();

			if (ent != null) {
				is = ent.getContent();
				Scanner s = new Scanner(is);

				while (s.hasNext()) {
					sb.append(s.next());
				}
			}
		} catch (Exception e) {

		} finally {
			if (is != null) {
				is.close();
			}
		}

		return sb.toString();
	}

}