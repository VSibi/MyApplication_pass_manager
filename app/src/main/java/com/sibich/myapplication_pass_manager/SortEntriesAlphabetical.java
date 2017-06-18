package com.sibich.myapplication_pass_manager;

import java.util.Comparator;

/**
 * Created by Sibic_000 on 08.05.2017.
 */
public class SortEntriesAlphabetical implements Comparator<Entry> {

    public int compare(Entry obj1, Entry obj2) {

        String s1 = obj1.getTitle().toUpperCase();
        String s2 = obj2.getTitle().toUpperCase();

        return s1.compareTo(s2);
    }

}
