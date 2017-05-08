package cn.golaxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;
import java.util.Map;

public final class HttpUtil {



	private static void connectTest(String con) throws Exception {
		URL url = new URL(con);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		byte[] buf = new byte[1024];
		StringBuilder builder = new StringBuilder();
		while ((in.read(buf)) != -1) {
			builder.append(new String(buf, "UTF-8"));
		}
		System.out.println(builder.toString());
		in.close();
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws Exception {
		// 1.打开连接
		URL realUrl = new URL(url + "?" + param);
		URLConnection connection = realUrl.openConnection();
		buliderConnectionHeader(connection);
		connection.connect();

		// 2.获取响应数据
		BufferedReader in = new BufferedReader(//
				new InputStreamReader(connection.getInputStream()));
		String line, result = null;
		while ((line = in.readLine()) != null)
			result += line;
		in.close();
		return result;
	}

	private static void buliderConnectionHeader(URLConnection connection) {
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求应该与目标协议一致
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) throws Exception {
		// 1.打开连接
		URLConnection conn = new URL(url).openConnection();
		buliderConnectionHeader(conn);
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);

		// 2.设置请求参数
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(param);
		out.flush();

		// 3.接受返回数据
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line, result = null;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		out.close();
		in.close();
		return result;
	}
}
