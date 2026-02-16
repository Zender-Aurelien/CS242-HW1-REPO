/*************************************************************************
 *
 *  Pace University
 *  Spring 2026
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Zender Aurelien-Nikolai, Safwan Chowdhury, Aleks Museridze
 *  Collaborators: PUT THE NAME OF ANY COLLABORATORS OUTSIDE YOUR TEAM HERE, IF NONE, PUT NONE
 *  References: PUT THE LINKS TO YOUR SOURCES HERE
 *
 *  Assignment: Experimental Assignment #1
 *  Problem: Maximum Subarray Sum
 *  Description: PUT A BRIEF DESCRIPTION HERE
 *
 *  Input: 
 *  Output: 
 *
 *  Visible data fields:
 *  COPY DECLARATION OF VISIBLE DATA FIELDS HERE
 *
 *  Visible methods:
 *  COPY SIGNATURE OF VISIBLE METHODS HERE
 *
 *
 *   Remarks
 *   -------
 *
 *   PUT ALL NON-CODING ANSWERS HERE
 *
 *
 *************************************************************************/
import java.util.Random;
public static int maxSubArrSumBruteForce(int[] arr) {
    int maxSub=Integer.MIN_VALUE;
    for(int i=1;i<arr.length;i++){
        int sum=0;
        for(int j=i; j<arr.length;j++){
        sum+=arr[j];
        maxSub=Math.max(sum,maxSub);
        }
    }
    return maxSub;
}

public static int maxSubArray(int[]arr, int low, int high){
    if(high==low){
        return arr[low];
    }
    else{
        int mid=(low+high)/2;
        int leftsum=maxSubArray(arr, low, mid);
        int rightsum=maxSubArray(arr, mid+1, high);
        int crosssum=maxCrossSub(arr, low, mid, high);
        if(rightsum>leftsum){
            return Math.max(rightsum,crosssum);
        }else{
            return Math.max(leftsum,crosssum);
        }

    }
}

static int maxCrossSub(int[] arr, int low, int mid, int high){
    int leftsum=Integer.MIN_VALUE, rightsum= Integer.MIN_VALUE;
    int sum=0;

    for(int i=mid; i>=low; i--){
        sum+=arr[i];
        if(sum>leftsum){
            leftsum=sum;
        }
    }

    sum=0;
    for(int j=mid+1; j<=high;j++){
        sum +=arr[j];
        if(sum>rightsum){
            rightsum=sum;
        }
    }
    return leftsum+rightsum;
}


public static void main(String[] args) {
    Random rand=new Random(20);
    int[] arr= new int[1000];

    for(int i=0;i<arr.length;i++){
        arr[i]=rand.nextInt();
    }

    //System.out.println(maxSubArrSumBruteForce(arr));
    int answer=maxSubArray(arr, arr[0], arr[arr.length-1]);
    System.out.println(answer);


}
