package com.digiturtle.events;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Represents an event bus that dispatches events to listeners via reflection
 */
public class EventBus {
	
	/**
	 * Represents an event queue of unknown size
	 */
	private EventQueue events = new EventQueue();
	
	/**
	 * Represents a list of Methods obtained via reflection that handle events
	 */
	private HashMap<Function, Class<?>> eventMethods = new HashMap<Function, Class<?>>();
	
	/**
	 * Represents the listeners that are attached to the bus
	 */
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	/**
	 * Attach a listener to this bus
	 * @param listener Listener object
	 */
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Detach a listener from this bus
	 * @param listener Listener object
	 */
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Dispatch an event to attached listeners
	 * @param event Event instance
	 */
	public void dispatchEvent(Event event) {
		events.addEvent(event);
	}
	
	/**
	 * Represents an annotated function
	 */
	private class Function {
		
		/**
		 * Function
		 */
		private Method method;
		
		/**
		 * Annotation
		 */
		private Handler annotation;
		
	}
	
	/**
	 * Call to update which methods receive Events
	 */
	public void attachMethods() {
		eventMethods.clear();
		ArrayList<Function> functions = new ArrayList<Function>();
		for (Listener listener : listeners) {
			Method[] methods = listener.getClass().getMethods();
			for (Method method : methods) {
				if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
					if (method.getName().equalsIgnoreCase("onEvent")) {
						if (method.getParameterCount() == 1 && method.getParameterTypes()[0].getSuperclass().equals(Event.class)) {
							if (method.isAnnotationPresent(Handler.class)) {
								// Format: public void onEvent(... event)
								Function function = new Function();
								function.method = method;
								function.annotation = (Handler) method.getAnnotation(Handler.class);
								functions.add(function);
							}
						}
					}
				}
			}
		}
		functions.sort(new Comparator<Function>() {
			public int compare(Function function1, Function function2) {
				return Integer.compare(function1.annotation.priority(), function2.annotation.priority());
			}
		});
		for (Function function : functions) {
			eventMethods.put(function, function.method.getParameterTypes()[0]);
		}
	}
	
	/**
	 * Send the next method from the queue to the listeners
	 * @throws Exception
	 */
	public void nextEvent() throws Exception {
		Event event = events.getEvent();
		int index = 0;
		if (event != null) {
			for (Entry<Function, Class<?>> entry : eventMethods.entrySet()) {
				Listener listener = listeners.get(index);
				Method method = entry.getKey().method;
				Class<?> type = entry.getValue();
				if (event.getClass().isAssignableFrom(type)) {
					if (entry.getKey().annotation.ignoreCancelled()) {
						if (!event.isCancelled()) {
							method.invoke(listener, type.cast(event));
						}
					} else {
						method.invoke(listener, type.cast(event));
					}
				}
				index++;
			}
		}
	}

}
