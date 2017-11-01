// A Swing application to compute atmospheric conditions based on the International Standard Atmosphere model.
import javax.swing.*;
import java.awt.event.*;  
import java.awt.*;

class ISA_Demo {
	JLabel jlab1, jlab2, jlab3, jlab4, jlabinvalid;
	JTextField text1, text2, text3, text4;
	JButton jbtnClear, jbtnCalculate;
	private static final int decimalDigitsPrint = 4;
	SAConditions condit = new SAConditions(0);


	ISA_Demo() {
		// Create a new JFrame container.
		JFrame jfrm = new JFrame("International Standard Atmosphere Conditions");
		// Give the frame an initial size.
		int framewidth = 700, frameheight = 500;
		jfrm.setSize(framewidth, frameheight);
		jfrm.setLayout(null);

		// Terminate the program when the user closes the application.
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

///---------------- Parameters --------------------------------------------------
		int textx, texty, textwidth, textheight;
		int text1y, text2y, text3y, text4y;

		int labx, laby, labwidth, labheight;
		int lab1y, lab2y, lab3y, lab4y;

		int interdist;

		int btn1x, btn1y, btn1width, btn1height;
		int btn2x, btn2y, btn2width, btn2height;

		labx = 20; laby = 50;
		labheight = 30; labwidth = 300;

		textx = labx+labwidth+10;
		texty = laby;
		textheight = labheight; textwidth = labwidth;

		interdist = 2*labheight;

		lab1y = laby;
		text1y = texty;

		btn1width = 100; btn1height = 30;
		btn2width = btn1width; btn2height = btn1height;

		btn1x = textx; btn1y = text1y+interdist;
		btn2x = textx+textwidth-btn2width; btn2y = btn1y;

		lab2y = btn1y+interdist;
		text2y = lab2y;

		lab3y = lab2y+interdist;
		text3y = lab3y;

		lab4y = lab3y+interdist;
		text4y = lab4y;

		String labtext1 = "Altitude (in m):";
		String labtext2 = "Temperature (in K):";
		String labtext3 = "Pressure (in hPa):";
		String labtext4 = "Air Density (in kg per cubic m):";
		final String invalidAltitude = "Altitude must be positive real number";
		final String largeAltitude = "Altitude must be smaller than "+condit.getmaxAltitude()+" m";


///-------------- Create Components -----------------------------------------------------



		jlab1 = new JLabel(labtext1);
		jlab1.setBounds(labx,lab1y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab1);


		text1 = new JTextField();
		text1.setBounds(textx,text1y,textwidth,textheight);
		jfrm.add(text1);


		jlabinvalid = new JLabel(invalidAltitude);
		jlabinvalid.setBounds(textx, text1y+textheight, textwidth, textheight);
		jlabinvalid.setVisible(false);
		jfrm.add(jlabinvalid);


////----------------------------------
		jlab2 = new JLabel(labtext2);
		jlab2.setBounds(labx,lab2y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab2);


		text2 = new JTextField();
		text2.setBounds(textx,text2y,textwidth,textheight);
		//text2.setEditable(false);
		//text2.setDisabledTextColor(text1.getSelectedTextColor());
		jfrm.add(text2);

///--------------------------------
		jlab3 = new JLabel(labtext3);
		jlab3.setBounds(labx,lab3y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab3);


		text3 = new JTextField();
		text3.setBounds(textx,text3y,textwidth,textheight);
		jfrm.add(text3);

///---------------------------------
		jlab4 = new JLabel(labtext4);
		jlab4.setBounds(labx,lab4y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab4);


		text4 = new JTextField();
		text4.setBounds(textx,text4y,textwidth,textheight);
		jfrm.add(text4);

//---------------------------------

		// Make two buttons.
		jbtnClear = new JButton("Clear");
		jbtnCalculate = new JButton("Calculate");

		jbtnClear.setBounds(btn1x, btn1y, btn1width, btn1height);
		jbtnCalculate.setBounds(btn2x, btn2y, btn2width, btn2height);


//---------------- Action Listeners --------------------------------
		// Add action listener for Clear.
		jbtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				text1.setText("");
				text2.setText("");
				text3.setText("");
				text4.setText("");
			}
		});


		// Add action listener for Calculate.
		jbtnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String altitudeText = text1.getText();

				if (condit.validAltitude(altitudeText)) {
					double altitude;

					//altitude = condit.getNumber(altitudeText);
					altitude = getNumber(altitudeText);

					if (altitude<=condit.getmaxAltitude()) {
						jlabinvalid.setVisible(false);
						condit = new SAConditions(altitude);
						text2.setText(""+roundNumber(condit.getTemperature()));
						text3.setText(""+roundNumber(condit.getPressure()));
						text4.setText(""+roundNumber(condit.getDensity()));
					}
					else {
						jlabinvalid.setText(largeAltitude);
						jlabinvalid.setVisible(true);
					}
				}
				else {
					text1.setText("");
					jlabinvalid.setText(invalidAltitude);
					jlabinvalid.setVisible(true);
				}
			}
		});


		// Add the buttons to the content pane.
		jfrm.add(jbtnClear);
		jfrm.add(jbtnCalculate);



		// Display the frame.

		jfrm.setVisible(true);
	}

	private static double getNumber(String str) {
		double number;
		double intpart, decpart = 0, decdigits = 0;
		String[] tokens = str.split("\\.");

		// check validity
		intpart = Integer.parseInt(tokens[0]);
		if (tokens.length==2) {
			decpart = Integer.parseInt(tokens[1]);
			decdigits = tokens[1].length();
		}

		number = intpart+decpart/Math.pow(10,decdigits);

/*		try {
			number = Integer.parseInt(str);
		}
		catch (exception ){
			double intpart, decpart, decdigits;
			String[] tokens = str.split("\\.");

			intpart = Integer.parseInt(tokens[0]);
			decpart = Integer.parseInt(tokens[1]);
			decdigits = tokens[1].length();

			number = decpart/Math.pow(10,decdigits)+intpart;
		} */

		return number;
	}


	private static double roundNumber(double number) {
		return Math.round(number*Math.pow(10,decimalDigitsPrint))/Math.pow(10,decimalDigitsPrint);
	}


	public static void main(String args[]) {
	// Create the frame on the event dispatching thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ISA_Demo();
			}
		});
	}
}
