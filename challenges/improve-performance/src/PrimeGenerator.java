import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class PrimeGenerator {

    public List<BigInteger> getPrimes(int size) {

        System.out.println("About to find " + size + " primes.");

        ConcurrentLinkedQueue<BigInteger> primes = new ConcurrentLinkedQueue<>();
        ForkJoinPool customThreadPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        try {
            customThreadPool.submit(() -> {
                while (primes.size() < size) {
                    int remaining = size - primes.size();
                    ThreadLocalRandom.current()
                        .ints(remaining * 2) // Generate more candidates dynamically
                        .parallel()
                        .mapToObj(i -> new BigInteger(2000, ThreadLocalRandom.current()).nextProbablePrime())
                        .forEach(prime -> {
                            if (primes.size() < size) {
                                primes.add(prime);
                            }
                        });
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            customThreadPool.shutdown();
        }

        System.out.println("Found all " + primes.size() + " primes.");
        return new ArrayList<>(primes).subList(0, size);
    }
}