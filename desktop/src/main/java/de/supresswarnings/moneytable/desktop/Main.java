package de.supresswarnings.moneytable.desktop;

public class Main {
    public static void main(String[] args){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Code 501 (Class not found).");
            e.printStackTrace();
        }
        if(args != null){
            if("-d".equals(args[0])){
                System.out.println("true");
            }
        }
    }
}
