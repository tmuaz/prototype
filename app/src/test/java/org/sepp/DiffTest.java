package org.sepp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class DiffTest {
  private String skeltonpath = "OptionalMonad.hs";
  private String studentpath = "OptionalMonad_1.hs";
  private String Invalidpath = "invalidpath";

  @Test
  public void validpathTest() throws IOException {
    assertTrue(Files.exists(Paths.get(skeltonpath)));
    assertTrue(Files.exists(Paths.get(studentpath)));
    assertTrue(DiffCommand.validDiffArgs(skeltonpath, studentpath));
  }

  @Test
  public void invalidpathTest() throws IOException {
    assertFalse(DiffCommand.validDiffArgs(skeltonpath, Invalidpath));
    assertFalse(DiffCommand.validDiffArgs(Invalidpath, studentpath));
  }

  @Test
  public void testDiff() throws IOException {
    System.out.println("diff:");
    DiffCommand.printDiff(skeltonpath, studentpath);
  }
}
