package com.ouc.rpc.framework.semantic;

import com.ouc.rpc.framework.util.SwingUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.neo4j.driver.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class WSDL2OWLSMatch extends JPanel implements ActionListener {


    final String[] columnNames = {"Atomic Process", "WSDL Name", "WSDL Type", "XSD Type", "OWL-S Name", "OWL Type", "Similarity"};
    final String[] matchResultColumnNames = {"Service ID", "Service Name", "Service Category", "Service Description", "Matching Degree(Jaccard)"};
    final String[] similarityColumnNames = {"WSDL Type", "Tmp OWL", "Domain OWL", "Name Score", "Attribute Score", "Similarity"};
    final String[][] emptyRow = new String[0][7];
    final String[][] matchResultRow = new String[0][5];
    final String[][] similarityEmptyRow = new String[0][6];
//    final String[] defaultWsdlFiles = {"http://localhost:80/wsdl/novel_author_service.wsdl", "http://localhost:80/wsdl/AuthorPublicationprice.wsdl", "http://localhost:80/wsdl/CountryHotel.wsdl", "http://localhost:80/wsdl/DegreeLending.wsdl", "http://localhost:80/wsdl/FoodPrice.wsdl",};
    final String[] defaultWsdlFiles = {
            "http://localhost:80/wsdl/novel_author_service.wsdl",
        "http://localhost:80/wsdl/AuthorPublicationprice.wsdl",
        "http://localhost:80/wsdl/car_price_service.wsdl",
        "http://localhost:80/wsdl/BookAuthorprice.wsdl"
    };
//    final String[] defaultOntFiles = {"http://127.0.0.1/ontology/books.owl", "http://127.0.0.1/ontology/concept.owl"};
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
    final String[] defaultServiceCategory = {"category1", "category2", "category3", "category4"};
    // init
    JComboBox wsdlUrls = new JComboBox(defaultWsdlFiles);
    JComboBox ontUrls = new JComboBox(defaultOntFiles);
    // wsdl operations
    JList opList = new JList();
    // input parameters
    JTable inputTable = new JTable(emptyRow, columnNames);
    // output parameters
    JTable outputTable = new JTable(emptyRow, columnNames);
    // match result table
    JTable matchResultTable = new JTable(matchResultRow, matchResultColumnNames);
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
    JButton saveButton, displayTmpOntButton, displayOWLSButton, ontMappingButton, serviceRegisterButton, displayServiceDetailsButton, selectServiceButton;
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


    public WSDL2OWLSMatch() {

        // set panel
        JPanel contentPane = new JPanel();
        JPanel addressPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel matchPanel = new JPanel();// 匹配结果面板
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


        // set layout
        setLayout(new GridLayout(1, 1));
        add(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        contentPane.add(addressPanel);
        contentPane.add(Box.createVerticalStrut(2));
        contentPane.add(middlePanel);
        contentPane.add(Box.createVerticalStrut(2));
        contentPane.add(matchPanel);
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


        // set matchPanel
        matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));
        matchPanel.setBorder(BorderFactory.createTitledBorder("Semantic Matching Query Results"));
        matchResultTable.setDefaultRenderer(Object.class, renderer);
        matchPanel.add(new JScrollPane(matchResultTable));
        matchPanel.add(Box.createVerticalStrut(2));


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

        serviceRegisterButton = new JButton("Match Query");
        serviceRegisterButton.setEnabled(false);
        serviceRegisterButton.setActionCommand("service_query");
        serviceRegisterButton.addActionListener(this);

        displayServiceDetailsButton = new JButton("Display Details");
        displayServiceDetailsButton.setEnabled(true);
        displayServiceDetailsButton.setActionCommand("display_details");
        displayServiceDetailsButton.addActionListener(this);

        selectServiceButton = new JButton("Select Service");
        selectServiceButton.setEnabled(true);
        selectServiceButton.setActionCommand("display_details");
        selectServiceButton.addActionListener(this);


        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("close");
        closeButton.addActionListener(this);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
//        buttonPanel.add(displayTmpOntButton);
//        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(ontMappingButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
//        buttonPanel.add(displayOWLSButton);
//        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(serviceRegisterButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(displayServiceDetailsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(selectServiceButton);
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
        else if (e.getActionCommand().equals("display_details")) doDisplayDetails();
        else if (e.getActionCommand().equals("service_query")) doServiceMatchQuery();
        else if (e.getActionCommand().equals("close")) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) window.dispose();
            else System.exit(0);
        } else if (e.getActionCommand().equals("load")) doLoad();
    }

    /**
     * @Description: 展示服务的详细信息
     */
    private void doDisplayDetails() {


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
                java.util.List<String> inputOwlTypeAndSimilarity = getOWLTypeAndSimilarity((String) (inputModel.getValueAt(i, 2)), similarityModel);
                if (inputOwlTypeAndSimilarity.size() != 0) {
                    inputModel.setValueAt(inputOwlTypeAndSimilarity.get(0) + "\n" + inputOwlTypeAndSimilarity.get(2) + "\n" + inputOwlTypeAndSimilarity.get(3), i, 5);
                    inputModel.setValueAt(inputOwlTypeAndSimilarity.get(1), i, 6);
                }
            }
        }

        // 2 输出参数
        for (int i = 0; i < outputModel.getRowCount(); i++) {
            if (outputModel.getValueAt(i, 5) == null && outputModel.getValueAt(i, 6) == null) {
                java.util.List<String> outputOwlTypeAndSimilarity = getOWLTypeAndSimilarity((String) (outputModel.getValueAt(i, 2)), similarityModel);
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
    private java.util.List<String> getOWLTypeAndSimilarity(String wsdlType, TableModel similarityModel) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < similarityModel.getRowCount(); i++) {

            String wsdl = (String) (similarityModel.getValueAt(i, 0));
            String simiTmp = (String) (similarityModel.getValueAt(i, 5));
            double similarity = Double.parseDouble(simiTmp.replace("%", "")) / 100.0;

            // 阈值设置为70%
            if (wsdl.equals(wsdlType) && similarity > 0.70) {
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
     * @Description: 服务的匹配查询
     */
    private void doServiceMatchQuery() {

        String uri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "tongyusheng";

        // 存储领域本体文件
        HashMap<String, List<String>> parameterMap = new HashMap<>();
        SimilarityCalculator sc = new SimilarityCalculator();
        // 循环遍历进行查找
        inputAndOutputMapperData.forEach((s, tableModels) -> tableModels.forEach(tableModel -> {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String value = (String) tableModel.getValueAt(i, 5);
                // 本体文件
                String owlType = value.substring(0, value.indexOf("\n"));
                if (!parameterMap.containsKey(owlType)) {

                    ArrayList<String> lists = new ArrayList<>();
                    parameterMap.put(owlType, lists);

                    String owlFilePath = value.substring(value.indexOf("\n") + 1, value.lastIndexOf("\n"));
                    List<OntClass> classesOfOntology = OntologyParser.getClassesOfOntology(owlFilePath);
                    String owlTypeClass = OntologyParser.getTNSOfOntology(owlFilePath) + owlType;
                    OntClass classOfOntology = OntologyParser.getClassOfOntology(owlFilePath, owlTypeClass);

                    classesOfOntology.forEach(ontClass -> {
                        Double conceptSimilarity = sc.getConceptSimilarity(classOfOntology, ontClass);
                        // 设置阈值
                        if (conceptSimilarity > 0.1) {
                            parameterMap.get(owlType).add(ontClass.getLocalName());
                        }
                    });
                }
            }
        }));

        // 匹配查询
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password)); Session session = driver.session()) {
            ArrayList<String> allType = new ArrayList<>();
            parameterMap.forEach(new BiConsumer<String, List<String>>() {
                @Override
                public void accept(String s, List<String> strings) {
                    allType.addAll(strings);
                }
            });

            // 去重
            allType.stream().distinct().collect(Collectors.toList());

            // 查询条件
            String queryCondition = "";
            for (String s : allType) {
                queryCondition += "in.parameterType CONTAINS \'" + s + "\' OR out.parameterType CONTAINS \'" + s + "\' OR ";
            }

            queryCondition = queryCondition.substring(0, queryCondition.lastIndexOf("OR"));

            String queryService = "MATCH (profile:ServiceProfile) WHERE size((profile)-[:has_process]->()) >= " + inputAndOutputMapperData.keySet().size() + " WITH profile MATCH (profile)-[:has_process]->(atomic:AtomicProcess) WITH atomic,profile MATCH ((atomic)-[:hasInput]->(in:Input)),((atomic)-[:hasOutput]->(out:Output)) WHERE " + queryCondition + " RETURN DISTINCT profile.serviceId,profile.serviceName,profile.serviceCategory,profile.textDescription";

            // Jaccard相似度：将两个列表视为两个集合，计算它们的交集元素数量除以它们的并集元素数量

            // 执行查询
            Result result = session.run(queryService);

            Vector<Vector<Object>> data = new Vector<>();

            DecimalFormat decimalFormat = new DecimalFormat("0.00%");

            while (result.hasNext()) {
                Record record = result.next();
                String serviceId = record.get("profile.serviceId").asString();
                String serviceName = record.get("profile.serviceName").asString();
                String serviceCategory = record.get("profile.serviceCategory").asString();
                String textDescription = record.get("profile.textDescription").asString();
                // 查询当前服务下所有的输入和输出参数
                HashSet<String> inputAndOutputOwl = new HashSet<>();

                String queryParameters = "MATCH (profile:ServiceProfile)-[:has_process]->(atomic:AtomicProcess) WHERE profile.serviceName = \'" + serviceName + "\' WITH atomic MATCH ((atomic)-[:hasInput]->(in:Input)),((atomic)-[:hasOutput]->(out:Output)) RETURN DISTINCT in.parameterType,out.parameterType";
                Result parametersResult = session.run(queryParameters);
                while (parametersResult.hasNext()) {
                    Record next = parametersResult.next();
                    String input = next.get("in.parameterType").asString();
                    String output = next.get("out.parameterType").asString();
                    inputAndOutputOwl.add(input.substring(input.indexOf("#") + 1));
                    inputAndOutputOwl.add(output.substring(output.indexOf("#") + 1));
                }

                Set<String> src = parameterMap.keySet();

                // 并集
                HashSet<String> union = new HashSet<>(src);
                union.addAll(inputAndOutputOwl);

                // 交集
                HashSet<String> intersection = new HashSet<>(src);
                intersection.retainAll(inputAndOutputOwl);

                Double v = (double) intersection.size() / union.size();

                String matchDegree = decimalFormat.format(v);

                Vector<Object> itemRowData = new Vector<>();

                itemRowData.add(serviceId);
                itemRowData.add(serviceName);
                itemRowData.add(serviceCategory);
                itemRowData.add(textDescription);
                itemRowData.add(matchDegree);

                data.add(itemRowData);
            }


            // 根据相似度降序
            data.sort((o1, o2) -> {
                String str1 = (String) o2.get(4);
                String str2 = (String) o1.get(4);
                Double value1 = Double.parseDouble(str1.substring(0, str1.indexOf("%"))) / 100.0;
                Double value2 = Double.parseDouble(str2.substring(0, str2.indexOf("%"))) / 100.0;
                return value1.compareTo(value2);
            });

            // 转为百分比


            Vector<String> columnNames = new Vector<>(Arrays.asList(matchResultColumnNames));

            // 设置不可编辑的数据模型
//            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
//                @Override
//                public boolean isCellEditable(int row, int column) {
//                    return false;
//                }
//            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);


            // 设置数据到表格中
            matchResultTable.setModel(model);

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

        java.util.List<OntClass> srcOntClasses = srcOntModel.listClasses().filterKeep(ontClass -> !ontClass.isAnon()).toList();
        // 存储所有的关系和属性
        HashMap<String, java.util.List<String>> srcOntClassesProperties = new HashMap<>();
        // 遍历并解析
        java.util.List<OntProperty> ontProperties = srcOntModel.listAllOntProperties().filterKeep(s -> !s.isAnon()).filterKeep(s -> s.isDatatypeProperty() || s.isObjectProperty()).toList();
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
        java.util.List<OntClass> tarOntClasses = tarOntModel.listClasses().filterKeep(ontClass -> !ontClass.isAnon()).toList();

        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        DecimalFormat decimalFormat1 = new DecimalFormat("#0.000");

        Vector<Vector<Object>> data = new Vector<>();

        HashMap<String, String> mapperData = wsdl2OWL.getMapperData();

        for (OntClass srcClass : srcOntClasses) {
            for (OntClass tarClass : tarOntClasses) {

                Double conceptSimilarity = similarityCalculator.getConceptSimilarity(srcClass, tarClass);
                java.util.List<String> srcProperties = srcOntClassesProperties.get(srcClass.getLocalName());
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
        serviceIdField.setText("reference service name");

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

        HashMap<String, java.util.List<Map<String, String>>> inAndOutOfOperation = wsdlParser.getInAndOutOfOperation(selectedOperation);
        java.util.List<Map<String, String>> inputMaps = inAndOutOfOperation.get(WSDLParser.INPUT_PARAMETERS);
        java.util.List<Map<String, String>> outputMaps = inAndOutOfOperation.get(WSDLParser.OUTPUT_PARAMETERS);

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
        JFrame mainWindow = new JFrame("sRPC Framework : WSDL2OWL-S Mapping Converter & Matcher");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setSize(900, 700);
        SwingUtils.centerFrame(mainWindow);
        mainWindow.getContentPane().add(new WSDL2OWLSMatch());
        mainWindow.setVisible(true);
    }

}
