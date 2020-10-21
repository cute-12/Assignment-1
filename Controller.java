package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    DictionaryManagement dim = new DictionaryManagement();

    @FXML
    private Button search;
    @FXML
    private TextField type;
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
    private Label meaning;
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

    public void searchingWord(ActionEvent event) {
        String text = type.getText();
        listView.getItems().clear();
        if(event.getSource() == search) {
            meaning.setText(dim.dictionaryLookup(text));
            int size = dim.wordList.size();
            for (int i = 0; i < size; i++) {
                if (!dim.wordList.get(i).getWord_target().startsWith(text.toLowerCase())) {
                    continue;
                }
                String s = dim.wordList.get(i).getWord_target();
                list.add(s);
            }
            listView.setItems(list);
        }
    }

    public void openAddBox(ActionEvent event) {
        addBox.setVisible(true);
    }

    public void closeAddBox(ActionEvent event) {
        addBox.setVisible(false);
        addEngWord.setText("");
        addVietWord.setText("");
    }

    public void saveAddBox(ActionEvent event) {
        addBox.setVisible(false);
        String eng = addEngWord.getText();
        String viet = addVietWord.getText();
        if (dim.dictionaryLookup(eng).equalsIgnoreCase("not found")) {
            dim.addWord(eng, viet);
            dim.dictionaryExportToFile();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("New word was successfully added!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("The word is vailable!");
            alert.show();
        }
        addEngWord.setText("");
        addVietWord.setText("");
    }

    public void openRemoveWord(ActionEvent event) {
        removeBox.setVisible(true);
    }

    public void removeWordBox(ActionEvent event) {
        removeBox.setVisible(false);
        String s = word_to_delete.getText();
        if (dim.dictionaryLookup(s).equalsIgnoreCase("not found")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("The word is not available!");
            alert.show();
        } else {
            for (Word w : dim.wordList) {
                if (s.equalsIgnoreCase(w.getWord_target())) {
                    dim.wordList.remove(w);
                    break;
                }
            }
            dim.dictionaryExportToFile();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("The word was successfull removed!");
            alert.show();
        }
    }

    public void closeRemoveBox(ActionEvent event) {
        removeBox.setVisible(false);
    }

    public void openEditBox(ActionEvent event) {
        editBox.setVisible(true);
    }
    public void saveEditBox(ActionEvent event) {
        editBox.setVisible(false);
        String word = word_to_edit.getText();
        String newWord = editNewWord.getText();
        String newMean = editNewMean.getText();
        if (dim.dictionaryLookup(word).equalsIgnoreCase("not found")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("The word is not available!");
            alert.show();
        } else {
            for (Word w : dim.wordList) {
                if (word.equalsIgnoreCase(w.getWord_target())) {
                    dim.wordList.remove(w);
                    break;
                }
            }
            dim.wordList.add(new Word(newWord, newMean));
            dim.dictionaryExportToFile();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Message");
            alert.setContentText("The word was successfull edited!");
            alert.show();
        }
        word_to_delete.setText("");
        editNewWord.setText("");
        editNewMean.setText("");
    }

    public void closeEditBox(ActionEvent event) {
        editBox.setVisible(false);
    }

    public void textSpeech(ActionEvent event) {
        String text = type.getText();
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