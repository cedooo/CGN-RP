package cn.com.dhcc.rp.persistence.po;

import java.text.DecimalFormat;

public class TxPowerBoxCommTotalPerf  extends POObj{
	private String id = null;
	private String partID = null;
	private String collectTime = null;
	private String frequency = null;
	private String currency = null;
	private String totalUsedPower = null;
	private String totalViewPower = null;
	private String totalUnUsedPower = null;
	private String powerUsed = null;
	private String powerUsedInUse = null;
	private String powerUsedUnUse = null;
	private String powerForwardInUse = null;
	private String powerForwardUnUse = null;
	private String powerNegativeInUse = null;
	private String powerNegativeUnUse = null;
	private String totalLoadRate = null;
	private String batteryPower = null;
	private String batteryPowerAh = null;
	private String batteryVoltage = null;
	private String batteryCurrency = null;
	private String batteryTime = null;
	private String tempInside = null;
	private String totalPowerRate = null;
	private String voltageOutU12 = null;
	private String voltageOutU23 = null;
	private String voltageOutU31 = null;
	private String voltageInU12 = null;
	private String voltageInU23 = null;
	private String voltageInU31 = null;
	private String voltageIn = null;
	private String voltageOut = null;
	private String voltageFloat = null;
	private String currencyOut = null;
	private String frequencyOut = null;
	private String powerOut = null;
	private String frequencySide = null;
	private String voltageChange = null;
	private String currencyChange = null;
	
	private String powerUsedViewUse = null;
	private String dirVoltageUnBlanace = null;
	private String lineVoltageUnBlanace = null;
	private String currencyUnBlanace = null;
	
	private String companyCode = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartID() {
		return partID;
	}
	public void setPartID(String partID) {
		this.partID = partID;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTotalUsedPower() {
		return totalUsedPower;
	}
	public void setTotalUsedPower(String totalUsedPower) {
		this.totalUsedPower = totalUsedPower;
	}
	public String getTotalViewPower() {
		return totalViewPower;
	}
	public void setTotalViewPower(String totalViewPower) {
		this.totalViewPower = totalViewPower;
	}
	public String getTotalUnUsedPower() {
		return totalUnUsedPower;
	}
	public void setTotalUnUsedPower(String totalUnUsedPower) {
		this.totalUnUsedPower = totalUnUsedPower;
	}
	public String getPowerUsed() {
		return powerUsed;
	}
	public void setPowerUsed(String powerUsed) {
		this.powerUsed = powerUsed;
	}
	public String getPowerUsedInUse() {
		return powerUsedInUse;
	}
	public void setPowerUsedInUse(String powerUsedInUse) {
		this.powerUsedInUse = powerUsedInUse;
	}
	public String getPowerUsedUnUse() {
		return powerUsedUnUse;
	}
	public void setPowerUsedUnUse(String powerUsedUnUse) {
		this.powerUsedUnUse = powerUsedUnUse;
	}
	public String getPowerForwardInUse() {
		return powerForwardInUse;
	}
	public void setPowerForwardInUse(String powerForwardInUse) {
		this.powerForwardInUse = powerForwardInUse;
	}
	public String getPowerForwardUnUse() {
		return powerForwardUnUse;
	}
	public void setPowerForwardUnUse(String powerForwardUnUse) {
		this.powerForwardUnUse = powerForwardUnUse;
	}
	public String getPowerNegativeInUse() {
		return powerNegativeInUse;
	}
	public void setPowerNegativeInUse(String powerNegativeInUse) {
		this.powerNegativeInUse = powerNegativeInUse;
	}
	public String getPowerNegativeUnUse() {
		return powerNegativeUnUse;
	}
	public void setPowerNegativeUnUse(String powerNegativeUnUse) {
		this.powerNegativeUnUse = powerNegativeUnUse;
	}
	public String getTotalLoadRate() {
		return totalLoadRate;
	}
	public void setTotalLoadRate(String totalLoadRate) {
		this.totalLoadRate = totalLoadRate;
	}
	public String getBatteryPower() {
		return batteryPower;
	}
	public void setBatteryPower(String batteryPower) {
		this.batteryPower = batteryPower;
	}
	public String getBatteryPowerAh() {
		return batteryPowerAh;
	}
	public void setBatteryPowerAh(String batteryPowerAh) {
		this.batteryPowerAh = batteryPowerAh;
	}
	public String getBatteryVoltage() {
		return batteryVoltage;
	}
	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}
	public String getBatteryCurrency() {
		return batteryCurrency;
	}
	public void setBatteryCurrency(String batteryCurrency) {
		this.batteryCurrency = batteryCurrency;
	}
	public String getBatteryTime() {
		return batteryTime;
	}
	public void setBatteryTime(String batteryTime) {
		this.batteryTime = batteryTime;
	}
	public String getTempInside() {
		return tempInside;
	}
	public void setTempInside(String tempInside) {
		this.tempInside = tempInside;
	}
	public String getTotalPowerRate() {
		return totalPowerRate;
	}
	public void setTotalPowerRate(String totalPowerRate) {
		this.totalPowerRate = totalPowerRate;
	}
	public String getVoltageOutU12() {
		return voltageOutU12;
	}
	public void setVoltageOutU12(String voltageOutU12) {
		this.voltageOutU12 = voltageOutU12;
	}
	public String getVoltageOutU23() {
		return voltageOutU23;
	}
	public void setVoltageOutU23(String voltageOutU23) {
		this.voltageOutU23 = voltageOutU23;
	}
	public String getVoltageOutU31() {
		return voltageOutU31;
	}
	public void setVoltageOutU31(String voltageOutU31) {
		this.voltageOutU31 = voltageOutU31;
	}
	public String getVoltageInU12() {
		return voltageInU12;
	}
	public void setVoltageInU12(String voltageInU12) {
		this.voltageInU12 = voltageInU12;
	}
	public String getVoltageInU23() {
		return voltageInU23;
	}
	public void setVoltageInU23(String voltageInU23) {
		this.voltageInU23 = voltageInU23;
	}
	public String getVoltageInU31() {
		return voltageInU31;
	}
	public void setVoltageInU31(String voltageInU31) {
		this.voltageInU31 = voltageInU31;
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
	public String getVoltageFloat() {
		return voltageFloat;
	}
	public void setVoltageFloat(String voltageFloat) {
		this.voltageFloat = voltageFloat;
	}
	public String getCurrencyOut() {
		return currencyOut;
	}
	public void setCurrencyOut(String currencyOut) {
		this.currencyOut = currencyOut;
	}
	public String getFrequencyOut() {
		return frequencyOut;
	}
	public void setFrequencyOut(String frequencyOut) {
		this.frequencyOut = frequencyOut;
	}
	public String getPowerOut() {
		return powerOut;
	}
	public void setPowerOut(String powerOut) {
		this.powerOut = powerOut;
	}
	public String getFrequencySide() {
		return frequencySide;
	}
	public void setFrequencySide(String frequencySide) {
		this.frequencySide = frequencySide;
	}
	public String getVoltageChange() {
		return voltageChange;
	}
	public void setVoltageChange(String voltageChange) {
		this.voltageChange = voltageChange;
	}
	public String getCurrencyChange() {
		return currencyChange;
	}
	public void setCurrencyChange(String currencyChange) {
		this.currencyChange = currencyChange;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPowerUsedViewUse() {
		return powerUsedViewUse;
	}
	public void setPowerUsedViewUse(String powerUsedViewUse) {
		this.powerUsedViewUse = powerUsedViewUse;
	}
	public String getDirVoltageUnBlanace() {
		return dirVoltageUnBlanace;
	}
	public void setDirVoltageUnBlanace(String dirVoltageUnBlanace) {
		this.dirVoltageUnBlanace = dirVoltageUnBlanace;
	}
	public String getLineVoltageUnBlanace() {
		return lineVoltageUnBlanace;
	}
	public void setLineVoltageUnBlanace(String lineVoltageUnBlanace) {
		this.lineVoltageUnBlanace = lineVoltageUnBlanace;
	}
	public String getCurrencyUnBlanace() {
		return currencyUnBlanace;
	}
	public void setCurrencyUnBlanace(String currencyUnBlanace) {
		this.currencyUnBlanace = currencyUnBlanace;
	}
	@Override
	public String toString() {
		return "TxPowerBoxCommTotalPerf [id=" + id + ", partID=" + partID
				+ ", collectTime=" + collectTime + ", frequency=" + frequency
				+ ", currency=" + currency + ", totalUsedPower="
				+ totalUsedPower + ", totalViewPower=" + totalViewPower
				+ ", totalUnUsedPower=" + totalUnUsedPower + ", powerUsed="
				+ powerUsed + ", powerUsedInUse=" + powerUsedInUse
				+ ", powerUsedUnUse=" + powerUsedUnUse + ", powerForwardInUse="
				+ powerForwardInUse + ", powerForwardUnUse="
				+ powerForwardUnUse + ", powerNegativeInUse="
				+ powerNegativeInUse + ", powerNegativeUnUse="
				+ powerNegativeUnUse + ", totalLoadRate=" + totalLoadRate
				+ ", batteryPower=" + batteryPower + ", batteryPowerAh="
				+ batteryPowerAh + ", batteryVoltage=" + batteryVoltage
				+ ", batteryCurrency=" + batteryCurrency + ", batteryTime="
				+ batteryTime + ", tempInside=" + tempInside
				+ ", totalPowerRate=" + totalPowerRate + ", voltageOutU12="
				+ voltageOutU12 + ", voltageOutU23=" + voltageOutU23
				+ ", voltageOutU31=" + voltageOutU31 + ", voltageInU12="
				+ voltageInU12 + ", voltageInU23=" + voltageInU23
				+ ", voltageInU31=" + voltageInU31 + ", voltageIn=" + voltageIn
				+ ", voltageOut=" + voltageOut + ", voltageFloat="
				+ voltageFloat + ", currencyOut=" + currencyOut
				+ ", frequencyOut=" + frequencyOut + ", powerOut=" + powerOut
				+ ", frequencySide=" + frequencySide + ", voltageChange="
				+ voltageChange + ", currencyChange=" + currencyChange
				+ ", companyCode=" + companyCode + "]";
	}
	/**
	 * 
	 * 以下将科学计数法的值转换为decimal(18,3)
	 */
	public String getValPowerForwardInUse(){
		return parseDecimal(this.powerForwardInUse);
	}
	public String getValPowerForwardUnUse(){
		return parseDecimal(this.powerForwardUnUse);
	}
	public String getValPowerNegativeInUse(){
		return parseDecimal(this.powerNegativeInUse);
	}
	public String getValPowerNegativeUnUse(){
		return parseDecimal(this.powerNegativeUnUse);
	}
	public String getValPowerUsedInUse(){
		return parseDecimal(this.powerUsedInUse);
	}
	public String getValPowerUsedUnUse(){
		return parseDecimal(this.powerUsedUnUse);
	}
	public String getValPowerUsedViewUse(){
		return parseDecimal(this.powerUsedViewUse);
	}
	
	static private DecimalFormat decimalFormat = new DecimalFormat("#.000");
	static public String parseDecimal(String valStr){
		String rtStr = null;
		if(valStr != null){
			try {
				Double doub = Double.parseDouble(valStr);
				rtStr = decimalFormat.format(doub);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return rtStr;
	}
}
