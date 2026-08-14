import java.util.Scanner;
import java.util.StringTokenizer;

public class Puke {
    public static void main(String[] args) {
        String banner = "██████╗ ██╗   ██╗██╗  ██╗███████╗\n"
                      + "██╔══██╗██║   ██║██║ ██╔╝██╔════╝\n"
                      + "██████╔╝██║   ██║█████╔╝ █████╗  \n"
                      + "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  \n"
                      + "██║     ╚██████╔╝██║  ██╗███████╗\n"
                      + "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int num_tasks = 0;

        System.out.println(banner);
        System.out.println("hello i'm puke ask me anyth bro");

        while (true) {
            String line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            if (!tokenizer.hasMoreTokens()) {
                continue;
            }

            String cmd = tokenizer.nextToken();
            if (cmd.equals("bye")) {
                break;
            }

            else if (cmd.equals("list")) {
                System.out.println("> puke says:");
                for (int i = 0; i < num_tasks; i++) {
                    Task task = tasks[i];
                    System.out.println(String.format("> %d. [%s] %s", i+1, task.getStatusIcon(), task.getDesc()));
                }
            }

            else if (cmd.equals("mark")) {
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("> puke says you're missing a task id!");
                    continue;
                }
                String id_str = tokenizer.nextToken();
                int id;
                try {
                    id = Integer.parseInt(id_str);
                }
                catch (NumberFormatException e) {
                    System.out.println("> puke says that's not a number!");
                    continue;
                }
                if (id >= num_tasks + 1 || id <= 0) {
                    System.out.println("> puke says that's an invalid task id");
                    continue;
                }
                Task task = tasks[id-1];
                task.mark();
                System.out.println(String.format("> puke marked your task [%d]. [X] %s", id, task.getDesc()));
            }

            else if (cmd.equals("unmark")) {
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("> puke says you're missing a task id!");
                    continue;
                }
                String id_str = tokenizer.nextToken();
                int id;
                try {
                    id = Integer.parseInt(id_str);
                }
                catch (NumberFormatException e) {
                    System.out.println("> puke says that's not a number!");
                    continue;
                }
                if (id >= num_tasks + 1 || id <= 0) {
                    System.out.println("> puke says that's an invalid task id");
                    continue;
                }
                Task task = tasks[id-1];
                task.unmark();
                System.out.println(String.format("> puke unmarked your task [%d]. [ ] %s", id, task.getDesc()));
            }

            else {
                if (num_tasks == 100) {
                    System.out.println("> puke wants you to stop adding more tasks");
                }
                else {
                    tasks[num_tasks++] = new Task(line);
                    System.out.println("> puke added: " + line);
                }
            }
        }

        System.out.println("> nvm i'm dipping lol bye");
        scanner.close();
    }
}
