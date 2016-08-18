package com.kannan.model;

/**
 * Level model to have each level information
 * @author Kannan Kuttalam
 *
 */
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

    /**
     * get the level Id
     *
     * @return level Id of the level
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * get the level name
     *
     * @return the level name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * get the ticket price for the level
     *
     * @return the price for the level
     */
    public double getPrice() {
        return price;
    }

    /**
     * get the number of rows in the level
     *
     * @return the number of rows in the level
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * get the number of seats in the row
     *
     * @return the number of seats in a row
     */
    public int getSeatsInRow() {
        return seatsInRow;
    }

    /**
     * checks whether the required number of seats are available or not
     *
     * @param seats
     *          number of seats required
     * @return whether required number of seats are available
     */
    public boolean isSeatsAvailable(int seats) {
        return availableSeats() - seats >= 0;
    }

    /**
     * returns available seats
     *
     * @return the available seats
     */
    public int availableSeats() {
        return totalSeats() - unAvailableSeats();
    }

    /**
     * gets the number of held seats
     *
     * @return the number of held seats
     */
    public int getHeldSeats() {
        return heldSeats;
    }

    /**
     * returns total number of seats
     *
     * @return the total number of seats
     */
    private int totalSeats() {
        return numRows * seatsInRow;
    }

    /**
     * returns number of unavailable seats
     *
     * @return the number of unavailable seats
     */
    private int unAvailableSeats() {
        return reservedSeats + heldSeats;
    }

    /**
     * increments the number of held seats
     * @param numSeats
     *          number of seats required to hold
     */
    public void incrementHold(int numSeats) {
        heldSeats += numSeats;
    }

    /**
     * decrements the number of held seats
     * @param numSeats
     *          number of seats required to release hold
     */
    public void decrementHold(int numSeats) {
        heldSeats -= numSeats;
    }

    /**
     * increments the number of reserved seats
     * @param numSeats
     *          number of seats required to reserve
     */
    public void incrementReservation(int numSeats) {
        reservedSeats += numSeats;
    }
}
