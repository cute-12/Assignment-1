package sample;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryManagement {
    public DictionaryManagement() {}

    public List<Word> wordList = new ArrayList<>();

    public  List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    // đọc dữ liệu từ file
    public void insertFromFile() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("src/sample/dictionaries.txt"));
            String contentline = bufferedReader.readLine();
            while (contentline != null) {
                String target = contentline.substring(0, contentline.indexOf(" "));
                String explain = contentline.substring(contentline.indexOf(" "), contentline.length()).trim();
                wordList.add(new Word(target, explain));
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

    // tra từ
    public String dictionaryLookup(String s) {
        boolean haveFound = false;
        String mean = "";
        for (Word w : wordList) {
            if (w.getWord_target().equalsIgnoreCase(s)) {
                haveFound = true;
                mean = w.getWord_explain();
                break;
            }
        }

        if (haveFound) {
            return mean;
        } else {
            return "not found";
        }
    }

    //thêm từ
    public void addWord(String s1, String s2) {
        wordList.add(new Word(s1, s2));
    }

    //ghi dữ liệu vào file
    public void dictionaryExportToFile() {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("src/sample/dictionaries.txt");
            file.delete();
            File newFile = new File("src/sample/dictionaries.txt");
            if (!newFile.exists()) {
                file.createNewFile();
            }

            bufferedWriter = new BufferedWriter(new FileWriter("src/sample/dictionaries.txt", true));
            for (Word w : wordList) {
                bufferedWriter.write(w.getWord_target() + "     " + w.getWord_explain() + "\n");
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
