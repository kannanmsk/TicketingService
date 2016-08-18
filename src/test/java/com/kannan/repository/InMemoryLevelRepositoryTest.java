package com.kannan.repository;

import com.kannan.model.Level;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class InMemoryLevelRepositoryTest {

    private LevelRepository inMemoryLevelRepository;
    private Level level1;
    private Level level2;

    @Before
    public void setUp() throws Exception {
        inMemoryLevelRepository = new InMemoryLevelRepository();
        level1 = new Level(1, "Balcony", 100.0, 5, 20);
        level2 = new Level(2, "Main", 50.0, 10, 100);
        inMemoryLevelRepository.add(level1);
        inMemoryLevelRepository.add(level2);
    }

    @Test
    public void shouldGetAllLevels() throws Exception {
        Stream<Level> allLevels = inMemoryLevelRepository.getAll();

        assertEquals(2, allLevels.count());
    }

    @Test
    public void shouldGetLevelBasedOnLevelId() throws Exception {
        Stream<Level> levels = inMemoryLevelRepository.get(Optional.of(1));

        List<Level> levelList = levels.collect(Collectors.toList());

        assertEquals(1, levelList.size());
        assertEquals(level1, levelList.get(0));
    }

    @Test
    public void shouldGetEmptyStreamForInvalidLevel() throws Exception {
        Stream<Level> levels = inMemoryLevelRepository.get(Optional.of(0));

        assertEquals(0, levels.count());
    }

    @Test
    public void shouldGetAllLevelsForEmptyLevel() throws Exception {
        Stream<Level> levels = inMemoryLevelRepository.get(Optional.empty());

        assertEquals(2, levels.count());
    }

    @Test
    public void shouldCountSeatsInLevel() throws Exception {
        int seats = inMemoryLevelRepository.seatCount(Optional.of(1));

        assertEquals(100, seats);
    }

    @Test
    public void shouldCountAllSeatsWhenLevelNotSpecified() throws Exception {
        int seats = inMemoryLevelRepository.seatCount(Optional.empty());

        assertEquals(1100, seats);
    }

}