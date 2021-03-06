package ziphil.custom

import groovy.transform.CompileStatic
import java.util.function.Supplier
import javafx.concurrent.Task
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class SimpleTask<V> extends Task<V> {

  private Supplier<V> $function

  public SimpleTask(Supplier<V> function) {
    $function = function
  }

  protected V call() {
    return $function.get()
  }

}