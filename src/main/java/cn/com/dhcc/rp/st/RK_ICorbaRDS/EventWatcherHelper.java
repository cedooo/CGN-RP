package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/EventWatcherHelper.java .
* 锟斤拷 IDL-to-Java 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷植锟斤拷锟斤拷锟芥本 "3.2" 锟斤拷锟?
* 锟斤拷锟斤拷 RK_ICorbaRDS.idl
* 2014锟斤拷3锟斤拷12锟斤拷 锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷09时21锟斤拷11锟斤拷 CST
*/


// ?6?f??
abstract public class EventWatcherHelper
{
  private static String  _id = "IDL:RK_ICorbaRDS/EventWatcher:1.0";

  public static void insert (org.omg.CORBA.Any a, cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherHelper.id (), "EventWatcher");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EventWatcherStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher)
      return (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cn.com.dhcc.rp.st.RK_ICorbaRDS._EventWatcherStub stub = new cn.com.dhcc.rp.st.RK_ICorbaRDS._EventWatcherStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher)
      return (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cn.com.dhcc.rp.st.RK_ICorbaRDS._EventWatcherStub stub = new cn.com.dhcc.rp.st.RK_ICorbaRDS._EventWatcherStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
