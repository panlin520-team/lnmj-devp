package com.lnmj.k3cloud.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.utils.NumberUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InvokeHelper {

//	@Value("${ftp.filePath}")
	private String filePath = "d:/temp";	//

	// K3 Cloud WebSite URL Example "http://47.108.29.183:8001/k3cloud/"
	public static String POST_K3CloudURL = K3cloudConfig.PARENT_URL;

	// Cookie ֵ
	private static String CookieVal = null;

	private static Map map = new HashMap();
	static {
		map.put("Save",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc");
		map.put("BatchSave",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.BatchSave.common.kdsvc");
		map.put("View",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc");
		map.put("Submit",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Submit.common.kdsvc");
		map.put("Audit",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Audit.common.kdsvc");
		map.put("UnAudit",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.UnAudit.common.kdsvc");
		map.put("Delete",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Delete.common.kdsvc");
		map.put("StatusConvert",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc");
//		map.put("Cancel",
//				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc");
//		map.put("UnCancel",
//				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc");
		map.put("Cancel",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
		map.put("UnCancel",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
		map.put("Forbid",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
		map.put("Enable",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
		map.put("Query",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc");
		map.put("ManageUnAudit",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation.common.kdsvc");
		map.put("Allocate",
				"Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Allocate.common.kdsvc");
	}

	// HttpURLConnection
	private static HttpURLConnection initUrlConn(String url, JSONArray paras)
			throws Exception {
		URL postUrl = new URL(POST_K3CloudURL.concat(url));
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		if (CookieVal != null) {
			connection.setRequestProperty("Cookie", CookieVal);
		}
		if (!connection.getDoOutput()) {
			connection.setDoOutput(true);
		}
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/json");
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());

		UUID uuid = UUID.randomUUID();
		int hashCode = uuid.toString().hashCode();

		JSONObject jObj = new JSONObject();

		jObj.put("format", 1);
		jObj.put("useragent", "ApiClient");
		jObj.put("rid", hashCode);
		jObj.put("parameters", chinaToUnicode(paras.toString()));
		jObj.put("timestamp", new Date().toString());
		jObj.put("v", "1.0");

		out.writeBytes(jObj.toString());
		out.flush();
		out.close();

		return connection;
	}

	// Login
	public static boolean Login(String dbId, String user, String pwd, int lang)
			throws Exception {

		boolean bResult = false;

		String sUrl = "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";

		JSONArray jParas = new JSONArray();
		jParas.put(dbId);// ����Id
		jParas.put(user);// �û���
		jParas.put(pwd);// ����
		jParas.put(lang);// ����

		HttpURLConnection connection = initUrlConn(sUrl, jParas);
		// ��ȡCookie
		String key = null;
		for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("Set-Cookie")) {
				String tempCookieVal = connection.getHeaderField(i);
				if (tempCookieVal.startsWith("kdservice-sessionid")) {
					CookieVal = tempCookieVal;
					break;
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			String sResult = new String(line.getBytes(), "utf-8");
//			System.out.println(sResult);
			bResult = line.contains("\"LoginResultType\":1");
		}
		reader.close();

		connection.disconnect();

		return bResult;
	}

	// BatchSave
	public static JSONObject BatchSave(String formId, String content) throws Exception {
		return Invoke("BatchSave", formId, content);
	}

	// Save
	public static JSONObject Save(String formId, String content) throws Exception {
		return Invoke("Save", formId, content);
	}

	// View
	public static JSONObject View(String formId, String content) throws Exception {
		return Invoke("View", formId, content);
	}

	// Submit
	public static JSONObject Submit(String formId, String content) throws Exception {
		return Invoke("Submit", formId, content);
	}

	// Audit
	public static JSONObject Audit(String formId, String content) throws Exception {
		return Invoke("Audit", formId, content);
	}

	// UnAudit
	public static JSONObject UnAudit(String formId, String content) throws Exception {
		return Invoke("UnAudit", formId, content);
	}

	// Delete
	public static JSONObject Delete(String formId, String content) throws Exception {
		return Invoke("Delete", formId, content);
	}

	// StatusConvert
	public static JSONObject StatusConvert(String formId, String content)
			throws Exception {
		return Invoke("StatusConvert", formId, content);
	}

	// Cancel
	public static JSONObject Cancel(String formId,String opNumber, String content)
			throws Exception {
		return Invoke3("Cancel", formId,opNumber, content);
	}

	// UnCancel
	public static JSONObject UnCancel(String formId,String opNumber, String content)
			throws Exception {
		return Invoke3("UnCancel", formId,opNumber, content);
	}
	// Forbid
	public static JSONObject Forbid(String formId,String opNumber, String content)
			throws Exception {
		return Invoke3("Forbid", formId,opNumber, content);
	}
	// Enable
	public static JSONObject Enable(String formId,String opNumber, String content)
			throws Exception {
		return Invoke3("Enable", formId,opNumber, content);
	}

	// Query
	public static com.alibaba.fastjson.JSONArray Query(String content)
			throws Exception {
		return Invoke1("Query",content);
	}

	// ManageUnAudit
	public static JSONObject ManageUnAudit(String formId,String content)
			throws Exception {
		return Invoke("ManageUnAudit",formId,content);
	}
	// Allocate
	public static JSONObject Allocate(String formId,String content)
			throws Exception {
		return Invoke("Allocate",formId,content);
	}

	private static JSONObject Invoke(String deal, String formId, String content)
			throws Exception {
		String sUrl = map.get(deal).toString();
		JSONArray jParas = new JSONArray();
		jParas.put(formId);
		jParas.put(content);

		HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connectionInvoke.getInputStream()));

		String sResult = "{}";
		String line;
		while ((line = reader.readLine()) != null) {
			sResult = new String(line.getBytes(), "utf-8");
//			System.out.println(sResult);
		}
		reader.close();

		connectionInvoke.disconnect();
		//结果解析成JSON
		JSONObject jsonObject = JSON.parseObject(sResult);
		InvokeHelper invokeHelper = new InvokeHelper();
		invokeHelper.writeFile(deal,formId,null,content,sResult);
		return jsonObject;
	}

	private static JSONObject Invoke3(String deal, String formId,String opNumber, String content)
			throws Exception {

		String sUrl = map.get(deal).toString();
		JSONArray jParas = new JSONArray();
		jParas.put(formId);
		jParas.put(opNumber);
		jParas.put(content);

		HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connectionInvoke.getInputStream()));

		String sResult = "{}";
		String line;
		while ((line = reader.readLine()) != null) {
			sResult = new String(line.getBytes(), "utf-8");
//			System.out.println(sResult);
		}
		reader.close();

		connectionInvoke.disconnect();
		//结果解析成JSON
		JSONObject jsonObject = JSON.parseObject(sResult);
		InvokeHelper invokeHelper = new InvokeHelper();
		invokeHelper.writeFile(deal,formId,opNumber,content,sResult);
		return jsonObject;
	}

	private static com.alibaba.fastjson.JSONArray Invoke1(String deal,String content)
			throws Exception {
		String sUrl = map.get(deal).toString();
		JSONArray jParas = new JSONArray();
		jParas.put(content);

		HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connectionInvoke.getInputStream()));

		String sResult = "{}";
		String line;
		while ((line = reader.readLine()) != null) {
			sResult = new String(line.getBytes(), "utf-8");
//			System.out.println(sResult);
		}
		reader.close();
		connectionInvoke.disconnect();
		//todo 结果解析
//		Object parse = JSON.parse(sResult);
		com.alibaba.fastjson.JSONArray array = JSON.parseArray(sResult);
		InvokeHelper invokeHelper = new InvokeHelper();
		invokeHelper.writeFile(deal,null,null,content,sResult);
		return array;
	}

	/**
	 * ������ת��Unicode��
	 *
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// ���ַ�Χ \u4e00-\u9fa5 (����)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}


	private Boolean writeFile(String deal, String formId,String opNumber, String content,String result){
		Boolean b = false;
		File dir = new File(filePath);
		//一、检查放置文件的文件夹路径是否存在，不存在则创建
		if (!dir.exists()) {
			dir.mkdirs();// mkdirs创建多级目录
		}
		String fileName = deal+"_"+NumberUtils.getRandomOrderNo();
		File file = new File(filePath + "/" + fileName+".txt");
		OutputStream out = null;
		try {
			//二、检查目标文件是否存在，不存在则创建
			if (!file.exists()) {
				file.createNewFile();// 创建目标文件
			}
			out = new FileOutputStream(file);	//实例化OutpurStream
			//三、拼接写入的文本
			String fileContent = "deal:"+ deal + "\n" + "formId:" + formId + "\n" +
					"opNumber:"+opNumber + "\n" + "content:" +content +"\n"+"result:"+result;
			//四、向文件写入内容
			out.write(fileContent.getBytes());//写入
			out.flush();
			out.close();//关闭
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}
}
