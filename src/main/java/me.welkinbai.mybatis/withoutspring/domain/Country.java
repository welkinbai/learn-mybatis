package me.welkinbai.mybatis.withoutspring.domain;

import java.io.Serializable;

/**
 * Created by welkinbai on 2017/1/5.
 */
public class Country implements Serializable{

    private static final long serialVersionUID = -6939348537294418488L;

    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Country{" + "countryCode='" + countryCode + '\'' +
                '}';
    }
}
