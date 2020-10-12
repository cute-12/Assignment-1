import java.util.Scanner;

public class DictionaryManagement {
    public DictionaryManagement() {

    }

    Dictionary dict = new Dictionary();
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Số lượng từ vựng là: ");
        int number_word = sc.nextInt();
        for(int i = 0; i < number_word; i++) {
            System.out.println("Nhập từ tiếng Anh: ");
            String target = sc.next();
            System.out.println("Nhập giải thích tiếng Việt: ");
            String explain = sc.next();
            dict.wordList.add(new Word(target, explain));
        }

    }
}
