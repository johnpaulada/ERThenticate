package com.erthenticate.app.erthenticate;

public class Record {
    private double phoneId;
    private double userId;
    private double documentId;
    private double timestamp;
    private double action;
    private double phoneOrientation;
    private double x;
    private double y;
    private double pressure;
    private double area;
    private double fingerOrientation;

    public Record(double phoneId, double userId, double documentId, double timestamp, double action, double phoneOrientation, double x, double y, double pressure, double area, double fingerOrientation) {
        this.phoneId = phoneId;
        this.userId = userId;
        this.documentId = documentId;
        this.timestamp = timestamp;
        this.action = action;
        this.phoneOrientation = phoneOrientation;
        this.x = x;
        this.y = y;
        this.pressure = pressure;
        this.area = area;
        this.fingerOrientation = fingerOrientation;
    }

    @Override
    public String toString() {
        return
                phoneId +
                "," + userId +
                "," + documentId +
                "," + timestamp +
                "," + action +
                "," + phoneOrientation +
                "," + x +
                "," + y +
                "," + pressure +
                "," + area +
                "," + fingerOrientation;
    }

    public String print() {
        return "Record{" +
                "phoneId=" + phoneId +
                ", userId=" + userId +
                ", documentId=" + documentId +
                ", timestamp=" + timestamp +
                ", action=" + action +
                ", phoneOrientation=" + phoneOrientation +
                ", x=" + x +
                ", y=" + y +
                ", pressure=" + pressure +
                ", area=" + area +
                ", fingerOrientation=" + fingerOrientation +
                '}';
    }

    public double getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(double phoneId) {
        this.phoneId = phoneId;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public double getDocumentId() {
        return documentId;
    }

    public void setDocumentId(double documentId) {
        this.documentId = documentId;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public double getAction() {
        return action;
    }

    public void setAction(double action) {
        this.action = action;
    }

    public double getPhoneOrientation() {
        return phoneOrientation;
    }

    public void setPhoneOrientation(double phoneOrientation) {
        this.phoneOrientation = phoneOrientation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getFingerOrientation() {
        return fingerOrientation;
    }

    public void setFingerOrientation(double fingerOrientation) {
        this.fingerOrientation = fingerOrientation;
    }
}
