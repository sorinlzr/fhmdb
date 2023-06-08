package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

public abstract class State {
    protected HomeController homeController;

    protected final String SORT_DEFAULT_TEXT_ASC = "Sort (asc)";

    protected final String SORT_DEFAULT_TEXT_DESC = "Sort (desc)";

    public State(HomeController homeController) {
        this.homeController = homeController;
    }

    public abstract void pressSortBtn();
}
