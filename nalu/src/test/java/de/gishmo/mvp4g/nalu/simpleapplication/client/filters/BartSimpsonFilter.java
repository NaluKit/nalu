package de.gishmo.mvp4g.nalu.simpleapplication.client.filters;

import com.github.mvp4g.nalu.client.filter.AbstractFilter;
import de.gishmo.mvp4g.nalu.simpleapplication.client.NaluSimpleApplicationContext;

public class BartSimpsonFilter
    extends AbstractFilter<NaluSimpleApplicationContext> {

  @Override
  public boolean filter(String route,
                        String... parms) {
    if ("/detail".equals(route)) {
      if ("3".equals(parms[0])) {
        System.out.print("Bart Simpsons is not selecteable -> redirecting to search!");
        return false;
      }
    }
    return true;
  }

  @Override
  public String redirectTo() {
    return "/search";
  }

  @Override
  public String[] parameters() {
    return new String[] { this.context.getSearchName(), this.context.getSearchCity() };
  }
}
