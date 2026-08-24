package puke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path path;

    public Storage(String fileName) {
        path = Path.of(fileName);
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(path)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                String[] fields = line.split("\\|", -1);
                if (fields.length < 3) {
                    continue;
                }
                Task task = TaskFactory.fromStorageFields(fields);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException | RuntimeException e) {
            return new ArrayList<>();
        }
        return tasks;
    }

    public void save(Task[] tasks, int count) {
        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            List<String> lines = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Task task = tasks[i];
                String done = task.getStatusIcon().equals("X") ? "1" : "0";
                if (task instanceof Deadline deadline) {
                    lines.add(String.join("|", "D", done, deadline.getDesc(), deadline.getDeadline()));
                } else if (task instanceof Event event) {
                    lines.add(String.join("|", "E", done, event.getDesc(), event.getFrom(), event.getTo()));
                } else {
                    lines.add(String.join("|", "T", done, task.getDesc()));
                }
            }
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("> puke could not save your tasks");
        }
    }
}
