package gpxs;

import model.Sample;
import model.SampleList;
import model.Statistics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class GpxsParser {
    public SampleList loadFromList(List<String> data) {
        int lines = data.size();

        SampleList list = new SampleList();

        for (int i=2;i<lines-4;i=i+4){
            String l1=data.get(i);
            String l2=data.get(i+1);
            String l3=data.get(i+2);
            String l4=data.get(i+3);

            String[] l1_tokens = l1.split("\"");

            String lat = l1_tokens[1].trim();
            String lon = l1_tokens[3].trim();
            String ele = l2.replace("<ele>","").replace("</ele>", "").trim();
            String t = l3.replace("<time>","").replace("</time>", "").trim();

            Double dlat = Double.parseDouble(lat);
            Double dlon = Double.parseDouble(lon);
            Double dele = Double.parseDouble(ele);

            Sample s = new Sample(dlat, dlon, dele, t);

            list.add(s);
        }
        return list;
    }

    public SampleList loadFromList(InputStream resource) {
        List<String> data = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        int lines = data.size();

        SampleList list = new SampleList();

        for (int i=2;i<lines-4;i=i+4){
            String l1=data.get(i);
            String l2=data.get(i+1);
            String l3=data.get(i+2);
            String l4=data.get(i+3);

            String[] l1_tokens = l1.split("\"");

            String lat = l1_tokens[1].trim();
            String lon = l1_tokens[3].trim();
            String ele = l2.replace("<ele>","").replace("</ele>", "").trim();
            String t = l3.replace("<time>","").replace("</time>", "").trim();

            Double dlat = Double.parseDouble(lat);
            Double dlon = Double.parseDouble(lon);
            Double dele = Double.parseDouble(ele);

            Sample s = new Sample(dlat, dlon, dele, t);

            list.add(s);
        }
        return list;
    }
}
