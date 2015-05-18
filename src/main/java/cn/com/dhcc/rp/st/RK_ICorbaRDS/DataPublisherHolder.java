package cn.com.dhcc.rp.st.RK_ICorbaRDS;

/**
* RK_ICorbaRDS/DataPublisherHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/

public final class DataPublisherHolder implements org.omg.CORBA.portable.Streamable
{
  public cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher value = null;

  public DataPublisherHolder ()
  {
  }

  public DataPublisherHolder (cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper.type ();
  }

}
