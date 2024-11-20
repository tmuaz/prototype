package com.dahirparty.prototype;

import java.util.ArrayList;

public class Config {
  public String name;
  public ArrayList<Task> tasks;

  public Config(String name) {
    this.name = name;
    this.tasks = new ArrayList<Task>();
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public void removeTask(int index) {
    if (index >= 0 && index < this.tasks.size()) {
      this.tasks.remove(index);
    }
  }

  public void runTasks(String path) {
    for (Task task : this.tasks) {
      task.runTask(path);
    }
  }
}
