/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mihirkumarp
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FirstComeFirstServe {
    public static void main(String [] argv) throws IOException {
      
        ArrayList<Integer> id = new ArrayList<>(); // Wait Time
        ArrayList<Integer> wTime = new ArrayList<>(); // Wait Time
        ArrayList<Integer> bTime = new ArrayList<>(); // job time
        
        int total = 0;
        float avg;
        
        File myFile = new File("jobs/job_5_0.txt");
        Scanner s = new Scanner(myFile);
        
        // Loops until there is next line
        while (s.hasNext()) {           
            //if there is int as next line, store it. Else, skip to next line.
            if(s.hasNextInt()){
                bTime.add(s.nextInt());
            }else
                s.nextLine();
           }
        s.close();
        
        Integer[] wtimearr = new Integer[bTime.size()];
        Integer[] btimearr = new Integer[bTime.size()];
        Integer[] idarr = new Integer[bTime.size()];
        int[] completearr = new int[bTime.size()];
      
        wtimearr = wTime.toArray(wtimearr);
        btimearr = bTime.toArray(btimearr);
        idarr = id.toArray(idarr);
        
        for (int j =0; j < bTime.size(); j++){
            idarr[j] = j+1;
            wtimearr[j] = 0;
            completearr[j] = 0;
        }
        
        for(int i = 1; i < wtimearr.length; i++){
           wtimearr[i]= btimearr[i-1] + wtimearr[i-1] ;
            total=total+wtimearr[i];
            completearr[i-1] = wtimearr[i];  
        }
        
        
        int n = bTime.size();
        avg=(float)total/n;
        System.out.println("\nProcess_ID\tBurst_time\tWait_time\tcomplete_time");
        for(int i=0;i<n;i++)
        {
            System.out.println(idarr[i]+"\t\t"+btimearr[i]+"\t\t"+wtimearr[i]+"\t\t"+completearr[i]);
        }
        System.out.println("\nTotal wait time: "+total+"\nAverage wait time: "+avg);
    }
}

