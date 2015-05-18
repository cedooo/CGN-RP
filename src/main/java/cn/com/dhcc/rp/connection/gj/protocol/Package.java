package cn.com.dhcc.rp.connection.gj.protocol;

public class Package {
	static public final String ENCODE = "GB2312";    //�ӿ�ʹ�õı���
	static public final int TYPE_DATA = 0;    //��������
	static public final int TYPE_EVENT = 1;    //�¼�����
	static public final int TYPE_FIRST = 2;    //��һ�ν��յİ�

	static public final int MAX_SIZE_PACKAGE = 1024*1024;     //���ݰ���� ��С(byte)
	static public final String REGEX_KEY_VAL = "";    //��ֵ�� ������ʽ
	
	/**
	 * �жϰ�������
	 * @param commandBytes ��������
	 * @return TYPE_DATA �������ͣ� TYPE_EVENT �¼�
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
	 * �ж��Ƿ��ǰ�ͷ
	 * @param bytes
	 * @return �ǰ�ͷ�򷵻�true�����򷵻�false
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
