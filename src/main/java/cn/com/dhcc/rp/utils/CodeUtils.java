package cn.com.dhcc.rp.utils;

import java.io.UnsupportedEncodingException;

public class CodeUtils {
	public static String parse(String val, String sourceCode, String targetCode){
		String rtStr = null;
		try {
			rtStr = new String(val.getBytes(sourceCode), targetCode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			rtStr = null;
		}
		return rtStr;
	}
}
