package com.i_tankdepo.Constants;

public class ConstantValues {
	public static final String FIRST_COLUMN="First";
	public static final String SECOND_COLUMN="Second";
	public static final String Link="http://192.168.1.39/iTankMobileService/";
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
	public static String baseURLHeatingUpdate=Link+"HeatingMobile.asmx/Update";
	public static String baseURLHeatingFilter=Link+"HeatingMobile.asmx/filter";
	public static String baseURLHeatingSearchList=Link+"HeatingMobile.asmx/SearchList";
	public static String baseURLCleaningPreadvice=Link+"Cleaning.asmx/PendingList";
	public static String baseURLCleaningMySubmit=Link+"Cleaning.asmx/MySubmit";
	public static String baseURLCleaningFilter=Link+"Cleaning.asmx/filter";
	public static String baseURLCleaninggSearchList=Link+"Cleaning.asmx/SearchList";
	public static String baseURLCreate_GateIn_DefaultValues=Link+"GateinMobile.asmx/OnCreateNew";
	public static String baseURLInspectionPreadvice=Link+"InspectionMobile.asmx/IPendingList";
	public static String baseURLInspectionMySubmit=Link+"InspectionMobile.asmx/MySubmit";
	public static String baseURLInspectionFilter=Link+"InspectionMobile.asmx/filter";
	public static String baseURLInspectionSearchList=Link+"InspectionMobile.asmx/SearchList";
	public static String baseURLRepairEstimateList=Link+"RepairEstimateMobile.asmx/RepairList";
	public static String baseURLRepairEstimateFilter=Link+"RepairEstimateMobile.asmx/filter";
	public static String baseURLRepairEstimateSearchList=Link+"RepairEstimateMobile.asmx/SearchList";
	public static String baseURLRepairApprovalList=Link+"RepairEstimateMobile.asmx/RepairList";
	public static String baseURLRepairApprovalFilter=Link+"RepairEstimateMobile.asmx/filter";
	public static String baseURLRepairApprovalSearchList=Link+"RepairEstimateMobile.asmx/SearchList";
	public static String baseURLSurveyCompletionList=Link+"RepairEstimateMobile.asmx/RepairList";
	public static String baseURLSurveyCompletionFilter=Link+"RepairEstimateMobile.asmx/filter";
	public static String baseURLSurveyCompletionSearchList=Link+"RepairEstimateMobile.asmx/SearchList";
	public static String baseURLRepairCompletionList=Link+"RepairCompletionMobile.asmx/RepairList";
	public static String baseURLRepairCompletionFilter=Link+"RepairCompletionMobile.asmx/filter";
	public static String baseURLRepairCompletionSearchList=Link+"RepairCompletionMobile.asmx/SearchList";
	public static String baseURLEquipmentHistory=Link+"EquipmentHistoryMobile.asmx/EquipmentHistoryList";
	public static String baseURLEquipmentValidation=Link+"EquipmentHistoryMobile.asmx/validateEquipmentDelete";
	public static String baseURLEquipmentDeleteActivity=Link+"EquipmentHistoryMobile.asmx/DeleteActivity";
	public static String baseURLLeakTestList=Link+"LeakTest.asmx/LeakTestList";
	public static String baseURLLeakTestFilter=Link+"LeakTest.asmx/filter";
	public static String baseURLLeakTestSearchList=Link+"LeakTest.asmx/SearchList";
	public static String baseURLLeakTestRevisionNo=Link+"LeakTest.asmx/RevisionList";
	public static String baseURLUpdateLeakTest=Link+"LeakTest.asmx/InsertLeakTest";
	public static String baseURLEquipmentNO=Link+"LeakTest.asmx/EquipmentList";
	public static String baseURLEquipmentNOValidation=Link+"LeakTest.asmx/EquipmentValidation";
	public static String baseURLCurrentStatusList=Link+"ChangeOfStatusMobile.asmx/currrentStatusList";
	public static String baseURLToStatusList=Link+"ChangeOfStatusMobile.asmx/ToStatusList";
	public static String baseURLSearch=Link+"ChangeOfStatusMobile.asmx/Search";
	public static String baseURLChange_Of_Status_Update=Link+"ChangeOfStatusMobile.asmx/UpdateStatus";
	public static String baseURLUpdate_GateOut=Link+"GateOutMobile.asmx/Update";
	public static String baseURLGateOutPending=Link+"GateOutMobile.asmx/List";
	public static String baseURLGateOutPendingFIlter=Link+"GateOutMobile.asmx/filter";
	public static String baseURLGateOutSearchList=Link+"GateOutMobile.asmx/SearchList";
	public static String baseURLStock_DropDown=Link+"StockReportMobile.asmx/StockDropdown";
	public static String baseURLStock_Run_Report=Link+"/StockReportMobile.asmx/StockReportView";
	//http://192.168.1.19/iTankMobileService/StockReportMobile.asmx/StockDropdown

}
