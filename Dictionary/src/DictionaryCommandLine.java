import java.io.IOException;
import java.util.List;

public class DictionaryCommandLine {
    public DictionaryCommandLine() {}
    public DictionaryManagement dim = new DictionaryManagement();

    public void showAllWords() {
        List<Word> list = dim.dict.getWordList();
        System.out.println("|-------------------------------------------|");
        System.out.println("|     No     |    English   |   Vietnamese  |");
        System.out.println("|-------------------------------------------|");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(" " + (i + 1) + "        | " + list.get(i).getWord_target());
            for(int j = 0; j < 16 - list.get(i).getWord_target().length(); j++) {
                System.out.print(" ");
            }
            System.out.print("| " + list.get(i).getWord_explain());
            System.out.println();
        }
    }

    public void dictionaryBasic() {
        dim.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAvanced() throws IOException {
        dim.insertFromFile();
        showAllWords();
        dim.dictionaryLookup();
    }

    public static void main(String[] args) throws IOException {
        DictionaryCommandLine dcl = new DictionaryCommandLine();
        dcl.dictionaryAvanced();
    }
}