package com.sibich.myapplication_pass_manager;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Sibic_000 on 08.05.2017.
 */
public class SortEntriesByDate implements Comparator<Entry> {

    public int compare(Entry obj1, Entry obj2) {

        Date d1 = obj1.getDate();
        Date d2 = obj2.getDate();

        return d1.compareTo(d2);
    }
}
