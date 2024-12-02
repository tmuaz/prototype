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

  public void run(String path) throws Exception{
    int taskCount = tasks.size();
    File folder = new File(path);
    if ( !folder.exists()){
      throw new Exception("Run directory does not exist");
    }
    if (folder.isFile() ) {
      throw new Exception("Run directory is not a folder");
    }

    File folders[] = folder.listFiles(File::isDirectory);
    for (int i = 0; i < folders.length; i++) {
      ArrayList<String>[] outputs = new ArrayList[taskCount];
      for (int j = 0; j < taskCount; j++) {
        Task task = tasks.get(j);
        try{
          outputs[j] = task.run(folders[i]);
          System.out.println("Task: "+j + "\nname: " + task.name);
        outputs[j].forEach((str)->System.out.println("\t"+str));
        } catch (Exception e){
          System.err.println("Exception error while running task #"+j +" name: " +task.name + "\n" + e.getMessage());
        }
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
    sb.append(String.format("name = \"%s\"\n\n", EscapeToml(name)));

    for (var task : tasks) {
      sb.append("[[tasks]]\n").append(task.serialize()).append("\n\n");
    }

    return sb.toString();
  }

  public static Config deserialize(String text) {
    var res = Toml.parse(text);

    if (res.hasErrors()) {
      for (TomlParseError error : res.errors()) {
        System.err.println(error.toString());
        throw new RuntimeException(String.format("Toml parsing errors when parsing:\n=====%s\n=====", text));
      }
    }

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
      // System.err.println("Not same class");
      return false;
    }

    Config config = (Config) obj;

    int size = this.tasks.size();
    if (size != config.tasks.size()) {
      // System.err.println("Not the same number of tasks");
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (!this.tasks.get(i).equals(config.tasks.get(i))) {
        // System.err.println("Task " + i + " not the same");
        return false;
      }
    }

    return true;
  }

  static protected String EscapeToml(String input) {
    var sb = new StringBuilder();

    for (int i=0; i<input.length(); i++) {
      switch (input.charAt(i)) {
      case '\\': sb.append("\\\\"); break;
      case '\"': sb.append("\\\""); break;
      case '\u0000': sb.append("\\u0000"); break;
      case '\u0001': sb.append("\\u0001"); break;
      case '\u0002': sb.append("\\u0002"); break;
      case '\u0003': sb.append("\\u0003"); break;
      case '\u0004': sb.append("\\u0004"); break;
      case '\u0005': sb.append("\\u0005"); break;
      case '\u0006': sb.append("\\u0006"); break;
      case '\u0007': sb.append("\\u0007"); break;
      case '\u0008': sb.append("\\u0008"); break;
      case '\u0009': sb.append("\\u0009"); break;
      case '\n': sb.append("\\n"); break; // for some reason won't compile if i just use \u000a
                                                // this is equivalent
      case '\u000b': sb.append("\\u000b"); break;
      case '\u000c': sb.append("\\u000c"); break;
      case '\r': sb.append("\\r"); break; // same
      case '\u000e': sb.append("\\u000e"); break;
      case '\u000f': sb.append("\\u000f"); break;
      case '\u0010': sb.append("\\u0010"); break;
      case '\u0011': sb.append("\\u0011"); break;
      case '\u0012': sb.append("\\u0012"); break;
      case '\u0013': sb.append("\\u0013"); break;
      case '\u0014': sb.append("\\u0014"); break;
      case '\u0015': sb.append("\\u0015"); break;
      case '\u0016': sb.append("\\u0016"); break;
      case '\u0017': sb.append("\\u0017"); break;
      case '\u0018': sb.append("\\u0018"); break;
      case '\u0019': sb.append("\\u0019"); break;
      case '\u001a': sb.append("\\u001a"); break;
      case '\u001b': sb.append("\\u001b"); break;
      case '\u001c': sb.append("\\u001c"); break;
      case '\u001d': sb.append("\\u001d"); break;
      case '\u001e': sb.append("\\u001e"); break;
      case '\u001f': sb.append("\\u001f"); break;
      case '\u007f': sb.append("\\u007f"); break;
      default: sb.append(input.charAt(i));
      }
    }

    return sb.toString();
  }

}
