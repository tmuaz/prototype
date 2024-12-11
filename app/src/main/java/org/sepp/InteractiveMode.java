package org.sepp;

import java.util.Scanner;

public class InteractiveMode {
  private Scanner scanner;
  private Context context;

  private boolean isOn;

  public InteractiveMode() {
    this.scanner = new Scanner(System.in);
    this.isOn = true;
  }

  public void start() {
    System.out.println("Type 'exit' to quit or 'help' for help");
    String command = scanner.nextLine().trim();
    while (isOn) {
      if ("exit".equalsIgnoreCase(command)) {
        if (context.saveConfig && context.config != null) {
          context.config.save(true);
        }
        isOn = false;
      } else {
        String[] arguments = command.split(" ");
        if (arguments.length == 0) {
          System.out.println("No command");
          return;
        }
        switch (command.toLowerCase()) {
          case "help":
            System.out.println("title <title name> - Set title of configuration");
            System.out.println("run <directory> - Directory to run in");
            System.out.println("list - List config files");
            System.out.println(
                "add-task <name> <type> <path-to-shell-script> - Shell script path is not store,"
                    + " only contents of the file");
            System.out.println("load <config> - Configuration to load");
            System.out.println("create - Create configuration");
            System.out.println("diff <skeleton-path> <submission-path>");
            break;
          case "list":
            Cli.list();
            break;
          case "diff":
            String[] diffArgs = new String[2];
            diffArgs[0] = arguments[1];
            diffArgs[1] = arguments[2];
            Cli.diffHandler(diffArgs);
            break;
          case "load":
            String filePath = arguments[1];
            Cli.loadConfig(filePath);
          case "create":
            Cli.createConfig(context);
          case "add-task":
            String[] taskstr = new String[3];
            taskstr[0] = arguments[1];
            taskstr[1] = arguments[2];
            taskstr[2] = arguments[3];
            Cli.addTask(context, taskstr);
            break;
          case "title":
            String title = arguments[1];
            Cli.setTitle(context, title);
            break;
          case "run":
            String directory = arguments[1];
            Cli.setTitle(context, directory);
            break;
          default:
            System.out.println("Unrecognised command. Type 'help' for commands");
            break;
        }
      }
    }
  }
}
