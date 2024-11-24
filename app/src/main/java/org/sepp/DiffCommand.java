package org.sepp;
import java.nio.file.Files;
import java.nio.file.Paths;
import  java.util.List;
import  java.io.IOException;
import com.github.difflib.text.DiffRowGenerator;
import  com.github.difflib.text.DiffRow;


public class DiffCommand {
    public static boolean validDiffArgs(String[] args)throws IOException {
        if (args.length < 3 || args.length > 4) {
            System.out.println("./gradlew run --args = \"diff <skeletonPath> <submissionPath> <outputPath>\"");
            return false;



        }
        if(!Files.exists(Paths.get(args[1]))) {
            System.out.println("Skeleton code path does not exist");
            return false;
        }
        if(!Files.exists(Paths.get(args[2]))) {
            System.out.println("Student Submission path does not exist");
            return false;

        }
        return true;



    }
    public static void genDiffFile(String skeletonPath,String studentSubmissionPath,String outputPath) throws IOException {
        List<String> skeletonLines = Files.readAllLines(Paths.get(skeletonPath));
        List<String> studentSubmissionLines = Files.readAllLines(Paths.get(studentSubmissionPath));

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .oldTag(f -> "(-)")
                .newTag(f -> "(+)")
                .build();

        List<DiffRow> diffRows = generator.generateDiffRows(skeletonLines, studentSubmissionLines);
        StringBuilder output = new StringBuilder();
        output.append("|Skeleton             |Student Submission   |\n");
        output.append("|---------------------|---------------------|\n");
        for (DiffRow diffRow : diffRows) {
            output.append("|").append(diffRow.getOldLine()).append("|").append(diffRow.getNewLine()).append("|\n");
        }
        Files.writeString(Paths.get(outputPath),output.toString());
        System.out.println("Diff written to " + outputPath);
    }
    public static void handleDiffArgs(String[] args) throws IOException {
        if (!validDiffArgs(args)) {
            System.out.println("./gradlew run --args = \"diff <skeletonPath> <submissionPath> <outputPath>\"");
            return;

        }
        String skeletonPath = args[1];
        String studentSubmissionPath = args[2];
        String outputPath;
        if (args.length == 4) {
            outputPath = args[3];
        }else {
            outputPath ="DiffOutput.txt";
        }
        genDiffFile(skeletonPath,studentSubmissionPath,outputPath);
    }


}