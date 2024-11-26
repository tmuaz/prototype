package org.sepp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.tomlj.*;

public class Config {
  public String name;
  public ArrayList<Task> tasks;

  public Config() {
    this.name = "Untitled configuration";
    this.tasks = new ArrayList<Task>();
  }

  public Config(String name) {
    this.name = name;
    this.tasks = new ArrayList<Task>();
  }

  public void run(String path) {
    int taskCount = tasks.size();
    File folder = new File(path);
    if (folder.isFile() || !folder.exists()) {
      // TODO: print a message
      return;
    }

    File folders[] = folder.listFiles(File::isDirectory);
    for (int i = 0; i < folders.length; i++) {
      String[] outputs = new String[taskCount];
      for (int j = 0; j < taskCount; j++) {
        outputs[j] = tasks.get(j).run(folders[i]);
      }

      // TODO: create toml file
    }
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public void removeTask(int index) {
    if (index >= 0 && index < tasks.size()) tasks.remove(index);
  }

  public boolean removeTask(Task task) {
    return tasks.remove(task);
  }

  // file handling

  public String serialize() {
    StringBuilder sb = new StringBuilder();

    sb.append("# Automatically generated automated assignment tasks config file\n");
    sb.append(String.format("name = \"%s\"\n\n", name));

    for (var task : tasks) {
      sb.append("[[tasks]]\n").append(task.serialize()).append("\n\n");
    }

    return sb.toString();
  }

  public static Config deserialize(String text) {
    var res = Toml.parse(text);

    var newconfig = new Config(res.getString("name"));

    var tasklist = res.getArray("tasks");

    for (int i = 0; i < tasklist.size(); i++) {
      newconfig.addTask(Task.fromTomlTable(tasklist.getTable(i)));
    }

    return newconfig;
  }

  public void storeConfig(String path) throws IOException {
    FileWriter writer = new FileWriter(path);
    writer.write(this.serialize());
    writer.close();
  }

  public static Config loadFromFile(File configFile) throws IOException {
    var res = Toml.parse(configFile.toPath());

    if (res.hasErrors()) {
      throw res.errors().get(0);
    }

    var newconfig = new Config(res.getString("name"));

    var tasklist = res.getArray("tasks");

    for (int i = 0; i < tasklist.size(); i++) {
      newconfig.addTask(Task.fromTomlTable(tasklist.getTable(i)));
    }

    return newconfig;
  }

  // -- testing
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      System.err.println("Not same class");
      return false;
    }

    Config config = (Config) obj;

    int size = this.tasks.size();
    if (size != config.tasks.size()) {
      System.err.println("Not the same number of tasks");
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (this.tasks.get(i) != config.tasks.get(i)) {
        System.err.println("Task " + i + " not the same");
        return false;
      }
    }

    return true;
  }
}
