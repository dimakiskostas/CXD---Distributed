package model;

import java.util.ArrayList;

public class SampleList extends ArrayList<Sample> {
    public void print() {
        for (Sample s : this) {
            System.out.println(s.serialize(" "));
        }
    }
}

