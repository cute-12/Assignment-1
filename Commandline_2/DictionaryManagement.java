import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public DictionaryManagement() {}

    Dictionary dict = new Dictionary();

    public void insertFromFile() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("dictionaries.txt"));
            String contentline = bufferedReader.readLine();
            while (contentline != null) {
                String target = contentline.substring(0, contentline.indexOf(" "));
                String explain = contentline.substring(contentline.indexOf(" "), contentline.length()).trim();
                dict.wordList.add(new Word(target, explain));
                contentline = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            System.out.println(keywords + ": " + mean);
        } else {
            System.out.println("not found");
        }
    }

    public void dictionaryUpdate() {
        System.out.println("1 - Add");
        System.out.println("2 - Edit");
        System.out.println("3 - Delete");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: ");
        int choose  = sc.nextInt();
        switch(choose){
            case 1: addWord();
            break;
            case 2: editWord();
            break;
            case 3: deleteWord();
            break;
            default: dictionaryUpdate();
            break;
        }
    }

    public void addWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter English words: ");
        String target = sc.nextLine();
        System.out.println("Enter Vietnamese meaning: ");
        String explain = sc.nextLine();
        dict.wordList.add(new Word(target, explain));
        System.out.println("Done!");
    }

    public void editWord() {
        System.out.println("Enter word which you want to edit: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();

        for (Word w : dict.wordList) {
            if (word.equalsIgnoreCase(w.getWord_target())) {
                System.out.println("Enter new word to replace this word: ");
                String newWord = sc.nextLine();
                w.setWord_target(newWord);
                System.out.println("Done!");
                return;
            } else if (word.equalsIgnoreCase(w.getWord_explain())) {
                System.out.println("Enter new word to replace this word: ");
                String newWord = sc.nextLine();
                w.setWord_explain(newWord);
                System.out.println("Done!");
                return;
            }
        }

        System.out.println(word + " was not found.");
    }

    public void deleteWord() {
        System.out.println("Enter word which you want to delete: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();

        for (Word w : dict.wordList) {
            if (word.equalsIgnoreCase(w.getWord_target())) {
                dict.wordList.remove(w);
                System.out.println("Done!");
                return;
            } else if (word.equalsIgnoreCase(w.getWord_explain())) {
                dict.wordList.remove(w);
                System.out.println("Done!");
                return;
            }
        }

        System.out.println(word + " was not found.");
    }

    public void dictionaryExportToFile() {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("dictionaries.txt");
            file.delete();
            File newFile = new File("dictionaries.txt");
            if (!newFile.exists()) {
                file.createNewFile();
            }

            bufferedWriter = new BufferedWriter(new FileWriter("dictionaries.txt", true));
            for (Word w : dict.wordList) {
                bufferedWriter.write(w.getWord_target() + "    " + w.getWord_explain() + "\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
