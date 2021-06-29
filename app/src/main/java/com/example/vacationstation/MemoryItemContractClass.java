package com.example.vacationstation;

import android.provider.BaseColumns;

public class MemoryItemContractClass {


        private MemoryItemContractClass(){}

        public static class MemoryEntry implements BaseColumns{
            public static final String TABLE_NAME = "memories";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_TAGS = "tags";
            public static final String COLUMN_NAME_COMMENT = "comment";
            public static final String COLUMN_NAME_COORDSLAT = "coordinatesLat";
            public static final String COLUMN_NAME_COORDSLON = "coordinatesLon";
            public static final String COLUMN_NAME_IMGPATH = "imgpath";


        }


}