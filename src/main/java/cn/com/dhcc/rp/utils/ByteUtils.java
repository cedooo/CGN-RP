package cn.com.dhcc.rp.utils;

public final class ByteUtils {
	/**
	 * 得到BIG数据值
	 * @param buf
	 * @return
	 */
	 static final public long getLong4(byte buf[]) {

        int firstByte = (0x000000FF & ((int) buf[0]));
        int secondByte = (0x000000FF & ((int) buf[1]));
        int thirdByte = (0x000000FF & ((int) buf[2]));
        int fourthByte = (0x000000FF & ((int) buf[3]));

        long unsignedLong = ((long) (firstByte | secondByte << 8 | thirdByte << 16 | fourthByte << 24)) & 0xFFFFFFFFL;

        return unsignedLong;
    }
    static final public short getShort(byte buf[]) {
    	
        int firstByte = (0x000000FF & ((int) buf[0]));
        int secondByte = (0x000000FF & ((int) buf[1]));

        short unsignedLong = (short)((firstByte | secondByte << 8 )& 0xFFFFFFFFL);

        return unsignedLong;
    }
}
