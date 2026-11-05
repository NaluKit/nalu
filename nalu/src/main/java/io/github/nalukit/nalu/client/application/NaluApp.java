package io.github.nalukit.nalu.client.application;

import io.github.nalukit.nalu.client.internal.NaluConfigOverrides;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;

import java.util.Objects;

/**
 * Fluent configuration as an alternative (or addition) to the attributes of the
 * {@link io.github.nalukit.nalu.client.application.annotation.Application}-annotation.
 * <br>
 * Values set here override the values of the annotation. Everything that is not set keeps the
 * value of the annotation.
 *
 * <pre>
 * NaluApp.configure()
 *        .routing(RoutingMode.PATH)
 *        .basePath("/app")
 *        .startRoute("/shell/home")
 *        .start(new MyApplicationImpl(),
 *               new NaluPluginElemental2());
 * </pre>
 */
public final class NaluApp {

  private NaluApp() {
  }

  /**
   * Creates a new configuration.
   *
   * @return the configuration
   */
  public static Configuration configure() {
    return new Configuration();
  }

  public static final class Configuration {

    private RoutingMode routingMode;
    private String      basePath;
    private String      startRoute;
    private Boolean     usingBaseHref;
    private Boolean     usingTrailingSlash;

    private Configuration() {
    }

    /**
     * Sets the routing mode: HASH (<code>#/shell/route</code>) or PATH (<code>/shell/route</code>).
     *
     * @param routingMode the routing mode
     * @return this configuration
     */
    public Configuration routing(RoutingMode routingMode) {
      this.routingMode = Objects.requireNonNull(routingMode,
                                                "routingMode must not be null");
      return this;
    }

    /**
     * Sets the base path of the application (PATH routing only), f.e.: "/app".
     *
     * @param basePath the base path
     * @return this configuration
     */
    public Configuration basePath(String basePath) {
      this.basePath = Objects.requireNonNull(basePath,
                                             "basePath must not be null");
      return this;
    }

    /**
     * Sets the start route of the application.
     *
     * @param startRoute the start route
     * @return this configuration
     */
    public Configuration startRoute(String startRoute) {
      this.startRoute = Objects.requireNonNull(startRoute,
                                               "startRoute must not be null");
      return this;
    }

    /**
     * Tells Nalu to handle the base href (PATH routing only).
     *
     * @param usingBaseHref true: handle base href
     * @return this configuration
     */
    public Configuration usingBaseHref(boolean usingBaseHref) {
      this.usingBaseHref = usingBaseHref;
      return this;
    }

    /**
     * Tells Nalu to add a trailing slash to the routes.
     *
     * @param usingTrailingSlash true: add a trailing slash
     * @return this configuration
     */
    public Configuration usingTrailingSlash(boolean usingTrailingSlash) {
      this.usingTrailingSlash = usingTrailingSlash;
      return this;
    }

    /**
     * Applies the configuration. All values that were not set are removed
     * from previous configurations (annotation values are used).
     * <br>
     * Call this method before <code>application.run(plugin)</code>, or use
     * {@link #start(IsApplication, IsNaluProcessorPlugin)}.
     */
    public void apply() {
      NaluConfigOverrides overrides = NaluConfigOverrides.INSTANCE;
      overrides.reset();
      overrides.setRoutingMode(this.routingMode);
      overrides.setBasePath(this.basePath);
      overrides.setStartRoute(this.startRoute);
      overrides.setUsingBaseHref(this.usingBaseHref);
      overrides.setUsingTrailingSlash(this.usingTrailingSlash);
    }

    /**
     * Applies the configuration and starts the application.
     *
     * @param application the generated application (f.e.: new MyApplicationImpl())
     * @param plugin      the plugin (f.e.: new NaluPluginElemental2())
     */
    public void start(IsApplication application,
                      IsNaluProcessorPlugin plugin) {
      this.apply();
      application.run(plugin);
    }

  }

}
