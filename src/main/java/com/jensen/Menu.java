package com.jensen;


import java.io.IOException;

//Klass som underlättar printandet av menyer
public class Menu {

    public Menu(String[] alternatives) {
        //Printa varje String i arrayen alternatives
        for(int i = 0; i< alternatives.length; i++) {
            System.out.println(i+1 + ". " + alternatives[i]);
        }
    }

    public Menu(String header, String[] alternatives) {
        //Loopa genom angiven array och printa varje string
        for(int i = 0; i< alternatives.length; i++) {
            System.out.println(i+1 + ". " + alternatives[i]);
        }
    }
    //metod för att rensa terminalen/kommandotolken
    public void clearCommandLine() {
        try {
            //Windows CMD
            Runtime.getRuntime().exec("cls");

        } catch (IOException e) {
            //macOS/Linux Terminal
            System.out.print("\"\\033[H\\033[2J\""); //Nån ANSI escape kod. https://stackoverflow.com/a/4062051
            System.out.flush(); //Tvingar terminalen att köra output från System.out.
        }
    }
}
