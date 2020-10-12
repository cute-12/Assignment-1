import java.util.List;

public class DictionaryCommandLine {
    public DictionaryManagement dim = new DictionaryManagement();
    public DictionaryCommandLine() {

    }


    public void showAllWords() {
        List<Word> list = dim.dict.getWordList();
        System.out.println("No     | English     | Vietnamese");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "     | " + list.get(i).getWord_target() + "     | " + list.get(i).getWord_explain());
        }

    }

    public void dictionaryBasic() {
        dim.insertFromCommandline();
        showAllWords();
    }

    public static void main(String[] args) {
        DictionaryCommandLine dcl = new DictionaryCommandLine();
        dcl.dictionaryBasic();
    }
}