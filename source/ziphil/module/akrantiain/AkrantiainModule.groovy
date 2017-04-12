package ziphil.module.akrantiain

import groovy.transform.CompileStatic
import ziphilib.transform.Ziphilify


@CompileStatic @Ziphilify
public class AkrantiainModule {

  private List<AkrantiainToken> $name = ArrayList.new()
  private Set<AkrantiainEnvironment> $environments = Collections.synchronizedSet(EnumSet.noneOf(AkrantiainEnvironment))
  private List<AkrantiainDefinition> $definitions = Collections.synchronizedList(ArrayList.new())
  private List<AkrantiainRule> $rules = Collections.synchronizedList(ArrayList.new())

  public String convert(String input, AkrantiainRoot root) {
    AkrantiainElementGroup currentGroup = AkrantiainElementGroup.create(input)
    for (AkrantiainRule rule : $rules) {
      currentGroup = rule.apply(currentGroup, this)
    }
    List<AkrantiainElement> invalidElements = currentGroup.invalidElements(this)
    if (invalidElements.isEmpty()) {
      return currentGroup.createOutput()
    } else {
      throw AkrantiainException.new("No rules that can handle some characters", invalidElements)
    }
  }

  public AkrantiainMatchable findContentOf(String identifierName) {
    for (AkrantiainDefinition definition : $definitions) {
      if (definition.getIdentifier().getText() == identifierName) {
        return definition.getContent()
      }
    }
    return null
  }

  public AkrantiainMatchable findPunctuationContent() {
    for (AkrantiainDefinition definition : $definitions) {
      if (definition.getIdentifier().getText() == Akrantiain.PUNCTUATION_IDENTIIER_NAME) {
        return definition.getContent()
      }
    }
    return AkrantiainDisjunction.EMPTY_DISJUNCTION
  }

  public Boolean containsEnvironment(AkrantiainEnvironment environment) {
    return $environments.contains(environment)
  } 

  public Boolean containsDefinitionOf(AkrantiainToken identifier) {
    for (AkrantiainDefinition definition : $definitions) {
      if (definition.getIdentifier().getText() == identifier.getText()) {
        return true
      }
    }
    return false
  }

  public List<AkrantiainToken> getName() {
    return $name
  }

  public void setName(List<AkrantiainToken> name) {
    $name = name
  }

  public Set<AkrantiainEnvironment> getEnvironments() {
    return $environments
  }

  public void setEnvironments(Set<AkrantiainEnvironment> environments) {
    $environments = environments
  }

  public List<AkrantiainDefinition> getDefinitions() {
    return $definitions
  }

  public void setDefinitions(List<AkrantiainDefinition> definitions) {
    $definitions = definitions
  }

  public List<AkrantiainRule> getRules() {
    return $rules
  }

  public void setRules(List<AkrantiainRule> rules) {
    $rules = rules
  }

}