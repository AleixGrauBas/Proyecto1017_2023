package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {
    @org.junit.jupiter.api.Test
    void readTable() {
        CSV obj=new CSV();
        String separator = System.getProperty( "file.separator" );
        assertNotNull(obj.readTable("src"+ separator + "miles_dollars.csv"));
    }

}