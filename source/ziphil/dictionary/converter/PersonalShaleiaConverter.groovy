package ziphil.dictionary.converter

import groovy.transform.CompileStatic
import java.util.regex.Matcher
import ziphil.dictionary.Loader
import ziphil.dictionary.Word
import ziphil.dictionary.personal.PersonalDictionary
import ziphil.dictionary.personal.PersonalWord
import ziphil.dictionary.shaleia.ShaleiaDescriptionReader
import ziphil.dictionary.shaleia.ShaleiaDictionary
import ziphil.dictionary.shaleia.ShaleiaWord
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class PersonalShaleiaConverter extends Loader<PersonalDictionary, PersonalWord> {

  private ShaleiaDictionary $sourceDictionary

  public PersonalShaleiaConverter(ShaleiaDictionary sourceDictionary) {
    super()
    $sourceDictionary = sourceDictionary
  }

  protected BooleanClass load() {
    List<ShaleiaWord> sourceWords = $sourceDictionary.getRawWords()
    Int size = sourceWords.size()
    for (Int i = 0 ; i < size ; i ++) {
      if (isCancelled()) {
        return false
      }
      ShaleiaWord sourceWord = sourceWords[i]
      if (!sourceWord.getName().startsWith("\$")) {
        PersonalWord word = PersonalWord.new()
        word.setName(sourceWord.getName())
        StringBuilder translation = StringBuilder.new()
        StringBuilder usage = StringBuilder.new()
        ShaleiaDescriptionReader sourceReader = ShaleiaDescriptionReader.new(sourceWord.getDescription())
        try {
          while (sourceReader.readLine() != null) {
            if (sourceReader.findCreationDate()) {
              String sourceCreationDate = sourceReader.lookupCreationDate()
              String sourceSort = sourceReader.lookupSort()
              appendCreationDate(translation, sourceSort, sourceCreationDate)
            }
            if (sourceReader.findEquivalent()) {
              String sourceCategory = sourceReader.lookupCategory()
              String sourceEquivalent = sourceReader.lookupEquivalent()
              appendEquivalent(translation, sourceCategory, sourceEquivalent)
            }
            if (sourceReader.findContent()) {
              String sourceTitle = sourceReader.title()
              String sourceContent = sourceReader.lookupContent()
              appendContent(usage, sourceTitle, sourceContent)
            }
            if (sourceReader.findSynonym()) {
              String sourceSynonym = sourceReader.lookupSynonym()
              appendSynonym(usage, sourceSynonym)
            }
          }
          modifyBreak(translation)
          modifyBreak(usage)
          word.setTranslation(translation.toString())
          word.setUsage(usage.toString())
          word.setDictionary($dictionary)
          $words.add(word)
        } finally {
          sourceReader.close()
        }
      }
      updateProgress(i + 1, size)
    }
    return true
  }

  private void appendCreationDate(StringBuilder translation, String sourceSort, String sourceCreationDate) {
    translation.append("《")
    translation.append(sourceSort)
    translation.append("》 ")
    translation.append(sourceCreationDate)
    translation.append("\n")
  }

  private void appendEquivalent(StringBuilder translation, String sourceCategory, String sourceEquivalent) {
    translation.append("〈")
    translation.append(sourceCategory)
    translation.append("〉 ")
    translation.append(sourceEquivalent.replaceAll(/(\{|\}|\/)/, ""))
    translation.append("\n")
  }

  private void appendContent(StringBuilder usage, String sourceTitle, String sourceContent) {
    usage.append("【")
    usage.append(sourceTitle)
    usage.append("】\n")
    usage.append(sourceContent.replaceAll(/(\{|\}|\[|\]|\/)/, ""))
    usage.append("\n")
  }

  private void appendSynonym(StringBuilder usage, String sourceSynonym) {
    usage.append("cf: ")
    usage.append(sourceSynonym.replaceAll(/(\{|\})/, ""))
    usage.append("\n")
  }

  private void modifyBreak(StringBuilder string) {
    if (string.length() > 0) {
      if (string.codePointAt(string.length() - 1) == '\n') {
        string.deleteCharAt(string.length() - 1)
      }
    }
  }

}