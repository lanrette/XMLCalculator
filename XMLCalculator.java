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
 * Simple XML calculator
 * Takes XML as input, validates it and gives out a XML result file
 * @author Alexander.
 * Test files:
 * C:\Lanrette\Java\Тестовое задание в Науку\Calculator.xsd
 * C:\Lanrette\Java\Тестовое задание в Науку\sampleTest2.xml
 */
public class XMLCalculator {
    /**
     * Representation of parsed XML file
     */
    private static File xmlToParse;

    /**
     * Main method
     * @param args
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        Schema schema = getXMLSchemaFile();
        xmlToParse = getXMLFile();
        Document doc = parseXMLFile(xmlToParse);
        validate(schema, doc);

        System.out.println(doc.getDocumentURI());
    }
    /**
     * Read XML file name from terminal and create a File object
     * @return File object representation of given XML file
     */
    public static File getXMLFile() {
        String fileToParseName = "";
        File file = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter filename");
            fileToParseName = br.readLine();
            file = new File(fileToParseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Read XML file name from terminal and create a File object
     * @return Schema object representation of given XML Schema
     */
    public static Schema getXMLSchemaFile () {
        String schemaFileName = "";
        Schema schema = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter XML Schema filename");
            schemaFileName = br.readLine();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(new File(schemaFileName));
            //Schema schema = factory.newSchema(schemaFile);
            schema = factory.newSchema(schemaFile);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema;
    }

    /**
     * Parse XML file
     * @param xmlToParse  File object of XML file to parse
     * @return Document object
     */
    public static Document parseXMLFile(File xmlToParse) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder parser = factory.newDocumentBuilder();
            doc = parser.parse(xmlToParse);
        } catch (SAXException  e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * Parse XML file
     * @param schema  XML Schema representated as Java object Schema
     * @param document  Document object of parsed XML file
     */
    public static void validate(Schema schema, Document document) {
        Validator validator = schema.newValidator();

        try {
            validator.validate(new DOMSource(document));
            System.out.println("XML document is valid!");
        } catch (SAXException e) {
            System.out.println("XML document is invalid because ");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
