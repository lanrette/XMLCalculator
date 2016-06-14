import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 09.06.2016.
 */
public class GetXMLFileTest {
    List<String> testData = new ArrayList<>();

    @Before
    public void setUp () {
        testData.add("");
        testData.add(null);
        testData.add(" ");
    }

    @After
    public void teatDown () {
        testData.clear();
    }

    @Test
    public void testGetXMLFile1() throws Exception {
        for (String s: testData)
            XMLCalculator.getXMLFile(s);
    }
}
