package edu.cnm.deepdive.tunefull.configuration;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * {@code ErrorConfiguration} provides methods both to handle errors that would result in an HTTP 500
 * response status and to translate other responses to the desired response status code.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
public class ErrorConfiguration {

  // TODO get help with this
  /**
   * Changes {@code NoSuchElementException} into a {@code 404 Not Found} response.
   */
  @ExceptionHandler({NoSuchElementException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found or moved")
  public void notFound() {
  }

  /**
   * Changes {@code IllegalArgumentException} into a {@code 400 Bad Request} response.
   */
  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Empty request body or invalid information")
  public void badRequest() {
  }

  // also needs a valid Location header and EntityLinks? How do these remaining three get set up? And
  // is 200 OK the default response or do I need to set that up too.
  /**
   *  Returns a {@code 201 Created} response.
   */
  @ResponseStatus(value = HttpStatus.CREATED)
  public void created() {
  }

  /**
   * Returns a {@code 204 No Content} response.
   */
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleted() {
  }

  /**
   * Returns a {@code 204 No Content} response.
   */
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void noContent() {
  }



}
