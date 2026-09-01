package puke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    void todoIsOpenByDefault() {
        assertEquals("[T][ ] buy milk", new Task("buy milk").toString());
    }

    @Test
    void markedTaskShowsDoneStatus() {
        Task task = new Task("buy milk");
        task.mark();
        assertEquals("[T][X] buy milk", task.toString());
    }

    @Test
    void keywordSearchMatchesDescriptionCaseInsensitively() {
        assertTrue(new Task("Read Book").matchesKeyword("book"));
    }

    @Test
    void deadlineFormatsDate() {
        assertEquals("[D][ ] submit report (by: Aug 24 2026)",
                new Deadline("submit report", "2026-08-24").toString());
    }

    @Test
    void taskFactoryAcceptsIndividualStorageFields() {
        assertEquals("[T][ ] buy milk",
                TaskFactory.fromStorageFields("T", "0", "buy milk").toString());
    }

    @Test
    void responseProcessesTaskCommands() throws IOException {
        Path file = Files.createTempFile("puke", ".txt");
        try {
            Puke puke = new Puke(file.toString());
            assertEquals("> 1. [T][ ] buy milk", puke.getResponse("todo buy milk"));
            assertTrue(puke.getResponse("list").contains("[T][ ] buy milk"));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
