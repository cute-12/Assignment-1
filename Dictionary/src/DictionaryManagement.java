import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public DictionaryManagement() {}

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

    public void insertFromFile() throws IOException {
        File file = new File("dictionaries.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String target = line.substring(0, line.indexOf(" ")).trim();
            String explain = line.substring(line.indexOf(" "), line.length()).trim();
            dict.wordList.add(new Word(target, explain));
        }
        sc.close();
    }

    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to look up: ");
        String keywords = scanner.nextLine();

        boolean find = false;
        String mean = "";
        for (Word w : dict.wordList) {
            if (w.getWord_target().equalsIgnoreCase(keywords)) {
                find = true;
                mean = w.getWord_explain();
                break;
            }
        }

        if (find) {
            System.out.println("The meaning of the " + keywords + " is " + mean);
        } else {
            System.out.println("not found");
        }
    }
}
