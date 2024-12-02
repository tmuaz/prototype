package org.sepp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.tomlj.*;

public class Task {
  public enum TaskType {
    COMPILE,
    CUSTOM
  }

  public String name;
  public TaskType type;
  public List<String> script;

  public Task(TaskType type, List<String> script) {
    this.name = "Unnamed task";
    this.type = type;
    this.script = script;
  }

  public Task(String name, TaskType type, List<String> script) {
    this.name = name;
    this.type = type;
    this.script = script;
  }

  public ArrayList<String> run(File folder) throws Exception {
    // FIXME: this is rather inefficient, will need to refactor
    String sh = String.join("\n", script);
    ProcessBuilder pbuilder = new ProcessBuilder("sh", "-c", sh);
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

    sb.append("scripts = [");

    for (String scriptStr : script) {
      sb.append(String.format("\"%s\",", Config.EscapeToml(scriptStr)));
    }

    sb.append("]");

    return sb.toString();
  }

  static Task fromTomlTable(TomlTable input) {
    TaskType type;

    if (input.getString("type").equals("compile")) {
      type = TaskType.COMPILE;
    } else if (input.getString("type").equals("custom")) {
      type = TaskType.CUSTOM;
    } else { // some issue
      throw new RuntimeException("Couldn't resolve task type.");
    }

    var scripts = new ArrayList<String>();
    var tomlArray = input.getArray("scripts");

    for (int i = 0; i < tomlArray.size(); i++) {
      scripts.add(tomlArray.getString(i));
    }

    return new Task(input.getString("name"), type, scripts);
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

    int size = this.script.size();
    if (size != task.script.size()) {
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (!this.script.get(i).equals(task.script.get(i))) {
        return false;
      }
    }

    return true;
  }
}
