import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Assignment1 {
    static final int MAX = 100000000;
    static final int THREADS = 8;
    static final int numbersPerThread = MAX / THREADS;
    static int counter = 0;
    static long sum = 0;
    static ArrayList<Integer> primes = new ArrayList<Integer>();


    public static void main(String[] args) throws Exception {

        // Create output file and file writer
        File myFile = new File("output.txt");
        FileWriter fw = new FileWriter("output.txt");

        // Start execution time, create thread array
        long startTime = System.nanoTime();
        Thread[] t = new Thread[THREADS];
        PrimeRun.m = new primeTracker();

        // Creates and starts 8 threads
        for (int i=0; i<THREADS; i++) {
            t[i] = new Thread(new PrimeRun(i));
            t[i].start();
        }

        // Wait for threads to finish
        for (int i=0; i<THREADS; i++)
            t[i].join();

        // Ensure primes list is in sorted order
        Collections.sort(primes);

        // End execution time and calculate
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        // Write output to file
        fw.write(duration / 1000000000 + " seconds, count = " + counter + ", sum = " + sum);
        fw.write(String.format("%n"));

        // Go to end - 10 index and count 10 primes up
        int size = primes.size();
        for (int s = 10; s > 0; s--)
        {
            fw.write(primes.get(size - s) + " ");
        }
        // Close file
        fw.close();
    }

    public static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n % 2) == 0) return false;

        // Prime numbers modded by 6 always equal 1 or 5
        if (n % 6 == 1 || n % 6 == 5)
        {
            // i starts at 3 and goes until i squared is greater than n
            // check if n is divisible by i
            for (int i = 3; i*i <= n; i += 2)
                // Not prime
                if (n % i == 0) return false;
        }
        // Not prime
        else
            return false;
        // prime
        return true;
    }

    public synchronized static void addPrime(int n) {
        primes.add(n);
    }
}

class PrimeRun implements Runnable {
    public static primeTracker m;
    final int ID;
    public PrimeRun(int i) {
        ID = i;
    }

    @Override
    public void run() {
        // Splits threads up into 8 chunks
        int start, end;
        start = ID * Assignment1.numbersPerThread;
        end = start + Assignment1.numbersPerThread - 1;

        // Check prime numbers from 2-100 million split into 8 chunks
        for(int i=start; i <= end; i++) {
            if(Assignment1.isPrime(i))
                m.addPrime(i);
        }
    }
}
// Synchronized method to add a prime to the arraylist, increment counter, and sum
class primeTracker {
    public synchronized void addPrime(int n) {
        Assignment1.addPrime(n);
        Assignment1.counter++;
        Assignment1.sum += n;
    }
}