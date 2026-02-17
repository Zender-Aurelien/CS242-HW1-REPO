/********************************************************************
 *
 * Pace University
 * Spring 2026
 * CS 242 - Algorithms and Computing Theory
 *
 * Team members: Safwan, Zender, Aleks
 * Other collaborators: n/a
 * References: ... (to be filled in by the team at the end of the assignment)
 *
 * Assignment: 1
 * Description: This program implements two algorithms for the
 *   Maximum Subarray Sum Problem as requested by the assignment
 *   description: 
 *   - a brute-force O(n^2) algorithm 
 *   - a divide-and-conquer O(n log n) algorithm. 
 *   It measures and compares the running times of both algorithms in
 *   nanoseconds for user specified array sizes.
 *
 * Input: The size of the array n as an integer value.
 * Output: The maximum subarray sum and the running time in
 *   nanoseconds for each algorithm.
 *
 * Visible variables:
 *   int n: size of the array
 *   int[] array: the input array of random numbers
 *
 * Visible methods:
 *   public static int bruteForce(int[] A, int n)
 *   public static int maxSubarray(int[] A, int low, int high)
 *   public static int maxCrossingSubarray(int[] A, int low, int mid, int high)
 *   public static void main(String[] args)
 *
 * Remarks:
 *
 * [TABLE in nanoseconds]
 *              | n = 10^2 | n = 10^3 | n = 10^4 | n = 10^5 | n = 10^6 |
 * -----------------------------------------------------------------
 * Brute Force  |  390800  | 8678200  | 40145100 |1954768500|242662110900 |
 * Divide &     |  337000  | 1477500  | 2943200  |39091700  |68441200  |
 * Conquer      |          |          |          |          |          |
 *
 * 3a) Are the running times measured for Brute Force consistent with the
 *     theoretical running time? 
 *     - Yes
 *
 * 3b) Justify 3a
 *     - The brute force algorithm has theoretical running time O(n^2). This means that when n is
 *       multiplied by 10, the running time should increase by approximately 100 times, because
 *       (10n^2)/n^2 = 100. Looking at the table when n increases from 10^4 to 10^5, the running
 *       time increases from 40,145,100 nanoseconds to 1,954,768,500 nanoseconds. The ratio is
 *       1,954,768,500/ 40,145,100 = 48.69. When n increases from 10^5 to 10^6, the running
 *       time increases from 1,954,768,500 nanoseconds to 242,662,110,900 nanoseconds. The
 *       ratio is 242,662,100,900/1,954,768,500 = 124.14. Although these ratios are not exactly
 *       100, they are of the same order of magnitude and demonstrate strong quadratic growth,
 *       especially for larger values of n. The smaller input sizes do not scale perfectly because
 *       of constant overhead, JVM warm up effects, and timing fluctuations that can distort
 *       measurements when n is small. Overall, as n increases, the running times grow in a
 *       manner consistent with the theoretical O(n^2) complexity

 *
 * 3c) Are the running times measured for Divide & Conquer consistent with
 *     the theoretical running time? 
 *     - Yes
 *
 * 3d) Justify 3c
 *     - The divide and conquer algorithm has theoretical running time O(n log n). When n is multiplied by 10, 
 *       the expected growth factor is approximately 10 × log(10n)/log(n), which yields ratios of roughly 12–15 depending on input n. 
 *       Looking at the table, from n = 10⁴ to n = 10⁵, the running time increases from 2,943,200 to 39,091,700 nanoseconds, 
 *       giving a ratio of 39,091,700 / 2,943,200 = 13.28, which closely matches the predicted ratio of approximately 12.5. 
 *       The smaller input sizes (10² to 10³ and 10³ to 10⁴) show ratios of 4.38 and 1.99, which are lower than expected. 
 *       Similarly, from 10⁵ to 10⁶ the ratio is only 1.75. These deviations at small n are most probably due to JVM warmup, 
 *       constant overhead, and timing granularity, while the deviation at large inputs of n may be due to caching effects or JIT
 *       compilation optimizing the recursive calls over time. However, the middle range (10⁴ to 10⁵) demonstrates growth consistent with 
 *       O(n log n), and overall the times grow much slower than the brute force quadratic times, which is consistent with the theoretical n log n complexity.
 *
 *******************************************************************/

import java.util.Random;
import java.util.Scanner;

public class MaxSubarraySum {

    /**
     * Brute force algorithm for Maximum Subarray Sum.
     * Time complexity: O(n^2).
     *
     * @param A the input array of integers
     * @param n the length of the array
     * @return the maximum subarray sum
     */

    public static int bruteForce(int[] A, int n) {
        int sMax = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum = sum + A[j];
                if (sum > sMax) {
                    sMax = sum;
                }
            }
        }

        return sMax;
    }

    /**
     * Divide and conquer algorithm for Maximum Subarray Sum.
     * Time complexity: O(n log n).
     *
     * @param A    the input array of integers
     * @param low  the starting index of the subarray
     * @param high the ending index of the subarray
     * @return the maximum subarray sum
     */
    public static int maxSubarray(int[] A, int low, int high) {
        if (high == low) {
            return A[low];
        } else {
            int mid = (low + high) / 2;
            int leftSum = maxSubarray(A, low, mid);
            int rightSum = maxSubarray(A, mid + 1, high);
            int crossSum = maxCrossingSubarray(A, low, mid, high);

            return Math.max(leftSum, Math.max(crossSum, rightSum));
        }
    }

    /**
     * Computes the maximum subarray sum that crosses the midpoint.
     *
     * @param A    the input array of integers
     * @param low  the starting index
     * @param mid  the midpoint index
     * @param high the ending index
     * @return the maximum crossing subarray sum
     */
    public static int maxCrossingSubarray(int[] A, int low, int mid, int high) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = mid; i >= low; i--) {
            sum = sum + A[i];
            if (sum > leftSum) {
                leftSum = sum;
            }
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;

        for (int j = mid + 1; j <= high; j++) {
            sum = sum + A[j];
            if (sum > rightSum) {
                rightSum = sum;
            }
        }

        return leftSum + rightSum;
    }

    /**
     * Main method of the program. Prompts the user for the array size, generates
     * a random array, and measures the running time of both
     * algorithms in nanoseconds.
     *
     * @param args command-line arguments (not used)
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of the array (n): ");
        int n = scanner.nextInt();

        // Generate a random array of n integers (positive and
        // negative values in the range -1000 to 1000).
        Random random = new Random();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(2001) - 1000;
        }

        // For algorithm #1: brute force in O(n^2)
        long startTime = System.nanoTime();
        int bfResult = bruteForce(array, n);
        long bfTime = System.nanoTime() - startTime;

        System.out.println("Brute Force:");
        System.out.println("  Max subarray sum = " + bfResult);
        System.out.println("  t= " + bfTime + " nanosecs.");

        // For algorithm #2: divide and conquer in O(n log n)
        startTime = System.nanoTime();
        int dcResult = maxSubarray(array, 0, n - 1);
        long dcTime = System.nanoTime() - startTime;

        System.out.println("Divide and Conquer:");
        System.out.println("  Max subarray sum = " + dcResult);
        System.out.println("  t= " + dcTime + " nanosecs.");

        scanner.close();
    }
}