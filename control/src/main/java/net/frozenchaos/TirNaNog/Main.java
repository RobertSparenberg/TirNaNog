/**
 * Main class that initializes the TirNaNog server.
 * @author imahilus
 */
package net.frozenchaos.TirNaNog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static String name = "TirNaNog";

    public static void main(String[] varargs) {
        SpringApplication.run(Main.class, varargs);
    }
}
