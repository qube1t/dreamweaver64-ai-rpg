package nz.ac.auckland.se206;

/**
 * An interface for defining actions to be taken with the result of a GPT
 * request.
 */
public interface GptResultAction {
  void call(String result);
}
