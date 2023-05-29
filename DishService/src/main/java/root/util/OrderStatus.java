package root.util;

public enum OrderStatus {
    PENDING("в ожидании"),
    IN_PROGRESS("в работе"),
    COMPLETED("выполнен"),
    CANCELLED("отменен");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
