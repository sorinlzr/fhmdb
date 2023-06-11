package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public class DefaultState extends State{
    public DefaultState(HomeController homeController) {
        super(homeController);
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }
}
