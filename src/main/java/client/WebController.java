package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class WebController {

    private Logger logger = LogManager.getLogger(WebController.class);

    @GetMapping("/index")
    public String IndexForm(Model model) {
        model.addAttribute("alert", new Alert());
        model.addAttribute("limit", new Limit());
        return "index";
    }

    @PostMapping("/addAlert")
    public String alertSubmit(@ModelAttribute Alert alert, @RequestParam(value="action") String action) throws IOException {
        if(action.equalsIgnoreCase("Add")) {
            URL url = new URL("http://localhost:8080/alert?" + "pair=" + alert.getPair() + "&limit=" + alert.getLimit());
            logger.info("cur: {}, limit: {}", alert.getPair(), alert.getLimit());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            logger.info("resp code: {}", connection.getResponseCode());
        }
        if(action.equalsIgnoreCase("Delete")) {
            URL url = new URL("http://localhost:8080/alert?" + "pair=" + alert.getPair());
            logger.info("delete pair: {}", alert.getPair());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            logger.info("resp code: {}", connection.getResponseCode());
        }
        return "index";
    }
}