package ziphil.controller

import groovy.transform.CompileStatic
import java.util.concurrent.Callable
import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import ziphil.custom.ExtensionFilter
import ziphil.custom.Measurement
import ziphil.custom.RichTextLanguage
import ziphil.custom.UtilityStage
import ziphil.module.akrantiain.Akrantiain
import ziphil.module.akrantiain.AkrantiainException
import ziphil.module.akrantiain.AkrantiainParseException
import ziphil.module.akrantiain.AkrantiainWarning
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class AkrantiainExecutorController extends Controller<Void> {

  private static final String RESOURCE_PATH = "resource/fxml/controller/akrantiain_executor.fxml"
  private static final String TITLE = "akrantiain"
  private static final Double DEFAULT_WIDTH = Measurement.rpx(480)
  private static final Double DEFAULT_HEIGHT = -1

  @FXML private TextField $snojPathControl
  @FXML private TextField $inputControl
  @FXML private TextField $outputControl
  @FXML private TextArea $logControl
  @FXML private CheckBox $usesDictionaryAkrantiainControl
  private Akrantiain $akrantiain = null
  private Akrantiain $dictionaryAkrantiain = null
  private FileStringChooserController.Result $result = null

  public AkrantiainExecutorController(UtilityStage<? super Void> stage) {
    super(stage)
    loadResource(RESOURCE_PATH, TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT, false)
  }

  @FXML
  private void initialize() {
    setupSnojPathControl()
    setupInputControl()
  }

  public void prepare(Akrantiain dictionaryAkrantiain) {
    $dictionaryAkrantiain = dictionaryAkrantiain
    setupUsesDictionaryAkrantiainControl()
  }

  @FXML
  private void execute() {
    Boolean usesDictionarySnoj = $usesDictionaryAkrantiainControl.isSelected()
    Akrantiain akrantiain = (usesDictionarySnoj) ? $dictionaryAkrantiain : $akrantiain
    if (akrantiain != null) {
      try {
        String output = akrantiain.convert($inputControl.getText())
        $outputControl.setText(output)
        $logControl.setText("")
      } catch (AkrantiainException exception) {
        $outputControl.setText("")
        $logControl.setText(exception.getFullMessage())
      }
    } else {
      $outputControl.setText("")
    }
  }

  @FXML
  private void openSnoj() {
    UtilityStage<FileStringChooserController.Result> nextStage = createStage()
    FileStringChooserController controller = FileStringChooserController.new(nextStage)
    ExtensionFilter filter = ExtensionFilter.new("snojファイル", "snoj")
    controller.prepare(filter, RichTextLanguage.AKRANTIAIN, $result)
    nextStage.showAndWait()
    if (nextStage.isCommitted()) {
      $result = nextStage.getResult()
      if ($result.isFileSelected()) {
        File file = $result.getFile()
        if (file != null) {
          $snojPathControl.setText(file.getAbsolutePath())
          $akrantiain = Akrantiain.new()
          try {
            $akrantiain.load(file)
            $logControl.setText(createWarningText($akrantiain.getWarnings()))
          } catch (AkrantiainParseException exception) {
            $logControl.setText(exception.getFullMessage())
            $akrantiain = null
          }
        } else {
          $snojPathControl.setText("")
          $logControl.setText("")
          $akrantiain = null
        }
      } else {
        String source = $result.getString()
        $snojPathControl.setText("[テキスト]")
        $akrantiain = Akrantiain.new()
        try {
          $akrantiain.load(source)
          $logControl.setText(createWarningText($akrantiain.getWarnings()))
        } catch (AkrantiainParseException exception) {
          $logControl.setText(exception.getFullMessage())
          $akrantiain = null
        }
      }
    }
  }

  private String createWarningText(List<AkrantiainWarning> warnings) {
    StringBuilder warningText = StringBuilder.new()
    for (Int i = 0 ; i < warnings.size() ; i ++) {
      warningText.append(warnings[i].getFullMessage())
      if (i < warnings.size() - 1) {
        warningText.append("\n")
      }
    }
    return warningText.toString()
  }

  private void setupSnojPathControl() {
    Callable<BooleanClass> function = (Callable){
      return $usesDictionaryAkrantiainControl.isSelected()
    }
    BooleanBinding binding = Bindings.createBooleanBinding(function, $usesDictionaryAkrantiainControl.selectedProperty()) 
    $snojPathControl.disableProperty().bind(binding)
  }

  private void setupInputControl() {
    $inputControl.sceneProperty().addListener() { ObservableValue<? extends Scene> observableValue, Scene oldValue, Scene newValue ->
      if (oldValue == null && newValue != null) {
        $inputControl.requestFocus()
      }
    }
  }

  private void setupUsesDictionaryAkrantiainControl() {
    if ($dictionaryAkrantiain != null) {
      $usesDictionaryAkrantiainControl.setDisable(false)
    }
  }

}