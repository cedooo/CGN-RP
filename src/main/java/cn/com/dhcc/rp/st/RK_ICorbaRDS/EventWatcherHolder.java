package cn.com.dhcc.rp.st.RK_ICorbaRDS;

/**
* RK_ICorbaRDS/EventWatcherHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/


// ?6?f??
public final class EventWatcherHolder implements org.omg.CORBA.portable.Streamable
{
  public cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher value = null;

  public EventWatcherHolder ()
  {
  }

  public EventWatcherHolder (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherHelper.type ();
  }

}
