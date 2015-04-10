/**
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course: 08600
 * Date: Oct 1st, 2014
 * 
 * Description:
 * 		This java is used to implement the the GUI, including
 * 		the interface, the action and so on.
 * 		In the method update and print the data, I will the 
 * 		sort the datas based on date and print them in proper
 * 		and alined format.
 *      
 */



// try catch example
/*
 *         try {
			Thread.sleep(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 */


// label : description and amount and other text
// buuton: deposit
// textField: date filed: the field of date input~
// textArea: output
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;



public class HW4GUI implements ActionListener{
	
	private final int objectAmount = 99;
    private JTextField dateField, descField, amountField;
    private JTextArea textArea1, textArea2;
    private JButton checkButton, depositButton;
    private HW4Data[] transactions = new HW4Data[objectAmount];
    private int count = 0;
    private DecimalFormat dfAmount   = new DecimalFormat("##,###,##0.00");
    private DecimalFormat dfFee    = new DecimalFormat("-###,##0.00");
    private DecimalFormat dfBalance = new DecimalFormat("###,###,##0.00");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    
    // implement the GUI, which is created in the constructor
    // including the labels texts and so on.
    public HW4GUI() {
    	// create the frame
    	JFrame frame = new JFrame("08-600 Checking Account Register");
        frame.setSize(980,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create the pane
        JPanel pane = new JPanel();
        
        // create the label including date description and amount
        JLabel label1 = new JLabel("      Date                            "
        		+ "     ");
        JLabel label2 = new JLabel("             Description              "
        		+ "               "
        		+ "                          ");
 
        JLabel label3 = new JLabel("                                        "
        		+ "                          Amount                ");
        
        pane.add(label1);
        pane.add(label2);
        pane.add(label3);

        // fix the font
        Font fixedFont = new Font(Font.MONOSPACED,Font.BOLD,12);
        
        // get the default day
		Calendar c = Calendar.getInstance();	
		int year   = c.get(Calendar.YEAR);
	    int month  = c.get(Calendar.MONTH) + 1;
	    int day    = c.get(Calendar.DAY_OF_MONTH);
	    String defaultDay = month + "/" + day + "/" + year;
	    
	    // add fields(date, description and amount
        dateField = new JTextField(defaultDay,18);
        dateField.setFont(fixedFont);
        pane.add(dateField);

        descField = new JTextField(87);
        descField.setFont(fixedFont);
        pane.add(descField);
        
        // create the $ sign
        JLabel label4 = new JLabel("$");
        pane.add(label4);
        
        
        amountField = new JTextField(17);
        amountField.setFont(fixedFont);
        pane.add(amountField);

        // create the two buttons
        checkButton = new JButton("Write Check");
        checkButton.addActionListener(this);
        pane.add(checkButton);
        
        depositButton = new JButton("Make Deposit");
        depositButton.addActionListener(this);
        pane.add(depositButton);
        
        //create the texts
        textArea1 = new JTextArea(2,120);
        textArea1.setFont(fixedFont);
        textArea1.setEditable(false);
        textArea1.setForeground(Color.RED);
        pane.add(textArea1);
        

        textArea2 = new JTextArea(35,130);
        textArea2.setFont(fixedFont);
        textArea2.setEditable(false);
        
        JScrollPane scroller = new JScrollPane(textArea2);
        scroller.setHorizontalScrollBarPolicy
        (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.add(scroller);

        frame.setContentPane(pane);
        frame.setVisible(true);
        

    }

    public static void main(String[] args) {
        new HW4GUI();
    }

	public void actionPerformed(ActionEvent event) {
		Date date;
        String description = null;
        double amount = 0;
      
        textArea1.setText("");
//        textArea2.setText("");
        
        
        // check the amount input and transfer it to a double
        try {
            amount = Double.parseDouble(amountField.getText());
            if (amount <= 0 || amount > 10000000) {
            	textArea1.append("Amount is not a valid number\n");
            	return;
            }
            if (event.getSource() == checkButton) {
            	if (Math.abs(amount) < 0.01){
            		textArea1.append("Check amount is not a valid number\n");
                	return;
            	} else
            		amount = -amount;
            }
            if (event.getSource() == depositButton) {
            	if (Math.abs(amount) < 1){
            		textArea1.append("Deposit amount is not a valid number\n");
                	return;
            	} 
            }
        } catch (NumberFormatException e1) {
            textArea1.append("Amount is not a valid number\n");
            return;
        }
        
     // check the the description
        // trim is used to delete the space of trailing and heading
        description = descField.getText().trim(); 
        if(description.isEmpty()){
        	textArea1.append("Descrition is not a valid string\n");
        	return;
        }
  
      // check the date and transfer it to Date
        try {
        	String dateString = dateField.getText();
        	String[] parts = dateString.split("/");
        	int month = Integer.parseInt(parts[0]);
        	int day = Integer.parseInt(parts[1]);
        	if ((parts[0].length() != 1 && parts[0].length() != 2)||
        			(1 > month) || (12 < month) ||
        			(parts[1].length() != 1 && parts[1].length() != 2) ||
        			(1 > day) || (31 < day) ||
        			parts[2].length() != 4) {
        		textArea1.append("Date is not a valid date\n");
        		return;
        	}
            date = dateFormat.parse(dateString);
        } 
        catch (ParseException e) {
        	textArea1.append("Date is not a valid date\n");
        	return;
        }
        String table = updateAndAppendData(date, description, amount, count);
        textArea2.setText(table);
        count++;
   //     descField.setText(null);
    //    amountField.setText(null);
    
		
	}
	
	// update the the new data and sort the rows.
	// print the amount, fee and other datas one by one.
    private String updateAndAppendData(Date date, String desc, double amount,
    		int count) {
    	Date Date;
    	String arrayDate;
    	String arrayDesc;
    	double arrayAmount;
    	int arrayCheckNum;
    	double arrayFee;
    	double[] arrayBalance = new double[objectAmount];
        StringBuilder b = new StringBuilder();
        transactions[count] = new HW4Data(date, desc, amount);
        HW4Data.sort(transactions);
        // Print header
        b.append(" Date           check #      Description               "
        		+ "                                   Amount          Fee  "
        		+ "         Balance");
        b.append('\n');

 
        
        // Print the table
        for (int i=0; i < count+1; i++) { //i <= count+1
        	Date = transactions[i].getDate();
        	arrayDate = dateFormat.format(Date);
        	arrayDesc = transactions[i].getDescription();
        	arrayAmount = transactions[i].getAmount();
        	arrayCheckNum = transactions[i].getCheckNumber();
        	arrayFee = transactions[i].getFee();
        	
        	// calculate the balance
        	if (0 == i)
        		arrayBalance[i] = arrayAmount + arrayFee;
        	else 
        		arrayBalance[i] = arrayBalance[i-1] + arrayAmount + arrayFee;
        	
        	// append the date
        	for (int p = arrayDate.length(); p < 11; p++) b.append(' ');
            b.append(arrayDate);
            
            // append the check num if it is a check
            // if not, append space
        	for (int p = 0; p < 6; p++)
        		b.append(' ');
        	if (arrayAmount <0)
        		b.append(arrayCheckNum);
        	else{
        		for (int p = 0; p < 3; p++) {
        			b.append(' ');
        		}
        	}
        	
        	//append description
        	arrayDesc = arrayDesc.substring
        			(0, Math.min(50, arrayDesc.length()));
        	for (int p = 0; p < 9; p++ ) b.append(' ');
        	b.append(arrayDesc);
        	for (int p = arrayDesc.length(); p <52; p++ ) b.append(' ');
            
        	// append amount
        	String amountString = dfAmount.format(arrayAmount);
            for (int p = amountString.length(); p < 15; p++) b.append(' ');
            b.append(amountString);

            // append fee
            String feeString = dfFee.format(arrayFee);
            for (int p=feeString.length(); p<13; p++) b.append(' ');
            b.append(feeString);
  
            // append balance
            String balanceString = dfBalance.format(arrayBalance[i]);
            for (int p=balanceString.length(); p<18; p++) b.append(' ');
            b.append(balanceString);
            b.append('\n');
        	
        }
        return b.toString();
        
      
    }
    
    
}