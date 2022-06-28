package rs.ac.bg.etf.pp1;

import java.io.IOException;

public class MegaTest {

	static final int cinilac = 4, delilac = 3;

	static class Predmet {
		int tezina;
		int koefTezine;
		char vrsta;

		Predmet() {
			koefTezine = 1;
		}

		void postaviTezinu(int tezina) {
			this.tezina = tezina * koefTezine;
		}

		void ucitajTezinu() {
			try {
				tezina = System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int zapremina() {
			return 0;
		}

		int Q() {
			return zapremina() * tezina;
		}

		void postaviVrstu(char vrsta) {
			this.vrsta = vrsta;
		}

		char dohvVrstu() {
			return vrsta;
		}

		int dohvTezinu() {
			return tezina;
		}

		void ispisi() {
			System.out.print(vrsta);
			System.out.print(Q());
		}

	}

	static class Sfera extends Predmet {
		int r;

		Sfera() {
			super();
			vrsta = 's';
		}

		void postaviPoluprecnik(int r) {
			this.r = r;
		}

		int zapremina() {
			int z;
			z = cinilac;
			return z * (r / delilac);
		}

		void ispisi() {
			super.ispisi();
			System.out.println(r);
		}

	}

	static class Kvadar extends Predmet {
		int a, b, c;

		Kvadar() {
			super();
			vrsta = 'k';
		}

		void postaviStranice(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		int zapremina() {
			int z;
			z = a * b * c;
			return z;
		}

		void ispisi() {
			super.ispisi();
			System.out.print(a);
			System.out.print(b);
			System.out.println(c);
		}

	}

	static int zapreminaK, zapreminaS;
	static int tezinaK, tezinaS;

	static void ispis( Predmet p)
		{
			p.ispisi();
			System.out.println(); 
			System.out.print(p.dohvTezinu());
		}

	public static void main(String[] args)
		{	
		 Predmet predmeti[]; int i; Sfera s1, s2, s3; Kvadar k1, k2, k3; int t;
			predmeti = new Predmet[6];
			s1 = new Sfera();
			s2 = new Sfera();
			s3 = new Sfera();
			
			k1 = new Kvadar();
			k2 = new Kvadar();
			k3 = new Kvadar();
			
			s1.postaviPoluprecnik(1);
			s2.postaviPoluprecnik(2);
			s3.postaviPoluprecnik(3);
			

			k1.postaviStranice(1,2,3);
			k2.postaviStranice(2,3,4);
			k3.postaviStranice(3,4,5);
			
			predmeti[0] = s1;
			predmeti[2] = s2;
			predmeti[4] = s3;
			predmeti[1] = k1;
			predmeti[3] = k2;
			predmeti[5] = k3;

			t = 46;
			i=0;
			do{
				predmeti[i].postaviTezinu(t);
				if(predmeti[i] instanceof Sfera) {
					System.out.print('s');
					System.out.print('f');
					System.out.print('e');
					System.out.print('r');
					System.out.println('a');
				} else if(predmeti[i] instanceof Kvadar) {
					System.out.print('k');
					System.out.print('v');
					System.out.print('a');
					System.out.print('d');
					System.out.print('a');
					System.out.println('r');
				} 	
				i++;
			}while(i<6);
			
			zapreminaS = 0;
			tezinaS = 0;
			i = 1;
			do{
				if(i % 2 == 0) {
					i++;
					continue; 
				}
				zapreminaS = zapreminaS + predmeti[i - 1].zapremina();
				tezinaS = tezinaS + predmeti[i - 1].Q();
				ispis(predmeti[i - 1]);
				i++;
			}while(i<=6);
			
			
			zapreminaK = 0; 
			tezinaK = 0;
			i = 1;
			do{
				if(i % 2 == 1) {
					i++;
					continue; 
				}
				zapreminaS = zapreminaS + predmeti[i - 1].zapremina();
				tezinaS = tezinaS + predmeti[i - 1].Q();
				ispis(predmeti[i - 1]);
				i++;
			}while(i<=6);
			
			System.out.println(zapreminaS);
			System.out.println(zapreminaK);
			System.out.println(tezinaS);
			System.out.println(tezinaK);

		}
	
}
