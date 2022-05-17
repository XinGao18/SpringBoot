package util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * <p>name：IdWorker.java</p>
 * <p>describe：Distributed self-growing ID</p>
 * <pre>
 *     Twitter's Snowflake JAVA implementation
 * </pre>
 * The core code for its IdWorker this class implementation, its principle structure is as follows, I use a 0 to indicate a bit respectively, with - to split the role of the part.
 * 1||0 --- 000000000000 0000000000 0000000000 0000000000 0 --- 00000 --- 00000 --- 00000000000000
 * In the above string, the first bit is unused (and can actually be used as a sign bit for long), the next 41 bits are milliseconds in time
 * then 5 bits for the datacenter identifier, 5 bits for the machine ID (not really an identifier, it's actually for the thread identifier), and
 * and then 12 bits of the current millisecond count within that millisecond, adding up to exactly 64 bits for a Long type.
 * The advantage of this is that the overall sorting is self-increasing by time, and there are no ID collisions throughout the distributed system (distinguished by datacenter and machine ID), * and it is more efficient.
 * And it is more efficient. snowflake has been tested to generate around 260,000 IDs per second, which is perfectly adequate.
 * <p>
 * 64-bit IDs (42(ms) + 5(machine IDs) + 5(business codes) + 12(repeat accumulation))
 *
 * @author Polim
 */
public class IdWorker {
    // The time start marker, as a reference, is generally the most recent time of the system (once established it cannot be changed).
    private final static long twepoch = 1288834974657L;
    //  Number of machine identification digits
    private final static long workerIdBits = 5L;
    // Number of data centre identification bits
    private final static long datacenterIdBits = 5L;
    // Number Machine ID Max
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // Data centre ID maximum
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // Self-increasing bits in milliseconds
    private final static long sequenceBits = 12L;
    // Machine ID shifted 12 digits to the left
    private final static long workerIdShift = sequenceBits;
    // Data centre ID shifted 17 digits left
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // Time millisecond left shift 22 bits
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    /* Last production id timestamp */
    private static long lastTimestamp = -1L;
    // 0, concurrency control
    private long sequence = 0L;

    private final long workerId;
    // Data identification id section
    private final long datacenterId;

    public IdWorker(){
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }
    /**
     * @param workerId
     *            Work machine ID
     * @param datacenterId
     *            Serial number
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    /**
     * Get the next ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // Within the current millisecond, then +1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // When the count is full in the current millisecond, wait for the next second
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // The ID offsets are combined to generate the final ID and return the ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * Access maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID hashcode Get 16 low bits
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * Data identification id section
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }


}

