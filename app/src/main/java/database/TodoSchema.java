package database;

public class TodoSchema {
    public class TodoTable{
        public static final String NAME = "Todo";
        public class cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE ="date";
            public static final String URGENCY = "urgency";
            public static final String POMOS = "pomos";
        }
    }

}
