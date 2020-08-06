package com.seven.log;

import com.seven.log.calc.SimHashService;

public class LogScript {
    public static void main(String[] args) {
        String s ="This is a test string for testing";
        SimHashService hash1 =new SimHashService(s, 64);
        System.out.println(hash1.intSimHash +"  " + hash1.intSimHash.bitLength());
        hash1.subByDistance(hash1,3);

        s ="This is a test string for testing, This is a test string for testing abcdef";
        SimHashService hash2 =new SimHashService(s, 64);
        System.out.println(hash2.intSimHash +"  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2,3);

        s ="This is a test string for testing als";
        SimHashService hash3 =new SimHashService(s, 64);
        System.out.println(hash3.intSimHash +"  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3,4);

        System.out.println("============================");

        int dis = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
        System.out.println(hash1.hammingDistance(hash2) +" " + dis);

        int dis2 = hash1.getDistance(hash1.strSimHash, hash3.strSimHash);
        System.out.println(hash1.hammingDistance(hash3) +" " + dis2);
    }
}
