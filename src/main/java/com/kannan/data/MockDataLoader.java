package com.kannan.data;

import com.kannan.model.Level;
import com.kannan.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MockDataLoader which loads all the initial data
 * @author Kannan Kuttalam
 *
 */
@Component
public class MockDataLoader {
    @Autowired
    private LevelRepository levelRepository;

    /**
     * Loads the initial data
     *
     */
    public void loadData() {
        levelRepository.add(new Level(1, "Orchestra", 100.0, 25, 50));
        levelRepository.add(new Level(2, "Main", 75.0, 20, 100));
        levelRepository.add(new Level(3, "Balcony 1", 50.0, 15, 100));
        levelRepository.add(new Level(4, "Balcony 2", 40.0, 15, 100));
    }
}
