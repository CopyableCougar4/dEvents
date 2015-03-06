package com.digiturtle.events;

/**
 * Abstract representation of an even that can be passed around
 */
public abstract class Event {
	
	/**
	 * This event's name
	 */
	private String name;
	
	/**
	 * Whether this event was cancelled
	 */
	private boolean cancelled = false;
	
	/**
	 * Construct the event
	 * @param name This event's custom name
	 */
	public Event(String name) {
		this.name = name;
	}

	/**
	 * Get the event's custom name
	 * @return Event name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get this event type
	 * @return Event type (Event class name)
	 */
	public String getType() {
		return getClass().getSimpleName();
	}
	
	/**
	 * See if this event was cancelled by a listener
	 * @return Whether the event was cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Cancel this event for future listeners
	 */
	public void cancel() {
		cancelled = true;
	}
	
}
