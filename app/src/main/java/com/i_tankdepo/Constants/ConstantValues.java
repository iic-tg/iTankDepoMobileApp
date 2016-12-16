package com.i_tankdepo.Constants;

public class ConstantValues {
	public static final String FIRST_COLUMN="First";
	public static final String SECOND_COLUMN="Second";
	public static final String Link="http://192.168.1.14/iTankMobileService/";
	public static String baseURLLogin=Link+"Login.asmx/Login";
	public static String baseURLRoleBasedLogin=Link+"Login.asmx/RoleDetails";
	public static String baseURLChangePwd=Link+"ChangePassword.asmx/ChangePassword";
	public static String baseURLForgotPwd=Link+"ForgotPassword.asmx/ValidateEmailID";
	public static String baseURLGatePending=Link+"GateinMobile.asmx/PreAdviceList";
	public static String baseURLGateMySubmit=Link+"GateinMobile.asmx/Mysubmit";
	public static String baseURLCreateGateInCustomer=Link+"Dropdown.asmx/Customer";
	public static String baseURLCreateGateInPreviousCargo=Link+"Dropdown.asmx/PreviousCargo";
	public static String baseURLCreateGateInEquipMentType=Link+"Dropdown.asmx/EquipmentType";
	public static String baseURLGatePendingFIlter=Link+"GateinMobile.asmx/filter";
	public static String baseURLCreate_GateIn=Link+"GateinMobile.asmx/Update";
	public static String baseURLGateInSearchList=Link+"GateinMobile.asmx/SearchList";
	public static String baseURLGateInAttachment=Link+"GateinMobile.asmx/pvt_ValidateGateINAttachment";
	public static String baseURLGet_Type_Code=Link+"GateinMobile.asmx/pvt_GetEquipmentCode";
	public static String baseURLVerify_Equipment_No=Link+"GateinMobile.asmx/ValidateEquipment";
	public static String baseURLVerify_EquipmentNo_Lock=Link+"GateinMobile.asmx/pvt_GIlockData";
	public static String baseURLVerify_Equipment_InFo=Link+"EquipmentInfoMobile.asmx/EIList";
	public static String baseURLVerify_Rental_Entry=Link+"GateinMobile.asmx/pvt_validateRentalEntry";
	public static String baseURLEquipmentInfoDropdownType=Link+"Dropdown.asmx/TestType";
	public static String baseURLHeatingList=Link+"HeatingMobile.asmx/HeatingList";
	public static String baseURLCalcHeatingPeriod=Link+"HeatingMobile.asmx/CalculateHeatingPeriod";
	public static String baseURLCalcTotalRate=Link+"HeatingMobile.asmx/CalculateTotalRate";
	public static String baseURLHeatingFilter=Link+"HeatingMobile.asmx/filter";
	public static String baseURLHeatingSearchList=Link+"HeatingMobile.asmx/SearchList";
}
