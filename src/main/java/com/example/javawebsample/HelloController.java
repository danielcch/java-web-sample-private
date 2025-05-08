package com.example.javawebsample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String form() {
        return "<html>\n" +
               "    <body>\n" +
               "        <h1>Conversor de Dólares a Euros</h1>\n" +
               "        <form action=\"/convert\" method=\"get\">\n" +
               "            <label for=\"amount\">Cantidad en dólares:</label>\n" +
               "            <input type=\"number\" id=\"amount\" name=\"amount\" step=\"0.01\" required>\n" +
               "            <br>\n" +
               "            <button type=\"submit\">Convertir</button>\n" +
               "        </form>\n" +
               "    </body>\n" +
               "</html>";
    }

    @GetMapping("/convert")
    public String convert(@RequestParam double amount) {
        double conversionRate = 0.85; // Ejemplo: 1 dólar = 0.85 euros
        double result = amount * conversionRate;
        return "La cantidad de " + amount + " dólares equivale a " + String.format("%.2f", result) + " euros.";
    }
}
