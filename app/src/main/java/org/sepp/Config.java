package org.sepp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
  // NOTE: @Dahir, help me figure out how to serialize and deserialize
  private String serialize() {
    throw new RuntimeException("Not implemented");
  }

  public void storeConfig(String path) throws IOException {
    FileWriter writer = new FileWriter(path);
    writer.write(this.serialize());
    writer.close();
  }

  public static Config loadFromFile(File configFile) {
    throw new RuntimeException("Not implemented");
  }
}
