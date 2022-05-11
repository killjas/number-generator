package com.example.demo.controllers;

import com.example.demo.services.NumberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с номерами
 */
@RestController
public class NumberRestController {
    @Autowired
    NumberService numberService;

    @ApiOperation(value = "Получить следующие номера")
    @RequestMapping(method = RequestMethod.GET, value = "/next", produces = "text/plain")
    private String getNextNumber() {
        return numberService.getNextNumber();
    }

    @ApiOperation(value = "Получить случайные номера")
    @RequestMapping(method = RequestMethod.GET, value = "/random", produces = "text/plain")
    private String getRandomNumber() {
        return numberService.getRandomNumber();
    }

    /**
     * Генерация номеров для базы данных
     */
    @RequestMapping(method = RequestMethod.GET, value = "/generate")
    private void generateData() {
        numberService.generateDataToDB();
    }
}
