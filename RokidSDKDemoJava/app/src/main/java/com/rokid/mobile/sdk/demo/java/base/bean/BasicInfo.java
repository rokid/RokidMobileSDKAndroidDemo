package com.rokid.mobile.sdk.demo.java.base.bean;

import com.google.gson.annotations.Expose;
import com.rokid.mobile.lib.entity.BaseBean;

/**
 * Created by showingcp on 4/1/17.
 */

public class BasicInfo extends BaseBean {

    @Expose
    private String region;
    @Expose
    private String ota;
    @Expose
    private String cy;
    @Expose
    private String mac;
    @Expose
    private String master;
    @Expose
    private String ip;
    @Expose
    private String lan_ip;
    @Expose
    private String id;
    @Expose
    private String identity;
    @Expose
    private String signLink;
    @Expose
    private String cmiit;
    @Expose
    private String lng;
    @Expose
    private String tts;
    @Expose
    private String ttsList;
    @Expose
    private String device_type_id;
    @Expose
    private String sn;


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOta() {
        return ota;
    }

    public void setOta(String ota) {
        this.ota = ota;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLanIp() {
        return lan_ip;
    }

    public void setLanIp(String lanIp) {
        this.lan_ip = lan_ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSignLink() {
        return signLink;
    }

    public void setSignLink(String signLink) {
        this.signLink = signLink;
    }

    public String getCmiit() {
        return cmiit;
    }

    public void setCmiit(String cmiit) {
        this.cmiit = cmiit;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getTtsList() {
        return ttsList;
    }

    public void setTtsList(String ttsList) {
        this.ttsList = ttsList;
    }

    public String getDevice_type_id() {
        return device_type_id;
    }

    public void setDevice_type_id(String device_type_id) {
        this.device_type_id = device_type_id;
    }

    public String getLan_ip() {
        return lan_ip;
    }

    public void setLan_ip(String lan_ip) {
        this.lan_ip = lan_ip;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
