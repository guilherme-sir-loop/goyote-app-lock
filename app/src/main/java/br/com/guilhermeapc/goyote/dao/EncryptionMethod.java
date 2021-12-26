package br.com.guilhermeapc.goyote.dao;

/**
 * Created by Guilherme on 27/03/2019.
 */

public class EncryptionMethod {
    public String descrypt(String saltedValue){
        String realValue;
        realValue =  saltedValue.replaceAll("AY!k","0").replaceAll("BV@l","1").replaceAll("CZ#m","2").replaceAll("DX\\$n","3").replaceAll("EW%o","4").replaceAll("FS&p","5").replaceAll("GT\\*q","6").replaceAll("HR1r","7").replaceAll("IU2s","8").replaceAll("JM3t","9");
        return realValue;
    }
    public String encrypt(String password){
        String encryptValue;
        /**
         * 0 = AY!k
         * 1 = BV@l
         * 2 = CZ#m
         * 3 = DX$n
         * 4 = EW%o
         * 5 = FS&p
         * 6 = GT*q
         * 7 = HR1r
         * 8 = IU2s
         * 9 = JM3t
         * **/
        encryptValue =  password.replaceAll("0", "AY!k").replaceAll("1", "BV@l").replaceAll("2", "CZ#m").replaceAll("3", "DX\\$n").replaceAll("4", "EW%o").replaceAll("5", "FS&p").replaceAll("6", "GT*q").replaceAll("7", "HR1r").replaceAll("8", "IU2s").replaceAll("9", "JM3t");
        return encryptValue;
    }
}
