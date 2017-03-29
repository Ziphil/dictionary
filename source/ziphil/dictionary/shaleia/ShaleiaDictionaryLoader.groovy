package ziphil.dictionary.shaleia

import groovy.transform.CompileStatic
import java.util.regex.Matcher
import javafx.collections.ObservableList
import ziphil.dictionary.DictionaryLoader
import ziphilib.transform.ConvertPrimitives
import ziphilib.transform.Ziphilify
import ziphilib.type.PrimLong


@CompileStatic @Ziphilify @ConvertPrimitives
public class ShaleiaDictionaryLoader extends DictionaryLoader<ShaleiaDictionary, ShaleiaWord> {

  public ShaleiaDictionaryLoader(ShaleiaDictionary dictionary, String path) {
    super(dictionary, path)
    updateProgress(0, 1)
  }

  protected ObservableList<ShaleiaWord> call() {
    if ($path != null) {
      File file = File.new($path)
      BufferedReader reader = file.newReader("UTF-8")
      PrimLong size = file.length()
      PrimLong offset = 0L
      try {
        String currentName = null
        StringBuilder currentDescription = StringBuilder.new()
        for (String line ; (line = reader.readLine()) != null ;) {
          if (isCancelled()) {
            reader.close()
            return null
          }
          Matcher matcher = line =~ /^\*\s*(.+)\s*$/
          if (matcher.matches()) {
            add(currentName, currentDescription)
            currentName = matcher.group(1)
            currentDescription.setLength(0)
          } else {
            currentDescription.append(line)
            currentDescription.append("\n")
          }
          offset += line.getBytes("UTF-8").length + 1
          updateProgress(offset, size)
        }
        add(currentName, currentDescription)
      } finally {
        reader.close()
      }
      for (ShaleiaWord word : $words) {
        word.updateComparisonString($dictionary.getAlphabetOrder())
      }
    }
    return $words
  }

  private void add(String currentName, StringBuilder currentDescription) {
    if (currentName != null) {
      if (currentName.startsWith("META-")) {
        if (currentName == "META-ALPHABET-ORDER") {
          addAlphabetOrder(currentDescription)
        } else if (currentName == "META-VERSION") {
          addVersion(currentDescription)
        } else if (currentName == "META-CHANGE") {
          addChangeDescription(currentDescription)
        }
      } else {
        addWord(currentName, currentDescription)
      }
    }
  }

  private void addWord(String currentName, StringBuilder currentDescription) {
    ShaleiaWord word = ShaleiaWord.new()
    word.setUniqueName(currentName)
    word.setDescription(currentDescription.toString())
    word.setDictionary($dictionary)
    word.update()
    $words.add(word)
  }

  private void addAlphabetOrder(StringBuilder currentDescription) {
    String alphabetOrder = currentDescription.toString().trim().replaceAll(/^\-\s*/, "")
    $dictionary.setAlphabetOrder(alphabetOrder)
  }

  private void addVersion(StringBuilder currentDescription) {
    String version = currentDescription.toString().trim().replaceAll(/^\-\s*/, "")
    $dictionary.setVersion(version)
  }

  private void addChangeDescription(StringBuilder currentDescription) {
    String changeDescription = currentDescription.toString().replaceAll(/^\s*\n/, "")
    $dictionary.setChangeDescription(changeDescription)
  }

}