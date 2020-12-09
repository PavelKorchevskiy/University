import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void firstTest() {
        Assert.assertEquals("Hello world! This is the best app", Main.getHelloMessage());
    }

   @Test
    public void secondTest() {
       Assert.assertEquals(33, Main.getHelloMessage().length());
    }

    @Test
    public void difficultTest() {
        Assert.assertTrue("How???", true);
    }
}
