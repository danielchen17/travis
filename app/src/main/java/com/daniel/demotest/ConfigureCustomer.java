package com.daniel.demotest;

public class ConfigureCustomer {

    public static void main(String[] args){
        String server = args[0];
        String customer = args[1];
        System.out.println(String.format("Configuring customer %s with server %s", customer, server));
    }
}
