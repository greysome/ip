package puke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Runs the Puke task manager and processes user commands. */
public class Puke {
    private static final int MAX_TASKS = 100;
    private static final String DATA_FILE = "data/puke.txt";

    private final Task[] tasks = new Task[MAX_TASKS];
    private final Storage storage;
    private int numTasks;
    private boolean exitRequested;

    /** Creates a task manager that stores tasks in the default data file. */
    public Puke() {
        this(DATA_FILE);
    }

    Puke(String dataFile) {
        storage = new Storage(dataFile);
        List<Task> savedTasks = storage.load();
        for (Task task : savedTasks) {
            tasks[numTasks++] = task;
        }
    }

    public static void main(String[] args) {
        Puke puke = new Puke();
        System.out.println(getBanner());
        System.out.println("hello i'm puke ask me anyth bro");

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine() && !puke.isExitRequested()) {
                String response = puke.getResponse(scanner.nextLine());
                if (!response.isEmpty()) {
                    System.out.println(response);
                }
            }
        }
    }

    /**
     * Processes one task-management command and returns the response for display.
     *
     * @param input command entered by the user
     * @return the response generated for the command
     */
    public String getResponse(String input) {
        String line = input.trim();
        if (line.isEmpty()) {
            return "";
        }

        String[] parts = line.split("\s+", 2);
        String command = parts[0];
        String arguments = parts.length == 2 ? parts[1].trim() : "";

        try {
            return switch (command) {
                case "todo" -> addTodo(arguments);
                case "deadline" -> addDeadline(arguments);
                case "event" -> addEvent(arguments);
                case "list" -> listTasks(arguments);
                case "mark" -> changeStatus(arguments, true);
                case "unmark" -> changeStatus(arguments, false);
                case "delete" -> deleteTask(arguments);
                case "find" -> findTasks(arguments);
                case "bye" -> exit();
                default -> "> puke does not understand you";
            };
        } catch (IllegalStateException e) {
            return "> puke wants you to stop adding more tasks";
        } catch (IllegalArgumentException e) {
            return "> puke wants a valid command and its required arguments";
        }
    }

    /** Returns whether the user has asked the application to exit. */
    public boolean isExitRequested() {
        return exitRequested;
    }

    private String addTodo(String description) {
        if (description.isEmpty()) {
            throw new IllegalArgumentException("description");
        }
        return addTask(new Task(description));
    }

    private String addDeadline(String input) {
        String[] fields = input.split("\s+/by\s+", 2);
        if (fields.length != 2 || fields[0].isBlank() || fields[1].isBlank()) {
            throw new IllegalArgumentException("deadline");
        }
        return addTask(new Deadline(fields[0].trim(), formatDate(fields[1].trim())));
    }

    private String addEvent(String input) {
        String[] fields = input.split("\s+/from\s+|\s+/to\s+", 3);
        if (fields.length != 3 || fields[0].isBlank() || fields[1].isBlank() || fields[2].isBlank()) {
            throw new IllegalArgumentException("event");
        }
        return addTask(new Event(fields[0].trim(), fields[1].trim(), fields[2].trim()));
    }

    private String addTask(Task task) {
        if (numTasks == MAX_TASKS) {
            throw new IllegalStateException("full");
        }
        tasks[numTasks++] = task;
        storage.save(tasks, numTasks);
        return formatTask(numTasks, task);
    }

    private String listTasks(String arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException("list");
        }
        StringBuilder response = new StringBuilder("> puke is fetching your list...");
        for (int i = 0; i < numTasks; i++) {
            response.append(System.lineSeparator()).append(formatTask(i + 1, tasks[i]));
        }
        return response.toString();
    }

    private String changeStatus(String input, boolean mark) {
        int id = taskId(input);
        if (mark) {
            tasks[id - 1].mark();
        } else {
            tasks[id - 1].unmark();
        }
        storage.save(tasks, numTasks);
        return formatTask(id, tasks[id - 1]);
    }

    private String deleteTask(String input) {
        int id = taskId(input);
        Task deleted = tasks[id - 1];
        System.arraycopy(tasks, id, tasks, id - 1, numTasks - id);
        tasks[--numTasks] = null;
        storage.save(tasks, numTasks);
        return "> puke deleted this task: " + deleted;
    }

    private String findTasks(String keyword) {
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("keyword");
        }
        StringBuilder response = new StringBuilder("> puke found these tasks:");
        for (int i = 0; i < numTasks; i++) {
            if (tasks[i].matchesKeyword(keyword)) {
                response.append(System.lineSeparator()).append(formatTask(i + 1, tasks[i]));
            }
        }
        return response.toString();
    }

    private String exit() {
        exitRequested = true;
        return "> puke is gonna dip bye";
    }

    private int taskId(String input) {
        if (!input.matches("\\d+") || input.contains(" ")) {
            throw new IllegalArgumentException("task id");
        }
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("task id");
        }
        if (id < 1 || id > numTasks) {
            throw new IllegalArgumentException("task id");
        }
        return id;
    }

    private String formatDate(String input) {
        try {
            return LocalDate.parse(input).toString();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("date");
        }
    }

    private String formatTask(int number, Task task) {
        return String.format("> %d. %s", number, task);
    }

    private static String getBanner() {
        return "██████╗ ██╗   ██╗██╗  ██╗███████╗\n"
                + "██╔══██╗██║   ██║██║ ██╔╝██╔════╝\n"
                + "██████╔╝██║   ██║█████╔╝ █████╗  \n"
                + "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  \n"
                + "██║     ╚██████╔╝██║  ██╗███████╗\n"
                + "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝";
    }
}
