//Author: Phong Tat
//Class: Object Oriented Programming
package com.mycompany.mentalmath;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
//import java.util.Arrays; //testing purposes

public class MentalMath {
    private final ArrayList<Double> instances = new ArrayList<>();      //layout of equation in number  
    private final int number[] = {1,2,3,4,5,6,7,8,9};                   //available numbers for equation
    private final int mutated_array[] = {0,0,0,0,0};                    //up to 5 numbers to mutate
    private final String operation[] = {" + "," - "," * "," \u00F7 "};  // "\u00F7" will display ÷
    //available operations for equation   0     1     2        3
    
    public MentalMath(){
    }
    
    public MentalMath(int level) {
        Generate_instance(level);
        Generate_mutator();
    }
    
    public double random_range(int start, int end) {
        Random rando = new Random();
        double number_range = rando.nextInt((end - start) + 1) + start; //pick a random number from (0,end-start)
        return number_range;                                            //then add start to get random number from (start,end)
    }
    
    public final void Generate_instance(int level) {
        int size = (level * 2) + 1;
        for (int i = 0; i < size; i++) {
            if ((i % 2) == 0)
                instances.add(random_range(1,9));
            else
                instances.add(random_range(0,3));
        }
    }
    
    public final void Generate_mutator() {
        Random rando = new Random();
        int location = rando.nextInt(instances.size()/2 + 1);           // random number from 0 to # of even instances
        if (instances.get(2*location) <= 3) {                           //if the number is 3 or below, apply mutator
            mutated_array[location] = 1;                                //flips the random location to on
        }
        //System.out.println("Location mutated " + Arrays.toString(mutated_array));
    }
    
    public boolean is_mutated(int index) {
        if (index % 2 == 0 && mutated_array[index/2] == 1) {            //only checks at even index
            return true;
        }
        else
            return false;
    }

    public void get_instance() {                                        //for code testing purposes
        System.out.print("The instance is: ");
        if (!instances.isEmpty()) {
            for (int i = 0; i < instances.size(); i++) {
                System.out.print(instances.get(i) + " ");
            }
        }
        else
            System.out.println("The instance is empty.");
    }
    
    public final double get_instance_at(int index) {
        return instances.get(index);
    }
    
    public final int get_instance_size() {
        return instances.size();
    }
    
    public int get_number(int index) {
        return number[index];
    }
    
    public String get_operation(int index) {
        return operation[index];
    }
    
    public void display_question(int num) {
        System.out.print("\n========================================\nQ" + num + ": ");
        for (int i = 0; i < instances.size(); i++) {
            if ((i%2) == 0 && mutated_array[i/2] == 1) {
                System.out.print(get_number((int) (instances.get(i) - 1.0)) + "!"); //display factorial symbol for mutated instances
            }
            else if ((i%2) == 0) {
                System.out.print(get_number((int) (instances.get(i) - 1.0)));
            }
            else
                System.out.print(get_operation((int) (instances.get(i) + 0.0)));
        }
        System.out.print(" = ");
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int level = 1, question_number = 0, correct = 0;
        double user_ans;
        long start_time, end_time;
        String start, auto_format, result;
        
        //introduction
        System.out.println("Welcome to MentalMather!");
        System.out.println("Please make sure to round your answer to the nearest 3rd decimal point!");
        System.out.println("Press ENTER to start");
        start = input.nextLine();
        
        //start
        start_time = System.currentTimeMillis();
        while (question_number < 10) {
            if ((question_number % 3) == 0 && question_number > 0) //every 3rd loop, increases difficulty
                level++;
            
            MentalMath test = new MentalMath(level);
            Operate solution = new Operate(test);
            try {
                //test.get_instance();
                //solution.get_reduced();
            
                test.display_question(question_number + 1);
                solution.solve();
            
                user_ans = input.nextDouble();
                auto_format = String.format("%.3f", user_ans);
 
                result = solution.validate(auto_format);
                System.out.println(result);
                if (result.equals("Correct!")) {
                    correct++;
                }
                solution.display_result();
            
                question_number++;

            }
            catch (InputMismatchException e) {
                //display error
                System.out.println(e.toString() + " ERROR,\n please enter a number as an answer.");
                
                //clean up
                input.nextLine();
                auto_format = "";
                
                result = solution.validate(auto_format);
                if (result.equals("Correct!")) {
                    correct++;
                }
                solution.display_result();
            
                question_number++;
            }
        }
        
        end_time = System.currentTimeMillis();
        System.out.println("\nYou got " + correct + " questions correct out of 10.");
        System.out.println("\nYou took " + (double)((end_time - start_time)/1000) + " seconds total.");
    }
}