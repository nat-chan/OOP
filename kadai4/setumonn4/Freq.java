import java.util.*;
import java.io.*;

public class Freq
{
    static Map<String, Integer> map = new TreeMap<String, Integer>();
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
    public static void main(String args[])
    {
        Scanner input = fileScanner (args [0]);
        String s;
        input.useDelimiter ("[^a-zA-Z]+");
        while (input.hasNext ()){
            if (map.containsKey (s = ((input.next ()).toLowerCase ()))) {
                map.put (s,(1 + (map.get (s))));
            }
            else {
                map.put (s,1);
            }
        }
        for (String k : map.keySet()){
            System.out.println(map.get (k) + ":" + k);
        }
    }
}
