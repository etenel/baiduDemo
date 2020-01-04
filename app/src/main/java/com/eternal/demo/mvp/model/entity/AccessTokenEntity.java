package com.eternal.demo.mvp.model.entity;

public class AccessTokenEntity {

    /**
     * refresh_token : 25.5a0acd6964d4a188d25dbeeb50d45a05.315360000.1893142776.282335-18115818
     * expires_in : 2592000
     * session_key : 9mzdCy1Ax5DuzF7Yok1Cuh0Fegigvj3NJdoqGtk+L4jjzxoHGAehw8VbfnmSvyEIfWdTiVng4cds4SqSgKWcOCiyjg POLA==
     * access_token : 24.05da59c2122b711b7bb6c1368a2a7195.2592000.1580374776.282335-18115818
     * scope : vis-faceverify_FACE_Police audio_voice_assistant_get brain_enhanced_asr public brain_all_scope v is-faceverify_faceverify_h5-face-liveness vis-faceverify_FACE_V3 vis-faceverify_idl_face_merge wise_adapt lebo _resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_ flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_s mart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_reca pi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理
     * session_secret : e49ddfa66f6ac4e0817d7bde2dc558d3
     */

    private String refresh_token;
    private long expires_in;
    private String session_key;
    private String access_token;
    private String scope;
    private String session_secret;
    private String error_description;
    private String error;

    public String getRefresh_token() {
        return refresh_token == null ? "" : refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getSession_key() {
        return session_key == null ? "" : session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAccess_token() {
        return access_token == null ? "" : access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope == null ? "" : scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_secret() {
        return session_secret == null ? "" : session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }

    public String getError_description() {
        return error_description == null ? "" : error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
