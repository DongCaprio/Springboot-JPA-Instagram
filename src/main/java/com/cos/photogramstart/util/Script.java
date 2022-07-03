package com.cos.photogramstart.util;

public class Script {
	
	public static String back(String msg) { //msg = 메세지
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
}
