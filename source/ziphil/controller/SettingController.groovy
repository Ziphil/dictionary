package ziphil.controller

import groovy.transform.CompileStatic
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager
import ziphil.custom.ClickType
import ziphil.custom.FontRenderingType
import ziphil.custom.IntegerUnaryOperator
import ziphil.custom.Measurement
import ziphil.custom.ScriptEngineFactoryCell
import ziphil.custom.SwitchButton
import ziphil.custom.UtilityStage
import ziphil.module.CustomBindings
import ziphil.module.Setting
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class SettingController extends Controller<BooleanClass> {

  private static final String RESOURCE_PATH = "resource/fxml/controller/setting.fxml"
  private static final String TITLE = "環境設定"
  private static final Double DEFAULT_WIDTH = Measurement.rpx(580)
  private static final Double DEFAULT_HEIGHT = Measurement.rpx(400)

  @FXML private ComboBox<String> $contentFontFamilyControl
  @FXML private Spinner<IntegerClass> $contentFontSizeControl
  @FXML private CheckBox $usesSystemContentFontFamilyControl
  @FXML private CheckBox $usesDefaultContentFontSizeControl
  @FXML private ComboBox<String> $editorFontFamilyControl
  @FXML private Spinner<IntegerClass> $editorFontSizeControl
  @FXML private CheckBox $usesSystemEditorFontFamilyControl
  @FXML private CheckBox $usesDefaultEditorFontSizeControl
  @FXML private Spinner<IntegerClass> $lineSpacingControl
  @FXML private TextField $variationMarkerControl
  @FXML private TextField $relationMarkerControl
  @FXML private SwitchButton $showsSeparatorControl
  @FXML private SwitchButton $colorsBadgedWordControl
  @FXML private SwitchButton $modifiesPunctuationControl
  @FXML private SwitchButton $keepsMainWindowOnTopControl
  @FXML private SwitchButton $keepsEditorOnTopControl
  @FXML private Spinner<IntegerClass> $mainWindowWidthControl
  @FXML private Spinner<IntegerClass> $mainWindowHeightControl
  @FXML private CheckBox $preservesMainWindowSizeControl
  @FXML private ComboBox<String> $systemFontFamilyControl
  @FXML private CheckBox $usesDefaultSystemFontFamilyControl
  @FXML private ComboBox<FontRenderingType> $fontRenderingTypeControl
  @FXML private SwitchButton $ignoresAccentControl
  @FXML private SwitchButton $ignoresCaseControl
  @FXML private SwitchButton $searchesPrefixControl
  @FXML private ComboBox<ScriptEngineFactory> $scriptControl
  @FXML private TextField $defaultGitMessageControl
  @FXML private SwitchButton $gitsCommitOnSaveControl
  @FXML private SwitchButton $ignoresDuplicateNumberControl
  @FXML private SwitchButton $showsNumberControl
  @FXML private SwitchButton $showsVariationControl
  @FXML private SwitchButton $asksMutualRelationControl
  @FXML private SwitchButton $asksDuplicateNameControl
  @FXML private SwitchButton $savesAutomaticallyControl
  @FXML private SwitchButton $persistsPanesControl
  @FXML private Spinner<IntegerClass> $separativeIntervalControl
  @FXML private Spinner<IntegerClass> $maxHistorySizeControl
  @FXML private ComboBox<ClickType> $linkClickTypeControl
  @FXML private GridPane $registeredDictionaryPane
  @FXML private List<TextField> $registeredDictionaryPathControls = ArrayList.new()
  @FXML private List<TextField> $registeredDictionaryNameControls = ArrayList.new()

  public SettingController(UtilityStage<? super BooleanClass> nextStage) {
    super(nextStage)
    loadResource(RESOURCE_PATH, TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT, false)
  }

  @FXML
  private void initialize() {
    setupScriptControl()
    setupRegisteredDictionaryPane()
    setupFontFamilyControls()
    setupIntegerControls()
    bindFontControlProperties()
    bindMainWindowSizeControlProperties()
    applySettings()
  }

  private void applySettings() {
    Setting setting = Setting.getInstance()
    String contentFontFamily = setting.getContentFontFamily()
    Int contentFontSize = setting.getContentFontSize()
    String editorFontFamily = setting.getEditorFontFamily()
    Int editorFontSize = setting.getEditorFontSize()
    Int lineSpacing = setting.getLineSpacing()
    String variationMarker = setting.getVariationMarker()
    String relationMarker = setting.getRelationMarker()
    Boolean showsSeparator = setting.getShowsSeparator()
    Boolean colorsBadgedWord = setting.getColorsBadgedWord()
    Boolean modifiesPunctuation = setting.getModifiesPunctuation()
    Boolean keepsMainWindowOnTop = setting.getKeepsMainWindowOnTop()
    Boolean keepsEditorOnTop = setting.getKeepsEditorOnTop()
    Int mainWindowWidth = setting.getMainWindowWidth()
    Int mainWindowHeight = setting.getMainWindowHeight()
    Boolean preservesMainWindowSize = setting.getPreservesMainWindowSize()
    String systemFontFamily = setting.getSystemFontFamily()
    FontRenderingType fontRenderingType = setting.getFontRenderingType()
    Boolean ignoresAccent = setting.getIgnoresAccent()
    Boolean ignoresCase = setting.getIgnoresCase()
    Boolean searchesPrefix = setting.getSearchesPrefix()
    String scriptName = setting.getScriptName()
    String defaultGitMessage = setting.getDefaultGitMessage()
    Boolean gitsCommitOnSave = setting.getGitsCommitOnSave()
    Boolean ignoresDuplicateNumber = setting.getIgnoresDuplicateNumber()
    Boolean showsNumber = setting.getShowsNumber()
    Boolean showsVariation = setting.getShowsVariation()
    Boolean asksMutualRelation = setting.getAsksMutualRelation()
    Boolean asksDuplicateName = setting.getAsksDuplicateName()
    Boolean savesAutomatically = setting.getSavesAutomatically()
    Boolean persistsPanes = setting.getPersistsPanes()
    Int separativeInterval = setting.getSeparativeInterval()
    Int maxHistorySize = setting.getMaxHistorySize()
    ClickType linkClickType = setting.getLinkClickType()
    List<String> registeredDictionaryPaths = setting.getRegisteredDictionaryPaths()
    List<String> registeredDictionaryNames = setting.getRegisteredDictionaryNames()
    if (contentFontFamily != null) {
      $contentFontFamilyControl.getSelectionModel().select(contentFontFamily)
    } else {
      $usesSystemContentFontFamilyControl.setSelected(true)
    }
    if (contentFontSize > 0) {
      $contentFontSizeControl.getValueFactory().setValue(contentFontSize)
    } else {
      $usesDefaultContentFontSizeControl.setSelected(true)
    }
    if (editorFontFamily != null) {
      $editorFontFamilyControl.getSelectionModel().select(editorFontFamily)
    } else {
      $usesSystemEditorFontFamilyControl.setSelected(true)
    }
    if (editorFontSize > 0) {
      $editorFontSizeControl.getValueFactory().setValue(editorFontSize)
    } else {
      $usesDefaultEditorFontSizeControl.setSelected(true)
    }
    $lineSpacingControl.getValueFactory().setValue(lineSpacing)
    $variationMarkerControl.setText(variationMarker)
    $relationMarkerControl.setText(relationMarker)
    $showsSeparatorControl.setSelected(showsSeparator)
    $colorsBadgedWordControl.setSelected(colorsBadgedWord)
    $modifiesPunctuationControl.setSelected(modifiesPunctuation)
    $keepsMainWindowOnTopControl.setSelected(keepsMainWindowOnTop)
    $keepsEditorOnTopControl.setSelected(keepsEditorOnTop)
    $mainWindowWidthControl.getValueFactory().setValue(mainWindowWidth)
    $mainWindowHeightControl.getValueFactory().setValue(mainWindowHeight)
    $preservesMainWindowSizeControl.setSelected(preservesMainWindowSize)
    if (systemFontFamily != null) {
      $systemFontFamilyControl.getSelectionModel().select(systemFontFamily)
    } else {
      $usesDefaultSystemFontFamilyControl.setSelected(true)
    }
    $fontRenderingTypeControl.getSelectionModel().select(fontRenderingType)
    $ignoresAccentControl.setSelected(ignoresAccent)
    $ignoresCaseControl.setSelected(ignoresCase)
    $searchesPrefixControl.setSelected(searchesPrefix)
    for (ScriptEngineFactory scriptEngineFactory : $scriptControl.getItems()) {
      if (scriptEngineFactory.getNames().contains(scriptName)) {
        $scriptControl.getSelectionModel().select(scriptEngineFactory)
      }
    }
    $defaultGitMessageControl.setText(defaultGitMessage)
    $gitsCommitOnSaveControl.setSelected(gitsCommitOnSave)
    $ignoresDuplicateNumberControl.setSelected(ignoresDuplicateNumber)
    $showsNumberControl.setSelected(showsNumber)
    $showsVariationControl.setSelected(showsVariation)
    $asksMutualRelationControl.setSelected(asksMutualRelation)
    $asksDuplicateNameControl.setSelected(asksDuplicateName)
    $savesAutomaticallyControl.setSelected(savesAutomatically)
    $persistsPanesControl.setSelected(persistsPanes)
    $separativeIntervalControl.getValueFactory().setValue(separativeInterval)
    $maxHistorySizeControl.getValueFactory().setValue(maxHistorySize)
    $linkClickTypeControl.getSelectionModel().select(linkClickType)
    for (Int i = 0 ; i < $registeredDictionaryPathControls.size() ; i ++) {
      $registeredDictionaryPathControls[i].setText(registeredDictionaryPaths[i])
      $registeredDictionaryNameControls[i].setText(registeredDictionaryNames[i])
    }
  }

  private void updateSettings() {
    Setting setting = Setting.getInstance()
    Boolean usesSystemContentFontFamily = $usesSystemContentFontFamilyControl.isSelected()
    Boolean usesDefaultContentFontSize = $usesDefaultContentFontSizeControl.isSelected()
    String contentFontFamily = (usesSystemContentFontFamily) ? null : $contentFontFamilyControl.getValue()
    Int contentFontSize = (usesDefaultContentFontSize) ? -1 : $contentFontSizeControl.getValue()
    Boolean usesSystemEditorFontFamily = $usesSystemEditorFontFamilyControl.isSelected()
    Boolean usesDefaultEditorFontSize = $usesDefaultEditorFontSizeControl.isSelected()
    String editorFontFamily = (usesSystemEditorFontFamily) ? null : $editorFontFamilyControl.getValue()
    Int editorFontSize = (usesDefaultEditorFontSize) ? -1 : $editorFontSizeControl.getValue()
    Int lineSpacing = $lineSpacingControl.getValue()
    String variationMarker = $variationMarkerControl.getText()
    String relationMarker = $relationMarkerControl.getText()
    Boolean showsSeparator = $showsSeparatorControl.isSelected()
    Boolean colorsBadgedWord = $colorsBadgedWordControl.isSelected()
    Boolean modifiesPunctuation = $modifiesPunctuationControl.isSelected()
    Boolean keepsMainWindowOnTop = $keepsMainWindowOnTopControl.isSelected()
    Boolean keepsEditorOnTop = $keepsEditorOnTopControl.isSelected()
    Int mainWindowWidth = $mainWindowWidthControl.getValue()
    Int mainWindowHeight = $mainWindowHeightControl.getValue()
    Boolean preservesMainWindowSize = $preservesMainWindowSizeControl.isSelected()
    Boolean usesDefaultSystemFontFamily = $usesDefaultSystemFontFamilyControl.isSelected()
    String systemFontFamily = (usesDefaultSystemFontFamily) ? null : $systemFontFamilyControl.getValue()
    FontRenderingType fontRenderingType = $fontRenderingTypeControl.getValue()
    Boolean ignoresAccent = $ignoresAccentControl.isSelected()
    Boolean ignoresCase = $ignoresCaseControl.isSelected()
    Boolean searchesPrefix = $searchesPrefixControl.isSelected()
    String scriptName = ($scriptControl.getValue() != null) ? $scriptControl.getValue().getNames()[0] : ""
    String defaultGitMessage = $defaultGitMessageControl.getText()
    Boolean gitsCommitOnSave = $gitsCommitOnSaveControl.isSelected()
    Boolean ignoresDuplicateNumber = $ignoresDuplicateNumberControl.isSelected()
    Boolean showsNumber = $showsNumberControl.isSelected()
    Boolean showsVariation = $showsVariationControl.isSelected()
    Boolean asksMutualRelation = $asksMutualRelationControl.isSelected()
    Boolean asksDuplicateName = $asksDuplicateNameControl.isSelected()
    Boolean savesAutomatically = $savesAutomaticallyControl.isSelected()
    Boolean persistsPanes = $persistsPanesControl.isSelected()
    Int separativeInterval = $separativeIntervalControl.getValue()
    Int maxHistorySize = $maxHistorySizeControl.getValue()
    ClickType linkClickType = $linkClickTypeControl.getValue()
    List<String> registeredDictionaryPaths = $registeredDictionaryPathControls.collect{it.getText()}
    List<String> registeredDictionaryNames = $registeredDictionaryNameControls.collect{it.getText()}
    setting.setContentFontFamily(contentFontFamily)
    setting.setContentFontSize(contentFontSize)
    setting.setEditorFontFamily(editorFontFamily)
    setting.setEditorFontSize(editorFontSize)
    setting.setLineSpacing(lineSpacing)
    setting.setVariationMarker(variationMarker)
    setting.setRelationMarker(relationMarker)
    setting.setShowsSeparator(showsSeparator)
    setting.setColorsBadgedWord(colorsBadgedWord)
    setting.setModifiesPunctuation(modifiesPunctuation)
    setting.setKeepsMainWindowOnTop(keepsMainWindowOnTop)
    setting.setKeepsEditorOnTop(keepsEditorOnTop)
    setting.setMainWindowWidth(mainWindowWidth)
    setting.setMainWindowHeight(mainWindowHeight)
    setting.setPreservesMainWindowSize(preservesMainWindowSize)
    setting.setSystemFontFamily(systemFontFamily)
    setting.setFontRenderingType(fontRenderingType)
    setting.setIgnoresAccent(ignoresAccent)
    setting.setIgnoresCase(ignoresCase)
    setting.setSearchesPrefix(searchesPrefix)
    setting.setScriptName(scriptName)
    setting.setDefaultGitMessage(defaultGitMessage)
    setting.setGitsCommitOnSave(gitsCommitOnSave)
    setting.setIgnoresDuplicateNumber(ignoresDuplicateNumber)
    setting.setShowsNumber(showsNumber)
    setting.setShowsVariation(showsVariation)
    setting.setAsksMutualRelation(asksMutualRelation)
    setting.setAsksDuplicateName(asksDuplicateName)
    setting.setSavesAutomatically(savesAutomatically)
    setting.setPersistsPanes(persistsPanes)
    setting.setSeparativeInterval(separativeInterval)
    setting.setMaxHistorySize(maxHistorySize)
    setting.setLinkClickType(linkClickType)
    setting.getRegisteredDictionaryPaths().clear()
    setting.getRegisteredDictionaryNames().clear()
    for (Int i = 0 ; i < registeredDictionaryPaths.size() ; i ++) {
      String path = registeredDictionaryPaths[i]
      String name = registeredDictionaryNames[i]
      setting.getRegisteredDictionaryPaths()[i] = (path != null && !path.isEmpty()) ? path : null
      setting.getRegisteredDictionaryNames()[i] = (name != null && !name.isEmpty()) ? name : null
    }
  }

  private void browseDictionary(Int index) {
    UtilityStage<File> nextStage = createStage()
    DictionaryChooserController controller = DictionaryChooserController.new(nextStage)
    String currentPath = $registeredDictionaryPathControls[index].getText()
    if (currentPath != null) {
      controller.prepare(null, File.new(currentPath).getParentFile(), false)
    }
    nextStage.showAndWait()
    if (nextStage.isCommitted()) {
      File file = nextStage.getResult()
      $registeredDictionaryPathControls[index].setText(file.getAbsolutePath())
    }
  }

  private void deregisterDictionary(Int index) {
    $registeredDictionaryPathControls[index].setText("")
    $registeredDictionaryNameControls[index].setText("")
    if (index >= 10 && index == $registeredDictionaryPathControls.size() - 1) {
      List<Node> children = ArrayList.new($registeredDictionaryPane.getChildren())
      for (Node node : children) {
        if ($registeredDictionaryPane.getRowIndex(node) == index) {
          $registeredDictionaryPane.getChildren().remove(node)
        }
      }
      $registeredDictionaryPathControls.removeAt(index)
      $registeredDictionaryNameControls.removeAt(index)
    }
  }

  @FXML
  protected void commit() {
    updateSettings()
    $stage.commit(true)
  }

  private void setupScriptControl() {
    ScriptEngineManager scriptEngineManager = ScriptEngineManager.new()
    $scriptControl.getItems().addAll(scriptEngineManager.getEngineFactories())
    $scriptControl.setButtonCell(ScriptEngineFactoryCell.new())
    $scriptControl.setCellFactory() { ListView<ScriptEngineFactory> view ->
      return ScriptEngineFactoryCell.new()
    }
  }

  private void setupRegisteredDictionaryPane() {
    Int registeredDictionarySize = Setting.getInstance().getRegisteredDictionaryPaths().size()
    for (Int i = 0 ; i < Math.max(registeredDictionarySize, 10) ; i ++) {
      insertRegisteredDictionaryControl()
    }
  }

  @FXML
  private void insertRegisteredDictionaryControl() {
    Int index = $registeredDictionaryPathControls.size()
    Label numberLabel = Label.new("登録辞書${index + 1}:")
    HBox box = HBox.new(Measurement.rpx(5))
    HBox innerBox = HBox.new()
    TextField dictionaryPathControl = TextField.new()
    TextField dictionaryNameControl = TextField.new()
    Button browseButton = Button.new("…")
    Button deregisterButton = Button.new("－")
    dictionaryPathControl.getStyleClass().add("left-pill")
    dictionaryNameControl.setPrefWidth(Measurement.rpx(130))
    dictionaryNameControl.setMinWidth(Measurement.rpx(130))
    browseButton.setMinWidth(Button.USE_PREF_SIZE)
    browseButton.getStyleClass().add("right-pill")
    browseButton.setOnAction() {
      browseDictionary(index)
    }
    deregisterButton.setOnAction() {
      deregisterDictionary(index)
    }
    innerBox.getChildren().addAll(dictionaryPathControl, browseButton)
    innerBox.setHgrow(dictionaryPathControl, Priority.ALWAYS)
    box.getChildren().addAll(dictionaryNameControl, innerBox, deregisterButton)
    box.setHgrow(innerBox, Priority.ALWAYS)
    $registeredDictionaryPathControls.add(dictionaryPathControl)
    $registeredDictionaryNameControls.add(dictionaryNameControl)
    $registeredDictionaryPane.add(numberLabel, 0, index)
    $registeredDictionaryPane.add(box, 1, index)
    $registeredDictionaryNameControls.last().requestFocus()
  }

  private void setupFontFamilyControls() {
    List<String> fontFamilies = Font.getFamilies()
    $contentFontFamilyControl.getItems().addAll(fontFamilies)
    $editorFontFamilyControl.getItems().addAll(fontFamilies)
    $systemFontFamilyControl.getItems().addAll(fontFamilies)
  }

  private void setupIntegerControls() {
    $contentFontSizeControl.getEditor().setTextFormatter(TextFormatter.new(IntegerUnaryOperator.new()))
    $editorFontSizeControl.getEditor().setTextFormatter(TextFormatter.new(IntegerUnaryOperator.new()))
    $lineSpacingControl.getEditor().setTextFormatter(TextFormatter.new(IntegerUnaryOperator.new()))
    $separativeIntervalControl.getEditor().setTextFormatter(TextFormatter.new(IntegerUnaryOperator.new()))
    $maxHistorySizeControl.getEditor().setTextFormatter(TextFormatter.new(IntegerUnaryOperator.new()))
  }

  private void bindFontControlProperties() {
    $contentFontFamilyControl.disableProperty().bind($usesSystemContentFontFamilyControl.selectedProperty())
    $contentFontSizeControl.disableProperty().bind($usesDefaultContentFontSizeControl.selectedProperty())
    $editorFontFamilyControl.disableProperty().bind($usesSystemEditorFontFamilyControl.selectedProperty())
    $editorFontSizeControl.disableProperty().bind($usesDefaultEditorFontSizeControl.selectedProperty())
    $systemFontFamilyControl.disableProperty().bind($usesDefaultSystemFontFamilyControl.selectedProperty())
  }

  private void bindMainWindowSizeControlProperties() {
    $mainWindowWidthControl.disableProperty().bind($preservesMainWindowSizeControl.selectedProperty())
    $mainWindowHeightControl.disableProperty().bind($preservesMainWindowSizeControl.selectedProperty())
  }

}