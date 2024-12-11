package org.sepp;

import java.util.Scanner;

public class InteractiveMode {
  private Scanner scanner;
  private Context context;


  public InteractiveMode() {
    this.scanner = new Scanner(System.in);
    this.context = new Context();
  }

  public void start() {
    System.out.println("Type 'exit' to quit or 'help' for help");
    String command = scanner.nextLine().trim();
    while (true) {
      if ("exit".equalsIgnoreCase(command)) {
        if (context.saveConfig && context.config != null) {
          context.config.save(true);
        }
        return;
      } else {
        String[] arguments = command.split(" ");
        if (arguments.length == 0) {
          System.out.println("No command");
          return;
        }
        switch (arguments[0].toLowerCase()) {
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
            if(arguments.length<3){
              System.out.println("Not enough arguments");
              break;
            }
            diffArgs[0] = arguments[1];
            diffArgs[1] = arguments[2];
            Cli.diffHandler(diffArgs);
            break;
          case "load":
            if(arguments.length<2){
              System.out.println("Not enough arguments");
              break;
            }
            String filePath = arguments[1];
            Cli.loadConfig(filePath);
            break;
          case "create":
            Cli.createConfig(context);
            break;
          case "add-task":
            String[] taskstr = new String[3];
            if(arguments.length<4){
              System.out.println("Not enough arguments");
              break;
            }
            taskstr[0] = arguments[1];
            taskstr[1] = arguments[2];
            taskstr[2] = arguments[3];
            Cli.addTask(context, taskstr);
            break;
          case "title":
            if(arguments.length <2){
              System.out.println("Not enough arguments");
              break;
            }
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
      // update line
      command = scanner.nextLine().trim();
    }
  }
}
