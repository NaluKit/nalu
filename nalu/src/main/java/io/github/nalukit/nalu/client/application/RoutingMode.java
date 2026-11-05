package io.github.nalukit.nalu.client.application;

/**
 * Defines how Nalu represents routes inside the browser url.
 */
public enum RoutingMode {

  /**
   * Hash based routing: <code>/index.html#/shell/route</code>. No server configuration needed.
   */
  HASH,

  /**
   * Path based routing (History API, no hash): <code>/base/shell/route</code>.
   * The server must answer unknown paths with the <code>index.html</code> of the application.
   */
  PATH

}
