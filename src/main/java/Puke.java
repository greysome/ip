import java.util.Scanner;

public class Puke {
    public static void main(String[] args) {
        String banner = "██████╗ ██╗   ██╗██╗  ██╗███████╗\n"
                      + "██╔══██╗██║   ██║██║ ██╔╝██╔════╝\n"
                      + "██████╔╝██║   ██║█████╔╝ █████╗  \n"
                      + "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  \n"
                      + "██║     ╚██████╔╝██║  ██╗███████╗\n"
                      + "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝";
        String[] tasks = new String[100];
        int num_tasks = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(banner);
        System.out.println("hello i'm puke ask me anyth bro");

        while (true) {
            String cmd = scanner.nextLine();
            if (cmd.equals("bye"))
                break;
            else if (cmd.equals("list")) {
                for (int i = 0; i < num_tasks; i++) {
                    System.out.println("> " + tasks[i]);
                }
            }
            else {
                if (num_tasks == 100) {
                    System.out.println("> puke wants you to stop adding more tasks");
                }
                else {
                    tasks[num_tasks++] = cmd;
                    System.out.println("> puke added: " + cmd);
                }
            }
        }

        System.out.println("> nvm i'm dipping lol bye");
        scanner.close();
    }
}
