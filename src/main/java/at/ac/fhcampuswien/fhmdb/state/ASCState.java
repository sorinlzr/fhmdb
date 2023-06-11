package at.ac.fhcampuswien.fhmdb.state;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;

import java.util.Comparator;

public class ASCState extends State{
    public ASCState(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void pressSortBtn() {
        homeController.setCurrentState(new DESCState(homeController));
        homeController.getMovies().sort(Comparator.reverseOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
    }

    public void pressFilterBtn(){
        homeController.getMovies().sort(Comparator.naturalOrder());
        homeController.sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
    }
}
