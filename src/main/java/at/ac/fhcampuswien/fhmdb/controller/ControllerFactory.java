package at.ac.fhcampuswien.fhmdb.controller;

import javafx.util.Callback;
import java.util.HashMap;

public class ControllerFactory implements Callback<Class<?>, Object> {
    // Single instance of ControllerFactory
    private static ControllerFactory instance;

    // HashMap to store single instances of controller classes
    private HashMap<Class<?>, Object> instances = new HashMap<>();

    // Private constructor to prevent direct instantiation
    private ControllerFactory() {}

    // Public method to get the single instance of ControllerFactory
    public static synchronized ControllerFactory getInstance() {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }

    @Override
    public Object call(Class<?> aClass) {
        // If we already have an instance of this controller class, return it
        if (instances.containsKey(aClass)) {
            return instances.get(aClass);
        }

        // Otherwise, create a new instance, store it, and return it
        try {
            Object controller = aClass.getDeclaredConstructor().newInstance();
            instances.put(aClass, controller);
            return controller;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
