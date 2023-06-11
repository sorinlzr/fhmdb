package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public class DESCState extends State {
    public DESCState(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void pressFilterBtn() {
        homeController.setCurrentState(new DESCState(homeController));
        homeController.getMovies().sort(Comparator.reverseOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }
}
