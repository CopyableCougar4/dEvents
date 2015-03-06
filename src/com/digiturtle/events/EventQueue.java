package com.digiturtle.events;

import java.util.ArrayList;

/**
 * Queue-like data structure with a first in last out structure
 */
public class EventQueue {
	
	/**
	 * Structure that is capable of holding an unknown number of events
	 */
	private ArrayList<Event> queueList;
	
	/**
	 * Structure that is capable of holding a known number of events
	 */
	private Event[] queueArray;
	
	/**
	 * Position in a queue with a known number of events
	 */
	private int index;
	
	/**
	 * Construct an event queue with an unknown number of events
	 */
	public EventQueue() {
		queueList = new ArrayList<Event>();
	}
	
	/**
	 * Construct an event queue with a known number of events
	 * @param size Size of the queue
	 */
	public EventQueue(int size) {
		queueArray = new Event[size];
		index = 0;
	}
	
	/**
	 * Get the next event in this queue
	 * @return Event instance
	 */
	public Event getEvent() {
		if (queueList != null) {
			if (queueList.size() > 0) {
				return queueList.remove(0);
			}
			return null;
		}
		if (queueArray != null) {
			if (index > 0) {
				Event result = queueArray[0];
				System.arraycopy(queueArray, 1, queueArray, 0, index);
				queueArray[index - 1] = null;
				index--;
				return result;
			}
			return null;
		}
		return null;
	}
	
	/**
	 * Add an event to this queue
	 * @param event Event instance
	 */
	public void addEvent(Event event) {
		if (queueList != null) {
			queueList.add(event);
		}
		if (queueArray != null) {
			queueArray[index] = event;
			index++;
		}
	}

}
