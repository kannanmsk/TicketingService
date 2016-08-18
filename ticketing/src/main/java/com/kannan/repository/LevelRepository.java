package com.kannan.repository;

import com.kannan.model.Level;

import java.util.Optional;
import java.util.stream.Stream;

public interface LevelRepository {

    void add(Level level);

    Stream<Level> getAll();

    Stream<Level> get(Optional<Integer> levelId);
}
