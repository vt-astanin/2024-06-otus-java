package tests;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnyTest {

    public void noAnnotate() {
        System.out.println("no annotate");
    }

    @Before
    public void testBefore() {
        System.out.println("Executing before test");
    }

    @Test
    public void testSuccess() {
        System.out.println("Executing success test");
    }

    @Test
    public void testFailure() {
        throw new RuntimeException("Test failed!");
    }

    @After
    public void testAfter(){
        System.out.println("Executing after test");
    }
}
