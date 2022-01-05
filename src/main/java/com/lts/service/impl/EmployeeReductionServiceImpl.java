package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.EmployeeReduction;
import com.lts.model.entities.User;
import com.lts.model.filters.EmployeeReductionFilter;
import com.lts.repository.EmployeeReductionRepository;
import com.lts.service.EmployeeReductionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;

@Service
public class EmployeeReductionServiceImpl implements EmployeeReductionService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeReductionServiceImpl.class);

    private String INDEMNIZATIE = "idemnizatie";

    private final EmployeeReductionRepository employeeReductionRepository;

    public EmployeeReductionServiceImpl(EmployeeReductionRepository employeeReductionRepository) {
        this.employeeReductionRepository = employeeReductionRepository;
    }

    @Override
    public Page<EmployeeReduction> filterEmployeesReduction(String filter, Object value, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        Page<EmployeeReduction> employeeReductionPage;
        if (StringUtils.equals(filter, EmployeeReductionFilter.NAME.name()) && value != null && !value.toString().isEmpty()) {
            employeeReductionPage = employeeReductionRepository.getByFileNameContainsIgnoreCase(page, value.toString());
        } else {
            employeeReductionPage = employeeReductionRepository.findAll(page);
        }
        return employeeReductionPage;
    }

    @Override
    public Page<EmployeeReduction> getEmployeesReduction(int pageNumber, int pageSize) {
        return filterEmployeesReduction(null, null, pageNumber, pageSize);
    }

    @Override
    public EmployeeReduction getEmployeeReduction(long id) {
        return employeeReductionRepository.findById(id).orElse(null);
    }
    @Override
    public void parseFile(String filename, String fileContent) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(fileContent)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Row1");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    EmployeeReduction employeeReduction = new EmployeeReduction();
                    String no = element.getElementsByTagName("nrCrt").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String cnp = element.getElementsByTagName("cnp").item(0).getTextContent();
                    String noCim = element.getElementsByTagName("taxe").item(0).getChildNodes().item(1).getTextContent();
                    String dateCim = element.getElementsByTagName("taxe").item(0).getChildNodes().item(3).getTextContent();
                    String noHoursCim = element.getElementsByTagName("taxe").item(0).getChildNodes().item(5).getTextContent();
                    String noDecision = element.getElementsByTagName(INDEMNIZATIE).item(0).getChildNodes().item(1).getTextContent();
                    String dateDecision = element.getElementsByTagName(INDEMNIZATIE).item(0).getChildNodes().item(3).getTextContent();
                    String noDaysReduction = element.getElementsByTagName(INDEMNIZATIE).item(0).getChildNodes().item(5).getTextContent();
                    String noHoursReduction = element.getElementsByTagName(INDEMNIZATIE).item(0).getChildNodes().item(7).getTextContent();
                    String noHoursProvided = element.getElementsByTagName(INDEMNIZATIE).item(0).getChildNodes().item(9).getTextContent();
                    String salaryCim = element.getElementsByTagName("altParinte").item(0).getChildNodes().item(1).getTextContent();
                    String salaryCimReduction = element.getElementsByTagName("altParinte").item(0).getChildNodes().item(3).getTextContent();
                    String requiredAmount = element.getElementsByTagName("sumaSolicitata").item(0).getTextContent();

                    employeeReduction.setFileContent(fileContent);
                    employeeReduction.setNo(no);
                    employeeReduction.setName(name);
                    employeeReduction.setCnp(cnp);
                    employeeReduction.setNoAndDateCim(noCim + "/" + dateCim.replace("-", "."));
                    employeeReduction.setNoHoursCim(noHoursCim);
                    employeeReduction.setNoAndDateDecision(noDecision + "/" + dateDecision.replace("-", "."));
                    employeeReduction.setNoDaysReduction(noDaysReduction);
                    employeeReduction.setNoHoursReduction(noHoursReduction);
                    employeeReduction.setNoHoursProvided(noHoursProvided);
                    employeeReduction.setSalaryCim(salaryCim);
                    employeeReduction.setSalaryCimReduction(salaryCimReduction);
                    employeeReduction.setRequiredAmount(requiredAmount);
                    employeeReduction.setCreatedAt(LocalDateTime.now());
                    employeeReduction.setFileName(filename);
                    employeeReductionRepository.save(employeeReduction);
                    System.out.println("Current Element :" + node.getNodeName());
                    System.out.println("NR CRT : " + no);
                    System.out.println("Numele si prenumele: " + name);
                    System.out.println("CNP : " + cnp);
                    System.out.println("Numarul si data contractului individual de munca : " + noCim + "/" + dateCim);
                    System.out.println("Durata timpului de munca prevazuta in contractul individual de munca (ore)**) : " + noHoursCim);
                    System.out.println("Numarul si data deciziei angajatorului  : " + noDecision + "/" + dateDecision);
                    System.out.println("Perioada de reducere a timpului de munca in perioada prevazuta la  : " + noDaysReduction);
                    System.out.println("Numarul de ore aferente reducerii timpului :" + noHoursReduction);
                    System.out.println("Numarul de ore de munca efectiv prestate ca urmare a reducerii :" + noHoursProvided);
                    System.out.println("Salariul de baza brut : " + salaryCim);
                    System.out.println("Salariul de baza brut : " + salaryCimReduction);
                    System.out.println("Suma solicitata  : " + requiredAmount);

                    System.out.print(" ");
                    //  System.out.printf("Salary [Currency] : %,.2f [%s]%n%n", Float.parseFloat(salary), currency);

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}