package com.my.domain;

import java.io.Serializable;

public class Request implements Serializable {
    private long id;
    private UserDetails userDetails;
    private double planCost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public double getPlanCost() {
        return planCost;
    }

    public void setPlanCost(double planCost) {
        this.planCost = planCost;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userDetails=" + userDetails +
                ", planCost=" + planCost +
                '}';
    }
}
