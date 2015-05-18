package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/EventWatcherPOA.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��10�� CST
*/


// ?6?f??
public abstract class EventWatcherPOA extends org.omg.PortableServer.Servant
 implements cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("OnEvent", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  /*
  			lType: ??{?
  			lTimeStamp: ??????
  			iLevel: ��+
  			strID: ???ID
  			strFullName: ???h?
  			strDesc: ????
  			strAdvice: ?��?
  		*/
       case 0:  // RK_ICorbaRDS/EventWatcher/OnEvent
       {
         int lType = in.read_long ();
         int lTimeStamp = in.read_long ();
         int lLevel = in.read_long ();
         String strID = in.read_string ();
         String strFullName = in.read_string ();
         String strDesc = in.read_string ();
         String strAdvice = in.read_string ();
         this.OnEvent (lType, lTimeStamp, lLevel, strID, strFullName, strDesc, strAdvice);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:RK_ICorbaRDS/EventWatcher:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public EventWatcher _this() 
  {
    return EventWatcherHelper.narrow(
    super._this_object());
  }

  public EventWatcher _this(org.omg.CORBA.ORB orb) 
  {
    return EventWatcherHelper.narrow(
    super._this_object(orb));
  }


} // class EventWatcherPOA
