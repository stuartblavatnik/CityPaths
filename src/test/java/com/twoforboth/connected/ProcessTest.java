package com.twoforboth.connected;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.*;


public class ProcessTest {

    private Process process;
    private File file;

    @Before
    public void Prepare() {
        process = new Process();

        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("cities.txt").getFile());

    }


    @Test
    public void TestProcessFileNotFound() {

        process.process("invalidFile.txt", "New York", "London");
        assertEquals("File: invalidFile.txt not found", process.getStatus());
    }

    @Test
    public void TestProcessTrueCaseBostonHartford() {
        process.process(file.getAbsolutePath(), "Boston", "Hartford");
        assertEquals("yes", process.getStatus());
    }

    @Test
    public void TestProcessFalseCaseBostonTampa() {
        process.process(file.getAbsolutePath(), "Boston", "Tampa");
        assertEquals("no", process.getStatus());
    }

    @Test
    public void TestProcessFalseCaseBostonYpsilanti() {
        process.process(file.getAbsolutePath(), "Boston", "Ypsilanti");
        assertEquals("no", process.getStatus());
    }
}
