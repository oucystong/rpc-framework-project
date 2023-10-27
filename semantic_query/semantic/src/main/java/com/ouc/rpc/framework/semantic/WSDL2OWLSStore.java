package com.ouc.rpc.framework.semantic;

import com.ouc.rpc.framework.util.SwingUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @Description: WSDL2OWLS
 */
public class WSDL2OWLSStore extends JPanel implements ActionListener {


//    static {
//
//        String[] a = {};
//        String[] b = {};
//        ArrayList<String> wsdls = new ArrayList<>();
//        //1
//        wsdls.add("http://localhost:80/wsdl/1/AuthorSciencefictionbookrecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorSciencefictionbookprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorSciencefictionbookmaxprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorPublicationtaxfreeprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorPublicationtaxedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorPublicationrecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorPublicationmaxprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorNovelrecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorNovelprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorNovelmaxprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorMonographtaxfreeprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorMonographrecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorMonographprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorMonographmaxprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorBooktaxfreeprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorBooktaxedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorBookrecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/1/AuthorBookmaxprice.wsdl");
//
//        //2
//        wsdls.add("http://localhost:80/wsdl/2/BookTaxedpriceprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookReviewprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookRecommendedpriceindollar.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookRecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookReaderreview.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookReaderreviewperson.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookPublisher.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookPrice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookPricereviewbook.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookPerson.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/Booknonmedicaltransport.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/Booknonmedicalflight.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/Bookmedicaltransport.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/Bookmedicalflight.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookComputer.wsdl");
//        wsdls.add("http://localhost:80/wsdl/2/BookAuthortext.wsdl");
//
//        //3
//        wsdls.add("http://localhost:80/wsdl/3/CarYearprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarTechnology.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarTaxedpricereport.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarRecommendedpriceindollar.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarPricequality.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarPriceauto.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarcyclePrice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarbicycleTaxedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarbicycleRecommendedprice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/CarbicyclePrice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/car_taxedpriceprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/car_report_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/car_recommendedprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/car_pricereport_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/3/car_pricecolor_service.wsdl");
//
//        //4
//        wsdls.add("http://localhost:80/wsdl/4/novelperson_price_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_userreviewauthor_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_price_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_publisher_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_person_Reserverservice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_person_Writerservice.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authortime_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authortaxedprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authorrecommendedprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authorprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authormaxprice_service.wsdl");
//        wsdls.add("http://localhost:80/wsdl/4/novel_authorgenre_service.wsdl");
//
//        defaultWsdlFiles = wsdls.toArray(a);
//        ArrayList<String> owl = new ArrayList<>();
//
//        owl.add("http://localhost:80/owl/concept.owl");
//        owl.add("http://localhost:80/owl/data.rdfs");
//        owl.add("http://localhost:80/owl/func.rdfs");
//        owl.add("http://localhost:80/owl/ActorDefault.owl");
//        owl.add("http://localhost:80/owl/ApothecaryOntology.owl");
//        owl.add("http://localhost:80/owl/books.owl");
//        owl.add("http://localhost:80/owl/core-plus-office.owl");
//        owl.add("http://localhost:80/owl/Country.owl");
//        owl.add("http://localhost:80/owl/EMAOntology.owl");
//        owl.add("http://localhost:80/owl/EmergencyPhysicianOntology.owl");
//        owl.add("http://localhost:80/owl/Expression.owl");
//        owl.add("http://localhost:80/owl/extendedCamera.owl");
//        owl.add("http://localhost:80/owl/finance_th_web.owl");
//        owl.add("http://localhost:80/owl/geographydataset.owl");
//        owl.add("http://localhost:80/owl/Grounding.owl");
//        owl.add("http://localhost:80/owl/HealthInsuranceOntology.owl");
//        owl.add("http://localhost:80/owl/HospitalPhysicianOntology.owl");
//        owl.add("http://localhost:80/owl/HospitalReceptionOntology.owl");
//        owl.add("http://localhost:80/owl/MedicalFlightCompanyOntology.owl");
//        owl.add("http://localhost:80/owl/MedicalTransportCompanyOntology.owl");
//        owl.add("http://localhost:80/owl/messemodul.owl");
//        owl.add("http://localhost:80/owl/Mid-level-ontology.owl");
//        owl.add("http://localhost:80/owl/my_ontology.owl");
//        owl.add("http://localhost:80/owl/NonMedicalFlightCompanyOntology.owl");
//        owl.add("http://localhost:80/owl/NonMedicalTransportCompanyOntology.owl");
//        owl.add("http://localhost:80/owl/ObjectList.owl");
//        owl.add("http://localhost:80/owl/om2-1.owl");
//        owl.add("http://localhost:80/owl/ontosem.owl");
//        owl.add("http://localhost:80/owl/order.owl");
//        owl.add("http://localhost:80/owl/PatientOntology.owl");
//        owl.add("http://localhost:80/owl/PDDLExpression.owl");
//        owl.add("http://localhost:80/owl/portal.owl");
//        owl.add("http://localhost:80/owl/Process.owl");
//        owl.add("http://localhost:80/owl/Profile.owl");
//        owl.add("http://localhost:80/owl/ProfileDeprecatedElements.owl");
//        owl.add("http://localhost:80/owl/protons.owl");
//        owl.add("http://localhost:80/owl/protont.owl");
//        owl.add("http://localhost:80/owl/protonu.owl");
//        owl.add("http://localhost:80/owl/Service.owl");
//        owl.add("http://localhost:80/owl/ShoppingCart.owl");
//        owl.add("http://localhost:80/owl/simplified_sumo.owl");
//        owl.add("http://localhost:80/owl/spatial_ontology.owl");
//        owl.add("http://localhost:80/owl/SUMO.owl");
//        owl.add("http://localhost:80/owl/support.owl");
//        owl.add("http://localhost:80/owl/technical.owl");
//        owl.add("http://localhost:80/owl/time-entry.owl");
//        owl.add("http://localhost:80/owl/travel.owl");
//        owl.add("http://localhost:80/owl/TravelMessageOntology.owl");
//        owl.add("http://localhost:80/owl/Units.owl");
//        owl.add("http://localhost:80/owl/univ-bench.owl");
//
//        defaultOntFiles = owl.toArray(b);
//
//
//    }


    final String[] columnNames = {"Atomic Process", "WSDL Name", "WSDL Type", "XSD Type", "OWL-S Name", "OWL Type", "Similarity"};
    final String[] similarityColumnNames = {"WSDL Type", "Tmp OWL", "Domain OWL", "Name Score", "Attribute Score", "Similarity"};
    final String[][] emptyRow = new String[0][7];
    final String[][] similarityEmptyRow = new String[0][6];
    //    final String[] defaultWsdlFiles = {"http://localhost:80/wsdl/novel_author_service.wsdl", "http://localhost:80/wsdl/AuthorPublicationprice.wsdl","http://localhost:80/wsdl/BookAuthorprice.wsdl", "http://localhost:80/wsdl/BookAuthortext.wsdl", "http://localhost:80/wsdl/DegreeLending.wsdl", "http://localhost:80/wsdl/FoodPrice.wsdl",};
//    final String[] defaultWsdlFiles = {"http://localhost:80/wsdl/novel_author_service.wsdl", "http://localhost:80/wsdl/AuthorPublicationprice.wsdl","http://localhost:80/wsdl/BookAuthorprice.wsdl", "http://localhost:80/wsdl/BookAuthortext.wsdl", "http://localhost:80/wsdl/car_price_service.wsdl", "http://localhost:80/wsdl/car_pricecolor_service.wsdl"};
    final String[] defaultWsdlFiles = {
//            1
            "http://localhost:80/wsdl/1/AuthorPublicationtaxfreeprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorPublicationtaxedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorPublicationrecommendedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorPublicationmaxprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorNovelrecommendedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorNovelprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorNovelmaxprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorMonographtaxfreeprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorMonographrecommendedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorMonographprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorMonographmaxprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorBooktaxfreeprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorBooktaxedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorBookrecommendedprice.wsdl",
            "http://localhost:80/wsdl/1/AuthorBookmaxprice.wsdl",
//2

            "http://localhost:80/wsdl/2/BookTaxedpriceprice.wsdl",
            "http://localhost:80/wsdl/2/BookReviewprice.wsdl",
            "http://localhost:80/wsdl/2/BookRecommendedpriceindollar.wsdl",
            "http://localhost:80/wsdl/2/BookRecommendedprice.wsdl",
            "http://localhost:80/wsdl/2/BookReaderreview.wsdl",
            "http://localhost:80/wsdl/2/BookReaderreviewperson.wsdl",
            "http://localhost:80/wsdl/2/BookPublisher.wsdl",
            "http://localhost:80/wsdl/2/BookPrice.wsdl",
            "http://localhost:80/wsdl/2/BookPricereviewbook.wsdl",
            "http://localhost:80/wsdl/2/BookPerson.wsdl",
            "http://localhost:80/wsdl/2/BookComputer.wsdl",
            "http://localhost:80/wsdl/2/BookAuthortext.wsdl",

//3
            "http://localhost:80/wsdl/3/CarYearprice.wsdl",
            "http://localhost:80/wsdl/3/CarTechnology.wsdl",
            "http://localhost:80/wsdl/3/CarTaxedpricereport.wsdl",
            "http://localhost:80/wsdl/3/CarRecommendedpriceindollar.wsdl",
            "http://localhost:80/wsdl/3/CarPricequality.wsdl",
            "http://localhost:80/wsdl/3/CarcyclePrice.wsdl",
            "http://localhost:80/wsdl/3/car_taxedpriceprice_service.wsdl",
            "http://localhost:80/wsdl/3/car_report_service.wsdl",
            "http://localhost:80/wsdl/3/car_recommendedprice_service.wsdl",
            "http://localhost:80/wsdl/3/car_pricereport_service.wsdl",
            "http://localhost:80/wsdl/3/car_pricecolor_service.wsdl",

//4
            "http://localhost:80/wsdl/4/novelperson_price_service.wsdl",
            "http://localhost:80/wsdl/4/novel_userreviewauthor_service.wsdl",
            "http://localhost:80/wsdl/4/novel_price_service.wsdl",
            "http://localhost:80/wsdl/4/novel_publisher_service.wsdl",
            "http://localhost:80/wsdl/4/novel_person_Reserverservice.wsdl",
            "http://localhost:80/wsdl/4/novel_person_Writerservice.wsdl",
            "http://localhost:80/wsdl/4/novel_authortime_service.wsdl",
            "http://localhost:80/wsdl/4/novel_authortaxedprice_service.wsdl",
            "http://localhost:80/wsdl/4/novel_authorrecommendedprice_service.wsdl",
            "http://localhost:80/wsdl/4/novel_authorprice_service.wsdl",
            "http://localhost:80/wsdl/4/novel_authormaxprice_service.wsdl",
            "http://localhost:80/wsdl/4/novel_authorgenre_service.wsdl"


    };
    //    final String[] defaultOntFiles = {"http://127.0.0.1/ontology/books.owl", "http://127.0.0.1/ontology/concept.owl","http://127.0.0.1/ontology/concept.owl"};
//    final String[] defaultOntFiles = {"http://127.0.0.1/ontology/books.owl", "http://127.0.0.1/ontology/concept.owl","http://127.0.0.1/ontology/my_ontology.owl","http://127.0.0.1/ontology/finance_th_web.owl","http://127.0.0.1/ontology/portal.owl","http://127.0.0.1/ontology/travel.owl"};
    final String[] defaultOntFiles = {
            "http://localhost:80/ontology/concept.owl",
            "http://localhost:80/ontology/data.rdfs",
            "http://localhost:80/ontology/func.rdfs",
            "http://localhost:80/ontology/ActorDefault.owl",
            "http://localhost:80/ontology/ApothecaryOntology.owl",
            "http://localhost:80/ontology/books.owl",
            "http://localhost:80/ontology/core-plus-office.owl",
            "http://localhost:80/ontology/Country.owl",
            "http://localhost:80/ontology/EMAOntology.owl",
            "http://localhost:80/ontology/EmergencyPhysicianOntology.owl",
            "http://localhost:80/ontology/Expression.owl",
            "http://localhost:80/ontology/extendedCamera.owl",
            "http://localhost:80/ontology/finance_th_web.owl",
            "http://localhost:80/ontology/geographydataset.owl",
            "http://localhost:80/ontology/Grounding.owl",
            "http://localhost:80/ontology/HealthInsuranceOntology.owl",
            "http://localhost:80/ontology/HospitalPhysicianOntology.owl",
            "http://localhost:80/ontology/HospitalReceptionOntology.owl",
            "http://localhost:80/ontology/MedicalFlightCompanyOntology.owl",
            "http://localhost:80/ontology/MedicalTransportCompanyOntology.owl",
            "http://localhost:80/ontology/messemodul.owl",
            "http://localhost:80/ontology/Mid-level-ontology.owl",
            "http://localhost:80/ontology/my_ontology.owl",
            "http://localhost:80/ontology/NonMedicalFlightCompanyOntology.owl",
            "http://localhost:80/ontology/NonMedicalTransportCompanyOntology.owl",
            "http://localhost:80/ontology/ObjectList.owl",
            "http://localhost:80/ontology/om2-1.owl",
            "http://localhost:80/ontology/ontosem.owl",
            "http://localhost:80/ontology/order.owl",
            "http://localhost:80/ontology/PatientOntology.owl",
            "http://localhost:80/ontology/PDDLExpression.owl",
            "http://localhost:80/ontology/portal.owl",
            "http://localhost:80/ontology/Process.owl",
            "http://localhost:80/ontology/Profile.owl",
            "http://localhost:80/ontology/ProfileDeprecatedElements.owl",
            "http://localhost:80/ontology/protons.owl",
            "http://localhost:80/ontology/protont.owl",
            "http://localhost:80/ontology/protonu.owl",
            "http://localhost:80/ontology/Service.owl",
            "http://localhost:80/ontology/ShoppingCart.owl",
            "http://localhost:80/ontology/simplified_sumo.owl",
            "http://localhost:80/ontology/spatial_ontology.owl",
            "http://localhost:80/ontology/SUMO.owl",
            "http://localhost:80/ontology/support.owl",
            "http://localhost:80/ontology/technical.owl",
            "http://localhost:80/ontology/time-entry.owl",
            "http://localhost:80/ontology/travel.owl",
            "http://localhost:80/ontology/TravelMessageOntology.owl",
            "http://localhost:80/ontology/Units.owl",
            "http://localhost:80/ontology/univ-bench.owl"
    };

//    public WSDL2OWLSStore(String[] defaultWsdlFiles, String[] defaultOntFiles) {
//        this.defaultWsdlFiles = defaultWsdlFiles;
//        this.defaultOntFiles = defaultOntFiles;
//    }

    final String[] defaultServiceCategory = {"Consumer Oriented", "Supply Oriented", "Function Oriented"};
    // init
    JComboBox wsdlUrls = new JComboBox(defaultWsdlFiles);
    JComboBox ontUrls = new JComboBox(defaultOntFiles);
    // wsdl operations
    JList opList = new JList();
    // input parameters
    JTable inputTable = new JTable(emptyRow, columnNames);
    // output parameters
    JTable outputTable = new JTable(emptyRow, columnNames);
    // similarity table
    JTable similarityTable = new JTable(similarityEmptyRow, similarityColumnNames);
    // service info
    JTextField nameSpaceField = new JTextField();
    JTextField serviceNameField = new JTextField();
    JTextField serviceIdField = new JTextField();
    JComboBox serviceCategory = new JComboBox(defaultServiceCategory);
    JTextArea serviceDescField = new JTextArea(10, 20);
    JTextField targetNameSpaceField = new JTextField();

    // function area
    JButton saveButton, displayTmpOntButton, displayOWLSButton, ontMappingButton, serviceRegisterButton;
    // temporary ontology
    OntModel tmpOntology;

    /**
     * @Description: WSDL解析器
     */
    private WSDLParser wsdlParser;

    /**
     * @Description: 将WSDL转化为临时本体的转换器
     */
    private WSDL2OWL wsdl2OWL;

    /**
     * @Description: 相似度计算器
     */
    private SimilarityCalculator similarityCalculator;

    private HashMap<String, ArrayList<TableModel>> inputAndOutputMapperData = new HashMap<>();
//    private HashMap<String, ArrayList<TableModel>> inputAndOutputMapperDataCopy;

    private String selectedOperation = "";

    private ArrayList<String> operations;


    public WSDL2OWLSStore() {
//    public WSDL2OWLSStore(String[] defaultWsdlFiles, String[] defaultOntFiles) {

        // 设置信息
//        this.defaultWsdlFiles = defaultWsdlFiles;
//        this.defaultOntFiles = defaultOntFiles;


        // set panel
        JPanel contentPane = new JPanel();
        JPanel addressPanel = new JPanel();
        JPanel servicePanel = new JPanel();
        JPanel middlePanel = new JPanel();
        // operationsPanel and detailsPanel belong middlePanel
        JPanel operationsPanel = new JPanel();
        JPanel detailsPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        // set addressPanel
        wsdlUrls.setEditable(false);
        wsdlUrls.setSelectedItem(defaultWsdlFiles[0]);
        wsdlUrls.setActionCommand("load");
        wsdlUrls.addActionListener(this);

        ontUrls.setEditable(false);
        ontUrls.setSelectedItem(defaultWsdlFiles[0]);
        ontUrls.setActionCommand("ontology_mapping");
        ontUrls.addActionListener(this);

        addressPanel = SwingUtils.createTableLayout(new JComponent[]{new JLabel("WSDL URL: "), new JLabel("Ontology URL: ")}, new JComponent[]{wsdlUrls, ontUrls});

        addressPanel.setBorder(BorderFactory.createTitledBorder("URL Address Information"));

        // set servicePanel
        serviceDescField.setLineWrap(true);
        serviceDescField.setWrapStyleWord(true);
        serviceDescField.setEditable(true);
        JScrollPane textDescriptionPane = new JScrollPane(serviceDescField);

        serviceNameField.setEditable(true);
        nameSpaceField.setEditable(true);

        textDescriptionPane.setPreferredSize(new Dimension(textDescriptionPane.getPreferredSize().width, 40));
        textDescriptionPane.setMinimumSize(new Dimension(textDescriptionPane.getPreferredSize().width, 40));

        JPanel leftServicePanel = SwingUtils.createTableLayout(new JComponent[]{new JLabel("Service Name: "), new JLabel("Service Desc: "), new JLabel("WSDL NameSpace: "),}, new JComponent[]{serviceNameField, textDescriptionPane, nameSpaceField,});

        serviceIdField.setEditable(true);
        serviceCategory.setEditable(false);
        serviceCategory.setSelectedItem(defaultServiceCategory[0]);
        targetNameSpaceField.setEditable(true);

        JPanel rightServicePanel = SwingUtils.createTableLayout(new JComponent[]{new JLabel("Service ID: "), new JLabel("Service Category: "), new JLabel("OWL-S NameSpace: "),}, new JComponent[]{serviceIdField, serviceCategory, targetNameSpaceField,});

        servicePanel.setLayout(new BoxLayout(servicePanel, BoxLayout.X_AXIS));
        servicePanel.setBorder(BorderFactory.createTitledBorder("OWL-S Service Information"));
        servicePanel.add(rightServicePanel);
        servicePanel.add(new JSeparator(SwingConstants.VERTICAL));
        servicePanel.add(leftServicePanel);


        // set layout
        setLayout(new GridLayout(1, 1));
        add(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        contentPane.add(addressPanel);
        contentPane.add(Box.createVerticalStrut(2));
        contentPane.add(servicePanel);
        contentPane.add(Box.createVerticalStrut(2));
        contentPane.add(middlePanel);
        contentPane.add(Box.createVerticalStrut(2));
        contentPane.add(buttonPanel);


        // set middlePanel
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.add(operationsPanel);
        middlePanel.add(detailsPanel);
        // set middlePanel operationsPanel
        operationsPanel.setBorder(BorderFactory.createTitledBorder("WSDL Operations"));
        operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.X_AXIS));

        JScrollPane jScrollPane = new JScrollPane(opList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(opList.getPreferredSize().width, jScrollPane.getPreferredSize().height));

        operationsPanel.add(jScrollPane);
        opList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        opList.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            doSelect();
        });


        // set middlePanel detailsPanel
        JPanel inputsPanel = new JPanel();
        JPanel outputsPanel = new JPanel();
        JPanel similarityPanel = new JPanel();
//        JPanel nsPanel = new JPanel();


        // set inputsPanel
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBorder(BorderFactory.createTitledBorder("Inputs Parameters"));

        // 设置单元格渲染器
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // 设置单元格内容过长时使用悬停提示显示完整内容
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setToolTipText(label.getText());
                }

                return component;
            }
        };

        // 设置单元格渲染器到表格
        inputTable.setDefaultRenderer(Object.class, renderer);

        inputsPanel.add(new JScrollPane(inputTable));
        inputsPanel.add(Box.createVerticalStrut(2));


        // set outputsPanel
        outputsPanel.setLayout(new BoxLayout(outputsPanel, BoxLayout.Y_AXIS));
        outputsPanel.setBorder(BorderFactory.createTitledBorder("Outputs Parameters"));

        // 设置单元格渲染器到表格
        outputTable.setDefaultRenderer(Object.class, renderer);

        outputsPanel.add(new JScrollPane(outputTable));
        outputsPanel.add(Box.createVerticalStrut(2));


        // set similarityPanel
        similarityPanel.setLayout(new BoxLayout(similarityPanel, BoxLayout.Y_AXIS));
        similarityPanel.setBorder(BorderFactory.createTitledBorder("Ontology Mapping And Similarity Score"));

        similarityTable.setDefaultRenderer(Object.class, renderer);

        similarityPanel.add(new JScrollPane(similarityTable));
        similarityPanel.add(Box.createVerticalStrut(2));

        // set detailsPanel
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(inputsPanel);
        detailsPanel.add(outputsPanel);
        detailsPanel.add(similarityPanel);
//        detailsPanel.add(nsPanel);


        // set buttonPanel
        displayTmpOntButton = new JButton("Display Temporary Ontology");
        displayTmpOntButton.setEnabled(true);
        displayTmpOntButton.setActionCommand("display_ontology");
        displayTmpOntButton.addActionListener(this);

        ontMappingButton = new JButton("Auto Mapping");
        ontMappingButton.setEnabled(true);
        ontMappingButton.setActionCommand("auto_mapping");
        ontMappingButton.addActionListener(this);

        saveButton = new JButton("Save Mapping");
        saveButton.setEnabled(false);
        saveButton.setActionCommand("save_mapping");
        saveButton.addActionListener(this);

        displayOWLSButton = new JButton("Display OWL-S");
        displayOWLSButton.setEnabled(false);
        displayOWLSButton.setActionCommand("display_owls");
        displayOWLSButton.addActionListener(this);

        serviceRegisterButton = new JButton("Register");
        serviceRegisterButton.setEnabled(false);
        serviceRegisterButton.setActionCommand("service_register");
        serviceRegisterButton.addActionListener(this);


        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("close");
        closeButton.addActionListener(this);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(displayTmpOntButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(ontMappingButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(displayOWLSButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(serviceRegisterButton);

        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);

        // init
        doLoad();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) doLoad();
        else if (e.getActionCommand().equals("display_ontology")) doDisplayOntology();
        else if (e.getActionCommand().equals("display_owls")) doDisplayOWLS();
        else if (e.getActionCommand().equals("save_mapping")) doSaveMapping();
        else if (e.getActionCommand().equals("ontology_mapping")) doOntologyMapping();
        else if (e.getActionCommand().equals("auto_mapping")) doAutoMapping();
        else if (e.getActionCommand().equals("service_register")) doServiceRegister();
        else if (e.getActionCommand().equals("close")) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) window.dispose();
            else System.exit(0);
        } else if (e.getActionCommand().equals("load")) doLoad();
    }

    /**
     * @Description: 执行自动映射 |  选择最高相似度的领域本体概念
     */
    private void doAutoMapping() {
        // 有效性判断
        selectedOperation = (String) opList.getSelectedValue();
        if (selectedOperation == null) {
            SwingUtils.showWarningMessage("Operation Tips", "请先选择Operation进行映射！");
            return;
        }
        // 获取当前输入框和输出框的model | 获取所有本体映射的model
        TableModel inputModel = inputTable.getModel();
        TableModel outputModel = outputTable.getModel();
        TableModel similarityModel = similarityTable.getModel();
        if (inputModel.getRowCount() == 0 && outputModel.getRowCount() == 0) {
            SwingUtils.showWarningMessage("Input Tips", "当前输入没有参数可以执行自动映射！");
            return;
        }
        if (similarityModel.getRowCount() == 0) {
            SwingUtils.showWarningMessage("Output Tips", "当前输出没有参数可以执行自动映射！");
            return;
        }

        // 正常逻辑
        // 1 输入参数
        for (int i = 0; i < inputModel.getRowCount(); i++) {
            if (inputModel.getValueAt(i, 5) == null && inputModel.getValueAt(i, 6) == null) {
                List<String> inputOwlTypeAndSimilarity = getOWLTypeAndSimilarity((String) (inputModel.getValueAt(i, 2)), similarityModel);
                if (inputOwlTypeAndSimilarity.size() != 0) {
                    inputModel.setValueAt(inputOwlTypeAndSimilarity.get(0) + "\n" + inputOwlTypeAndSimilarity.get(2) + "\n" + inputOwlTypeAndSimilarity.get(3), i, 5);
                    inputModel.setValueAt(inputOwlTypeAndSimilarity.get(1), i, 6);
                }
            }
        }

        // 2 输出参数
        for (int i = 0; i < outputModel.getRowCount(); i++) {
            if (outputModel.getValueAt(i, 5) == null && outputModel.getValueAt(i, 6) == null) {
                List<String> outputOwlTypeAndSimilarity = getOWLTypeAndSimilarity((String) (outputModel.getValueAt(i, 2)), similarityModel);
                if (outputOwlTypeAndSimilarity.size() != 0) {
                    outputModel.setValueAt(outputOwlTypeAndSimilarity.get(0) + "\n" + outputOwlTypeAndSimilarity.get(2) + "\n" + outputOwlTypeAndSimilarity.get(3), i, 5);
                    outputModel.setValueAt(outputOwlTypeAndSimilarity.get(1), i, 6);
                }
            }
        }

        // 3 check是否可以保存映射关系 | 并且修改保存按钮状态
        checkSaveButtonStatus(inputModel, outputModel);

    }

    private void checkSaveButtonStatus(TableModel inputModel, TableModel outputModel) {
        for (int i = 0; i < inputModel.getRowCount(); i++) {
            Object value1 = inputModel.getValueAt(i, 5);
            Object value2 = inputModel.getValueAt(i, 6);
            if (value1 == null || value2 == null) {
                saveButton.setEnabled(false);
                return;
            }
        }

        for (int i = 0; i < outputModel.getRowCount(); i++) {
            Object value1 = outputModel.getValueAt(i, 5);
            Object value2 = outputModel.getValueAt(i, 6);
            if (value1 == null || value2 == null) {
                saveButton.setEnabled(false);
                return;
            }
        }

        saveButton.setEnabled(true);

    }


    /**
     * @Description: 从相似度得分表格中选取合适的OWL概念及其相似度
     */
    private List<String> getOWLTypeAndSimilarity(String wsdlType, TableModel similarityModel) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < similarityModel.getRowCount(); i++) {

            String wsdl = (String) (similarityModel.getValueAt(i, 0));
            String simiTmp = (String) (similarityModel.getValueAt(i, 5));
            double similarity = Double.parseDouble(simiTmp.replace("%", "")) / 100.0;

            // 阈值设置为70%
            if (wsdl.equals(wsdlType) && similarity > 0.090) {
                result.add((String) (similarityModel.getValueAt(i, 2)));
                result.add((String) (similarityModel.getValueAt(i, 5)));
                // 领域本体文件路径
                String owlPath = ontUrls.getSelectedItem().toString().replaceAll(" ", "%20");
                result.add(owlPath);
                // 领域本体文件的命名空间
                result.add(OntologyParser.getTNSOfOntology(owlPath));
                // 找到退出循环
                break;
            }
        }

        return result;
    }


    /**
     * @Description: 服务语义信息的注册
     */
    private void doServiceRegister() {

        String uri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "tongyusheng";
        String namespace = targetNameSpaceField.getText() + "#";
        // 注册数据
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password)); Session session = driver.session()) {

            // 实例节点
            session.run("CREATE (:Service {name:\'" + serviceNameField.getText() + "\'," +
                    "referName:\'" + namespace + serviceNameField.getText() + "\'})");


            session.run("CREATE (:ServiceProfile {name:\'" + serviceNameField.getText() + "Profile\'" + "," +
                    "referName:\'" + namespace + serviceNameField.getText() + "Profile\'" + "," +
                    "serviceName:\'" + serviceNameField.getText() + "\'," +
                    "serviceId:\'" + serviceIdField.getText() + "\'," +
                    "serviceCategory:\'" + serviceCategory.getSelectedItem().toString() + "\'," +
                    "textDescription:\'" + serviceDescField.getText() + "\'})");
            session.run("CREATE (:ServiceGrounding {name:\'" + serviceNameField.getText() + "Grounding" + "\'," +
                    "referName:\'" + namespace + serviceNameField.getText() + "Grounding" + "\'})");
            session.run("CREATE (:ServiceModel {name:\'" + serviceNameField.getText() + "ProcessModel" + "\'," +
                    "referName:\'" + namespace + serviceNameField.getText() + "ProcessModel" + "\'})");

            // 创建关系
            session.run("MATCH (front:Service {referName:\'" + namespace + serviceNameField.getText() + "\'}),(behind:ServiceGrounding{referName:\'" + namespace + serviceNameField.getText() + "Grounding" + "\'})\n" +
                    "CREATE (front)-[:supports]->(behind),(behind)-[:supportedBy]->(front)");
            session.run("MATCH (front:Service {referName:\'" + namespace + serviceNameField.getText() + "\'}),(behind:ServiceProfile{referName:\'" + namespace + serviceNameField.getText() + "Profile" + "\'})\n" +
                    "CREATE (front)-[:presents]->(behind),(behind)-[:presentedBy]->(front)");
            session.run("MATCH (front:Service {referName:\'" + namespace + serviceNameField.getText() + "\'}),(behind:ServiceModel{referName:\'" + namespace + serviceNameField.getText() + "ProcessModel" + "\'})\n" +
                    "CREATE (front)-[:describedBy]->(behind),(behind)-[:describes]->(front)");

            inputAndOutputMapperData.forEach(new BiConsumer<String, ArrayList<TableModel>>() {
                @Override
                public void accept(String s, ArrayList<TableModel> tableModels) {
                    // 创建AtomicProcess节点
                    session.run("CREATE (:AtomicProcess {name:\'" + s + "_Process" + "\'," +
                            "referName:\'" + namespace + s + "_Process" + "\'})");

                    // 创建关系
                    session.run("MATCH (front:ServiceProfile {referName:\'" + namespace + serviceNameField.getText() + "Profile" + "\'}),(behind:AtomicProcess{referName:\'" + namespace + s + "_Process" + "\'})\n" +
                            "CREATE (front)-[:has_process]->(behind)");

                    session.run("MATCH (front:ServiceModel {referName:\'" + namespace + serviceNameField.getText() + "ProcessModel" + "\'}),(behind:AtomicProcess{referName:\'" + namespace + s + "_Process" + "\'})\n" +
                            "CREATE (front)-[:hasProcess]->(behind)");

                    // 创建输入节点
                    TableModel inputModel = tableModels.get(0);
                    TableModel outputModel = tableModels.get(1);
                    for (int i = 0; i < inputModel.getRowCount(); i++) {
                        String inputParaName = (String) (inputModel.getValueAt(i, 4));
                        String owlType = (String) (inputModel.getValueAt(i, 5));
                        String inputParaType = owlType.substring(owlType.indexOf("\n") + 1, owlType.lastIndexOf("\n")) + "#" + owlType.substring(0, owlType.indexOf("\n"));
                        session.run("CREATE (:Input {name:\'" + inputParaName + "\'," +
                                "referName:\'" + namespace + inputParaName + "\'," +
                                "parameterType:\'" + inputParaType +
                                "\'})");
                        // 创建关系
                        session.run("MATCH (front:AtomicProcess {referName:\'" + namespace + s + "_Process" + "\'}),(behind:Input{referName:\'" + namespace + inputParaName + "\'})\n" +
                                "CREATE (front)-[:hasInput]->(behind)");

                    }
                    // 创建输出节点
                    for (int i = 0; i < outputModel.getRowCount(); i++) {
                        String outputParaName = (String) (outputModel.getValueAt(i, 4));
                        String owlType = (String) (outputModel.getValueAt(i, 5));
                        String outputParaType = owlType.substring(owlType.indexOf("\n") + 1, owlType.lastIndexOf("\n")) + "#" + owlType.substring(0, owlType.indexOf("\n"));
                        session.run("CREATE (:Output {name:\'" + outputParaName + "\'," +
                                "referName:\'" + namespace + outputParaName + "\'," +
                                "parameterType:\'" + outputParaType +
                                "\'})");
                        // 创建关系
                        session.run("MATCH (front:AtomicProcess {referName:\'" + namespace + s + "_Process" + "\'}),(behind:Output{referName:\'" + namespace + outputParaName + "\'})\n" +
                                "CREATE (front)-[:hasOutput]->(behind)");
                    }

                }
            });

            // 清空数据
            inputAndOutputMapperData.clear();
            // 改变注册按钮状态
            serviceRegisterButton.setEnabled(false);
            // 展示提示信息
            SwingUtils.showInfoMessage("Save Tips", "当前服务的语义信息已成功注册！");
        }


    }


    private void doOntologyMapping() {
        // 获取领域本体模型
        String owlFilePath = ontUrls.getSelectedItem().toString().replaceAll(" ", "%20");
        OntModel tarOntModel = ModelFactory.createOntologyModel();
        tarOntModel.read(owlFilePath);

        // 语义标注 | 本体映射求解相似度得分和相似度比
        fillSimilarityScore(tmpOntology, tarOntModel, similarityTable);
    }


    private void fillSimilarityScore(OntModel srcOntModel, OntModel tarOntModel, JTable table) {

        // DefaultTableModel model = new DefaultTableModel(similarityColumnNames, 0);

        List<OntClass> srcOntClasses = srcOntModel.listClasses().filterKeep(ontClass -> !ontClass.isAnon()).toList();
        // 存储所有的关系和属性
        HashMap<String, List<String>> srcOntClassesProperties = new HashMap<>();
        // 遍历并解析
        List<OntProperty> ontProperties = srcOntModel.listAllOntProperties().filterKeep(s -> !s.isAnon()).filterKeep(s -> s.isDatatypeProperty() || s.isObjectProperty()).toList();
        ontProperties.forEach(ontProperty -> {
            String key = ontProperty.getDomain().getLocalName();
            String value = ontProperty.getLocalName();
            if (srcOntClassesProperties.containsKey(key)) {
                srcOntClassesProperties.get(key).add(value);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(value);
                srcOntClassesProperties.put(key, list);
            }
        });
        List<OntClass> tarOntClasses = tarOntModel.listClasses().filterKeep(ontClass -> !ontClass.isAnon()).toList();

        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        DecimalFormat decimalFormat1 = new DecimalFormat("#0.000");

        Vector<Vector<Object>> data = new Vector<>();

        HashMap<String, String> mapperData = wsdl2OWL.getMapperData();

        for (OntClass srcClass : srcOntClasses) {
            for (OntClass tarClass : tarOntClasses) {

                Double conceptSimilarity = similarityCalculator.getConceptSimilarity(srcClass, tarClass);
                List<String> srcProperties = srcOntClassesProperties.get(srcClass.getLocalName());
                Double attributeSimilarity = similarityCalculator.getAttributeSimilarity(srcClass, srcProperties, tarClass);
                Double similarity = conceptSimilarity * 0.9 + attributeSimilarity * 0.1;

                Vector<Object> itemRowData = new Vector<>();

                itemRowData.add(mapperData.get(srcClass.getLocalName()));// wsdl type

                itemRowData.add(srcClass.getLocalName());
                itemRowData.add(tarClass.getLocalName());
                itemRowData.add(decimalFormat1.format(conceptSimilarity));
                itemRowData.add(decimalFormat1.format(attributeSimilarity));
                itemRowData.add(decimalFormat.format(similarity));

                data.add(itemRowData);
            }
        }

        // 根据相似度降序
        data.sort((o1, o2) -> {
            String str1 = (String) o2.get(5);
            String str2 = (String) o1.get(5);
            Double value1 = Double.parseDouble(str1.replace("%", "")) / 100.0;
            Double value2 = Double.parseDouble(str2.replace("%", "")) / 100.0;
            return value1.compareTo(value2);
        });

        Vector<String> columnNames = new Vector<>(Arrays.asList(similarityColumnNames));

        // 设置不可编辑的数据模型
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 设置数据到表格中
        table.setModel(model);
    }


    /**
     * @Description: 展示对于WSDL文件生成的临时本体
     */
    public void doDisplayOntology() {
        StringWriter stringWriter = new StringWriter();
        tmpOntology.write(stringWriter, "RDF/XML-ABBREV");
        SwingUtils.showMessage("Temporary Ontology", stringWriter.toString());
    }

    /**
     * @Description: 生成OWL-S上层本体实例文档
     */
    public void doDisplayOWLS() {

        // 创建一个空的本体模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // 创建导入的本体模型
        OntModel topModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        topModel.read("http://127.0.0.1/unionOWL/OWL-S-1.0-Union-Ontology-1.0.0.owl");
        ontModel.setNsPrefix("srpc", "http://www.srpc.com/owl-s-1.0-union-ontology#");

        // 导入本体模型
        ontModel.addSubModel(topModel);

        // 创建本体的命名空间
        String namespace = targetNameSpaceField.getText() + "#";
        ontModel.setNsPrefix("", namespace);

        // 创建本体实例
        Ontology ontology = ontModel.createOntology(null);
        ontology.addImport(ResourceFactory.createResource("http://127.0.0.1/unionOWL/OWL-S-1.0-Union-Ontology-1.0.0.owl"));
        // 存储领域本体文件
        ArrayList<String> allResource = new ArrayList<>();
        // 循环遍历进行查找
        inputAndOutputMapperData.forEach((s, tableModels) -> tableModels.forEach(tableModel -> {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String value = (String) tableModel.getValueAt(i, 5);
                String newValue = value.substring(value.indexOf("\n") + 1);
                if (!allResource.contains(newValue)) {
                    allResource.add(newValue);
                }
            }
        }));

        // 导入
        allResource.forEach(s -> {
            ontology.addImport(ResourceFactory.createResource(s.substring(0, s.lastIndexOf("\n"))));
        });

        // 获取上层本体中的类
        OntClass serviceClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Service");
        OntClass serviceProfileClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#ServiceProfile");
        OntClass serviceModelClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#ServiceModel");
        OntClass serviceGroundingClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#ServiceGrounding");
        OntClass processModelClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#ProcessModel");
        OntClass inputClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Input");
        OntClass outputClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Output");
        OntClass wsdlGroundingClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#WsdlGrounding");
        OntClass profileClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Profile");
        OntClass processClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Process");
        OntClass simpleProcessClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#SimpleProcess");
        OntClass compositeProcessClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#CompositeProcess");
        OntClass atomicProcessClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#AtomicProcess");

        Individual serviceIndividual = ontModel.createIndividual(namespace + serviceNameField.getText(), serviceClass);
        Individual profileIndividual = ontModel.createIndividual(namespace + serviceNameField.getText() + "Profile", profileClass);
        Individual processModelIndividual = ontModel.createIndividual(namespace + serviceNameField.getText() + "ProcessModel", processModelClass);
        Individual wsdlGroundingIndividual = ontModel.createIndividual(namespace + serviceNameField.getText() + "Grounding", wsdlGroundingClass);

        serviceIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#presents"), profileIndividual);
        serviceIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#describedBy"), processModelIndividual);
        serviceIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#supports"), wsdlGroundingIndividual);

        profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#presentedBy"), serviceIndividual);
        profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#serviceId"), serviceIdField.getText());
        profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#serviceName"), serviceNameField.getText());
        profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#serviceCategory"), serviceCategory.getSelectedItem().toString());
        profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#textDescription"), serviceDescField.getText());

        processModelIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#describes"), serviceIndividual);

        wsdlGroundingIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#supportedBy"), serviceIndividual);

        // 多个operation对应多个atomicprocess
        ArrayList<Individual> atomicProcessList = new ArrayList<>();

        inputAndOutputMapperData.keySet().forEach(s -> {

            Individual atomicProcessIndividual = ontModel.createIndividual(namespace + s + "_Process", atomicProcessClass);
            atomicProcessList.add(atomicProcessIndividual);

            atomicProcessIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#name"), s);

            // 创建输入 输出
            TableModel inputTableModel = inputAndOutputMapperData.get(s).get(0);
            TableModel outputTableModel = inputAndOutputMapperData.get(s).get(1);

            // 遍历 输入
            for (int i = 0; i < inputTableModel.getRowCount(); i++) {
                Individual individual = ontModel.createIndividual(namespace + (String) (inputTableModel.getValueAt(i, 4)), inputClass);
                // 属性值
                String owlType = (String) (inputTableModel.getValueAt(i, 5));
                String value = owlType.substring(owlType.indexOf("\n") + 1, owlType.lastIndexOf("\n")) + "#" + owlType.substring(0, owlType.indexOf("\n"));
                individual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#parameterType"), value);

                atomicProcessIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#hasInput"), individual);

            }
            // 遍历输出
            for (int i = 0; i < outputTableModel.getRowCount(); i++) {
                Individual individual = ontModel.createIndividual(namespace + (String) (outputTableModel.getValueAt(i, 4)), outputClass);
                // 属性值
                String owlType = (String) (outputTableModel.getValueAt(i, 5));
                String value = owlType.substring(owlType.indexOf("\n") + 1, owlType.lastIndexOf("\n")) + "#" + owlType.substring(0, owlType.indexOf("\n"));
                individual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#parameterType"), value);

                atomicProcessIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#hasOutput"), individual);

            }

        });

        atomicProcessList.forEach(individual -> {
            profileIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#has_process"), individual);
            processModelIndividual.addProperty(ontModel.getProperty("http://www.srpc.com/owl-s-1.0-union-ontology#hasProcess"), individual);
        });


        displayOWLSButton.setEnabled(false);

        StringWriter stringWriter = new StringWriter();
        ontModel.write(stringWriter, "RDF/XML-ABBREV");
        SwingUtils.showMessage("OWL-S Top Ontology Service Instance", stringWriter.toString());

//        try {
//            ontModel.write(new FileOutputStream("instance.owl"), "RDF/XML-ABBREV");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

    }


    void doLoad() {

        String wsdlFilePath = wsdlUrls.getSelectedItem().toString().replaceAll(" ", "%20");

        wsdlParser = new WSDLParser(wsdlFilePath);

        // get service info
        HashMap<String, String> serviceInfo = wsdlParser.getServiceInfoName();

        serviceNameField.setText(serviceInfo.get(WSDLParser.SERVICE_NAME));
        nameSpaceField.setText(serviceInfo.get(WSDLParser.SERVICE_TARGET_NAMESPACE));
        serviceDescField.setText(serviceInfo.get(WSDLParser.SERVICE_DESC));
        targetNameSpaceField.setText(serviceInfo.get(WSDLParser.SERVICE_TARGET_NAMESPACE) + "/owls");
        serviceIdField.setText("/srpc/authorService/com.ouc.api.AuthorService/provider");

        // get wsdl operations
        ArrayList<String> serviceOperations = wsdlParser.getServiceOperations();
        operations = serviceOperations;
        opList.setListData(serviceOperations.toArray());

        // get temporary ontology
        wsdl2OWL = new WSDL2OWL(wsdlParser);
        tmpOntology = wsdl2OWL.getTmpOntologyFromWSDL();

        // similarity calculator
        similarityCalculator = new SimilarityCalculator();

        // execute
        doOntologyMapping();

    }


    private void doSelect() {

        selectedOperation = (String) opList.getSelectedValue();

        if (selectedOperation == null) {
            return;
        }

        // 判断是否执行本体映射 | 未执行的话提醒用户执行
        if (similarityTable.getModel().getRowCount() == 0) {
            SwingUtils.showErrorMessage("Refresh Tips", "请调整WSDL文档和领域本体文档实现本体映射！");
        }

        HashMap<String, List<Map<String, String>>> inAndOutOfOperation = wsdlParser.getInAndOutOfOperation(selectedOperation);
        List<Map<String, String>> inputMaps = inAndOutOfOperation.get(WSDLParser.INPUT_PARAMETERS);
        List<Map<String, String>> outputMaps = inAndOutOfOperation.get(WSDLParser.OUTPUT_PARAMETERS);

        addParams(inputMaps, inputTable, selectedOperation + "_PROCESS", "_IN");
        addParams(outputMaps, outputTable, selectedOperation + "_PROCESS", "_OUT");

    }

    private void addParams(List<Map<String, String>> params, JTable table, String atomicProcessName, String suffix) {

        // 不可编辑的数据模型
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 处理每一个参数
        params.forEach(new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                String[] rowData = new String[columnNames.length];

                String wsdlName = stringStringMap.get(WSDLParser.WSDL_NAME);
                String wsdlType = stringStringMap.get(WSDLParser.WSDL_TYPE);
                String xsdType = stringStringMap.get(WSDLParser.XSD_TYPE);

                rowData[0] = atomicProcessName;
                rowData[1] = wsdlName;
                rowData[2] = wsdlType;
                rowData[3] = xsdType;
                rowData[4] = wsdlName + suffix;

                model.addRow(rowData);
            }
        });

        table.setModel(model);
    }


    /**
     * @Description: 保存映射信息
     */
    private void doSaveMapping() {
        // 保存信息
        TableModel inputModel = inputTable.getModel();
        TableModel outputModel = outputTable.getModel();

        ArrayList<TableModel> tableModels = new ArrayList<>();
        tableModels.add(inputModel);
        tableModels.add(outputModel);

        inputAndOutputMapperData.put(selectedOperation, tableModels);

        // 检查是否可以生成OWL-S文档
        operations.forEach(s -> {
            if (!inputAndOutputMapperData.containsKey(s)) {

                displayOWLSButton.setEnabled(false);
                // 保存完设置该按钮不可用 | 防止重复保存
                saveButton.setEnabled(false);
                serviceRegisterButton.setEnabled(false);

                // 提示用户映射已保存
                SwingUtils.showInfoMessage("Save Tips", "当前Operation的参数映射已保存！");
                return;
            }
        });


        // 设置按钮状态
        displayOWLSButton.setEnabled(true);
        serviceRegisterButton.setEnabled(true);
        // 保存完设置该按钮不可用 | 防止重复保存
        saveButton.setEnabled(false);
        // 提示用户映射已保存
        SwingUtils.showInfoMessage("Save Tips", "当前Operation的参数映射已保存！");
    }


    public static void main(String[] args) throws Exception {
        JFrame mainWindow = new JFrame("sRPC Framework : WSDL2OWL-S Mapping Converter & Registrar");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setSize(900, 700);
        SwingUtils.centerFrame(mainWindow);
        mainWindow.getContentPane().add(new WSDL2OWLSStore());
        mainWindow.setVisible(true);
    }


}
