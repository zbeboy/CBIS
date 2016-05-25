package com.school.cbis.vo.system;

/**
 * Created by lenovo on 2016-05-25.
 */
public class MobileInfoVo {
    private boolean isSwitch;
    private String apikey;

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean aSwitch) {
        isSwitch = aSwitch;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public String toString() {
        return "MobileInfoVo{" +
                "isSwitch=" + isSwitch +
                ", apikey='" + apikey + '\'' +
                '}';
    }
}
