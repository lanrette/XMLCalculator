import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

/**
 * Created by Александр on 17.05.2016.
 */
public class XMLCalculator {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileToParseName = "";
        String schemaFileName = "";

        try (BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter XML Schema filename");
            schemaFileName = br2.readLine();

            System.out.println("Please enter filename");
            fileToParseName = br2.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(new File(fileToParseName));

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        Source schemaFile = new StreamSource(new File(schemaFileName));
        Schema schema = factory.newSchema(schemaFile);

        Validator validator = schema.newValidator();

        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            System.out.println("XML document " + fileToParseName + " is invalid!");
        }
    }
}
