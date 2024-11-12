package main.main;

import main.objects.Assets;

public class Main {

    public static void main(String[] args) {
        System.out.println("Kamusta Mundo!");

        Assets assets = new Assets();
        new Splash(assets);
    }
}
