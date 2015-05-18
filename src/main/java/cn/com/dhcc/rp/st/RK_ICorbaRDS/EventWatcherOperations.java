package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/EventWatcherOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���?
* ���� RK_ICorbaRDS.idl
* 2014��3��12�� ������ ����09ʱ21��11�� CST
*/


// ?6?f??
public interface EventWatcherOperations 
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
  void OnEvent (int lType, int lTimeStamp, int lLevel, String strID, String strFullName, String strDesc, String strAdvice);
} // interface EventWatcherOperations
