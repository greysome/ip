package puke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class StorageTest {
    @Test
    void savesAndLoadsTasks() throws Exception {
        Path file = Files.createTempFile("puke", ".txt");
        try {
            Storage storage = new Storage(file.toString());
            Task[] tasks = {new Task("read book"), new Deadline("submit report", "2026-08-24")};
            tasks[0].mark();
            storage.save(tasks, tasks.length);
            var loaded = storage.load();
            assertEquals(2, loaded.size());
            assertEquals("[T][X] read book", loaded.get(0).toString());
            assertEquals("[D][ ] submit report (by: Aug 24 2026)", loaded.get(1).toString());
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
