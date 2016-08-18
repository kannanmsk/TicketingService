package com.kannan.repository;

import com.kannan.model.Level;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Level Repository Interface for having stored all the level detail information
 * @author Kannan Kuttalam
 *
 */
public interface LevelRepository {

    /**
     * Adding levels to the repository
     *
     * @param level level to be added to the repository
     */
    void add(Level level);

    /**
     * Adding levels to the repository
     *
     * @return the stream of all levels in the repository
     */
    Stream<Level> getAll();

    /**
     * Get the stream of level information based on the level Id
     *
     * @param levelId level Id of the level required
     * @return the stream of level information for the level Id
     */
    Stream<Level> get(Optional<Integer> levelId);

    /**
     * get the number of seats on a particular level
     *
     * @param levelId level Id to get the seat count
     * @return the number of seats on the provided level
     */
    int seatCount(Optional<Integer> levelId);
}
