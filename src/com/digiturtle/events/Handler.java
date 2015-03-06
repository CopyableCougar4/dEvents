package com.digiturtle.events;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Represents an event-handling method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Handler {
	
	/**
	 * Determines whether cancelled events should still be passed
	 */
	boolean ignoreCancelled() default false;
	
	/**
	 * Determines the ordering for event handlers
	 */
	int priority() default 0;

}
