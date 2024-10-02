package com.plants.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Agent_Main" , uniqueConstraints = {@UniqueConstraint(columnNames = {"mobileNumber","emailId","aadhaarNumber","accNumber","AccMobNumber"})})
public class AgentMain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int AgentIDPk;
	
	private String firstName;
	private String lastName;
	private String selfiImage;
	@Column(unique = true)
	private String emailId;
	@Column(unique = true)
	private String mobileNumber;
	 @Column(name = "agent_verified", nullable = true)
	boolean AgentVerified;
	 @Column(name = "is_active_agent", nullable = true)
	boolean isActiveAgent;
	private String state;
	private String city;
	private String address;
	private String pincode;
	private double latitude;
	private double longitude;
	
	private String aadharImg;
	@Column(unique = true)
	private String aadhaarNumber;
	
	private String accHolderName;
	@Column(unique = true)
	private String accNumber;
	private String bankName;
	private String IFSCCode;
	@Column(unique = true)
	private String AccMobNumber;
	private String fcmTokenAgent;
	private String BankAccPassBookImage;
	public AgentMain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentMain(int agentIDPk, String firstName, String lastName,String selfiImage, String emailId, String mobileNumber, boolean AgentVerified, boolean isActiveAgent,
			String state, String city, String address, String pincode, double latitude, double longitude,
			String aadharImg, String aadhaarNumber, String accHolderName, String accNumber, String bankName,
			String iFSCCode, String accMobNumber ,String BankAccPassBookImage , String fcmTokenAgent) {
		super();
		AgentIDPk = agentIDPk;
		this.firstName = firstName;
		this.lastName = lastName;
		this.selfiImage = selfiImage;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.AgentVerified = AgentVerified;
		this.isActiveAgent = isActiveAgent;
		this.state = state;
		this.city = city;
		this.address = address;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.aadharImg = aadharImg;
		this.aadhaarNumber = aadhaarNumber;
		this.accHolderName = accHolderName;
		this.accNumber = accNumber;
		this.bankName = bankName;
		this.IFSCCode = iFSCCode;
		this.AccMobNumber = accMobNumber;
		this.BankAccPassBookImage= BankAccPassBookImage;
		this.fcmTokenAgent= fcmTokenAgent;
	}
	public int getAgentIDPk() {
		return AgentIDPk;
	}
	public void setAgentIDPk(int agentIDPk) {
		AgentIDPk = agentIDPk;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSelfiImage() {
		return selfiImage;
	}
	public void setSelfiImage(String selfiImage) {
		this.selfiImage = selfiImage;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public boolean getAgentVerified() {
		return AgentVerified;
	}
	public void setAgentVerified(boolean agentVerified) {
		AgentVerified = agentVerified;
	}
	public boolean isActiveAgent() {
		return isActiveAgent;
	}
	public void setActiveAgent(boolean isActiveAgent) {
		this.isActiveAgent = isActiveAgent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAadharImg() {
		return aadharImg;
	}
	public void setAadharImg(String aadharImg) {
		this.aadharImg = aadharImg;
	}
	public String getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
	public String getAccHolderName() {
		return accHolderName;
	}
	public void setAccHolderName(String accHolderName) {
		this.accHolderName = accHolderName;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIFSCCode() {
		return IFSCCode;
	}
	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}
	public String getAccMobNumber() {
		return AccMobNumber;
	}
	public void setAccMobNumber(String accMobNumber) {
		AccMobNumber = accMobNumber;
	}
	public String getBankAccPassBookImage() {
		return BankAccPassBookImage;
	}
	public void setBankAccPassBookImage(String bankAccPassBookImage) {
		BankAccPassBookImage = bankAccPassBookImage;
	}
	
	public String getFcmTokenAgent() {
		return fcmTokenAgent;
	}
	public void setFcmTokenAgent(String fcmTokenAgent) {
		this.fcmTokenAgent = fcmTokenAgent;
	}
	@Override
	public String toString() {
		return "AgentMain [AgentIDPk=" + AgentIDPk + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", selfiImage=" + selfiImage + ", emailId=" + emailId + ", mobileNumber=" + mobileNumber
				+ ", AgentVerified=" + AgentVerified + ", isActiveAgent=" + isActiveAgent + ", state=" + state
				+ ", city=" + city + ", address=" + address + ", pincode=" + pincode + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", aadharImg=" + aadharImg + ", aadhaarNumber=" + aadhaarNumber
				+ ", accHolderName=" + accHolderName + ", accNumber=" + accNumber + ", bankName=" + bankName
				+ ", IFSCCode=" + IFSCCode + ", AccMobNumber=" + AccMobNumber + ", fcmTokenAgent=" + fcmTokenAgent
				+ ", BankAccPassBookImage=" + BankAccPassBookImage + "]";
	}
	
}