package edu.cnm.deepdive.tunefull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * Initializes the SpringBoot server for the Tunefull application.
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@SpringBootApplication
public class TunefullApplication {

  /**
   * The main method of Tunefull Application provides an entry point to run the server.
   *
   * @param args String[] of command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(TunefullApplication.class, args);
  }

}
