package io.github.nalukit.nalu.client.component;

/**
 * Default implementation of a composite condition
 */
public class AlwaysLoadComposite
    implements IsLoadCompositeCondition {

  public AlwaysLoadComposite() {
  }

  @Override
  public boolean loadComposite(String route,
                               String... params) {
    return true;
  }

}
