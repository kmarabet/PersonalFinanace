package com.company.saveload;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.company.model.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RateCurrency {

    public static HashMap<String, Double> getRates(Currency base) throws ParserConfigurationException, IOException, SAXException {
        HashMap<String, NodeList> result = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dateFormat.format(new Date());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());

        NodeList nodeList = doc.getElementsByTagName("Valute");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeListChilds = node.getChildNodes();
            for (int j = 0; j < nodeListChilds.getLength(); j++) {
                if (nodeListChilds.item(j).getNodeName().equals("CharCode")) {
                    result.put(nodeListChilds.item(j).getTextContent(), nodeListChilds);
                }
            }
        }
        HashMap<String, Double> rates = new HashMap<>();
        for (Map.Entry<String, NodeList> entry : result.entrySet()) {
            NodeList temp = entry.getValue();
            double value = 0;
            int nominal = 0;
            for (int i = 0; i < temp.getLength(); i++) {
                if (temp.item(i).getNodeName().equals("Value"))
                    value = Double.parseDouble(temp.item(i).getTextContent().replace(',','.'));
                else if (temp.item(i).getNodeName().equals("Nominal"))
                    nominal = Integer.parseInt(temp.item(i).getTextContent());
            }
            double amount = value / nominal;
            rates.put(entry.getKey(), (((double) Math.round(amount * 10000)) / 10000));
        }
        rates.put("RUB", 1.0);
        //System.out.println(rates);
        double div = rates.get(base.getCode());
        for (Map.Entry<String, Double> entry : rates.entrySet() ) {
            entry.setValue(entry.getValue() / div);
        }
        return rates;
    }
}