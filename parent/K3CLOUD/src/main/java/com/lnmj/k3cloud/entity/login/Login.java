/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.login;

/**
 * Auto-generated: 2019-11-08 19:53:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Login {

    private String Message;
    private String MessageCode;
    private String CheckPasswordPolicy;
    private Integer LoginResultType;
    private Context Context;
    private String KDSVCSessionId;
    private String FormId;
    private String RedirectFormParam;
    private String FormInputObject;
    private String ErrorStackTrace;
    private Integer Lcid;
    private String AccessToken;
    private String KdAccessResult;
    private Boolean IsSuccessByAPI;


    public void setMessage(String Message) {
         this.Message = Message;
     }
     public String getMessage() {
         return Message;
     }

    public void setMessageCode(String MessageCode) {
         this.MessageCode = MessageCode;
     }
     public String getMessageCode() {
         return MessageCode;
     }

    public void setLoginResultType(Integer LoginResultType) {
         this.LoginResultType = LoginResultType;
     }
     public Integer getLoginResultType() {
         return LoginResultType;
     }

    public void setContext(Context Context) {
         this.Context = Context;
     }
     public Context getContext() {
         return Context;
     }

    public void setKDSVCSessionId(String KDSVCSessionId) {
         this.KDSVCSessionId = KDSVCSessionId;
     }
     public String getKDSVCSessionId() {
         return KDSVCSessionId;
     }

    public void setFormId(String FormId) {
         this.FormId = FormId;
     }
     public String getFormId() {
         return FormId;
     }

    public void setRedirectFormParam(String RedirectFormParam) {
         this.RedirectFormParam = RedirectFormParam;
     }
     public String getRedirectFormParam() {
         return RedirectFormParam;
     }

    public void setFormInputObject(String FormInputObject) {
         this.FormInputObject = FormInputObject;
     }
     public String getFormInputObject() {
         return FormInputObject;
     }

    public void setErrorStackTrace(String ErrorStackTrace) {
         this.ErrorStackTrace = ErrorStackTrace;
     }
     public String getErrorStackTrace() {
         return ErrorStackTrace;
     }

    public void setLcid(Integer Lcid) {
         this.Lcid = Lcid;
     }
     public Integer getLcid() {
         return Lcid;
     }

    public void setAccessToken(String AccessToken) {
         this.AccessToken = AccessToken;
     }
     public String getAccessToken() {
         return AccessToken;
     }

    public void setKdAccessResult(String KdAccessResult) {
         this.KdAccessResult = KdAccessResult;
     }
     public String getKdAccessResult() {
         return KdAccessResult;
     }

    public void setIsSuccessByAPI(Boolean IsSuccessByAPI) {
         this.IsSuccessByAPI = IsSuccessByAPI;
     }
     public Boolean getIsSuccessByAPI() {
         return IsSuccessByAPI;
     }

}