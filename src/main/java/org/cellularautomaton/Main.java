package org.cellularautomaton;

import org.cellularautomaton.meadow.Meadow;
import org.cellularautomaton.services.InitialConditions;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class Main {
    public static void main(String[] args) throws Throwable {

        // Чтение файла с начальными данными
        List<String> initValuesList;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(args[0].replace("\\", "/")))) {
            initValuesList = (fileReader.lines()).collect(Collectors.toList());
        }

        // Чтение начальных значений из файла
        int time = InitialConditions.getTime(initValuesList);
        int statsShowTime = InitialConditions.getStatsShowTime(initValuesList);
        List<List<Integer>> initialHives = InitialConditions.getHives(initValuesList);
        HashMap<List<Integer>, String> initialFlowers = InitialConditions.getFlowers(initValuesList);

        // Инициализация поля с заданными начальными значениями
        Meadow meadow = new Meadow(InitialConditions.getMeadowX(initValuesList), InitialConditions.getMeadowY(initValuesList));
        meadow.initialize(initialHives, initialFlowers);

        // Итерации на поле
        for (int t = 1;  t <= time; t++) {
            if (t % statsShowTime == 0) {
                meadow.showStatistics(t, args[1].replace("\\", "/"));
            }
            meadow.runNewIteration();
        }
    }
}