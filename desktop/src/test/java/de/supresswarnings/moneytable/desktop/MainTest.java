package de.supresswarnings.moneytable.desktop;

import org.junit.Test;

public class MainTest {
    @Test
    public void test(){
        Main.main(new String[]{"-d"});
        Main.main(new String[]{"test"});
        Main.main(null);
    }
}
