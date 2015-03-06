Usage:

```java
// create the bus
EventBus bus = new EventBus();

// bind the listener
bus.addListener(new ObjectEventListener());
bus.attachMethods();

// dispatch the event
bus.dispatchEvent(new DropObjectEvent("Drop-1"));

// push the next event to the listener
bus.nextEvent();

// Helper Classes

public class DropObjectEvent extends Event {

	public DropObjectEvent(String name) {
		super(name);
	}

}

public class ObjectEventListener implements Listener {

	@Handler(priority = 1000, ignoreCancelled = true)
	public void onEvent(DropObjectEvent event) {
		// Handle this event
	}

}
```