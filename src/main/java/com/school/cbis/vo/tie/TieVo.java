package com.school.cbis.vo.tie;

import javax.validation.constraints.NotNull;

/**
 * Created by lenovo on 2016-02-07.
 */
public class TieVo {
    @NotNull
    private int tieId;
    @NotNull
    private String tieName;
    private String tieAddress;
    private String tiePhone;
    @NotNull
    private int yard;

    public int getTieId() {
        return tieId;
    }

    public void setTieId(int tieId) {
        this.tieId = tieId;
    }

    public String getTieName() {
        return tieName;
    }

    public void setTieName(String tieName) {
        this.tieName = tieName;
    }

    public String getTieAddress() {
        return tieAddress;
    }

    public void setTieAddress(String tieAddress) {
        this.tieAddress = tieAddress;
    }

    public String getTiePhone() {
        return tiePhone;
    }

    public void setTiePhone(String tiePhone) {
        this.tiePhone = tiePhone;
    }

    public int getYard() {
        return yard;
    }

    public void setYard(int yard) {
        this.yard = yard;
    }

    @Override
    public String toString() {
        return "TieVo{" +
                "tieId=" + tieId +
                ", tieName='" + tieName + '\'' +
                ", tieAddress='" + tieAddress + '\'' +
                ", tiePhone='" + tiePhone + '\'' +
                ", yard=" + yard +
                '}';
    }
}
