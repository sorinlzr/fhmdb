package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public class ASCState extends State{
    public ASCState(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void pressSortBtn() {
        homeController.setState(new DESCState(homeController));
        homeController.movies.sort(Comparator.reverseOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }
}
