import java.util.*;
import java.io.*;

public class Spell{

    static Scanner fileScanner(String filename) {
        Scanner value = null;
        try {
            value = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File:" + filename + " does not exist.");
            System.exit(1);
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner input = fileScanner("./dict");
        Set<String> dict = new HashSet<String>();
        String s;
        while (input.hasNext()) {
            dict.add (s = ((input.next()).toLowerCase()));
        }

        input = fileScanner(args [0]);
        input.useDelimiter ("[^a-zA-Z]+");

        while (input.hasNext ()) {
            s = (input.next ()).toLowerCase ();
            if (!(dict.contains(s))) {
                dict.add (s);
                System.out.println(s);
            }
        }
    }
}
