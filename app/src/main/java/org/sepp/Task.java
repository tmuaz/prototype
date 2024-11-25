package org.sepp;

import java.io.File;
import java.util.List;
import org.tomlj.*;
import java.util.ArrayList;

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

  public String run(File folder) {
    throw new RuntimeException("Not implemented");
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

    sb.append(String.format("name = \"%s\"\n", name));


    if (type == TaskType.CUSTOM) {
      sb.append("type = \"custom\"\n");
    } else {
      sb.append("type = \"compile\"\n");
    }

    sb.append("scripts = [");
    

    for (String scriptStr: script) {
      sb.append("\"\"\"")
        .append(scriptStr.replace("\\", "\\\\")
                         .replace("\"", "\\\""))
        .append("\"\"\",");
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

    for (int i=0; i<tomlArray.size(); i++) {
      scripts.add(tomlArray.getString(i));
    }

    return new Task(input.getString("name"), type, scripts);
  }
}
