import java.util.Scanner;

public class Puke {
    public static void main(String[] args) {
        String banner = "██████╗ ██╗   ██╗██╗  ██╗███████╗\n"
                      + "██╔══██╗██║   ██║██║ ██╔╝██╔════╝\n"
                      + "██████╔╝██║   ██║█████╔╝ █████╗  \n"
                      + "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  \n"
                      + "██║     ╚██████╔╝██║  ██╗███████╗\n"
                      + "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝";
        Scanner scanner = new Scanner(System.in);

        System.out.println(banner);
        System.out.println("hello i'm puke ask me anyth bro");

        while (true) {
            String cmd = scanner.nextLine();
            if (cmd.equals("bye"))
              break;
            else
              System.out.println(cmd);
        }

        System.out.println("nvm i'm dipping lol bye");
        scanner.close();
    }
}
