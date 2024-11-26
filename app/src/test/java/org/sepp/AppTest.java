/*
 * This source file was generated by the Gradle 'init' task
 */
package org.sepp;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

public class AppTest {

  /* @Test
  public void DiffTest() {
    throw new RuntimeException("Not implemented");
  } */

  @Test
  public void serializationSymmetry() {
    Random rand = new Random();
    Config config = new Config(genRandomString(rand));

    int taskCount = rand.nextInt(11) + 1; // 1-10 tasks
    for (int i = 0; i < taskCount; i++) {
      config.addTask(genRandomTask(rand));
    }

    String serialized = config.serialize();
    Config deseralized = Config.deserialize(serialized);

    assertTrue(config.serialize().equals(deseralized.serialize()));
    // FIXME: this is not equal
    assertTrue(config == deseralized);
  }

  private static Task genRandomTask(Random rand) {
    Task.TaskType type;
    if (rand.nextInt() % 2 == 0) {
      type = Task.TaskType.CUSTOM;
    } else {
      type = Task.TaskType.COMPILE;
    }

    int lineCount = rand.nextInt(50) + 1;
    List<String> script = new ArrayList<>(lineCount);

    for (int i = 0; i < lineCount; i++) {
      script.add(genRandomString(rand));
    }

    return new Task(type, script);
  }

  private static String genRandomString(Random rand) {
    // string with 10-20 characters
    int lineLen = rand.nextInt(11) + 10;
    StringBuilder sb = new StringBuilder(lineLen);
    for (int i = 0; i < lineLen; i++) {
      // generate random character with code 48-127
      char c = (char) (rand.nextInt(80) + 48);
      sb.append(c);
    }

    return sb.toString();
  }
}
