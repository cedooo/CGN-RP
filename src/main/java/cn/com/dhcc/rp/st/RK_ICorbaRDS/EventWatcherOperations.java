package cn.com.dhcc.rp.st.RK_ICorbaRDS;


/**
* RK_ICorbaRDS/EventWatcherOperations.java .
* 锟斤拷 IDL-to-Java 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷植锟斤拷锟斤拷锟芥本 "3.2" 锟斤拷锟?
* 锟斤拷锟斤拷 RK_ICorbaRDS.idl
* 2014锟斤拷3锟斤拷12锟斤拷 锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷09时21锟斤拷11锟斤拷 CST
*/


// ?6?f??
public interface EventWatcherOperations 
{

  /*
  			lType: ??{?
  			lTimeStamp: ??????
  			iLevel: 锟斤拷+
  			strID: ???ID
  			strFullName: ???h?
  			strDesc: ????
  			strAdvice: ?锟斤拷?
  		*/
  void OnEvent (int lType, int lTimeStamp, int lLevel, String strID, String strFullName, String strDesc, String strAdvice);
} // interface EventWatcherOperations
