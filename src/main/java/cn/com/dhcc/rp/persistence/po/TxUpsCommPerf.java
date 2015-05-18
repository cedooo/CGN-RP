package cn.com.dhcc.rp.persistence.po;

public class TxUpsCommPerf extends POObj{

	private String collectTime = null;    //�ɼ�ʱ��
	private String partID = Constants.DEFAULT_PART_ID;    //ģ��ID
	private String voltageIn = null;    //�����ѹ
	private String voltageOut = null;    //�����ѹ
	private String currentIn = null;    //�������
	private String currentOut = null;    //�������
	private String powerOut = null;    //�������
	private String powerViewOut = null;    //���ڹ���
	private String powerRateOut = null;    //�����������
	private String frequencyIn = null;    //����Ƶ��
	private String frequencyOut = null;    //���Ƶ��
	private String inverterFrequency = null;    //���Ƶ��
	private String sideFrequency = null;    //��·Ƶ��
	private String batteryBakTime = null;    //��ر���ʱ��
	private String batteryCurrency = null;    //��ص���
	private String batteryVoltage = null;    //��ص�ѹ
	private String batteryFloatVoltage = null;    //��ظ����ѹ
	private String batteryChargeRate = null;    //��س��̶�
	private String batteryVolume = null;    //�������
	private String batteryVolumeAh = null;    //�������Ah
	private String batteryCon = null;    //��ص���
	private String upsLoad = null;    //UPS���ذٷֱ�
	private String upsTem = null;    //ups�¶�    
	private String upsCunTem = null;    //ups�����¶�
	private String negativeBatteryCurrency = null;    //����ص���
	private String negativeBatteryVoltage = null;    //����ص�ѹ
	private String voltageABOut = null;    //�����ѹAB
	private String voltageBCOut = null;    //�����ѹBC
	private String voltageCAOut = null;    //�����ѹCA
	private String id = null;    //    //�豸ID
	private String powerUsedOut = null;    //����й�����
	private String powerUnUsedOut = null;    //����޹�����
	private String otherVoltage = null;    //��·��ѹ
	private String otherCurrency = null;    //��·����
	
	private String companyCode = null;    //���ұ�־������ɾ����ʷʵʱ����
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getPartID() {
		return partID;
	}
	public void setPartID(String partID) {
		this.partID = partID;
	}
	public String getVoltageIn() {
		return voltageIn;
	}
	public void setVoltageIn(String voltageIn) {
		this.voltageIn = voltageIn;
	}
	public String getVoltageOut() {
		return voltageOut;
	}
	public void setVoltageOut(String voltageOut) {
		this.voltageOut = voltageOut;
	}
	public String getCurrentIn() {
		return currentIn;
	}
	public void setCurrentIn(String currentIn) {
		this.currentIn = currentIn;
	}
	public String getCurrentOut() {
		return currentOut;
	}
	public void setCurrentOut(String currentOut) {
		this.currentOut = currentOut;
	}
	public String getPowerOut() {
		return powerOut;
	}
	public void setPowerOut(String powerOut) {
		this.powerOut = powerOut;
	}
	public String getPowerViewOut() {
		return powerViewOut;
	}
	public void setPowerViewOut(String powerViewOut) {
		this.powerViewOut = powerViewOut;
	}
	public String getPowerRateOut() {
		return powerRateOut;
	}
	public void setPowerRateOut(String powerRateOut) {
		this.powerRateOut = powerRateOut;
	}
	public String getFrequencyIn() {
		return frequencyIn;
	}
	public void setFrequencyIn(String frequencyIn) {
		this.frequencyIn = frequencyIn;
	}
	public String getFrequencyOut() {
		return frequencyOut;
	}
	public void setFrequencyOut(String frequencyOut) {
		this.frequencyOut = frequencyOut;
	}
	public String getInverterFrequency() {
		return inverterFrequency;
	}
	public void setInverterFrequency(String inverterFrequency) {
		this.inverterFrequency = inverterFrequency;
	}
	public String getSideFrequency() {
		return sideFrequency;
	}
	public void setSideFrequency(String sideFrequency) {
		this.sideFrequency = sideFrequency;
	}
	public String getBatteryBakTime() {
		return batteryBakTime;
	}
	public void setBatteryBakTime(String batteryBakTime) {
		this.batteryBakTime = batteryBakTime;
	}
	public String getBatteryCurrency() {
		return batteryCurrency;
	}
	public void setBatteryCurrency(String batteryCurrency) {
		this.batteryCurrency = batteryCurrency;
	}
	public String getBatteryVoltage() {
		return batteryVoltage;
	}
	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}
	public String getBatteryFloatVoltage() {
		return batteryFloatVoltage;
	}
	public void setBatteryFloatVoltage(String batteryFloatVoltage) {
		this.batteryFloatVoltage = batteryFloatVoltage;
	}
	public String getBatteryChargeRate() {
		return batteryChargeRate;
	}
	public void setBatteryChargeRate(String batteryChargeRate) {
		this.batteryChargeRate = batteryChargeRate;
	}
	public String getBatteryVolume() {
		return batteryVolume;
	}
	public void setBatteryVolume(String batteryVolume) {
		this.batteryVolume = batteryVolume;
	}
	public String getBatteryVolumeAh() {
		return batteryVolumeAh;
	}
	public void setBatteryVolumeAh(String batteryVolumeAh) {
		this.batteryVolumeAh = batteryVolumeAh;
	}
	public String getBatteryCon() {
		return batteryCon;
	}
	public void setBatteryCon(String batteryCon) {
		this.batteryCon = batteryCon;
	}
	public String getUpsLoad() {
		return upsLoad;
	}
	public void setUpsLoad(String upsLoad) {
		this.upsLoad = upsLoad;
	}
	public String getUpsTem() {
		return upsTem;
	}
	public void setUpsTem(String upsTem) {
		this.upsTem = upsTem;
	}
	public String getUpsCunTem() {
		return upsCunTem;
	}
	public void setUpsCunTem(String upsCunTem) {
		this.upsCunTem = upsCunTem;
	}
	public String getNegativeBatteryCurrency() {
		return negativeBatteryCurrency;
	}
	public void setNegativeBatteryCurrency(String negativeBatteryCurrency) {
		this.negativeBatteryCurrency = negativeBatteryCurrency;
	}
	public String getNegativeBatteryVoltage() {
		return negativeBatteryVoltage;
	}
	public void setNegativeBatteryVoltage(String negativeBatteryVoltage) {
		this.negativeBatteryVoltage = negativeBatteryVoltage;
	}
	public String getVoltageABOut() {
		return voltageABOut;
	}
	public void setVoltageABOut(String voltageABOut) {
		this.voltageABOut = voltageABOut;
	}
	public String getVoltageBCOut() {
		return voltageBCOut;
	}
	public void setVoltageBCOut(String voltageBCOut) {
		this.voltageBCOut = voltageBCOut;
	}
	public String getVoltageCAOut() {
		return voltageCAOut;
	}
	public void setVoltageCAOut(String voltageCAOut) {
		this.voltageCAOut = voltageCAOut;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPowerUsedOut() {
		return powerUsedOut;
	}
	public void setPowerUsedOut(String powerUsedOut) {
		this.powerUsedOut = powerUsedOut;
	}
	public String getPowerUnUsedOut() {
		return powerUnUsedOut;
	}
	public void setPowerUnUsedOut(String powerUnUsedOut) {
		this.powerUnUsedOut = powerUnUsedOut;
	}
	public String getOtherVoltage() {
		return otherVoltage;
	}
	public void setOtherVoltage(String otherVoltage) {
		this.otherVoltage = otherVoltage;
	}
	public String getOtherCurrency() {
		return otherCurrency;
	}
	public void setOtherCurrency(String otherCurrency) {
		this.otherCurrency = otherCurrency;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Override
	public String toString() {
		return "TxUpsCommPerf [collectTime=" + collectTime + ", partID="
				+ partID + ", voltageIn=" + voltageIn + ", voltageOut="
				+ voltageOut + ", currentIn=" + currentIn + ", currentOut="
				+ currentOut + ", powerOut=" + powerOut + ", powerViewOut="
				+ powerViewOut + ", powerRateOut=" + powerRateOut
				+ ", frequencyIn=" + frequencyIn + ", frequencyOut="
				+ frequencyOut + ", inverterFrequency=" + inverterFrequency
				+ ", sideFrequency=" + sideFrequency + ", batteryBakTime="
				+ batteryBakTime + ", batteryCurrency=" + batteryCurrency
				+ ", batteryVoltage=" + batteryVoltage
				+ ", batteryFloatVoltage=" + batteryFloatVoltage
				+ ", batteryChargeRate=" + batteryChargeRate
				+ ", batteryVolume=" + batteryVolume + ", batteryVolumeAh="
				+ batteryVolumeAh + ", batteryCon=" + batteryCon + ", upsLoad="
				+ upsLoad + ", upsTem=" + upsTem + ", upsCunTem=" + upsCunTem
				+ ", negativeBatteryCurrency=" + negativeBatteryCurrency
				+ ", negativeBatteryVoltage=" + negativeBatteryVoltage
				+ ", voltageABOut=" + voltageABOut + ", voltageBCOut="
				+ voltageBCOut + ", voltageCAOut=" + voltageCAOut + ", id="
				+ id + ", powerUsedOut=" + powerUsedOut + ", powerUnUsedOut="
				+ powerUnUsedOut + ", otherVoltage=" + otherVoltage
				+ ", otherCurrency=" + otherCurrency + "]";
	}
	
}
