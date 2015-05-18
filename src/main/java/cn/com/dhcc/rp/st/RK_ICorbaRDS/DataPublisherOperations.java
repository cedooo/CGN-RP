package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/DataPublisherOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/

public interface DataPublisherOperations 
{

  /*
  			strIDProp: IDh??? 1.2.Value
  			strValue: ??/??<
  		*/
  boolean GetData (String strIDProp, org.omg.CORBA.StringHolder strValue);
  boolean SetData (String strIDProp, String strValue);

  /*
  			��?�� "?f??" ???p
  		*/
  boolean RegisterEventWatcher (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher eventWatcher);
  boolean UnregisterEventWatcher (cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher eventWatcher);
} // interface DataPublisherOperations
