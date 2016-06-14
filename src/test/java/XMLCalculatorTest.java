import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Александр on 04.06.2016.
 */
public class XMLCalculatorTest {
    XMLCalculator xmlCalc;

    @BeforeClass
    public void setUp() {
        xmlCalc = new XMLCalculator();
    }

    @AfterClass
    public void tearDown () {
        xmlCalc = null;
    }

    @Test
    public void testMain() throws Exception {

    }
    /*
    @Test(expected = NullPointerException.class)
    public void testGetXMLFile1() throws Exception {
        String s1 = null;

        XMLCalculator.getXMLFile(s1);
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetXMLFile2() throws Exception {
        String s1 = "";

        XMLCalculator.getXMLFile(s1);
    }
    */
    @Test
    public void testGetXMLSchemaFile() throws Exception {

    }

    @Test
    public void testParseXMLFile() throws Exception {

    }

    @Test
    public void testValidate() throws Exception {

    }

    @Test
    public void testGetNodesAndCalculate() throws Exception {

    }

    @Test
    public void testOperation() throws Exception {

    }

    @Test
    public void testWriteToXMLFile() throws Exception {

    }


}
