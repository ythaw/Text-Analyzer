import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * TextAnalyzer: a simple command-line tool similar to Unix `wc`.
 *
 * Rules:
 *   java TextAnalyzer <file_path>
 *   java TextAnalyzer -l <file_path>   (lines only)
 *   java TextAnalyzer -w <file_path>   (words only)
 *   java TextAnalyzer -c <file_path>   (chars only)
 *   java TextAnalyzer -l -w -c <file_path>
 */

public class TextAnalyzer{
    private static final String RULES = "Rules:\n" +
            "java TextAnalyzer <file_path>\n" +
            "java TextAnalyzer -l <file_path>   (lines only)\n" +
            "java TextAnalyzer -w <file_path>   (words only)\n" +
            "java TextAnalyzer -c <file_path>   (chars only)\n" +
            "java TextAnalyzer -l -w -c <file_path>\n";
    private static class Counts {
        long lines = 0;
        long words = 0;
        long chars = 0;
    }

    public static void main(String[] args){
        try{
            Command parsed = fileRead(args);
            Counts counts = countFile(parsed.filePath);

            // if no flags, print all
            boolean printLines = parsed.flags.isEmpty() || parsed.flags.contains("-l");
            boolean printWords = parsed.flags.isEmpty() || parsed.flags.contains("-w");
            boolean printChars = parsed.flags.isEmpty() || parsed.flags.contains("-c");

            StringBuilder out = new StringBuilder();
            out.append("File: ").append(parsed.filePath).append("\n");
            if (printLines) out.append("Lines: ").append(counts.lines).append("\n");
            if (printWords) out.append("Words: ").append(counts.words).append("\n");
            if (printChars) out.append("Chars: ").append(counts.chars).append("\n");

            System.out.print(out.toString());
        } catch(IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.print(RULES);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
    private static class Command {
        Set<String> flags = new HashSet<>();
        String filePath;
    }

    private static Command fileRead(String[] args){
        if(args == null || args.length == 0){
            throw new IllegalArgumentException("Missing arguments");
        }
        Command cmd = new Command();
        // file path is the last argument
        cmd.filePath = args[args.length-1];

        for(int i = 0; i < args.length -1; i++){
            String a = args[i].trim();
            if(!a.equals("-l") && !a.equals("-w") && !a.equals("-c")){
                throw new IllegalArgumentException("Unknown flag: " + a);
                
            }
            cmd.flags.add(a);
        }
        if(cmd.filePath.startsWith("-")) {
            throw new IllegalArgumentException("Missing file path");
        }
        return cmd;
    }

    private static Counts countFile(String filePath) throws IOException {
        Counts c = new Counts();
        try (BufferedReader br = new BufferedReader (new FileReader(filePath))) {
            String line;
            boolean LineRead = false;

            while((line = br.readLine()) != null) {
                LineRead = true;
                c.lines++;

                String trimmed = line.trim();
                if (!trimmed.isEmpty()){
                    String[] tokens = trimmed.split("\\s+");
                    c.words += tokens.length;
                }
                c.chars += line.length();
                c.chars += 1; // for new line character

            }
        }
        return c;
    }
}