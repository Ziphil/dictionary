package ziphil.dictionary.slime

import groovy.transform.CompileStatic
import ziphil.dictionary.Suggestion
import ziphil.module.Setting
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class SlimeSuggestion extends Suggestion<SlimePossibility> {

  private SlimeDictionary $dictionary

  public SlimeSuggestion() {
    update()
  }

  public void update() {
    $isChanged = true
  }

  public void createContentPane() {
    Setting setting = Setting.getInstance()
    Boolean modifiesPunctuation = setting.getModifiesPunctuation()
    SlimeSuggestionContentPaneCreator creator = SlimeSuggestionContentPaneCreator.new($contentPane, this, $dictionary)
    creator.setModifiesPunctuation(modifiesPunctuation)
    creator.create()
    $isChanged = false
  }

  public SlimeDictionary getDictionary() {
    return $dictionary
  }

  public void setDictionary(SlimeDictionary dictionary) {
    $dictionary = dictionary
  }

}