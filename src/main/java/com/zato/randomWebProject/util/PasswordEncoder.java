package com.zato.randomWebProject.util;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;


public class PasswordEncoder {

    public static String EncodePassword(String password) {
        int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
        int memoryCost = 8;      // increases memory usage
        int parallelization = 1; // currently not supported by Spring Security
        int keyLength = 32;      // key length in bytes
        int saltLength = 64;     // salt length in bytes

        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
                cpuCost,
                memoryCost,
                parallelization,
                keyLength,
                saltLength);
        return sCryptPasswordEncoder.encode(password);
    }
}
