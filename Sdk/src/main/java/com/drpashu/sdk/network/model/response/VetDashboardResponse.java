package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VetDashboardResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("Patient_report")
        @Expose
        private PatientReport patientReport;
        @SerializedName("Vet_status")
        @Expose
        private String vetStatus;
        @SerializedName("User_bio")
        @Expose
        private String userBio;
        @SerializedName("Timings")
        @Expose
        private String timings;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("Priority")
        @Expose
        private int Priority;
        @SerializedName("Appointments")
        @Expose
        private List<Appointment> appointments = null;

        public int getPriority() {
            return Priority;
        }

        public void setPriority(int priority) {
            Priority = priority;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getTimings() {
            return timings;
        }

        public void setTimings(String timings) {
            this.timings = timings;
        }

        public PatientReport getPatientReport() {
            return patientReport;
        }

        public void setPatientReport(PatientReport patientReport) {
            this.patientReport = patientReport;
        }

        public String getUserBio() {
            return userBio;
        }

        public void setUserBio(String userBio) {
            this.userBio = userBio;
        }

        public String getVetStatus() {
            return vetStatus;
        }

        public void setVetStatus(String vetStatus) {
            this.vetStatus = vetStatus;
        }

        public List<Appointment> getAppointments() {
            return appointments;
        }

        public void setAppointments(List<Appointment> appointments) {
            this.appointments = appointments;
        }

        public class Appointment {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("role")
            @Expose
            private String role;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("time")
            @Expose
            private String time;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public class PatientReport {

            @SerializedName("Patient_checked")
            @Expose
            private Integer patientChecked;
            @SerializedName("Patient_satisfied")
            @Expose
            private Integer patientSatisfied;
            @SerializedName("Patient_active")
            @Expose
            private Integer patientActive;
            @SerializedName("missed_calls")
            @Expose
            private Integer missedCalls;
            @SerializedName("completed_calls")
            @Expose
            private Integer completedCalls;

            public Integer getPatientChecked() {
                return patientChecked;
            }

            public void setPatientChecked(Integer patientChecked) {
                this.patientChecked = patientChecked;
            }

            public Integer getPatientSatisfied() {
                return patientSatisfied;
            }

            public void setPatientSatisfied(Integer patientSatisfied) {
                this.patientSatisfied = patientSatisfied;
            }

            public Integer getPatientActive() {
                return patientActive;
            }

            public void setPatientActive(Integer patientActive) {
                this.patientActive = patientActive;
            }

            public Integer getMissedCalls() {
                return missedCalls;
            }

            public void setMissedCalls(Integer missedCalls) {
                this.missedCalls = missedCalls;
            }

            public Integer getCompletedCalls() {
                return completedCalls;
            }

            public void setCompletedCalls(Integer completedCalls) {
                this.completedCalls = completedCalls;
            }
        }
    }
}