import java.util.*;
import java.io.*;

/**
 * JobScheduling
 */
public class JobScheduling {

  public static void printJobs(int[][] jobs) {
    for (int i = 0; i < jobs.length; i++){
      System.out.print(jobs[i][0] + " " + jobs[i][1] + " " + jobs[i][2]);
      System.out.println();
    }
  }

  public static void main(String[] args) throws IOException {
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    int nJobs = Integer.parseInt(br.readLine());

    int[][] jobs = new int[nJobs][3];

    //* taking input start time, finish time, profit

    for (int i = 0; i < nJobs; i++) {
      String[] jobDetails = br.readLine().split(" ");
      jobs[i][0] = Integer.parseInt(jobDetails[0]);
      jobs[i][1] = Integer.parseInt(jobDetails[1]);
      jobs[i][2] = Integer.parseInt(jobDetails[2]);
    }

    printJobs(jobs);

  }
}