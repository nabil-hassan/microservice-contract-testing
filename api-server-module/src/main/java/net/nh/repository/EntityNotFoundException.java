package net.nh.repository;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Class clazz, Long id) {
        super("Entity not found for class: " + clazz.getSimpleName() + ", and id: " + id);
    }
}
