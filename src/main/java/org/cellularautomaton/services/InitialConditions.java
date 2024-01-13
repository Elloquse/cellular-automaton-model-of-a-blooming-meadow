package org.cellularautomaton.services;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InitialConditions {

        public static int getMeadowX(List<String> file) {
            return Integer.parseInt(file.get(0).replaceAll("\\D+",""));
        }

        public static int getMeadowY(List<String> file) {
            return Integer.parseInt(file.get(1).replaceAll("\\D+",""));
        }

        public static int getTime(List<String> file) {
            return Integer.parseInt(file.get(2).replaceAll("\\D+",""));
        }

        public static int getStatsShowTime(List<String> file) {
            return Integer.parseInt(file.get(3).replaceAll("\\D+",""));
        }

        public static List<List<Integer>> getHives(List<String> file) {
            Pattern regExpX = Pattern.compile("\\d(?=[|])");
            Pattern regExpY = Pattern.compile("\\d(?![|])");

            List<String> hiveList = file.stream().filter(e -> e.contains("Hive")).collect(Collectors.toList());
            ArrayList<List<Integer>> hiveCoordinates = new ArrayList<>();
            
            for (String hive : hiveList) {
                Matcher x = regExpX.matcher(hive);
                Matcher y = regExpY.matcher(hive);
                if (x.find() && y.find()) {
                    hiveCoordinates.add(Stream.of(Integer.parseInt(x.group()), Integer.parseInt(y.group())).collect(Collectors.toList()));
                };
            }
            
            return hiveCoordinates;
        }

        public static HashMap<List<Integer>, String> getFlowers(List<String> file) {
            Pattern regExpX = Pattern.compile("\\d(?=[|])");
            Pattern regExpY = Pattern.compile("\\d(?![|])");
            Pattern flowerSpecies = Pattern.compile("[a-zA-Z]*(?=[|])");

            List<String> flowerList = file.stream().filter(e -> e.contains("Flower")).collect(Collectors.toList());
            ArrayList<List<Integer>> flowerCoordinates = new ArrayList<>();
            ArrayList<String> flowerNames = new ArrayList<>();
            
            for (String flower : flowerList) {

                Matcher x = regExpX.matcher(flower);
                Matcher y = regExpY.matcher(flower);
                Matcher flowerName = flowerSpecies.matcher(flower);
                if (x.find() && y.find() && flowerName.find()) {
                    flowerCoordinates.add(Stream.of(Integer.parseInt(x.group()), Integer.parseInt(y.group())).collect(Collectors.toList()));
                    flowerNames.add(flowerName.group());
                };
            }
            
            HashMap<List<Integer>, String> flowerMap = new HashMap<>();
            
            int iteration = 0;
            for (String str : flowerNames) {
                flowerMap.put(flowerCoordinates.get(iteration), str);
                iteration += 1;
            }

            return flowerMap;
        }
}
