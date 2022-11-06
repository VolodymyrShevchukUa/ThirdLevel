package org.validation.shpp;

public interface Configurable {
    Config config = new Config();
    String QUEUE_NAME = config.getQUEUE();
    String NAME = config.getNAME();
    String URL = config.getBROKE_URL();
    String PASS = config.getPASSWORD();
    int time = config.getTIME();
    String POISON_PILL = "poison";
}
