package com.visma.fishing.queries;

public class Queries {


    public static final String LOGBOOK_FIND_DISTINCT =
            " SELECT  distinct (L.*) from LOGBOOK L" +
                    " JOIN LOGBOOK_CATCH LC on L.ID = LC.LOGBOOK_ID" +
                    " JOIN CATCH C on LC.CATCHLIST_ID = C.ID";

    public static final String LOGBOOK_FIND = "SELECT *  FROM LOGBOOK L";

    public static final String LOGBOOK_FIND_BY_DEPARTURE_PORT =
            LOGBOOK_FIND +
                    " LEFT JOIN DEPARTURE D ON l.DEPARTURE_ID = D.ID " +
                    " WHERE D.PORT LIKE ?1";

    public static final String LOGBOOK_FIND_BY_ARRIVAL_PORT =
            LOGBOOK_FIND +
                    " JOIN ARRIVAL A ON l.ARRIVAL_ID = A.ID " +
                    " WHERE A.PORT LIKE ?1";

    public static final String LOGBOOK_FIND_BY_SPECIES =
            LOGBOOK_FIND_DISTINCT +
                    " WHERE C.SPECIES LIKE ?1";

    public static final String LOGBOOK_FIND_WHERE_WEIGHT_IS_BIGGER =
            LOGBOOK_FIND_DISTINCT +
                    " WHERE C.WEIGHT >= ?1";

    public static final String LOGBOOK_FIND_WHERE_WEIGHT_IS_LOWER =
            LOGBOOK_FIND_DISTINCT +
                    " WHERE C.WEIGHT < ?1";

    public static final String LOGBOOK_FIND_BY_DEPARTURE_DATE =
            LOGBOOK_FIND +
                    " join DEPARTURE D on L.DEPARTURE_ID = D.ID" +
                    " where D.DATE between ?1 and ?2";


    public static final String END_OF_FISHING_FIND_START = "SELECT E.* FROM ENDOFFISHING E ";
    public static final String END_OF_FISHING_FIND_BY_DATE =
            END_OF_FISHING_FIND_START
                    + " WHERE E.DATE BETWEEN ?1 and ?2 ";


    public static final String DEPARTURE_FIND_START = "SELECT D.* FROM DEPARTURE D ";
    public static final String DEPARTURE_FIND_BY_PORT =
            DEPARTURE_FIND_START
                    + " WHERE D.PORT LIKE ?1 ";
    public static final String DEPARTURE_FIND_BY_DATE =
            DEPARTURE_FIND_START
                    + " WHERE D.DATE BETWEEN ?1 and ?2 ";


    public static final String CONFIGURATION_FIND_START = "SELECT C.* from CONFIGURATION C";
    public static final String CONFIGURATION_FIND_VALUE_BY_KEY = CONFIGURATION_FIND_START
            + " WHERE C.KEY = ?1";

    public static final String CATCH_FIND_START = "SELECT C.* FROM CATCH C ";
    public static final String CATCH_FIND_BY_SPECIES =
            CATCH_FIND_START
                    + " WHERE C.SPECIES LIKE ?1 ";
    public static final String CATCH_FIND_WITH_BIGGER_WEIGHT =
            CATCH_FIND_START
                    + " WHERE C.WEIGHT >= ?1 ";
    public static final String CATCH_FIND_WITH_LOWER_WEIGHT =
            CATCH_FIND_START
                    + " WHERE C.WEIGHT < ?1 ";


    public static final String ARRIVAL_START = "SELECT A.* FROM ARRIVAL A ";
    public static final String ARRIVAL_FIND_BY_PORT =
            ARRIVAL_START
                    + " WHERE A.PORT LIKE ?1 ";
    public static final String ARRIVAL_FIND_BY_DATE =
            ARRIVAL_START
                    + " WHERE A.DATE BETWEEN ?1 and ?2 ";


}
