package dsx.bcv.server.services;

import java.util.concurrent.atomic.AtomicLong;

public class TmpIdGeneratorService {
    private static AtomicLong idCounter = new AtomicLong();

    public static long createID()
    {
        return idCounter.getAndIncrement();
    }
}
