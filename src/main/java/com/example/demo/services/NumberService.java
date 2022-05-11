package com.example.demo.services;

import com.example.demo.models.Numbers;
import com.example.demo.repositories.NumberRepository;
import com.mifmif.common.regex.Generex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NumberService {

    @Autowired
    private NumberRepository numberRepository;

    private long lastId = 0;

    final private String region = "116 RUS";

    /**
     * @return возвращает случайный номер
     */
    public String getRandomNumber() {
        Optional<Numbers> numbersOptional = Optional.ofNullable(numberRepository.findNumberFromRandomSelect());
        if (numbersOptional.isPresent()) {
            lastId = numbersOptional.get().getId();
            numberRepository.updateFlagByIdEquals(numbersOptional.get().getId());
            if (lastId == 1728000L) {
                lastId = 0;
            }
            return numbersOptional.get().getNumber();
        } else {
            return "Numbers end";
        }
    }

    /**
     * @return возвращает следующий номер
     */
    public String getNextNumber() {
        Optional<Numbers> numbersOptional = Optional.ofNullable(numberRepository.findNumberFromRecentSelect(lastId));
        if (numbersOptional.isPresent()) {
            lastId = numbersOptional.get().getId();
            numberRepository.updateFlagByIdEquals(numbersOptional.get().getId());
            if (lastId == 1728000L) {
                lastId = 0;
            }
            return numbersOptional.get().getNumber();
        } else {
            return "Numbers end";
        }
    }

    /**
     * Генерирует номера для базы данных
     */
    public void generateDataToDB() {
        Generex generex = new Generex("[АЕТОРНУКХСВМ]{3}[0-9]{3}");
        List<String> matchedStrings = generex.getAllMatchedStrings();
        List<Numbers> numbers = new ArrayList<>();
        for (int i = 0; i < matchedStrings.size(); i++) {
            String stringChanged = matchedStrings.get(i).substring(0, 1)
                    + matchedStrings.get(i).substring(3, 6)
                    + matchedStrings.get(i).substring(1, 3)
                    + " "
                    + region;
            numbers.add(Numbers.builder()
                    .number(stringChanged)
                    .flag(false)
                    .build());
        }
        numberRepository.saveAll(numbers);
    }
}
