package com.kannan.model;

public class Level {
    private int levelId;
    private String levelName;
    private double price;
    private int numRows;
    private int seatsInRow;
    private int reservedSeats;
    private int heldSeats;

    public Level(int levelId, String levelName, double price, int rows, int seatsInRow) {
        this.levelId = levelId;
        this.levelName = levelName;
        this.price = price;
        this.numRows = rows;
        this.seatsInRow = seatsInRow;
    }

    public int getLevelId() {
        return levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public double getPrice() {
        return price;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public boolean isSeatsAvailable(int seats) {
        return availableSeats() - seats >= 0;
    }

    public int availableSeats() {
        return totalSeats() - unAvailableSeats();
    }

    private int totalSeats() {
        return numRows * seatsInRow;
    }

    private int unAvailableSeats() {
        return reservedSeats + heldSeats;
    }

    public void incrementHold(int numSeats) {
        heldSeats += numSeats;
    }

    public void decrementHold(int numSeats) {
        heldSeats -= numSeats;
    }

    public void incrementReservation(int numSeats) {
        reservedSeats += numSeats;
    }
}
