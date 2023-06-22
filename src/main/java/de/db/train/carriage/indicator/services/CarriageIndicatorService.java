package de.db.train.carriage.indicator.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Element;

@Service
@Slf4j
public class CarriageIndicatorService {

    public List<String> getTrackSection(String stationShortcodeInput, int trainNumberInput, int wagonNumberInput) {
        Element stationElement = parseFileAndGetStationElement();

        if (stationElement != null && checkIfWantedStationExistsInFile(stationElement, stationShortcodeInput)) {
                if (checkIfWantedTrainExists(stationElement, trainNumberInput)){
                    return getSectionsList(stationElement, wagonNumberInput);
                }
            }
        return null;
    }

    public Element parseFileAndGetStationElement() {
        File inputFile = new File("src/main/java/de/db/train/carriage/indicator/scripts/FF_2017-12-01_10-47-17.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();
            Node stationNode = document.getElementsByTagName("station").item(0);
            if (stationNode.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) stationNode;
            }
        } catch (IOException ex) {
            log.error("[parseFileAndCheckIfStationExistsInFile] could not read file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkIfWantedStationExistsInFile(Element stationElement, String stationShortcodeInput) {
        String shortcode = stationElement.getElementsByTagName("shortcode").item(0).getTextContent();
        return shortcode.equals(stationShortcodeInput);
    }

    public boolean checkIfWantedTrainExists(Element stationElement, int trainNumberInput) {
        NodeList trainNodes = stationElement.getElementsByTagName("train");
        for (int i = 0; i < trainNodes.getLength(); i++) {
            Element trainElement = (Element) trainNodes.item(i);
            int trainNumber = Integer.parseInt(trainElement.getElementsByTagName("trainNumber").item(0).getTextContent());
            if (trainNumber == trainNumberInput) {
                return true;
            }
        }
        return false;
    }
    public List<String> getSectionsList(Node stationNode, int wagonNumberInput) {
        Element stationElement = (Element) stationNode;
        NodeList wagonNodes = stationElement.getElementsByTagName("wagon");
        for (int j = 0; j < wagonNodes.getLength(); j++) {
            Node wagonNode = wagonNodes.item(j);
            if (wagonNode.getNodeType() == Node.ELEMENT_NODE) {
                Element wagonElement = (Element) wagonNode;
                String wagonNumber = wagonElement.getElementsByTagName("number").item(0).getTextContent();
                if (!wagonNumber.isEmpty() && Integer.parseInt(wagonNumber) == wagonNumberInput) {
                    String section = wagonElement.getElementsByTagName("sections").item(0).getTextContent();
                    return Arrays.asList(section.trim().split("\\s+"));
                }
            }
        }
        return null;
    }

}
