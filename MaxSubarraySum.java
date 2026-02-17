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
 * [TABLE]
 *             | n = 10^2 | n = 10^3 | n = 10^4 | n = 10^5 | n = 10^6 |
 * -----------------------------------------------------------------
 * Brute Force  |  390800  | 8678200  | 40145100 |1954768500|242662110900 |
 * Divide &     |  337000  | 1477500  | 2943200  |39091700  |68441200  |
 * Conquer      |          |          |          |          |          |
 *
 * 3a) Are the running times measured for Brute Force consistent with the
 *     theoretical running time? 
 *     - [Y/N to be answered]
 *
 * 3b) Justify 3a
 *     - [reasoning to be filled in by the team]
 *
 * 3c) Are the running times measured for Divide & Conquer consistent with
 *     the theoretical running time? 
 *     - [Y/N to be answered]
 *
 * 3d) Justify 3c
 *     - [reasoning to be filled in by the team]
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