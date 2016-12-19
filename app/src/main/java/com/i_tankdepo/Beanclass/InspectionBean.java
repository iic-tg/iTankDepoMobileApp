package com.i_tankdepo.Beanclass;

import java.io.Serializable;

/**
 * Created by Admin on 12/19/2016.
 */

public class InspectionBean implements Serializable {
    String equip_no;
    String equip_status;
    String equip_statusType;
    String customer;
    String customerId;
    String inDate;
    String previous_cargo;
    String lastStatusDate;
    String add_cleaningBit;
    String cleaningId;
    String cleaningRefNo;
    String remark;
    String orgCleaningDate;
    String orgInspectionDate;
    String clean_unclean;
    String seal_no;
    String eir_no;
    String cleaningRate;
    String slabrate;
    String gi_trans_no;

    public String getEquip_no() {
        return equip_no;
    }

    public void setEquip_no(String equip_no) {
        this.equip_no = equip_no;
    }

    public String getEquip_status() {
        return equip_status;
    }

    public void setEquip_status(String equip_status) {
        this.equip_status = equip_status;
    }

    public String getEquip_statusType() {
        return equip_statusType;
    }

    public void setEquip_statusType(String equip_statusType) {
        this.equip_statusType = equip_statusType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getPrevious_cargo() {
        return previous_cargo;
    }

    public void setPrevious_cargo(String previous_cargo) {
        this.previous_cargo = previous_cargo;
    }

    public String getLastStatusDate() {
        return lastStatusDate;
    }

    public void setLastStatusDate(String lastStatusDate) {
        this.lastStatusDate = lastStatusDate;
    }

    public String getAdd_cleaningBit() {
        return add_cleaningBit;
    }

    public void setAdd_cleaningBit(String add_cleaningBit) {
        this.add_cleaningBit = add_cleaningBit;
    }

    public String getCleaningId() {
        return cleaningId;
    }

    public void setCleaningId(String cleaningId) {
        this.cleaningId = cleaningId;
    }

    public String getCleaningRefNo() {
        return cleaningRefNo;
    }

    public void setCleaningRefNo(String cleaningRefNo) {
        this.cleaningRefNo = cleaningRefNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgCleaningDate() {
        return orgCleaningDate;
    }

    public void setOrgCleaningDate(String orgCleaningDate) {
        this.orgCleaningDate = orgCleaningDate;
    }

    public String getOrgInspectionDate() {
        return orgInspectionDate;
    }

    public void setOrgInspectionDate(String orgInspectionDate) {
        this.orgInspectionDate = orgInspectionDate;
    }

    public String getClean_unclean() {
        return clean_unclean;
    }

    public void setClean_unclean(String clean_unclean) {
        this.clean_unclean = clean_unclean;
    }

    public String getSeal_no() {
        return seal_no;
    }

    public void setSeal_no(String seal_no) {
        this.seal_no = seal_no;
    }

    public String getEir_no() {
        return eir_no;
    }

    public void setEir_no(String eir_no) {
        this.eir_no = eir_no;
    }

    public String getCleaningRate() {
        return cleaningRate;
    }

    public void setCleaningRate(String cleaningRate) {
        this.cleaningRate = cleaningRate;
    }

    public String getSlabrate() {
        return slabrate;
    }

    public void setSlabrate(String slabrate) {
        this.slabrate = slabrate;
    }

    public String getGi_trans_no() {
        return gi_trans_no;
    }

    public void setGi_trans_no(String gi_trans_no) {
        this.gi_trans_no = gi_trans_no;
    }
}
