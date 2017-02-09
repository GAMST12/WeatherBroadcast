package thread;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class WeatherParser implements Callable<HashMap<String, String>>{
    private String url;
    private HashMap<String, String> singleWeather = new HashMap<String, String>();

    public WeatherParser(String url) {
        this.url = url;
    }

    @Override
    public HashMap<String, String> call() throws Exception {
        SAXBuilder parser = new SAXBuilder();
        Document xmlDoc = parser.build(new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=14420df3272249d28dc155154170702&q="+url +"&num_of_days=1&tp=3&format=xml"));
        singleWeather.put("Город", url);
        Element rootNode = xmlDoc.getRootElement();
        List<Element> weatherList = rootNode.getChildren("weather");
        for (Element nextWeather : weatherList) {
            singleWeather.put("Дата", nextWeather.getChildText("date"));
            singleWeather.put("Максимальная температура", nextWeather.getChildText("maxtempC"));
            singleWeather.put("Минимальная температура", nextWeather.getChildText("mintempC"));
            List<Element> astronomyList = nextWeather.getChildren("astronomy");
            for (Element nextAstronomy : astronomyList) {
                singleWeather.put("Восход солнца", nextAstronomy.getChildText("sunrise"));
                singleWeather.put("Заход солнца", nextAstronomy.getChildText("sunset"));
                singleWeather.put("Восход луны", nextAstronomy.getChildText("moonrise"));
                singleWeather.put("Заход луны", nextAstronomy.getChildText("moonset"));
            }
        }
        return singleWeather;
    }
}
