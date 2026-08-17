import java.util.Scanner;

public class Puke {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String banner = "██████╗ ██╗   ██╗██╗  ██╗███████╗\n"
                + "██╔══██╗██║   ██║██║ ██╔╝██╔════╝\n"
                + "██████╔╝██║   ██║█████╔╝ █████╗  \n"
                + "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  \n"
                + "██║     ╚██████╔╝██║  ██╗███████╗\n"
                + "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int numTasks = 0;

        System.out.println(banner);
        System.out.println("hello i'm puke ask me anyth bro");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
                continue;
            String[] parts = line.split("\\s+", 2);
            String command = parts[0];
            String arguments = parts.length == 2 ? parts[1].trim() : "";

            if (command.equals("bye"))
                break;
            try {
                switch (command) {
                case "todo":
                    if (arguments.isEmpty())
                        throw new IllegalArgumentException("description");
                    if (numTasks == MAX_TASKS)
                        throw new IllegalStateException("full");
                    tasks[numTasks++] = new Task(arguments);
                    printTask(numTasks, tasks[numTasks - 1]);
                    break;
                case "deadline": {
                    String[] fields = arguments.split("\\s+/by\\s+", 2);
                    if (fields.length != 2 || fields[0].isBlank() || fields[1].isBlank())
                        throw new IllegalArgumentException("deadline");
                    if (numTasks == MAX_TASKS)
                        throw new IllegalStateException("full");
                    tasks[numTasks++] = new Deadline(fields[0].trim(), fields[1].trim());
                    printTask(numTasks, tasks[numTasks - 1]);
                    break;
                }
                case "event": {
                    String[] fields = arguments.split("\\s+/from\\s+|\\s+/to\\s+", 3);
                    if (fields.length != 3 || fields[0].isBlank() || fields[1].isBlank() || fields[2].isBlank())
                        throw new IllegalArgumentException("event");
                    if (numTasks == MAX_TASKS)
                        throw new IllegalStateException("full");
                    tasks[numTasks++] = new Event(fields[0].trim(), fields[1].trim(), fields[2].trim());
                    printTask(numTasks, tasks[numTasks - 1]);
                    break;
                }
                case "list":
                    if (!arguments.isEmpty())
                        throw new IllegalArgumentException("list");
                    System.out.println("> puke is fetching your list...");
                    for (int i = 0; i < numTasks; i++)
                        printTask(i + 1, tasks[i]);
                    break;
                case "mark":
                    tasks = changeStatus(tasks, numTasks, arguments, true);
                    break;
                case "unmark":
                    tasks = changeStatus(tasks, numTasks, arguments, false);
                    break;
                case "delete": {
                    int id = taskId(arguments, numTasks);
                    Task deleted = tasks[id - 1];
                    System.arraycopy(tasks, id, tasks, id - 1, numTasks - id);
                    tasks[--numTasks] = null;
                    System.out.println("> puke deleted this task: " + deleted);
                    break;
                }
                case "find":
                    if (arguments.isEmpty())
                        throw new IllegalArgumentException("keyword");
                    System.out.println("> puke found these tasks:");
                    for (int i = 0; i < numTasks; i++)
                        if (tasks[i].getDesc().toLowerCase().contains(arguments.toLowerCase()))
                            printTask(i + 1, tasks[i]);
                    break;
                default:
                    System.out.println("> puke does not understand you");
                }
            } catch (IllegalStateException e) {
                System.out.println("> puke wants you to stop adding more tasks");
            } catch (IllegalArgumentException e) {
                System.out.println("> puke wants a valid command and its required arguments");
            }
        }
        System.out.println("> puke is gonna dip bye");
        scanner.close();
    }

    private static Task[] changeStatus(Task[] tasks, int count, String input, boolean mark) {
        int id = taskId(input, count);
        if (mark)
            tasks[id - 1].mark();
        else
            tasks[id - 1].unmark();
        printTask(id, tasks[id - 1]);
        return tasks;
    }

    private static int taskId(String input, int count) {
        if (!input.matches("\\d+") || input.contains(" "))
            throw new IllegalArgumentException("task id");
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("task id");
        }
        if (id < 1 || id > count)
            throw new IllegalArgumentException("task id");
        return id;
    }

    private static void printTask(int number, Task task) {
        System.out.println(String.format("> %d. %s", number, task));
    }

}
