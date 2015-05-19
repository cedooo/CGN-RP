package cn.com.dhcc.rp.st.RK_ICorbaRDS;

/**
* RK_ICorbaRDS/EventWatcherHolder.java .
* 锟斤拷 IDL-to-Java 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷植锟斤拷锟斤拷锟芥本 "3.2" 锟斤拷锟?
* 锟斤拷锟斤拷 RK_ICorbaRDS.idl
* 2014锟斤拷3锟斤拷12锟斤拷 锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷09时21锟斤拷11锟斤拷 CST
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
