import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public List<Word> map = new ArrayList<>();

    public List<Word> getMap() {
        return map;
    }

    public void setMap(List<Word> map) {
        this.map = map;
    }

    public Dictionary(List<Word> map) {
        this.map = map;
    }

    public void returnAllWord(List<Word> map) {
        for (Word word : map) {
            System.out.println(word.getWord_target());
            System.out.println(word.getWord_explain());
        }
    }

    public Dictionary() {

    }
}
