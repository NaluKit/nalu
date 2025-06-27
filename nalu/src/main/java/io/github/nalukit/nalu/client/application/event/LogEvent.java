package io.github.nalukit.nalu.client.application.event;

import org.gwtproject.event.shared.Event;

import java.util.ArrayList;
import java.util.List;

public class LogEvent
    extends Event<LogEvent.LogHandler> {

  public final static Type<LogEvent.LogHandler> TYPE = new Type<>();
  private final       List<String>              messages;
  private             boolean                   sdmOnly;

  @SuppressWarnings("unused")
  private LogEvent() {
    this.messages = new ArrayList<>();
    this.sdmOnly  = true;
  }

  public static LogEvent create() {
    return new LogEvent();
  }

  /**
   * Adds a message to the list of messages.
   *
   * @param message the message to add
   * @return instance of this event
   */
  public LogEvent addMessage(String message) {
    this.messages.add(message);
    return this;
  }

  /**
   * Sets a flag that controls if the message is logged only in SDM or not.
   *
   * <ul>
   *   <li>true: message will only be logged if application is running SDM</li>
   *   <li>false: message will always be logged</li>
   * </ul>
   * <p>
   * Default is <b>true</b>.
   *
   * @param sdmOnly defines if the message is only logged in SDM or not
   * @return instance of this event
   */
  public LogEvent sdmOnly(boolean sdmOnly) {
    this.sdmOnly = sdmOnly;
    return this;
  }

  @Override
  public Type<LogEvent.LogHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LogEvent.LogHandler handler) {
    handler.onLog(this);
  }

  /**
   * Returns the List of messages to log.
   *
   * @return message list
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * Return the SDM flag.
   *
   * <ul>
   *   <li>true: message will only be logged if application is running SDM</li>
   *   <li>false: message will always be logged</li>
   * </ul>
   *
   * @return the SDM flag
   */
  public boolean isSdmOnly() {
    return sdmOnly;
  }

  @FunctionalInterface
  public interface LogHandler {

    void onLog(LogEvent event);

  }

}
