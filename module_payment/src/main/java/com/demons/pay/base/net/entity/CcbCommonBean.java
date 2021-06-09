package com.demons.pay.base.net.entity;

/**
 * @author demons
 */
public class CcbCommonBean {

    private String appName, transId, resultInfoCode,
            resultMsg, transDat;

    public CcbCommonBean(String appName, String transId, String resultInfoCode, String resultMsg, String transDat) {
        this.appName = appName;
        this.transId = transId;
        this.resultInfoCode = resultInfoCode;
        this.resultMsg = resultMsg;
        this.transDat = transDat;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getResultInfoCode() {
        return resultInfoCode;
    }

    public void setResultInfoCode(String resultInfoCode) {
        this.resultInfoCode = resultInfoCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getTransDat() {
        return transDat;
    }

    public void setTransDat(String transDat) {
        this.transDat = transDat;
    }
}
