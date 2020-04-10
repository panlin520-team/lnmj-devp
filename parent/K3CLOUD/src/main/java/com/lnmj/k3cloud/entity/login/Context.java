/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.login;
import java.util.List;

/**
 * Auto-generated: 2019-11-08 19:53:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Context {
    private String UserLocale;
    private String LogLocale;
    private String DBid;
    private Integer DatabaseType;
    private String SessionId;
    private List<UseLanguages> UseLanguages;
    private Integer UserId;
    private String UserName;
    private String CustomName;
    private String DisplayVersion;
    private String DataCenterName;
    private String UserToken;
    private CurrentOrganizationInfo CurrentOrganizationInfo;
    private Boolean IsCH_ZH_AutoTrans;
    private Integer ClientType;
    private WeiboAuthInfo WeiboAuthInfo;
    private UTimeZone UTimeZone;
    private STimeZone STimeZone;
    private String GDCID;
    public void setUserLocale(String UserLocale) {
         this.UserLocale = UserLocale;
     }
     public String getUserLocale() {
         return UserLocale;
     }

    public void setLogLocale(String LogLocale) {
         this.LogLocale = LogLocale;
     }
     public String getLogLocale() {
         return LogLocale;
     }

    public void setDBid(String DBid) {
         this.DBid = DBid;
     }
     public String getDBid() {
         return DBid;
     }

    public void setDatabaseType(Integer DatabaseType) {
         this.DatabaseType = DatabaseType;
     }
     public Integer getDatabaseType() {
         return DatabaseType;
     }

    public void setSessionId(String SessionId) {
         this.SessionId = SessionId;
     }
     public String getSessionId() {
         return SessionId;
     }

    public void setUseLanguages(List<UseLanguages> UseLanguages) {
         this.UseLanguages = UseLanguages;
     }
     public List<UseLanguages> getUseLanguages() {
         return UseLanguages;
     }

    public void setUserId(Integer UserId) {
         this.UserId = UserId;
     }
     public Integer getUserId() {
         return UserId;
     }

    public void setUserName(String UserName) {
         this.UserName = UserName;
     }
     public String getUserName() {
         return UserName;
     }

    public void setCustomName(String CustomName) {
         this.CustomName = CustomName;
     }
     public String getCustomName() {
         return CustomName;
     }

    public void setDisplayVersion(String DisplayVersion) {
         this.DisplayVersion = DisplayVersion;
     }
     public String getDisplayVersion() {
         return DisplayVersion;
     }

    public void setDataCenterName(String DataCenterName) {
         this.DataCenterName = DataCenterName;
     }
     public String getDataCenterName() {
         return DataCenterName;
     }

    public void setUserToken(String UserToken) {
         this.UserToken = UserToken;
     }
     public String getUserToken() {
         return UserToken;
     }

    public void setCurrentOrganizationInfo(CurrentOrganizationInfo CurrentOrganizationInfo) {
         this.CurrentOrganizationInfo = CurrentOrganizationInfo;
     }
     public CurrentOrganizationInfo getCurrentOrganizationInfo() {
         return CurrentOrganizationInfo;
     }

    public void setIsCH_ZH_AutoTrans(Boolean IsCH_ZH_AutoTrans) {
         this.IsCH_ZH_AutoTrans = IsCH_ZH_AutoTrans;
     }
     public Boolean getIsCH_ZH_AutoTrans() {
         return IsCH_ZH_AutoTrans;
     }

    public void setClientType(Integer ClientType) {
         this.ClientType = ClientType;
     }
     public Integer getClientType() {
         return ClientType;
     }

    public void setWeiboAuthInfo(WeiboAuthInfo WeiboAuthInfo) {
         this.WeiboAuthInfo = WeiboAuthInfo;
     }
     public WeiboAuthInfo getWeiboAuthInfo() {
         return WeiboAuthInfo;
     }

    public void setUTimeZone(UTimeZone UTimeZone) {
         this.UTimeZone = UTimeZone;
     }
     public UTimeZone getUTimeZone() {
         return UTimeZone;
     }

    public void setSTimeZone(STimeZone STimeZone) {
         this.STimeZone = STimeZone;
     }
     public STimeZone getSTimeZone() {
         return STimeZone;
     }

    public void setGDCID(String GDCID) {
         this.GDCID = GDCID;
     }
     public String getGDCID() {
         return GDCID;
     }

}