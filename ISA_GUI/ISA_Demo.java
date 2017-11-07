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
	JComboBox<String> cbAltitude;
	JComboBox<String> cbTemperature;
	JComboBox<String> cbPressure;
	JComboBox<String> cbDensity;
	boolean cleared = true;

	ISA_Demo() {


///---------------- Parameters --------------------------------------------------


		int textx, texty, textwidth, textheight;
		int text1y, text2y, text3y, text4y;

		int labx, laby, labwidth, labheight;
		int lab1y, lab2y, lab3y, lab4y;

		int interdist;

		int btn1x, btn1y, btn1width, btn1height;
		int btn2x, btn2y, btn2width, btn2height;

		labx = 20; laby = 50;
		labheight = 30; labwidth = 150;

		textx = labx+labwidth+10;
		texty = laby;
		textheight = labheight;
		textwidth = 350;

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

		int cbx, cby, cbwidth, cbheight;
		int cb1y, cb2y, cb3y, cb4y;

		cbx = textx+textwidth; cby = texty;
		cbwidth = 70; cbheight = textheight;

		cb1y = text1y;
		cb2y = text2y;
		cb3y = text3y;
		cb4y = text4y;


		int framewidth = cbx+cbwidth+interdist;
		int frameheight = cb4y+cbheight+interdist;


		String labtext1 = "Altitude:";
		String labtext2 = "Temperature:";
		String labtext3 = "Pressure:";
		String labtext4 = "Air Density:";
		final String invalidAltitude = "Altitude must be positive real number";
		final String largeAltitudeMeters = "Altitude must be smaller than "+condit.getmaxAltitude()+" m";
		final String largeAltitudeFeet = "Altitude must be smaller than "+Math.round(condit.m2foot(condit.getmaxAltitude()))+" feet";
		final String emptyAltitude = "Please enter an altitude";
		String[] altitudeUnits = {"m", "foot"};
		String[] temperatureUnits = {"K", "C"};
		String[] pressureUnits = {"Pa", "hPa", "bar"};
		String[] densityUnits = {"kg/m3"};


///-------------- Create Components -----------------------------------------------------


		// Create a new JFrame container.
		JFrame jfrm = new JFrame("International Standard Atmosphere Conditions");
		// Give the frame an initial size.
		jfrm.setSize(framewidth, frameheight);
		jfrm.setLayout(null);

		// Terminate the program when the user closes the application.
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


///------------------------------------------
		jlab1 = new JLabel(labtext1);
		jlab1.setBounds(labx,lab1y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab1);


		text1 = new JTextField();
		text1.setBounds(textx,text1y,textwidth,textheight);
		jfrm.add(text1);

		cbAltitude = new JComboBox<String>(altitudeUnits);
		cbAltitude.setBounds(cbx,cb1y,cbwidth,cbheight);
		jfrm.add(cbAltitude);

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
		text2.setEnabled(false);
		text2.setDisabledTextColor(Color.black);
		//text2.setDisabledTextColor(text1.getSelectedTextColor());
		jfrm.add(text2);

		cbTemperature = new JComboBox<String>(temperatureUnits);
		cbTemperature.setBounds(cbx,cb2y,cbwidth,cbheight);
		jfrm.add(cbTemperature);


///--------------------------------
		jlab3 = new JLabel(labtext3);
		jlab3.setBounds(labx,lab3y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab3);


		text3 = new JTextField();
		text3.setBounds(textx,text3y,textwidth,textheight);
		text3.setEnabled(false);
		text3.setDisabledTextColor(Color.black);

		jfrm.add(text3);

		cbPressure = new JComboBox<String>(pressureUnits);
		cbPressure.setBounds(cbx,cb3y,cbwidth,cbheight);
		jfrm.add(cbPressure);


///---------------------------------
		jlab4 = new JLabel(labtext4);
		jlab4.setBounds(labx,lab4y,labwidth,labheight);
		//jlab.setLocation(labx,laby);
		jfrm.add(jlab4);


		text4 = new JTextField();
		text4.setBounds(textx,text4y,textwidth,textheight);
		text4.setEnabled(false);
		text4.setDisabledTextColor(Color.black);

		jfrm.add(text4);

		cbDensity = new JComboBox<String>(densityUnits);
		cbDensity.setBounds(cbx,cb4y,cbwidth,cbheight);
		jfrm.add(cbDensity);

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
				cleared = true;
			}
		});


		// Add action listener for Calculate.
		jbtnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String altitudeText = text1.getText();

				if (condit.validAltitude(altitudeText)) {
					double altitude;


					altitude = getNumber(altitudeText);
					String units = cbAltitude.getSelectedItem().toString();
					if (units.equals("foot")) altitude = condit.foot2m(altitude);

					if (altitude<=condit.getmaxAltitude()) {
						jlabinvalid.setVisible(false);
						cleared = false;
						condit = new SAConditions(altitude);

						double temp = adjustTemperature(condit, cbTemperature);
						printValue(text2, roundNumber(temp));
						double press = adjustPressure(condit, cbPressure);
						printValue(text3, roundNumber(press));
						double dens = adjustDensity(condit, cbDensity);
						printValue(text4, roundNumber(dens));
					}
					else {
						if (cbAltitude.getSelectedItem().toString().equals("m"))
							jlabinvalid.setText(largeAltitudeMeters);
						else
							jlabinvalid.setText(largeAltitudeFeet);
						jlabinvalid.setVisible(true);
					}
				}
				else {
					if (altitudeText.equals("")) jlabinvalid.setText(emptyAltitude);
					else {
						text1.setText("");
						jlabinvalid.setText(invalidAltitude);
					}
					jlabinvalid.setVisible(true);
				}
			}
		});


/*		cbAltitude.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (!cleared) {
					double temp = adjustAltitude(condit, cbAltitude);
					printValue(text1, roundNumber(temp));
				}
			}
		}); */


		cbTemperature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (!cleared) {
					double value = adjustTemperature(condit, cbTemperature);
					printValue(text2, roundNumber(value));
				}
			}
		});


		cbPressure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (!cleared) {
					double value = adjustPressure(condit, cbPressure);
					printValue(text3, roundNumber(value));
				}
			}
		});


		cbDensity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (!cleared) {
					double value = adjustDensity(condit, cbDensity);
					printValue(text4, roundNumber(value));
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
		try {
			intpart = Integer.parseInt(tokens[0]);
		}
		catch (NumberFormatException e){
			//intpart = condit.getMaxAltitude()+1;
			intpart = 900000;
		}

		if (tokens.length==2) {
			try {
				decpart = Integer.parseInt(tokens[1]);
			}
			catch (NumberFormatException e) {
				tokens[1] = tokens[1].substring(0,10);
				decpart = Integer.parseInt(tokens[1]);
			}
			decdigits = tokens[1].length();
		}

		number = intpart+decpart/Math.pow(10,decdigits);


		return number;
	}


	private static double roundNumber(double number) {
		return Math.round(number*Math.pow(10,decimalDigitsPrint))/Math.pow(10,decimalDigitsPrint);
	}


	private static void printValue(JTextField field, double value) {
		field.setText(""+value);
	}


	private static double adjustAltitude(SAConditions cond, JComboBox<String> jcb) {
		String units = jcb.getSelectedItem().toString();
		double alt;
		if (units.equals("foot"))
			alt = cond.m2foot(cond.getAltitude());
		else
			alt = cond.getAltitude();

		return alt;
	}


	private static double adjustTemperature(SAConditions cond, JComboBox<String> jcb) {
		String units = jcb.getSelectedItem().toString();
		double temp;
		if (units.equals("C"))
			temp = cond.kelvin2Celcius(cond.getTemperature());
		else
			temp = cond.getTemperature();

		return temp;
	}


	private static double adjustPressure(SAConditions cond, JComboBox<String> jcb) {
		String units = jcb.getSelectedItem().toString();
		double press;
		if (units.equals("bar"))
			press = cond.pascal2bar(cond.getPressure());
		else if (units.equals("hPa"))
			press = cond.pascal2hpascal(cond.getPressure());
		else
			press = cond.getPressure();

		return press;
	}


	private static double adjustDensity(SAConditions cond, JComboBox<String> jcb) {
		//String units = jcb.getSelectedItem().toString();

		return cond.getDensity();
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
