package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/DataPublisherHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/

abstract public class DataPublisherHelper
{
  private static String  _id = "IDL:RK_ICorbaRDS/DataPublisher:1.0";

  public static void insert (org.omg.CORBA.Any a, cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper.id (), "DataPublisher");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_DataPublisherStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher)
      return (cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cn.com.dhcc.rp.st.RK_ICorbaRDS._DataPublisherStub stub = new cn.com.dhcc.rp.st.RK_ICorbaRDS._DataPublisherStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher)
      return (cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      cn.com.dhcc.rp.st.RK_ICorbaRDS._DataPublisherStub stub = new cn.com.dhcc.rp.st.RK_ICorbaRDS._DataPublisherStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
