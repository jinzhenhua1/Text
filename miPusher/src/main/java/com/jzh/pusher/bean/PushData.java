package com.jzh.pusher.bean;

import org.json.JSONObject;

public class PushData {
    /**
     * action : 0表示注册，１表示消息
     * type : HUAWEI
     * token : xxxx
     * message : thisismessage
     */

    private int action;
    private String type;
    private String token;
    private String message;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("action", this.action);
            json.put("type", this.type);
            json.put("token", this.token);
            json.put("message", this.message);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
