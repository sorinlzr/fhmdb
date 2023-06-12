package at.ac.fhcampuswien.fhmdb.controller;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    // Single instance of ControllerFactory
    private static ControllerFactory instance;

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
        try {
            // invoke the getInstance() method of controller class via Reflection.
            // We pass null as parameter because the method is static
            return aClass.getDeclaredMethod("getInstance").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
