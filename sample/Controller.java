package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    DictionaryManagement dim = new DictionaryManagement();

    @FXML
    private Button search;
    @FXML
    private TextField textField;
    @FXML
    private AnchorPane addBox;
    @FXML
    private AnchorPane removeBox;
    @FXML
    private AnchorPane editBox;
    @FXML
    private TextField addEngWord;
    @FXML
    private TextField addVietWord;
    @FXML
    private TextField word_to_delete;
    @FXML
    private TextField word_to_edit;
    @FXML
    private TextField editNewWord;
    @FXML
    private TextField editNewMean;
    @FXML
    private TextArea meaning;
    @FXML
    private ListView listView;

    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dim.insertFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tra từ
    @FXML
    public void searchingWord(ActionEvent event) {
        String text = textField.getText();
        if (event.getSource() == search) {
            meaning.setText(dim.dictionaryLookup(text));
        }
    }

    // tìm kiếm từ
    @FXML
    public void handleKeyReleased() throws IOException {
        list.clear();
        listView.getItems().clear();
        String text = textField.getText();
        boolean disableButton = text.isEmpty() || text.trim().isEmpty();
        search.setDisable(disableButton);
        if (!(text.isEmpty() && text.trim().isEmpty())) {
            int size = dim.wordList.size();
            for (int i = 0; i < size; i++) {
                if (!dim.wordList.get(i).getWord_target().startsWith(text.toLowerCase())) {
                    continue;
                }

                String s = dim.wordList.get(i).getWord_target();
                list.add(s);
            }

            if (list == null) return;
            listView.getItems().setAll(list);
        } else meaning.setText("");
    }

    // bắt sự kiện cho selectedItem trong listview
    public void handleMouseClick(MouseEvent mouseEvent) {
        String wordSelected = (String) listView.getSelectionModel().getSelectedItem();
        textField.setText(wordSelected);
        meaning.setText(dim.dictionaryLookup(wordSelected));
    }

    // add a new word
    @FXML
    public void openAddBox(ActionEvent event) {
        addBox.setVisible(true);
    }

    @FXML
    public void closeAddBox(ActionEvent event) {
        addBox.setVisible(false);
        addEngWord.setText("");
        addVietWord.setText("");
    }

    @FXML
    public void saveAddBox(ActionEvent event) {
        String eng = addEngWord.getText();
        String viet = addVietWord.getText();
        if (eng.isEmpty() || viet.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("WARNING!");
            alert.setContentText("Bạn chưa nhập đủ thông tin!");
            alert.show();
        } else {
            if (dim.dictionaryLookup(eng).equals("not found")) {
                addBox.setVisible(false);
                dim.addWord(eng, viet);
                dim.dictionaryExportToFile();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Done!");
                alert.setContentText("Thêm từ mới thành công!");
                alert.show();
                addEngWord.setText("");
                addVietWord.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("WARNING");
                alert.setContentText("Từ bạn nhập đã có sẵn!");
                alert.show();
            }
        }
    }

    // remove a word
    @FXML
    public void openRemoveWord(ActionEvent event) {
        removeBox.setVisible(true);
    }

    @FXML
    public void removeWordBox(ActionEvent event) {
        String s = word_to_delete.getText();
        if (dim.dictionaryLookup(s).equals("not found")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("WARNING!");
            alert.setContentText("Từ bạn nhập không có sẵn!");
            alert.show();
        } else {
            for (Word w : dim.wordList) {
                if (s.equalsIgnoreCase(w.getWord_target())) {
                    dim.wordList.remove(w);
                    break;
                }
            }
            dim.dictionaryExportToFile();
            removeBox.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Done!");
            alert.setContentText("Xóa từ thành công!");
            alert.show();
            word_to_delete.setText("");
        }
    }

    @FXML
    public void closeRemoveBox(ActionEvent event) {
        removeBox.setVisible(false);
        word_to_delete.setText("");
    }

    // edit a word
    @FXML
    public void openEditBox(ActionEvent event) {
        editBox.setVisible(true);
    }

    @FXML
    public void saveEditBox(ActionEvent event) {
        String word = word_to_edit.getText();
        String newWord = editNewWord.getText();
        String newMean = editNewMean.getText();
        if (word.isEmpty() || newWord.isEmpty() || newMean.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("WARNING!");
            alert.setContentText("Bạn chưa nhập đủ thông tin!");
            alert.show();
        } else {
            if (dim.dictionaryLookup(word).equals("not found")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("WARNING!");
                alert.setContentText("Từ bạn nhập không có sẵn!");
                alert.show();
            } else {
                editBox.setVisible(false);
                for (Word w : dim.wordList) {
                    if (word.equalsIgnoreCase(w.getWord_target())) {
                        dim.wordList.remove(w);
                        break;
                    }
                }
                dim.wordList.add(new Word(newWord, newMean));
                dim.dictionaryExportToFile();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Done!");
                alert.setContentText("Sửa từ thành công!");
                alert.show();
                word_to_edit.setText("");
                editNewWord.setText("");
                editNewMean.setText("");
            }
        }
    }

    @FXML
    public void closeEditBox(ActionEvent event) {
        editBox.setVisible(false);
        word_to_edit.setText("");
        editNewWord.setText("");
        editNewMean.setText("");
    }

    //pronunciation function
    //sources code: geeksforgeeks
    @FXML
    public void textSpeech(ActionEvent event) {
        String text = textField.getText();

        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}