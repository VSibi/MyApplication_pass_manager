package com.sibich.myapplication_pass_manager.database;

import java.util.UUID;

/**
 * Created by Sibic_000 on 09.05.2017.
 */
public class MyPassDbSchema {

    public static final class EntriesWebSitesTable {
        public static final String NAME = "entriesWebSites";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String WEBSITE = "website";
            public static final String DATE = "date";
        }
    }

    public static final class EntriesCreditCardsTable {
        public static final String NAME = "entriesCreditCards";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String NAME = "name";
            public static final String NUMBER = "number";
            public static final String DATE_CARD = "date_card";
            public static final String CWW = "cww";
            public static final String PIN_CODE = "pin_code";
            public static final String DATE = "date";
        }
    }

    public static final class PassTable {
        public static final String NAME = "pass";

        public static final class Cols {
            public static final String PASS = "pass";
        }
    }


}
