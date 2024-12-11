package org.sepp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tomlj.*;

public class Task {
  public enum TaskType {
    COMPILE,
    CUSTOM;

    @Override
    public String toString() {
      if (this == COMPILE) return "Compile";
      return "Custom";
    }

    public static TaskType fromString(String str) {
      if (str.toLowerCase().startsWith("compile")) {
        return COMPILE;
      }
      return CUSTOM;
    }
  }

  private static Logger LOGGER = Logger.getLogger("App.Task");

  @Override
  public String toString() {
    return "name: " + this.name + "\ntype: " + this.type + "\nsh: " + this.script;
  }

  public String name;
  public TaskType type;
  public String script;

  public Task(TaskType type, String script) {
    this.name = "Unnamed task";
    this.type = type;
    this.script = script;
  }

  public Task(String name, TaskType type, String script) {
    this.name = name;
    this.type = type;
    this.script = script;
  }

  public ArrayList<String> run(File folder) throws Exception {
    ProcessBuilder pbuilder = new ProcessBuilder("sh", "-c", script);
    pbuilder.directory(folder);
    var process = pbuilder.start(); // IOException
    ArrayList<String> output = new ArrayList<>();

    if (type == TaskType.COMPILE) {
      int code = process.waitFor(); // InterruptedException
      output.add(String.valueOf(code));
      return output;
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    reader.lines().forEach((str) -> output.add(str));
    reader.close();

    return output;
  }

  public static Task.TaskType parseType(String input) {
    input = input.toLowerCase();
    if (input.startsWith("co")) {
      return TaskType.COMPILE;
    }
    return TaskType.CUSTOM;
  }

  public String serialize() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format("name = \"%s\"\n", Config.EscapeToml(name)));

    if (type == TaskType.CUSTOM) {
      sb.append("type = \"custom\"\n");
    } else {
      sb.append("type = \"compile\"\n");
    }

    sb.append("script = ");
    sb.append(String.format("\"%s\"", Config.EscapeToml(script)));

    return sb.toString();
  }

  static Task fromTomlTable(TomlTable input) {
    TaskType type;

    if (input.getString("type").equals("compile")) {
      type = TaskType.COMPILE;
    } else if (input.getString("type").equals("custom")) {
      type = TaskType.CUSTOM;
    } else { // some issue
      LOGGER.log(Level.SEVERE, "Error while trying to parse Task");
      throw new RuntimeException("Couldn't resolve task type.");
    }

    var script = input.getString("script");

    return new Task(input.getString("name"), type, script);
  }

  // -- testing

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Task task = (Task) obj;
    if (!this.name.equals(task.name)) {
      return false;
    }
    ;

    if (this.type != task.type) {
      return false;
    }

    if (!this.script.equals(task.script)) {
      return false;
    }

    return true;
  }
}
