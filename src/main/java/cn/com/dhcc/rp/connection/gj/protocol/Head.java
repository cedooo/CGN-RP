package cn.com.dhcc.rp.connection.gj.protocol;

public class Head {
    static public int HEAD_ELE_LENGTH = 4;    //��ͷ����
    static public int HEAD_ELE_COUNT = 8;
    static public int BODY_LENGTH_POSITION = 4;    //���峤��λ��
    static public byte[] HEAD_TAG = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
    static public byte[] COMMAND_FIRST = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};    //��һ�η��Ͱ�
    static public byte[] COMMAND_EVENT = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};    //�¼���
    static public byte[] COMMAND_DATA = {(byte)1,(byte)0xff,(byte)0xff,(byte)0xff};    //���ݰ�
}
