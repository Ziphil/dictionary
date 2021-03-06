package ziphil.module

import groovy.transform.CompileStatic
import javafx.concurrent.Task
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class CharacterFrequencySaver<D extends Dictionary> extends Task<BooleanClass> {

  private CharacterFrequencyAnalyzer $analyzer
  private String $path

  public CharacterFrequencySaver(CharacterFrequencyAnalyzer analyzer, String path) {
    $analyzer = analyzer
    $path = path
  }

  private BooleanClass save() {
    File file = File.new($path)
    BufferedWriter writer = file.newWriter("UTF-8")
    String separator = separator()
    try {
      for (CharacterStatus status : $analyzer.characterStatuses()) {
        writer.write("\"")
        writer.write(status.getCharacter().replaceAll(/"/, "\"\""))
        writer.write("\"")
        writer.write(separator)
        writer.write(status.getFrequency().toString())
        writer.write(separator)
        writer.write(status.getFrequencyPercentage().toString())
        writer.write(separator)
        writer.write(status.getUsingWordSize().toString())
        writer.write(separator)
        writer.write(status.getUsingWordSizePercentage().toString())
        writer.newLine()
      }
    } finally {
      writer.close()
    }
    return true
  }

  protected BooleanClass call() {
    if ($path != null) {
      BooleanClass result = save()
      return result
    } else {
      return false
    }
  }

  private String separator() {
    if ($path.endsWith(".csv")) {
      return ","
    } else if ($path.endsWith(".tsv")) {
      return "\t"
    } else {
      return ","
    }
  }

}