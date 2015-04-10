/**
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course: 08600
 * Date: Oct 1st, 2014
 * 
 * Description:
 * 		This java is used to handle the data, return the amount,
 *      fee, and date. Also, in this java, a static sort method 
 *      is provided.
 *      
 */

import java.util.Date;


public class HW4Data {
	private static int checkNumCount = 100;
	private int checkNum;
	private Date date;
	private String description;
	private double amount;

	
	// checkNumCount is static which is used to update the amount
	// of check. It will be updated when a new check(hw4Data) is created.
	public HW4Data(Date d, String desc, double a){
		this.date = d;
		this.description = desc;
		this.amount = a;
		if(!this.isDeposit()){
			checkNumCount++;
			checkNum = checkNumCount;
		}
		
	}
	
	// Based on the date to sort the data.
	public static void sort(HW4Data[] transactions){
		HW4Data temp;
		int count = 0;
		for (int m = 0; m < transactions.length; m++){
			if (transactions[m] != null){
				count ++;	
			
			}else
				break;
		}

		temp = transactions[0];
		for (int i= 0; i < count; i++){
			for(int k = i; k < count; k++){
				if(transactions[k].getDate().before
						(transactions[i].getDate())){
					temp = transactions[i];
					transactions[i] = transactions[k];
					transactions[k] = temp;
				}
			}
				
			
		}
	}
	
	// return the date
	public Date getDate(){
		return date;
	}
	
	// return the description
    public String getDescription(){
    	return description;
    }
    
    // return the amount
    public double getAmount(){
    	return amount;
    }
    
    // Based on the amount, return if it is a deposit
    public boolean isDeposit(){
    	if (amount < 0){
    		
    		return false;
    		
    	} else
    		return true;	
    }
    
    //return the fee
    public double getFee(){
    	if (isDeposit()){
    		if (amount <100)
    			return 0.25;
    		else 
    			return amount * 0.25 / 100;
    
    	}else{
    		if (Math.abs(amount) < 10)
    			return 0.10;
    		else if(Math.abs(amount) > 100)
    			return 1;
    		else
    			return -amount * 0.01;
    	}
    }
    
    // return the check number
    public int getCheckNumber(){
    	return checkNum;
    }
    

}
