package puke;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void deadlineFormatsDate() {
        assertEquals("[D][ ] submit report (by: Aug 24 2026)",
                new Deadline("submit report", "2026-08-24").toString());
    }
}
