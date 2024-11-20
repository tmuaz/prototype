package com.dahirparty.prototype;

public class Task {
  enum TaskType {
    COMPILE,
    CUSTOM
  }

  public TaskType type;
  public String name;
  public String script;

  public Task(TaskType type, String script) {
    this.type = type;
    this.script = script;
    this.name = "Unnamed Task";
  }

  public Task(TaskType type, String script, String name) {
    this.type = type;
    this.script = script;
    this.name = name;
  }

  public String runTask(String path) {
    // TODO: Implement run task
    return "";
  }
  ;
}
