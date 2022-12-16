package com.hacktiv8.finalproject4.BusList;

public class Buses {
    private String busName;
    private String busType;
    private String travelTime;
    private String startPoint;
    private String endPoint;
    private String ratingPoint;
    private String rating;
    private String seatAvailable;
    private String bedAvailable;
    private String windowSeats;
    private String price;
    private String fromLocation;
    private String toLocation;
    private String date;

    public Buses(String busName, String busType, String travelTime, String startPoint,
                 String endPoint, String ratingPoint, String rating,
                 String seatAvailable, String bedAvailable, String windowSeats,
                 String price, String fromLocation, String toLocation, String date) {
        this.busName = busName;
        this.busType = busType;
        this.travelTime = travelTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.ratingPoint = ratingPoint;
        this.rating = rating;
        this.seatAvailable = seatAvailable;
        this.bedAvailable = bedAvailable;
        this.windowSeats = windowSeats;
        this.price = price;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(String ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(String seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public String getBedAvailable() {
        return bedAvailable;
    }

    public void setBedAvailable(String bedAvailable) {
        this.bedAvailable = bedAvailable;
    }

    public String getWindowSeats() {
        return windowSeats;
    }

    public void setWindowSeats(String windowSeats) {
        this.windowSeats = windowSeats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
