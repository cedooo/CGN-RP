package cn.com.dhcc.rp.connection.gj.protocol;

public class Head {
    static public int HEAD_ELE_LENGTH = 4;    //包头长度
    static public int HEAD_ELE_COUNT = 8;
    static public int BODY_LENGTH_POSITION = 4;    //包体长度位置
    static public byte[] HEAD_TAG = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
    static public byte[] COMMAND_FIRST = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};    //第一次发送包
    static public byte[] COMMAND_EVENT = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};    //事件包
    static public byte[] COMMAND_DATA = {(byte)1,(byte)0xff,(byte)0xff,(byte)0xff};    //数据包
}
