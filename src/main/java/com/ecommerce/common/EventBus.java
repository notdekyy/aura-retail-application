package com.ecommerce.common;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<String, List<Consumer<DomainEvent>>> listeners = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private EventBus() {}

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public void subscribe(String eventType, Consumer<DomainEvent> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void publish(DomainEvent event) {
        List<Consumer<DomainEvent>> eventListeners = listeners.getOrDefault(event.eventType(), List.of());
        for (Consumer<DomainEvent> listener : eventListeners) {
            executor.submit(() -> listener.accept(event));
        }
    }
}