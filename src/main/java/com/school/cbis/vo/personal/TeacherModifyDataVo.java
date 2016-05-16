package com.school.cbis.vo.personal;

/**
 * Created by lenovo on 2016-05-16.
 */
public class TeacherModifyDataVo {
    private String username;
    private String realName;
    private String sex;
    private String birthday;
    private String nation;
    private String post;
    private String politicalLandscape;
    private String religiousBelief;
    private String headImg;
    private String identityCard;
    private String familyResidence;
    private String personaIntroduction;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPoliticalLandscape() {
        return politicalLandscape;
    }

    public void setPoliticalLandscape(String politicalLandscape) {
        this.politicalLandscape = politicalLandscape;
    }

    public String getReligiousBelief() {
        return religiousBelief;
    }

    public void setReligiousBelief(String religiousBelief) {
        this.religiousBelief = religiousBelief;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFamilyResidence() {
        return familyResidence;
    }

    public void setFamilyResidence(String familyResidence) {
        this.familyResidence = familyResidence;
    }

    public String getPersonaIntroduction() {
        return personaIntroduction;
    }

    public void setPersonaIntroduction(String personaIntroduction) {
        this.personaIntroduction = personaIntroduction;
    }

    @Override
    public String toString() {
        return "TeacherModifyDataVo{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nation='" + nation + '\'' +
                ", post='" + post + '\'' +
                ", politicalLandscape='" + politicalLandscape + '\'' +
                ", religiousBelief='" + religiousBelief + '\'' +
                ", headImg='" + headImg + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", familyResidence='" + familyResidence + '\'' +
                ", personaIntroduction='" + personaIntroduction + '\'' +
                '}';
    }
}
