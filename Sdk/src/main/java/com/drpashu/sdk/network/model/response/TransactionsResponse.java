package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionsResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lot_id")
    @Expose
    private String lotId;
    @SerializedName("Transactions")
    @Expose
    private List<Transaction> transactions = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public class Transaction {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("info")
        @Expose
        private String info;
        @SerializedName("account")
        @Expose
        private String account;
        @SerializedName("amount")
        @Expose
        private String amount;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

    }
}
