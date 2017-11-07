import java.lang.Math;

class SAConditions {
	private double H,T,P,D;
	public static int manyObjects = 0;

	private static final double T0 = 288.15;
	private static final double rho0 = 1.225;
	private static final double P0 = 101325;
	private static final double R = 287.00;
	private static final double g0 = 9.8065;
	private static final double gtoR = g0/R;
	private static final double maxAltitude = 84852;

	private static final double[] height = {0, 11000, 20000, 32000, 47000, 51000, 71000, maxAltitude};
	private static final double[] lapseRate = {-0.0065, 0, 0.001, 0.0028, 0, -0.0028, -0.002};


	private static double[] temperature;
	private static double[] pressure;
	private static double[] density;


	private static void initializeTables() {

		temperature = new double[height.length];
		pressure = new double[height.length];
		density = new double[height.length];

		temperature[0] = T0;
		pressure[0] = P0;
		density[0] = rho0;

		double dh;
		double quantPressure, quantDensity;
		int i;

		for (i = 1; i<lapseRate.length+1; i++) {
			dh = height[i]-height[i-1];
			temperature[i] = temperature[i-1]+lapseRate[i-1]*dh;

			if (lapseRate[i-1]==0) {
				quantPressure = Math.exp(-gtoR/temperature[i]*dh);
				quantDensity = quantPressure;
			}
			else {
				quantPressure = Math.pow(temperature[i]/temperature[i-1], -gtoR/lapseRate[i-1]);
				quantDensity = quantPressure*temperature[i-1]/temperature[i];
			}

			pressure[i] = pressure[i-1]*quantPressure;
			density[i] = density[i-1]*quantDensity;

		}

	}


	SAConditions(double h) {
		if (manyObjects==0) {
			initializeTables();
		}

		this.H = h;
		int i = -1;
		while (h>height[++i]) {}
		if (i>0) i--;

		this.T = temperature[i]+lapseRate[i]*(h-height[i]);

		double quantPressure, quantDensity;
		if (lapseRate[i]==0) {
			quantPressure = Math.exp(-gtoR/T*(h-height[i]));
			quantDensity = quantPressure;
		}
		else {
			quantPressure = Math.pow(T/temperature[i], -gtoR/lapseRate[i]);
			quantDensity = quantPressure*temperature[i]/T;
		}

		this.P = pressure[i]*quantPressure;
		this.D = density[i]*quantDensity;

		manyObjects++;
		
	}


	public boolean validAltitude(String str) {
		if ((str.length()==0) || (str.substring(0,1).equals("-"))) return false;

		int size = str.length();
		int numberofCommas = 0;

		for (int i = 0; i < size; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				if (str.charAt(i)!='.')
					return false;
				else
					numberofCommas++;
			}
		}

		return numberofCommas<2;

	}


	public double getAltitude() {
		return this.H;
	}

	public double getTemperature() {
		return this.T;
	}

	public double getPressure() {
		return this.P;
	}

	public double getDensity() {
		return this.D;
	}

	public double getmaxAltitude() {
		return maxAltitude;
	}


	public double foot2m(double altitude) {
		return 0.3048*altitude;
	}

	public double m2foot(double altitude) {
		return altitude/0.3048;
	}

	public double kelvin2Celcius(double temp) {
		return temp-273.15;
	}

	public double celcius2Kelvin(double temp) {
		return temp+273.15;
	}

	public double pascal2hpascal(double press) {
		return press/100;
	}

	public double hpascal2pascal(double press) {
		return 100*press;
	}

	public double pascal2bar(double press) {
		return 0.00001*press;
	}

	public double bar2pascal(double press) {
		return 100000*press;
	}

	public double hpascal2bar(double press) {
		return 0.001*press;
	}

	public double bar2hpascal(double press) {
		return 1000*press;
	}

}

