//Author: Phong Tat
//Class: Object Oriented Programming
package com.mycompany.mentalmath;
import java.util.ArrayList;

public class Operate extends MentalMath{
    private ArrayList<Double> reduced = new ArrayList<>(); //copied arraylist able to manipulate size for reduction
    private String result; //store reduced data in result
    
    public Operate(MentalMath obj) {
        for (int i = 0; i < obj.get_instance_size(); i++) {
            if (obj.is_mutated(i)) {
                reduced.add(factorial(obj.get_instance_at(i))); //copy over instances arraylist to reduced arraylist
            }
            else
                reduced.add(obj.get_instance_at(i));
        }
    }
    
    public void get_reduced() {
        if (!reduced.isEmpty()) {
            System.out.print("\nThe reduced is: ");
            for (int i = 0; i < reduced.size(); i++) {
                System.out.print(reduced.get(i) + " ");
            }
        }
        else
            System.out.println("The instance is empty.");
    }
    
    public void set_solution(double ans) {
        result = String.format("%.3f",ans); //store results as a string to 3 decimal places.
    }
    
    public String get_solution() {
        return result;
    }
    
    
    public boolean PEMDAS() {
        double highest = 0;
        for(int i = 0; i < ((reduced.size() - 1) / 2); i++) { //for size N, check (N-1)/2 operators
            if (reduced.get((2*i) + 1) > highest) //
                highest = reduced.get((2*i) + 1);
        }
        //if there exists a mult or div operator, prioritize those first.
        if (highest >= 2)
            return true;
        else
            return false;
    }
    
    public final double factorial(double num) {
        if (num <= 1)
            return 1.0;
        else
            return num * factorial(num - 1);
    }
    
    public void solve() {
        int index_to_check;
        double calc, op_at_index;
        
        while(reduced.size() != 1) {
            for (int i = 0; i < ((reduced.size() - 1) / 2); i++) { //for size N, check for (N-1)/2 operators.
                index_to_check = (2*i) + 1;
                op_at_index = reduced.get(index_to_check);      //check the odd index for operators
                if (op_at_index == 2.0) {
                    //System.out.println("\nMult found");
                    calc = (reduced.get(index_to_check - 1)) * (reduced.get(index_to_check + 1)); // (left of op) (op) (right of op)
                    reduced.set(index_to_check - 1, calc);      //store results in (left of op)
                    reduced.remove(index_to_check);             //remove (op)
                    reduced.remove(index_to_check);             //remove (right of op)
                    break;
                }
                else if (op_at_index == 3.0) {
                    //System.out.println("\nDiv found");
                    calc = (reduced.get(index_to_check - 1)) / (reduced.get(index_to_check + 1));
                    reduced.set(index_to_check - 1, calc);
                    reduced.remove(index_to_check);
                    reduced.remove(index_to_check);
                    break;
                }
                else if (!PEMDAS() && op_at_index == 1.0) {
                    //System.out.println("\nSubtraction found");
                    calc = (reduced.get(index_to_check - 1)) - (reduced.get(index_to_check + 1));
                    reduced.set(index_to_check - 1, calc);
                    reduced.remove(index_to_check);
                    reduced.remove(index_to_check);
                    break;
                }
                else if (!PEMDAS() && op_at_index == 0.0) {
                    //System.out.println("\nAddition found");
                    calc = (reduced.get(index_to_check - 1)) + (reduced.get(index_to_check + 1));
                    reduced.set(index_to_check - 1, calc);
                    reduced.remove(index_to_check);
                    reduced.remove(index_to_check);
                    break;
                }
            }
            
        }
        set_solution(reduced.get(0));
    }
    
    public void display_result() {
        System.out.print("\nThe answer is: " + get_solution());
    }
    
    public String validate(String answer) {
        if (answer.equals(get_solution())) {
            return "Correct!";
        }
        else
            return "Incorrect.";
    }
}
