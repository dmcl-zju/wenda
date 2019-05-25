package com.zju.async;

import java.util.List;

public interface EventHandler {
	public void doHandler(EventModel model);
	public List<EventType> getSupportEventTypes();
}
