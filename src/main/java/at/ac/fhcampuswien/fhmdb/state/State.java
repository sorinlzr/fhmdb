package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public abstract class State {
    protected HomeController homeController;

    protected final String SORT_DEFAULT_TEXT_ASC = "Sort (asc)";

    protected final String SORT_DEFAULT_TEXT_DESC = "Sort (desc)";

    public State(HomeController homeController) {
        this.homeController = homeController;
    }

    public void pressSortBtn(){
        homeController.setCurrentState(new ASCState(homeController));
        homeController.getMovies().sort(Comparator.naturalOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
    }

    public void pressFilterBtn(){
        homeController.setCurrentState(new DESCState(homeController));
        homeController.getMovies().sort(Comparator.reverseOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }
}
