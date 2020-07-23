package entity;

public class LeaveApplication {
    private final String leaveType;
    private final String description;
    private final String toDate;
    private final String fromDate;
    private String numOfDays;

    private LeaveApplication(LeaveApplicationBuilder leaveApplicationBuilder) {
        this.leaveType = leaveApplicationBuilder.leaveType;
        this.description = leaveApplicationBuilder.description;
        this.toDate = leaveApplicationBuilder.toDate;
        this.fromDate = leaveApplicationBuilder.fromDate;
        this.numOfDays = leaveApplicationBuilder.numOfDays;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getDescription() {
        return description;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(String numOfDays) {
        this.numOfDays = numOfDays;
    }

    public static class LeaveApplicationBuilder {
        private final String leaveType;
        private final String description;
        private final String toDate;
        private final String fromDate;
        private String numOfDays;

        public LeaveApplicationBuilder(String leaveType, String description, String toDate, String fromDate) {
            this.leaveType = leaveType;
            this.description = description;
            this.toDate = toDate;
            this.fromDate = fromDate;
        }

        public LeaveApplicationBuilder setNumOfDays(String days) {
            this.numOfDays = days;
            return this;
        }

        public LeaveApplication build() {
            return new LeaveApplication(this);
        }
    }
}
