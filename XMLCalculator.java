import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Simple XML calculator
 * Takes XML as input, validates it and gives out a XML result file
 * @author Alexander.
 * Test files:
 * C:\Lanrette\Java\XMLCalculator\Calculator.xsd
 * C:\Lanrette\Java\XMLCalculator\sampleTest2.xml
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
        String fileToParseName = "";
        String schemaFileName = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter filename");
            fileToParseName = br.readLine();
            System.out.println("Please enter XML Schema filename");
            schemaFileName = br.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " enter proper file name!");
        }

        Schema schema = getXMLSchemaFile(schemaFileName);
        xmlToParse = getXMLFile(fileToParseName);
        Document doc = parseXMLFile(xmlToParse);
        validate(schema, doc);

        System.out.println(doc.getDocumentURI());

        getNodesAndCalculate(doc);

        writeToXMLFile();

    }
    /**
     * Read XML file name from terminal and create a File object
     * @param fileToParseName
     * @return File object representation of given XML file
     */
    public static File getXMLFile(String fileToParseName) {
        return new File(fileToParseName);
    }

    /**
     * Read XML file name from terminal and create a File object
     * @param schemaFileName
     * @return Schema object representation of given XML Schema
     */
    public static Schema getXMLSchemaFile(String schemaFileName) {
        Schema schema = null;
        try {

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(new File(schemaFileName));
            //Schema schema = factory.newSchema(schemaFile);
            schema = factory.newSchema(schemaFile);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
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
     * Validate XML file
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

    /**
     *
     * @param doc
     */
    public static void getNodesAndCalculate (Document doc) {
        // Get Node object for "SimpleCalculator"
        Node simpleCalc = doc.getChildNodes().item(0);  //Костыль и магическое число! М.б. текст вместо узла

        // Get Node object for "expressions"
        Node expressions = simpleCalc.getChildNodes().item(1);

        // Get children of "expressions"
        NodeList expressionsChildren = expressions.getChildNodes();

        //System.out.println(expressionsChildren.getLength());
        for (int i = 0; i < expressionsChildren.getLength(); i++) {
            Node node = expressionsChildren.item(i);
            if (node.getNodeName().equals("expression")) {
                Node operation1 = node.getChildNodes().item(1);     //Костыль и магическое число!
                if (operation1.getNodeName().equals("operation"))
                    System.out.println("Result of " + i + " expression is: " + operation(operation1));
            }
        }
    }

    /**
     *
     * @param parent
     * @return
     */
    public static int operation(Node parent) {

        int result = 0; //!!!

        NodeList children = parent.getChildNodes();

        //store for arguments to calculate
        List<Integer> list = new ArrayList<>();

        if (children.item(1).getNodeName().equals("arg")) {   //check end of recursion
            for (int i = 0; i < children.getLength(); i++) {
                Node argument = children.item(i);
                String s = argument.getTextContent();
                //System.out.println("Проблема: " + s);
                try {
                    list.add(Integer.parseInt(s));  //Bad String
                } catch (Exception e) {

                }
            }
        } else {
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeName().equals("operation")) {
                    Node operationNode = children.item(i);
                    list.add(operation(operationNode));
                }
            }
        }

        //get type of mathematical operation
        String mathOpName = parent.getAttributes().getNamedItem("OperationType").getNodeValue();

        //do mathematical operations with arguments from list
        switch (mathOpName) {
            case "SUB":
                for (Integer i: list)
                    result -= i;
                break;
            case "SUM":
                for (Integer i: list)
                    result += i;
                break;
            case "MUL":
                for (Integer i: list)
                    result *= i;
                break;
            case "DIV":
                for (Integer i: list)
                    result /= i;
                break;
            default:
                throw new IllegalArgumentException("Incorrect mathematical operation type!");
        }
        return result;
    }

    /**
     * Writing result to output XML file
     */
    public static void writeToXMLFile() {

    }
}
