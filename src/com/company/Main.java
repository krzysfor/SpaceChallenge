package com.company;

public class Main {



    class Item{


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


    class Rocket implements SpaceShip {


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
            this.randomNumber = (int) Math.random()*100 +1;
            return randomNumber;
        }
    }


    class U1 extends Rocket {

        public U1() {
            cost = 100;             // w milionach
            weight = 10000;         // w kilogramach
            maxWeight = 18000;      // w kilogramach
            launchExplosion = 0.0;  // wybuch przy starcie
            landingCrash = 0.0;     // wybuch przy ladowaniu

        }

        @Override
        public boolean launch() {

            int random = drawingNumber();
            this.launchExplosion = (this.weight / this.maxWeight);
            return ;
        }

        @Override
        public boolean land() {
            return ;
        }
    }




    public static void main(String[] args) {
	// write your code here
    }


}
