package TrainParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Calendar;

/*
* There is a list of trains, submitted with the form of XML. Display the information on the trains, which
sent today from 15:00 to 19:00.
<?xml version="1.0" encoding="UTF-8"?>
<trains>
<train id=“1”>
<from>Kyiv</from>
<to>Donetsk</to>
<date>19.12.2013</date>
<departure>15:05</departure>
</train>
<train id=“2”>
<from>Lviv</from>
<to>Donetsk</to>
<date>19.12.2013</date>
<departure>19:05</departure>
</train>
</trains>

Write code to add new trains into existing XML.
*/

public class MainTrainParser {
    public void check(Trains trains) {
        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        int begintime = 15 * 3600;
        int endtime = 19 * 3600;

        for (Train tr : trains.getList()) {
            int trday = Integer.parseInt(tr.getDate().substring(0, 2));
            int trmonth = Integer.parseInt(tr.getDate().substring(3, 5));
            int tryear = Integer.parseInt(tr.getDate().substring(6, 10));
            int trhour = Integer.parseInt(tr.getDeparture().substring(0, 2));
            int trminute = Integer.parseInt(tr.getDeparture().substring(3, 5));
            int checktime = trhour * 3600 + trminute * 60;
            if ((today == trday) && (month == trmonth) && (year == tryear)) {
                if ((begintime <= checktime) && (endtime >= checktime)) {
                    System.out.println(tr);
                }
            }
        }
    }

    public static void main(String[] args) {
        JaxbParser parser = new JaxbParser();
        final File f = new File("c:\\trains.xml");
        try {
            Trains trains = (Trains) parser.getObject(f, Trains.class);
            System.out.println(trains);
            trains.add(new Train(3, "Kiev", "Varna", "02.02.2016", "16.00"));
            trains.add(new Train(4, "Kiev", "Dnepr", "02.02.2016", "17.30"));
            parser.saveObject(f, trains);
            MainTrainParser mtp = new MainTrainParser();
            mtp.check(trains);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


}
