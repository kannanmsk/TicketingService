package com.kannan.repository;

import com.kannan.model.Level;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class InMemoryLevelRepository implements LevelRepository {
    private Map<Integer, Level> levels = new HashMap<>();

    @Override
    public void add(Level level) {
        levels.put(level.getLevelId(), level);
    }

    @Override
    public Stream<Level> getAll() {
        return levels.values().stream();
    }

    @Override
    public Stream<Level> get(Optional<Integer> levelId) {
        if (isInvalidLevel(levelId)) {
            return Stream.empty();
        }
        return levelId.map(level -> Stream.of(levels.get(level))).orElseGet(() -> levels.values().stream());
    }

    @Override
    public int seatCount(Optional<Integer> levelId) {
        return this.get(levelId).map(Level::availableSeats).mapToInt(i -> i).sum();
    }

    private boolean isInvalidLevel(Optional<Integer> levelId) {
        return levelId.isPresent() && !levels.containsKey(levelId.get());
    }
}
