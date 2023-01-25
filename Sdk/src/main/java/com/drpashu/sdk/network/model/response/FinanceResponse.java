package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinanceResponse {
    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("max_income")
    @Expose
    private Integer maxIncome;
    @SerializedName("max_expense")
    @Expose
    private Integer maxExpense;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Integer getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(Integer maxIncome) {
        this.maxIncome = maxIncome;
    }

    public Integer getMaxExpense() {
        return maxExpense;
    }

    public void setMaxExpense(Integer maxExpense) {
        this.maxExpense = maxExpense;
    }

    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("expense")
    @Expose
    private Integer expense;
    @SerializedName("profit")
    @Expose
    private Integer profit;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public class Response {

        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("income")
        @Expose
        private Integer income;
        @SerializedName("expense")
        @Expose
        private Integer expense;
        @SerializedName("profit")
        @Expose
        private Integer profit;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public Integer getIncome() {
            return income;
        }

        public void setIncome(Integer income) {
            this.income = income;
        }

        public Integer getExpense() {
            return expense;
        }

        public void setExpense(Integer expense) {
            this.expense = expense;
        }

        public Integer getProfit() {
            return profit;
        }

        public void setProfit(Integer profit) {
            this.profit = profit;
        }

    }
}