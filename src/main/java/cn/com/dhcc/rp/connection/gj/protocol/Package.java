package cn.com.dhcc.rp.connection.gj.protocol;

public class Package {
	static public final String ENCODE = "GB2312";    //接口使用的编码
	static public final int TYPE_DATA = 0;    //数据类型
	static public final int TYPE_EVENT = 1;    //事件类型
	static public final int TYPE_FIRST = 2;    //第一次接收的包

	static public final int MAX_SIZE_PACKAGE = 1024*1024;     //数据包最大 大小(byte)
	static public final String REGEX_KEY_VAL = "";    //键值对 正则表达式
	
	/**
	 * 判断包的类型
	 * @param commandBytes 命令类型
	 * @return TYPE_DATA 数据类型， TYPE_EVENT 事件
	 */
	static public int dataType(byte[] commandBytes){
		boolean data = true;
		for(int k=0; k<Head.HEAD_ELE_LENGTH; k++){
            if(commandBytes[k] != Head.COMMAND_DATA[k]){
            	data =  false;
                break;
            }
        }
		if(data){
			return TYPE_DATA;
		}
		boolean event = true;
		for(int k=0; k<Head.HEAD_ELE_LENGTH; k++){
            if(commandBytes[k] != Head.COMMAND_EVENT[k]){
            	event =  false;
                break;
            }
        }
		if(event){
			return TYPE_EVENT;
		}
		boolean first = true;
		for(int k=0; k<Head.HEAD_ELE_LENGTH; k++){
            if(commandBytes[k] != Head.COMMAND_EVENT[k]){
            	first =  false;
                break;
            }
        }
		if(first){
			return TYPE_FIRST;
		}
		return -1;
	}
	/**
	 * 判断是否是包头
	 * @param bytes
	 * @return 是包头则返回true，否则返回false
	 */
	static public boolean isHeadTag(byte[] bytes){
		boolean isHeadTag = true;
		for(int k=0; k<Head.HEAD_ELE_LENGTH; k++){
            if(bytes[k] != Head.HEAD_TAG[k]){
            	isHeadTag =  false;
                break;
            }
        }
		return isHeadTag;
	}

	public static void main(String[] args) {
		System.out.println();
	}
}
