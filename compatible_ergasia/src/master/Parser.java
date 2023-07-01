package master;

import com.example.tracker.model.Route;
import com.example.tracker.model.Waypoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<Waypoint> parse(Route data){
        List<Waypoint> waypoints = new ArrayList<>();
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            InputStream input = new ByteArrayInputStream(data.getData());
            Document document = dBuilder.parse(input);
            String creator = document.getDocumentElement().getAttribute("creator");
            NodeList ls = document.getElementsByTagName("wpt");
            for (int i = 0; i < ls.getLength(); i++) {
                Element element = (Element) ls.item(i);
                double lat = Double.parseDouble(element.getAttribute("lat"));
                double lon = Double.parseDouble(element.getAttribute("lon"));
                double ele = Double.parseDouble(element.getElementsByTagName("ele").item(0).getTextContent());
                String timeStr = element.getElementsByTagName("time").item(0).getTextContent();
                LocalDateTime time = LocalDateTime.parse(timeStr.substring(0, timeStr.length() - 1));
                Waypoint waypoint = new Waypoint(lat, lon, ele, time, creator, data.getName());
                waypoints.add(waypoint);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return waypoints;
    }
}
