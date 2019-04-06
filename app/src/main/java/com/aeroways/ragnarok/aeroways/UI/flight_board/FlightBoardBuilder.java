package com.aeroways.ragnarok.aeroways.UI.flight_board;

import android.util.Log;

import com.aeroways.ragnarok.aeroways.Entities.FlightBoardEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FlightBoardBuilder {

    public static List<FlightBoardEntry> buildFlightBoard(String sHTML) {
        List<FlightBoardEntry> result = new ArrayList<FlightBoardEntry>();
        if (sHTML != null) {
            try {
                Document doc = Jsoup.connect(sHTML).get();
                Log.e("MyApp","Got the doc");
                Element boardTable = doc.getElementsByAttributeValue("width","100%").first();
                Elements rows = boardTable.select("tr");
                for (Element row : rows) {
                    Elements cells = row.select("td");
                    if ((cells.size() == 5) && (!(cells.first().text().equalsIgnoreCase("Heure Locale")))&& (!(cells.first().text().equalsIgnoreCase("00:00:00")))) {
                        FlightBoardEntry entry = new FlightBoardEntry();
                        Log.e("MyApp","Added an entry");
                        entry.setTime(cells.get(0).text());
                        entry.setPlace(cells.get(1).text());
                        entry.setCompany(cells.get(2).text());
                        entry.setNumber(cells.get(3).text());
                        entry.setStatus(cells.get(4).text());
                        result.add(entry);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}
