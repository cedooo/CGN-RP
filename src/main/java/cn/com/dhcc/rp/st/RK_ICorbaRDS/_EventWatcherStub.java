package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/_EventWatcherStub.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/


// ?6?f??
public class _EventWatcherStub extends org.omg.CORBA.portable.ObjectImpl implements cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher
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
  public void OnEvent (int lType, int lTimeStamp, int lLevel, String strID, String strFullName, String strDesc, String strAdvice)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("OnEvent", false);
                $out.write_long (lType);
                $out.write_long (lTimeStamp);
                $out.write_long (lLevel);
                $out.write_string (strID);
                $out.write_string (strFullName);
                $out.write_string (strDesc);
                $out.write_string (strAdvice);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                OnEvent (lType, lTimeStamp, lLevel, strID, strFullName, strDesc, strAdvice        );
            } finally {
                _releaseReply ($in);
            }
  } // OnEvent

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:RK_ICorbaRDS/EventWatcher:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _EventWatcherStub
