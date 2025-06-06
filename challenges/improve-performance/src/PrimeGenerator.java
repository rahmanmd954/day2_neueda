import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator {

    public List<BigInteger> getPrimes(int size) {
        System.out.println("About to find " + size + " primes.");

        List<BigInteger> primes = generatePrimes(size);

        System.out.println("Found all " + primes.size() + " primes.");
        return primes;
    }

    private List<BigInteger> generatePrimes(int size) {
        int limit = estimateUpperBound(size); // Estimate an upper bound for the nth prime
        boolean[] isPrime = new boolean[limit + 1];
        List<BigInteger> primes = new ArrayList<>();

        // Initialize the sieve
        java.util.Arrays.fill(isPrime, true); // Use Arrays.fill for faster initialization
        isPrime[0] = false;
        isPrime[1] = false;

        // Sieve of Eratosthenes
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        // Collect primes
        for (int i = 2; i <= limit && primes.size() < size; i++) {
            if (isPrime[i]) {
                primes.add(BigInteger.valueOf(i));
            }
        }

        return primes;
    }

    private int estimateUpperBound(int size) {
        // Improved approximation for the nth prime using a tighter bound
        if (size < 6) return 15; // Small size fallback
        return (int) (size * (Math.log(size) + Math.log(Math.log(size))) + 10); // Add a small buffer
    }
}