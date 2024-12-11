package org.sepp;

import static org.junit.Assert.assertTrue;

import java.io.*;
import org.junit.Test;

public class InteractiveModeTest {

  @Test
  public void testHelp() {
    String help = "help\nexit\n";
    System.setIn(new ByteArrayInputStream(help.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();
    assertTrue(output.contains("title <title name> - Set title of configuration"));
    assertTrue(output.contains("run <directory> - Directory to run in"));
    assertTrue(output.contains("list - List config files"));
    assertTrue(
        output.contains(
            "add-task <name> <type> <path-to-shell-script> - Shell script path is not store,"
                + " only contents of the file"));
    assertTrue(output.contains("create - Create configuration"));
    assertTrue(output.contains("diff <skeleton-path> <submission-path>"));
    assertTrue(output.contains("save - Saves current config"));
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  public void testCreate() {
    String create = "create\nexit\n";
    System.setIn(new ByteArrayInputStream(create.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();
    assertTrue(output.contains("Created new config \"Untitled configuration\""));
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  public void testAddTask() {
    String addTask = "create\nadd-task TestTask Custom script1.sh\nexit\n";
    System.setIn(new ByteArrayInputStream(addTask.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();
    assertTrue(output.contains("Created new task \"TestTask\""));
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  public void testTitle() {
    String title = "create\ntitle TestTitle\nexit\n";
    System.setIn(new ByteArrayInputStream(title.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();
    assertTrue(output.contains("Set title to \"TestTitle\""));
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  public void testSave() {
    String save = "create\ntitle savetest\nsave\nexit\n";
    System.setIn(new ByteArrayInputStream(save.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();

    assertTrue(output.contains("Saved config"));
    System.setIn(System.in);
    System.setOut(System.out);
  }

  // list
  @Test
  public void testList() throws IOException {
    String list = "list\nexit\n";
    System.setIn(new ByteArrayInputStream(list.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    File testConfig = new File(Config.configsPath, "config.toml");
    testConfig.createNewFile();

    InteractiveMode mode = new InteractiveMode();
    mode.start();
    String output = out.toString();
    assertTrue(output.contains("Configs:"));
    assertTrue(output.contains(" - config"));
    testConfig.delete();
    System.setIn(System.in);
    System.setOut(System.out);
  }
  // run test
  /*@Test
  public void testRun() throws IOException {
      String randomstring= RandomStringUtils.randomAlphabetic(10).toLowerCase();

      File testScript= new File(Config.configsPath, "testScript.sh");
      try(PrintWriter write = new PrintWriter(testScript)) {
          write.println("!#/usr/bin/env bash");
          write.println("echo " + randomstring);
      }testScript.setExecutable(true);

      String runinput="create\ntitle runtest\nadd-task runtest custom " + testScript.getAbsolutePath() +"\nsave\nexit\n";
      System.setIn(new ByteArrayInputStream(runinput.getBytes()));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));
      InteractiveMode mode = new InteractiveMode();
      mode.start();
      System.setIn(System.in);
      System.setOut(System.out);

      Config loadrun =Config.load("runtest");
      try {
          loadrun.run(Config.configsPath);
      } catch (Exception e) {
          assertTrue(false);

      }
      File logdir = new File(Config.configsPath, "logs");
      File output = new File(logdir, "output.toml");
      String outputText = Files.readString(output.toPath());
      assertTrue(outputText.contains(randomstring));

      testScript.delete();
      output.delete();
      logdir.delete();

  }*/
}
