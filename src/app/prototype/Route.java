package app.prototype;

import java.util.ArrayList;

public class Route {
    //start and finish points
    private Place StartPlace;
    private Place FinishPlace;
    private ArrayList<Note> Notes; //notes about routes
    //
    //basic constructor
    public Route(Place startPoint, Place finishPoint) {
        StartPlace = startPoint;
        FinishPlace = finishPoint;
    }
}
