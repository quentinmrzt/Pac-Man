package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import evolution.Population;
import graphe.Graphe;
import model.Map;

public class LancerEvolution {
	public static void main(String[] args) {
		int NOMBREGLOBAL = 5;

		for (int indexglobal=1 ; indexglobal<=NOMBREGLOBAL ; indexglobal++) 
		{
			{
				int NOMBRETEST = 100;
				int NOMBREGENERATION = 100;

				int PROFONDEUR = 10;

				int NOMBREPOPULATION = 100;
				int NBSELECTION = 80;
				int NBVAINQUEUR = 2;
				double POURCENTAGEMUTATION = 0.20;

				int tableau[][][] = new int[NOMBRETEST][NOMBREGENERATION][NOMBREPOPULATION];
				double moyenneGeneration[][] = new double[NOMBRETEST][NOMBREGENERATION];
				double moyenneTest[] = new double[NOMBRETEST];
				double moyenneTestSelection[] = new double[NOMBRETEST];
				int selection = 10;

				Map map = new Map("src/map_gomme.txt");
				Graphe graphe = new Graphe(map);

				for (int indexTest=0 ; indexTest<NOMBRETEST ; indexTest++) {
					System.out.print(indexTest+"/");
					/*if(indexTest%20==0 && indexTest!=0) {
								System.out.println("");
							}*/
					// Test 1
					Population population = new Population(map,graphe,PROFONDEUR,NOMBREPOPULATION,NBSELECTION,NBVAINQUEUR,POURCENTAGEMUTATION);

					for (int indexGeneration=0 ; indexGeneration<NOMBREGENERATION; indexGeneration++) {
						population.reinitialisation(map, graphe);
						population.lancerJeux();
						tableau[indexTest][indexGeneration] = population.lancerSelection();
					}

					/*for (int indexGeneration=0 ; indexGeneration<100; indexGeneration++) {
								for (int indexIndividu=0 ; indexIndividu<100; indexIndividu++) {
									tableau[indexTest][indexGeneration][indexIndividu] = population.getResultat(indexGeneration, indexIndividu);
								}
							}*/
				}
				System.out.println("");

				double sommeGlobale = 0;
				double sommeGlobaleSelection = 0;
				for (int indexTest=0 ; indexTest<NOMBRETEST ; indexTest++) {
					double sommeTest = 0;
					double sommeTestSelection = 0;
					for (int indexGeneration=0 ; indexGeneration<NOMBREGENERATION; indexGeneration++) {
						double sommeGeneration = 0;
						for (int indexIndividu=0 ; indexIndividu<NOMBREPOPULATION; indexIndividu++) {
							sommeGeneration +=tableau[indexTest][indexGeneration][indexIndividu];
						}
						moyenneGeneration[indexTest][indexGeneration] = sommeGeneration/NOMBREGENERATION;
						sommeTest += sommeGeneration/NOMBREPOPULATION;
						if(indexGeneration>=NOMBREGENERATION-selection) {
							sommeTestSelection += sommeGeneration/NOMBREPOPULATION;
						}
					}
					moyenneTest[indexTest] = sommeTest/NOMBREGENERATION;
					sommeGlobale += sommeTest/NOMBREGENERATION;
					moyenneTestSelection[indexTest] = sommeTestSelection/selection;
					sommeGlobaleSelection += sommeTestSelection/selection;
				}

				double moyenneGlobale = sommeGlobale/NOMBRETEST;
				double moyenneGlobaleSelection = sommeGlobaleSelection/NOMBRETEST;
				double somme = 0;
				for (int i=0 ; i<NOMBRETEST ; i++) {
					somme += Math.pow(moyenneTest[i]-moyenneGlobale, 2);
				}
				double variance = somme/NOMBRETEST;
				double ecarttype = Math.sqrt(variance);

				System.out.print("Moyenne: "+moyenneGlobale+" / ");
				System.out.print("Moyenne "+selection+" génération: "+moyenneGlobaleSelection+" / ");

				System.out.print("Variance: "+variance+" / ");
				System.out.println("Ecart type: "+ecarttype);


				// ==============================================================
				// SAUVEGARDE DES DONNEES
				String STR_NOMBRETEST = "TEST";
				String STR_NOMBREGENERATION = "GENERATION";
				String STR_PROFONDEUR = "PROFONDEUR";
				String STR_NOMBREPOPULATION = "POPULATION";
				String STR_NBSELECTION = "SELECTION";
				String STR_NBVAINQUEUR = "VAINQUEUR";
				String STR_POURCENTAGEMUTATION = "MUTATION";


				int index = 1;
				File file = new File("test_"+index+".txt");

				while(file.exists()) {
					index++;
					file = new File("test_"+index+".txt");
				} 

				FileWriter fw;

				try {
					//Création de l'objet
					fw = new FileWriter(file);
					String str = "";
					str += "MOYENNE:;"+moyenneGlobale+"\n";
					str += "MOYENNE("+selection+"):;"+sommeGlobaleSelection+"\n";
					str += "VARIANCE:;"+variance+"\n";
					str += "ECART TYPE:;"+ecarttype+"\n";

					str += ";"+STR_NOMBRETEST+";"+STR_NOMBREGENERATION+";"+STR_PROFONDEUR+";"+STR_NOMBREPOPULATION+";"+STR_NBSELECTION+";"+STR_NBVAINQUEUR+";"+STR_POURCENTAGEMUTATION+"\n";
					str += "Parametres:;"+NOMBRETEST+";"+NOMBREGENERATION+";"+PROFONDEUR+";"+NOMBREPOPULATION+";"+NBSELECTION+";"+NBVAINQUEUR+";"+POURCENTAGEMUTATION+"\n";
					str += "\n";
					fw.write(str);


					for (int indexTest=0 ; indexTest<NOMBRETEST ; indexTest++) {
						System.out.print(indexTest+"/");
						/*if(indexTest%20==0 && indexTest!=0) {
									System.out.println("");
								}*/
						str = "";
						str += "Test "+indexTest+":;"+moyenneTest[indexTest]+"\n";
						for (int indexGeneration=0 ; indexGeneration<NOMBREGENERATION; indexGeneration++) {
							str += "G"+indexGeneration+":;"+moyenneGeneration[indexTest][indexGeneration]+";Pop:;";
							for (int indexIndividu=0 ; indexIndividu<NOMBREPOPULATION; indexIndividu++) {
								str += tableau[indexTest][indexGeneration][indexIndividu]+";";
							}
							str += "\n";
						}
						fw.write(str);
					}


					str = "\n";

					//On écrit la chaîne
					fw.write(str);
					//On ferme le flux
					fw.close();

					System.out.println("FIN ECRITURE");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}