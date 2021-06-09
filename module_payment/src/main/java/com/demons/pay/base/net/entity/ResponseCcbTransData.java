package com.demons.pay.base.net.entity;

/**
 * 建行支付TransData实体字段
 *
 * @author demons
 */
public class ResponseCcbTransData {

    /**
     * resCode : 00
     * resMsg : 消费成功
     * merchantName : One Avenue卓悦中心
     * merchantID : 105584053110905
     * terminalID : 10020682
     * operID : 01
     * cardIssuer : 招商银行
     * cardNo : 621483******2275
     * cardCode : CUP
     * cardIputMethod : 07
     * transName : 消费
     * batchNo : 000002
     * traceNo : 000405
     * checkNo :
     * refNo : 115645226551
     * transDate : 0605
     * transTime : 150853
     * amt : 000000000001
     * payChannel : CHANNEL_UNIONPAY
     * orgAmt : 000000000001
     * isScancodeSale : 0
     */

    private String resCode;
    private String resMsg;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户编号
     */
    private String merchantID;
    /**
     * 终端号
     */
    private String terminalID;
    /**
     * 操作员号
     */
    private String operID;
    /**
     * 卡种信息
     */
    private String cardIssuer;
    /**
     * 卡号（180701开始返回前6后4，中间以*代替）
     */
    private String cardNo;
    /**
     * 卡组织信息（非CUP为外币卡）
     */
    private String cardCode;
    /**
     * 刷卡方式，如刷卡为02，挥卡为07、98、96，插卡为05，手输为01、91，降级为92，扫码或其他为99
     */
    private String cardIputMethod;
    /**
     * 交易类型
     */
    private String transName;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 凭证号
     */
    private String traceNo;
    /**
     * 凭证号（不使用）
     */
    private String checkNo;
    /**
     * 参考号
     */
    private String refNo;
    /**
     * 交易日期
     */
    private String transDate;
    /**
     * 交易时间
     */
    private String transTime;
    /**
     * 交易金额
     */
    private String amt;
    /**
     * 支付渠道（CHANNEL_ALIPAY  支付宝，CHANNEL_WEPAY  微信，
     * CHANNEL_DRAGONPAY  龙支付，
     * CHANNEL_UNIONCODEPAY 银联二维码，DIGITAL_CASH 数字人民币支付，
     * CHANNEL_UNIONPAY  其他银行卡交易）
     */
    private String payChannel;
    /**
     * （预授权撤销）原交易金额
     */
    private String orgAmt;
    /**
     * 是否银联二维码消费
     */
    private String isScancodeSale;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getOperID() {
        return operID;
    }

    public void setOperID(String operID) {
        this.operID = operID;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardIputMethod() {
        return cardIputMethod;
    }

    public void setCardIputMethod(String cardIputMethod) {
        this.cardIputMethod = cardIputMethod;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getOrgAmt() {
        return orgAmt;
    }

    public void setOrgAmt(String orgAmt) {
        this.orgAmt = orgAmt;
    }

    public String getIsScancodeSale() {
        return isScancodeSale;
    }

    public void setIsScancodeSale(String isScancodeSale) {
        this.isScancodeSale = isScancodeSale;
    }
}
