package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


 public  class Main {



    public static class Item{


        String name;
        int weight;

        public Item(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }


    public interface SpaceShip {


        boolean launch();
        boolean land();
        boolean canCarry(Item item);
        int carry(Item item);

    }


     public static class Rocket implements SpaceShip {


        int maxWeight;
        int currentWeight;
        int cost;
        int weight;
        double launchExplosion;
        double landingCrash;
        int randomNumber;


        @Override
        public boolean launch() {
            return true;
        }

        @Override
        public boolean land() {
            return true;
        }


        // zwraca true jeśli może zabrac przedmiot
        @Override
        public boolean canCarry(Item item) {

            if(this.currentWeight + item.weight <= this.maxWeight) {
                return true;
            } else {
                return false;
            }
        }

        // aktualna masa rakiety
        @Override
        public int carry(Item item) {
            this.currentWeight += item.weight;
            return currentWeight;
        }


        public int drawingNumber(){
            this.randomNumber = (int) (Math.random()*100 +1);
            return randomNumber;
        }
    }


     public static class U1 extends Rocket {

        public U1() {
            cost = 100;             // w milionach
            weight = 10000;         // w kilogramach
            maxWeight = 18000;      // w kilogramach
            launchExplosion = 0.0;  // wybuch przy starcie
            landingCrash = 0.0;     // wybuch przy ladowaniu
            currentWeight = weight;

        }

        // zwraca true w zaleznosci szansy na wybuch przy starcie
        @Override
        public boolean launch() {

            int random = drawingNumber();
            this.launchExplosion = 5.0 * (this.currentWeight - this.weight) / (this.maxWeight - this.weight);
            if (this.launchExplosion < random){
                return true;
            } else {
                return false;
            }

        }

        // zwraca true w zaleznosci szansy na wybuch przy ladowaniu
        @Override
        public boolean land() {

            int random = drawingNumber();
            this.launchExplosion = 1.0 * (this.currentWeight - this.weight) / (this.maxWeight - this.weight);
            if (this.launchExplosion < random){
                return true;
            } else {
                return false;
            }
        }
    }


    public static class U2 extends Rocket {

        public U2() {
            cost = 120;             // w milionach
            weight = 18000;         // w kilogramach
            maxWeight = 29000;      // w kilogramach
            launchExplosion = 0.0;  // wybuch przy starcie
            landingCrash = 0.0;     // wybuch przy ladowaniu
            currentWeight = weight;

        }

        // zwraca true w zaleznosci szansy na wybuch przy starcie
        @Override
        public boolean launch() {

            int random = drawingNumber();
            this.launchExplosion = 4.0 * (this.currentWeight - this.weight) / (this.maxWeight - this.weight);
            if (this.launchExplosion < random){
                return true;
            } else {
                return false;
            }

        }

        // zwraca true w zaleznosci szansy na wybuch przy ladowaniu
        @Override
        public boolean land() {

            int random = drawingNumber();
            this.launchExplosion = 8.0 * (this.currentWeight - this.weight) / (this.maxWeight - this.weight);
            if (this.launchExplosion < random){
                return true;
            } else {
                return false;
            }
        }
    }


    public static class Simulation {

        ArrayList<Item>loadItems(String filePhase) throws Exception {

            File file = new File(filePhase);
            Scanner scanner = new Scanner(file);
            ArrayList<Item> items = new ArrayList<>();

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] oneItem = line.split("=");
                items.add(new Item(oneItem[0], Integer.valueOf(oneItem[1])));

            }

            for (Item i : items) {
            //System.out.println(i.name + ": " + i.weight);
            }
            //System.out.println();

            return items;
        }


        ArrayList<Rocket> loadU1(ArrayList<Item> phase){
            ArrayList<Rocket> rockets = new ArrayList<>();
            Rocket r = new U1();

            int itemCounter = 1;
            int rocketCounter = 1;
            System.out.println("\nU1 Rocket weight = " + r.weight + "; maxWeight = " + r.maxWeight);

            for (Item i:phase) {


                while (r.canCarry(i)){
                    r.carry(i);
                    itemCounter++;
/*                    System.out.println("i weight: " + i.weight + "  " +i.name);
                    System.out.println("i currentWeight: " + r.currentWeight );*/
                }
                rockets.add(r);
                r = new U1();
                rocketCounter++;



            }
            rockets.add(r);
            System.out.println("U1 rockets after load: " + rockets.size() );
            return rockets;
        }



        ArrayList<Rocket> loadU2(ArrayList<Item> phase){
            ArrayList<Rocket> rockets = new ArrayList<>();
            Rocket r = new U2();

            int itemCounter = 1;
            int rocketCounter = 1;
            System.out.println("\nU2 Rocket weight = " + r.weight + "; maxWeight = " + r.maxWeight);

            for (Item i:phase) {


                while (r.canCarry(i)){
                    r.carry(i);
                    itemCounter++;
/*                    System.out.println("i weight: " + i.weight + "  " +i.name);
                    System.out.println("i currentWeight: " + r.currentWeight );*/
                }
                rockets.add(r);
                r = new U2();
                rocketCounter++;



            }
            rockets.add(r);
            System.out.println("U2 rockets after load: " + rockets.size());
            return rockets;
        }


        int runSimulation(ArrayList<Rocket> list) {
            int num = 0; //failed trials of launch/land
            int indexSuccess = 1;
            for (Rocket r : list) {

                while (!r.launch()) {
                    r.launch();
                    num++;
            //        System.out.println("Extra trials: " + num);
                }
           //     System.out.println("No." + indexSuccess + " rocket launched");

                while (!r.land()) {
                    r.land();
                    num++;
             //       System.out.println("Extra trials: " + num);
                }
            //    System.out.println("No." + indexSuccess + " rocket landed\n");
                indexSuccess++;
            }
            int budget = list.get(0).cost * (list.size() + num);
            System.out.println(list.size() + " rockets and " + num + " extra = "
                    + (list.size() + num) + " in total");
            return budget;
        }


    }


    public  static void main(String[] args) throws Exception  {

        Simulation  simulation = new Simulation();
        ArrayList<Item>phase1 = simulation.loadItems("phase-1.txt");
        ArrayList<Item>phase2 = simulation.loadItems("phase-2.txt");

        // dla U1
        ArrayList<Rocket> u1RocketsPhase1 = simulation.loadU1(phase1);
        ArrayList<Rocket> u1RocketsPhase2 = simulation.loadU1(phase2);

        System.out.println("\nU1 rocket cost = 100");
        int budgetU1phase1 = simulation.runSimulation(u1RocketsPhase1);
        System.out.println("Budget of U1 fleet for phase 1 = " + budgetU1phase1 + " (millions)");

        int budgetU1phase2 = simulation.runSimulation(u1RocketsPhase2);
        System.out.println("Budget of U1 fleet for phase 2 = " + budgetU1phase2 + " (millions)");

        System.out.println("Total budget of U1 fleet = " + (budgetU1phase1 + budgetU1phase2) + " (millions)\n");


        /// dla U2
        ArrayList<Rocket> u2RocketsPhase1 = simulation.loadU2(phase1);
        ArrayList<Rocket> u2RocketsPhase2 = simulation.loadU2(phase2);

        System.out.println("\nU2 rocket cost = 100");
        int budgetU2phase1 = simulation.runSimulation(u2RocketsPhase1);
        System.out.println("Budget of U2 fleet for phase 1 = " + budgetU2phase1 + " (millions)");

        int budgetU2phase2 = simulation.runSimulation(u2RocketsPhase2);
        System.out.println("Budget of U2 fleet for phase 2 = " + budgetU2phase2 + " (millions)");

        System.out.println("Total budget of U2 fleet = " + (budgetU2phase1 + budgetU2phase2) + " (millions)\n");


    }


}
