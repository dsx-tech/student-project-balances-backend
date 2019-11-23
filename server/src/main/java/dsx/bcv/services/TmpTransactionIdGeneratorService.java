package dsx.bcv.services;

import java.util.concurrent.atomic.AtomicLong;

public class TmpTransactionIdGeneratorService {
    private static AtomicLong idCounter = new AtomicLong();

    public static long createID()
    {
        return idCounter.getAndIncrement();
    }
}
