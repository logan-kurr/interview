package main.java.com.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.tests.uiTests.FullBookingFlow;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting 'Booking Flow End to End'");
        FullBookingFlow fullBookingFlow = new FullBookingFlow();
        fullBookingFlow.startUp();
        fullBookingFlow.testSelectRoomType();
        fullBookingFlow.cleanUp();
    }
}
