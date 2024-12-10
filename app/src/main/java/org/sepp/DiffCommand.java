package org.sepp;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DiffCommand {
  public static boolean validDiffArgs(String skeletonPath, String submissionPath)
      throws IOException {
    if (!Files.exists(Paths.get(skeletonPath))) {
      System.out.println("Skeleton code path does not exist");
      return false;
    }
    if (!Files.exists(Paths.get(submissionPath))) {
      System.out.println("Student Submission path does not exist");
      return false;
    }
    return true;
  }

  public static void printDiff(String skeletonPath, String studentSubmissionPath)
      throws IOException {
    List<String> skeletonLines = Files.readAllLines(Paths.get(skeletonPath));
    List<String> studentSubmissionLines = Files.readAllLines(Paths.get(studentSubmissionPath));

    DiffRowGenerator generator =
        DiffRowGenerator.create().showInlineDiffs(false).lineNormalizer(s -> s).build();

    List<DiffRow> diffRows = generator.generateDiffRows(skeletonLines, studentSubmissionLines);

    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";

    for (DiffRow row : diffRows) {
      switch (row.getTag()) {
        case DELETE:
          System.out.println(RED + "- " + RESET + row.getOldLine());
          break;
        case INSERT:
          System.out.println(GREEN + "+ " + RESET + row.getNewLine());
          break;
        case CHANGE:
          System.out.println(RED + "- " + RESET + row.getOldLine());
          System.out.println(GREEN + "+ " + RESET + row.getNewLine());
          break;
        case EQUAL:
          System.out.println("  " + row.getNewLine());
          break;
      }
    }
  }
}
