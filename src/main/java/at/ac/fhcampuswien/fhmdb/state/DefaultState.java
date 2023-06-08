package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public class DefaultState extends State{
    public DefaultState(HomeController homeController) {
        super(homeController);
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }

    @Override
    public void pressSortBtn() {
        homeController.setState(new ASCState(homeController));
        homeController.movies.sort(Comparator.naturalOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
    }
}
