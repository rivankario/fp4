package com.hacktiv8.finalproject4.BookingList;

public class BookingLists {
    //BookingID
    //BusName
    //TravelTime
    //DepartureTime
    //ArriveTime
    //Seat
    //Total

    private String BookingID;
    private String BusName;
    private String TravelTime;
    private String DepartureTime;
    private String ArriveTime;
    private String Seat;
    private String Total;

    public BookingLists(String bookingID, String busName, String travelTime, String departureTime, String arriveTime, String seat, String total) {
        BookingID = bookingID;
        BusName = busName;
        TravelTime = travelTime;
        DepartureTime = departureTime;
        ArriveTime = arriveTime;
        Seat = seat;
        Total = total;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String busName) {
        BusName = busName;
    }

    public String getTravelTime() {
        return TravelTime;
    }

    public void setTravelTime(String travelTime) {
        TravelTime = travelTime;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(String arriveTime) {
        ArriveTime = arriveTime;
    }

    public String getSeat() {
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
