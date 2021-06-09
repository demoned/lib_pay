package com.demons.payment;

/**
 * 建行支付传入的TransData的Json实体
 *
 * @author demons
 */
public class TransData {
    private String amt;
    private boolean isNeedPrintReceipt;
    private String counterNo;
    private String orderNo;
    private String lsOrderNo;
    private String inputRemarkInfo;
    private String remarkSize;
    private String projectTag;

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public boolean isNeedPrintReceipt() {
        return isNeedPrintReceipt;
    }

    public void setNeedPrintReceipt(boolean needPrintReceipt) {
        isNeedPrintReceipt = needPrintReceipt;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLsOrderNo() {
        return lsOrderNo;
    }

    public void setLsOrderNo(String lsOrderNo) {
        this.lsOrderNo = lsOrderNo;
    }

    public String getInputRemarkInfo() {
        return inputRemarkInfo;
    }

    public void setInputRemarkInfo(String inputRemarkInfo) {
        this.inputRemarkInfo = inputRemarkInfo;
    }

    public String getRemarkSize() {
        return remarkSize;
    }

    public void setRemarkSize(String remarkSize) {
        this.remarkSize = remarkSize;
    }

    public String getProjectTag() {
        return projectTag;
    }

    public void setProjectTag(String projectTag) {
        this.projectTag = projectTag;
    }
}
