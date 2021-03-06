package com.i_tankdepo.Constants;



import com.i_tankdepo.Beanclass.CustomerReportBean;
import com.i_tankdepo.Beanclass.GeneralReportBean;
import com.i_tankdepo.Beanclass.Image_Bean;
import com.i_tankdepo.Beanclass.LineItem_Bean;
import com.i_tankdepo.Beanclass.Multi_Photo_Bean;
import com.i_tankdepo.Beanclass.PendingBean;
import com.i_tankdepo.Beanclass.RepairBean;
import com.i_tankdepo.Beanclass.RepairCompletionBean;
import com.i_tankdepo.Beanclass.TypeReportBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Metaplore on 9/10/2016.
 */
public class GlobalConstants {


    public static String EIMNFCTR_DT;
    public static String EITR_WGHT_NC;
    public static String EIGRSS_WGHT_NC;
    public static String EICPCTY_NC;
    public static String EILST_SRVYR_NM;
    public static String EILST_TST_DT;
    public static String EILST_TST_TYP_ID;
    public static String EINXT_TST_DT;
    public static String EINXT_TST_TYP_ID;
    public static String EIRMRKS_VC;
    public static String fullname="";
    public static String blocks_numbers="";
    public static String selectedId="";
    public static String Attachment_status="";
    public static int Invoice_party;
    public static int Invoice_count=0;
    public static int Survey_Invoice_party;
    public static String InvoiceParty_name ="";
    public static String InvoiceParty_Id ="";
    public static String from="";
    public static String ActivityDate="";
    public static String summaryfrom="";
    public static String new_line_item="";
    public static String image_name="";
    public static String cust_code="";
    public static String type_id="";
    public static String code_id="";
    public static String print_string="";
    public static ArrayList<Image_Bean> multiple_encodeArray;
    public static ArrayList<LineItem_Bean> lineItem_beanArrayList;
    public static ArrayList<LineItem_Bean> add_lineItem_beanArrayList;
    public static String pre_code="";
    public static String pre_id="";
    public static String pre_adv_id="";
    public static String selectedName="";
    public static String PreAdviceId="";
    public static String GateInId="";
    public static String customer_name="";
    public static String Line_item_Json="";
    public static String Line_item_id="";
    public static String DPT_ID="";
    public static String SHL_TST="";
    public static String STM_TB_TST="";
    public static String selectedName_optional="";
    public static String add_detail_jsonobject="";
    public static List<String> selected_Stock_Cust_Id;
    public static List<String> selected_Stock_Equp_Id;
    public static List<String> selected_Stock_Prev_Crg_Id;
    public static List<String> selected_Stock_Curnt_Status_Id;
    public static List<String> selected_Stock_Nxt_Tst_Type_Id;
    public static List<String> selected_Stock_Depot_Id;
    public static String SubmitGateInId="";
    public static String AttachmentStatus="";
    public static String equipment_no="";
    public static String est_mh_cost="";
    public static String actual_mh="";
    public static String totale_amount="";
    public static String leskTestID="";
    public static String equip_status="";
    public static String equip_status_id="";
    public static String ChemicalName="";
    public static String equip_status_type="";
    public static String status="";
    public static String status_id="";
    public static String estimate_no="";
    public static String TestDate="";
    public static String RLFVLV1="";
    public static String RLFVLV2="";
    public static String PG1="";
    public static String PG2="";
    public static String repair_type="";
    public static String estimated_manHr="";
    public static String actual_manHr="";
    public static String completion_date="";
    public static String attachment="";
    public static String code="";
    public static String location="";
    public static String date="";
    public static String type="";
    public static String CHECKED="";
    public static String cleaning_method="";
    public static String indate="";
    public static String lastStatusDate="";
    public static String last_testType="";
    public static String next_testType="";
    public static String last_testDate="";
    public static String next_testDate="";
    public static String lobor_rate="";
    public static String add_cleaning_bit="";
    public static String cleaning_id="";
    public static String cleaning_RefNo="";
    public static String org_cleaningDate="";
    public static String org_inspectionDate="";
    public static String clean_unclean="";
    public static String seal_no="";
    public static String lastSurveyor="";
    public static String ValidityPeriodforTest="";
    public static String repair_TypeID="";
    public static String repair_TypeCD="";
    public static String invoice_PartyCD="";
    public static String invoice_PartyID="";
    public static String invoice_PartyName="";
    public static String repair_EstimateID="";
    public static String repair_EstimateNo="";
    public static String Cust_AppRef="";
    public static String approvalDate="";
    public static String Party_AppRef="";
    public static String revisionNo="";
    public static String LastGrantedBy="";
    public static String LastGrantedDate="";
    public static String ListRptNo="";
    public static String no_of_tms_granted="";
    public static String SHLL_TST_BT="";
    public static String STM_TB_TST_BT="";
    public static String Surveyor_name="";
    public static String tariff_Group="";
    public static String tariff_Id="";
    public static String attach_count="";
    public static int original_totel_amount=0;
    public static int estimate_original_totel_amount=0;
    public static int estimate_mysubmit_original_totel_amount=0;
    public static int original_summary_amount=0;
    public static String Survey_CompletionDate="";
    public static String lineItems="";
    public static String attchement="";
    public static String currency="";
    public static String repairEstimateNo="";
    public static String repairEstimateId="";
    public static String customer_Id="";
    public static String cust_Id="";

    public static String clean_rate="";
    public static String slab_rate="";
    public static String LocationofCleaning="";
    public static String InvoiceToID="";
    public static String InvoiceToCD="";
    public static String Cleanedfor="";
    public static String CleaningRate="";
    public static String gi_trans_no="";
    public static String BottomSealNo="";
    public static String TopSealNo="";
    public static String ManLidSealNo="";
    public static String ConditionCD="";
    public static String ConditionID="";
    public static String CleaningStatusIIcd="";
    public static String CleaningStatusIIid="";
    public static String CleaningStatusIcd="";
    public static String CleaningStatusIid="";
    public static String ValveandFittingConditionCD="";
    public static String ValveandFittingConditionID="";
    public static String LatestInspectionDate="";
    public static String OriginalInspectionDate="";

    public static String gateIn_Trans_no="";

    public static String time="";
    public static String previous_cargo="";
    public static String eir_no="";
    public static String vechicle_no="";
    public static String Transport_No="";
    public static String heating_bt="";
    public static String rental_bt="";
    public static String remark="";
    public static String currentStatus="";
    public static String currentStatusDate="";
    public static String ACTV_BT="";
    public static String CRTD_BY="";
    public static String CRTD_DT="";
    public static String MDFD_BY="";
    public static String MDFD_DT="";
    public static String EQPMNT_TYP_ID="";
    public static String EQPMNT_TYP_CD="";
    public static String EQPMNT_CD_ID="";
    public static String EQPMNT_CD_CD="";
    public static String EQPMNT_STTS_ID="";
    public static String EQPMNT_STTS_CD="";
    public static String attachmentStatus="";
    public static String attach_filename="";
    public static String attach_ID="";
    public static String position="";
    public static String gt_transaction_no="";
    public static String htng_startDate="";
    public static String htng_startTime="";
    public static String htng_EndDate="";
    public static String htng_EndTime="";
    public static String htng_temp="";
    public static String ttl_htngPrd="";
    public static String min_htng_rate="";
    public static String hourly_charge="";
    public static String cust_currency="";
    public static String min_htngPrd="";
    public static String ttl_RT_NC="";
    public static String GateInCount="";
    public static String GateOutCount="";
    public static String CleaningCount="";
    public static String InspectionCount="";
    public static String HeatingCount="";
    public static String LeaktestCount="";
    public static String Cleaning_date_from="";
    public static String Cleaning_date_to="";
    public static String Inspection_date_from="";
    public static String Inspection_date_to="";
    public static String Current_status_date_from="";
    public static String Current_status_date_to="";
    public static String Nxt_Tst_date_from="";
    public static String Nxt_Tst_date_to="";
    public static String In_date_from="";
    public static String In_date_to="";
    public static String Out_date_from="";
    public static String Out_date_to="";
    public static String Equip_No_date="";
    public static String EIR_No_date="";
    public static ArrayList<GeneralReportBean> generalReportBeanArrayList;
    public static ArrayList<TypeReportBean> typeReportBeanArrayList;
    public static ArrayList<CustomerReportBean> customerReportbeanArrayList;
    public static ArrayList<RepairBean> repair_arraylist;

    public static String roleID="";
        public static int pendingcount=0;
    public static String validationStatus="";
    public static ArrayList<RepairCompletionBean> repair_completion_arraylist;
    public static String repair_est_count="";
    public static String repair_app_count="";
    public static String repair_com_count="";
    public static String survey_com_count="";
    public static ArrayList<Multi_Photo_Bean> pending_attach_arraylist;
}
