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
               "        <h1>Web de Sumas</h1>\n" +
               "        <form action=\"/sum\" method=\"get\">\n" +
               "            <label for=\"num1\">Número 1:</label>\n" +
               "            <input type=\"number\" id=\"num1\" name=\"num1\" required>\n" +
               "            <br>\n" +
               "            <label for=\"num2\">Número 2:</label>\n" +
               "            <input type=\"number\" id=\"num2\" name=\"num2\" required>\n" +
               "            <br>\n" +
               "            <button type=\"submit\">Sumar</button>\n" +
               "        </form>\n" +
               "    </body>\n" +
               "</html>";
    }

    @GetMapping("/sum")
    public String sum(@RequestParam int num1, @RequestParam int num2) {
        int result = num1 + num2;
        return "La suma de " + num1 + " y " + num2 + " es: " + result;
    }
}
