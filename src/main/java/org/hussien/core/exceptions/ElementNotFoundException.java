package org.hussien.core.exceptions;

import org.hussien.core.exceptions.FrameworkException;
import org.openqa.selenium.By;

/**
 * Custom exception thrown when a WebElement is not found on the page.
 * Extends FrameworkException to provide more context about the failure.
 */
public class ElementNotFoundException extends FrameworkException {

    /**
     * Constructor with a locator parameter.
     *
     * @param locator The By locator of the element that was not found.
     */
    public ElementNotFoundException(By locator) {
        super("Element not found using locator: " + locator.toString());
    }

    /**
     * Constructor with a locator and additional context message.
     *
     * @param locator The By locator of the element that was not found.
     * @param message Additional context about the failure.
     */
    public ElementNotFoundException(By locator, String message) {
        super("Element not found using locator: " + locator.toString() + ". " + message);
    }

    /**
     * Constructor with a locator and the root cause exception.
     *
     * @param locator The By locator of the element that was not found.
     * @param cause   The root cause exception (e.g., NoSuchElementException).
     */
    public ElementNotFoundException(By locator, Throwable cause) {
        super("Element not found using locator: " + locator.toString(), cause);
    }
}