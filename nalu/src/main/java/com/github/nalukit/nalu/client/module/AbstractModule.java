package com.github.nalukit.nalu.client.module;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.context.AbstractApplicationContext;
import com.github.nalukit.nalu.client.context.IsModuleContext;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractModule<C extends IsModuleContext>
    implements IsModule<C> {

  protected Router              router;
  protected SimpleEventBus      eventBus;
  /* instance of AlwaysLoadComposite-class */
  protected AlwaysLoadComposite alwaysLoadComposite;
  private   C                   moduleContext;

  public AbstractModule(Router router,
                        C moduleContext,
                        SimpleEventBus eventBus,
                        AlwaysLoadComposite alwaysLoadComposite) {
    this.router = router;
    this.moduleContext = moduleContext;
    this.eventBus = eventBus;
    this.alwaysLoadComposite = alwaysLoadComposite;
  }

  public void configureModuleContext(AbstractApplicationContext context) {
    this.moduleContext.setApplicationContext(context.getContext());
  }

  @Override
  public void loadModule(RouterConfiguration routeConfiguration) {
    this.loadShellFactory();
    this.loadCompositeController();
    this.loadComponents();
    this.loadFilters(routeConfiguration);
    this.loadHandlers();
  }

  protected abstract void loadHandlers();

  protected abstract void loadFilters(RouterConfiguration routeConfiguration);

  protected abstract void loadComponents();

  protected abstract void loadCompositeController();

  protected abstract void loadShellFactory();

}
