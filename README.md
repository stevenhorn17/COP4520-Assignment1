# COP4520-Assignment1
Assignment 1 for Parallel Processing to calculate primes up to 100 million

My approach splits up the work to find primes up to 100 million by dividing the range into chunks to split between the 8 processors. 

I took advantage of an optimization trick to reduce my exectuion time significantly. I learned of a rule where all prime numbers when modded by 6, produce either 1 or 5. So the first check for a number would be to see if it produced 1 or 5 when modded by 6. This helped to skip many of the larger numbers that would have taken longer to identify as prime or not. Once a number modded by 6 equals 1 or 5, I check to see if it has any factors.

Another optimization trick was to only check up to a numbers square root for factors, as it was pointless to go further.

To run:
cd to src directory with Assignment1.java
In command prompt type: javac Assignment1.java
Then type: java Assignment1
The output will be printed to output.txt
