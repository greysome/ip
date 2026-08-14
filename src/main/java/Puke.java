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
        int numTasks = 0;

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

            else if (cmd.equals("todo")) {
                if (numTasks == 100) {
                    System.out.println("> puke wants you to stop adding more tasks");
                    continue;
                }

                StringBuilder descSb = new StringBuilder();
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    descSb.append(token);
                    descSb.append(" ");
                }
                descSb.deleteCharAt(descSb.length() - 1);
                String descStr = descSb.toString();
                tasks[numTasks++] = new Task(descStr);
                System.out.println(String.format("> %d. %s", numTasks, tasks[numTasks - 1].toString()));
            }

            else if (cmd.equals("deadline")) {
                if (numTasks == 100) {
                    System.out.println("> puke wants you to stop adding more tasks");
                    continue;
                }

                StringBuilder descSb = new StringBuilder();
                StringBuilder bySb = new StringBuilder();
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (token.equals("/by"))
                        break;
                    descSb.append(token);
                    descSb.append(" ");
                }
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    bySb.append(token);
                    bySb.append(" ");
                }
                descSb.deleteCharAt(descSb.length() - 1);
                bySb.deleteCharAt(bySb.length() - 1);
                String descStr = descSb.toString();
                String byStr = bySb.toString();
                tasks[numTasks++] = new Deadline(descStr, byStr);
                System.out.println(String.format("> %d. %s", numTasks, tasks[numTasks - 1].toString()));
            }

            else if (cmd.equals("event")) {
                if (numTasks == 100) {
                    System.out.println("> puke wants you to stop adding more tasks");
                    continue;
                }

                StringBuilder descSb = new StringBuilder();
                StringBuilder fromSb = new StringBuilder();
                StringBuilder toSb = new StringBuilder();
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (token.equals("/from"))
                        break;
                    descSb.append(token);
                    descSb.append(" ");
                }
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (token.equals("/to"))
                        break;
                    fromSb.append(token);
                    fromSb.append(" ");
                }
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    toSb.append(token);
                    toSb.append(" ");
                }
                descSb.deleteCharAt(descSb.length() - 1);
                fromSb.deleteCharAt(fromSb.length() - 1);
                toSb.deleteCharAt(toSb.length() - 1);
                String descStr = descSb.toString();
                String fromStr = fromSb.toString();
                String toStr = toSb.toString();
                tasks[numTasks++] = new Event(descStr, fromStr, toStr);
                System.out.println(String.format("> %d. %s", numTasks, tasks[numTasks - 1].toString()));
            }

            else if (cmd.equals("list")) {
                System.out.println("> puke is fetching your list...");
                for (int i = 0; i < numTasks; i++) {
                    Task task = tasks[i];
                    System.out.println(String.format("> %d. %s", i+1, task.toString()));
                }
            }

            else if (cmd.equals("mark")) {
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                String idStr = tokenizer.nextToken();
                int id;
                try {
                    id = Integer.parseInt(idStr);
                }
                catch (NumberFormatException e) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                if (id >= numTasks + 1 || id <= 0) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                Task task = tasks[id-1];
                task.mark();
                System.out.println(String.format("> %d. %s", id, task.toString()));
            }

            else if (cmd.equals("unmark")) {
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                String idStr = tokenizer.nextToken();
                int id;
                try {
                    id = Integer.parseInt(idStr);
                }
                catch (NumberFormatException e) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                if (id >= numTasks + 1 || id <= 0) {
                    System.out.println("> puke wants a valid task id");
                    continue;
                }
                Task task = tasks[id-1];
                task.unmark();
                System.out.println(String.format("> %d. %s", id, task.toString()));
            }

            else {
                System.out.println("> puke does not understand you");
            }
        }

        System.out.println("> puke is gonna dip bye");
        scanner.close();
    }
}
