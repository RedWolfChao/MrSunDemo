package model;

import java.io.Serializable;

/**
 * 用户类
 */
public class UserModel implements Serializable {
    private String uID;
    private String uName;
    private String uPass;
    private String uSex;
    private String uType;

    public String getuPass() {
        return uPass;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    private String uAddress;
    private String uMobile;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuMobile() {
        return uMobile;
    }

    public void setuMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    public void setuPass(String uPass) {
        this.uPass = uPass;
    }
}
