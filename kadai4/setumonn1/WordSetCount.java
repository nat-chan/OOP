import java.util.*;

class WordSetCount {
    public static void main(String[] args) {
        Set<String> set = new HashSet<String>(); // HashSet は集合なので、重複は取り除かれる
        Scanner input = new Scanner(System.in);
        int count = 0;
        while (input.hasNext()) {
            set.add((input.next())); // トークン(単語)をひとつ読む．読んだ結果は使わない．
        }
        System.out.println ((set.size()) + " words.");
    }
}
