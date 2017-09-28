package pg0018_vb0018KFold;

/*
Source code file name: "pg0018_vb0018KFold.java"

Date: 03/29/2017
References: 1. CS641 Class Notes, Dr. Ramazan Aygun
			2. 
			3. 
			4. 
			5. http://www.hub4tech.com/			
			6. http://stackoverflow.com/
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class pg0018_vb0018KFold {
    static Integer  K = 7;
	private static void NaiveBayesianClassification(ArrayList<ArrayList<String>> Data, int exclude_fold_number) {
		
		// ArrayList<ArrayList<Integer>> individual_population = new
		// ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<String>> Testing_data = new ArrayList<ArrayList<String>>();
		Integer[][] Individual_population = new Integer[10][6];
		Double[][] Cond_probability = new Double[10][6];

		for (int a = 0; a < 10; a++) {
			for (int b = 0; b < 6; b++) {
				Individual_population[a][b] = 0;
				Cond_probability[a][b] = 0.0;
			}
		}

		Integer two_count = 0;
		Integer four_count = 0;
		// Integer third = 0;
		// Integer fourth = 0;
		// Integer fifth = 0;
		// Integer sixth = 0;

		for (int i = 0; i < Data.size(); i++) {

			if (Data.get(i).get(9).equals("2")) {
				two_count++;
			} else if (Data.get(i).get(9).equals("4")) {
				four_count++;
			}

			// System.out.println(Data.get(i).get(Data.get(0).size() - 1));
			if (Data.get(i).get(10).equals(Integer.toString(exclude_fold_number))) {
				// System.out.println(exclude_fold_number);
				Testing_data.add(Data.get(i));
				continue;
			} else {
				if (Data.get(i).get(9).equals("2")) {
					// System.out.println(Data.get(i).get(Data.get(0).size() -
					// 2));
					for (int k = 0; k < 9; k++) {
						if (Data.get(i).get(k).equals("0")) {
							Individual_population[k][0]++;
						} else if (Data.get(i).get(k).equals("1")) {
							Individual_population[k][1]++;
						} else if (Data.get(i).get(k).equals("2")) {
							Individual_population[k][2]++;
						}
					}

				} else if (Data.get(i).get(9).equals("4")) {
					// System.out.println(Data.get(i).get(Data.get(0).size() -
					// 2));
					for (int k = 0; k < Data.get(0).size() - 2; k++) {
						if (Data.get(i).get(k).equals("0")) {
							Individual_population[k][3]++;
						} else if (Data.get(i).get(k).equals("1")) {
							Individual_population[k][4]++;
						} else if (Data.get(i).get(k).equals("2")) {
							Individual_population[k][5]++;
						}
					}

				}

			}
		}

		for (int k = 0; k < 9; k++) {
			Cond_probability[k][0] = (double) ( Individual_population[k][0]) / two_count;
			Cond_probability[k][1] = (double) ( Individual_population[k][1]) / two_count;
			Cond_probability[k][2] = (double) ( Individual_population[k][2]) / two_count;
			Cond_probability[k][3] = (double) ( Individual_population[k][3]) / four_count;
			Cond_probability[k][4] = (double) ( Individual_population[k][4]) / four_count;
			Cond_probability[k][5] = (double) ( Individual_population[k][5]) / four_count;

			/*System.out.println(k + "   " + Cond_probability[k][0]);
			System.out.println(k + "   " + Cond_probability[k][1]);
			System.out.println(k + "   " + Cond_probability[k][2]);
			System.out.println(k + "   " + Cond_probability[k][3]);
			System.out.println(k + "   " + Cond_probability[k][4]);
			System.out.println(k + "   " + Cond_probability[k][5]);
*/
		}

		/* Testing with the excluded Data */
		int Temp_Size = 9;

		for (int i = 0; i < Testing_data.size(); i++) {

			Double[] Probality_classification = new Double[K];

			for (int a = 0; a < K; a++) {
				Probality_classification[a]	= 1.0;
			}
			// Each testing data
			if (Testing_data != null) {
				for (int j = 0; j < Temp_Size; j++) {
					if (Testing_data.get(i).get(j).equals("0")) {
						Probality_classification[0] = Probality_classification[0] * Cond_probability[j][0];
						Probality_classification[1] = Probality_classification[1] * Cond_probability[j][3];
						//System.out.println("Probality_classification[0] " + Probality_classification[0]);
						//System.out.println("Probality_classification[1] " + Probality_classification[1]);
					} else if (Testing_data.get(i).get(j).equals("1")) {
						Probality_classification[0] = Probality_classification[0] * Cond_probability[j][1];
						Probality_classification[1] = Probality_classification[1] * Cond_probability[j][4];
						//System.out.println("Probality_classification[0] " + Probality_classification[0]);
						//System.out.println("Probality_classification[1] " + Probality_classification[1]);
					} else if (Testing_data.get(i).get(j).equals("2")) {
						Probality_classification[0] = Probality_classification[0] * Cond_probability[j][2];
						Probality_classification[1] = Probality_classification[1] * Cond_probability[j][5];
						//System.out.println("Probality_classification[0] " + Probality_classification[0]);
						//System.out.println("Probality_classification[1] " + Probality_classification[1]);
					}
				}
				 
			}
			//System.out.println("Probality_classification[0] " + Probality_classification[0]);
			//System.out.println("Probality_classification[1] " + Probality_classification[1]);
			if(Probality_classification[0] >= Probality_classification[1]){
				Testing_data.get(i).add("2");
			}
			else {
				Testing_data.get(i).add("4");
			}
		}
		/*StringBuilder sb = new StringBuilder();
		for (ArrayList<String> arrayList : Testing_data) {
			for (String s : arrayList) {
				sb.append(s);
				sb.append('\t');
			}
			sb.append('\n');
		}
		String Testing_data_sample = sb.toString();
		//System.out.println(Testing_data_sample);
*/	}

	public static void main(String args[]) {

		

		// The name of the file to open.
		String fileName = "bcwdisc.arff";
		ArrayList<ArrayList<String>> data_Attributes = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> data_Values = new ArrayList<ArrayList<String>>();

		// This will reference one line at a time
		String line = null;

		// Location of file
		String location_File = null;

		// Argument validation to check the availability of input file name and
		// percentage//
		if (args.length < 2) {
			location_File = fileName;
			System.out.println(
					"\n Provide input file name in the format of pg0018_vb0018DT -i <InputFile> -c <classattribute> -T M");
			// return;
		}

		// Scraping file path name from arguments//
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-i")) {
				location_File = args[i + 1];
			}
		}

		// Scraping percentage from arguments//
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-K")) {
				K = Integer.parseInt(args[i + 1]);
			}
		}

		// Scraping input file name from the file path//
		String[] location_File_Split = location_File.split(Pattern.quote("/"));
		String inputFileName = location_File_Split[location_File_Split.length - 1];

		try {

			// Changing variable inputFileName//

			FileReader fileReader = new FileReader(inputFileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedRead = new BufferedReader(fileReader);
			boolean readline = false;
			while ((line = bufferedRead.readLine()) != null) {

				if (!readline) {
					System.out.println(line);
					System.out.println("\n");
					if (line.contains("class")) {
						System.out.println("@attribute X");
						System.out.println("\n");
						System.out.println("@attribute bayesClass real");
						System.out.println("\n");
					}
				}

				if (line.equals("@data")) {
					readline = true;
					continue;
				}

				if ((line.length() > 2)) {
					if (!readline) {
						String[] lineSplit = line.trim().split("\\s+");
						// System.out.println(Arrays.toString(lineSplit));
						// System.out.println(Arrays.toString(lineSplit1));
						data_Attributes.add(new ArrayList<String>(Arrays.asList(lineSplit)));
					} else if (readline) {
						// if (rl_ct < m) {
						String[] lineSplit = line.trim().split("\\s+");
						// lineSplit.Append
						// System.out.println(Arrays.toString(lineSplit));
						data_Values.add(new ArrayList<String>(Arrays.asList(lineSplit)));
						// data_Values.add();

					}

				}

			}

			// Splitting the dataset as buckets of K-fold values//
			// System.out.println(data_Values);
			Integer data_count = 1;

			for (int j = 0; j < data_Values.size(); j++) {
				data_Values.get(j).add(Integer.toString(data_count));
				data_count++;
				if (data_count == K + 1) {
					data_count = 1;
				}
			}

			// Writing the Final ArrayList of ArrayList to the output file //

		
			// System.out.println(Data_Value);
			// System.out.println(data_Values);

			for (int I = 1; I <= K; I++) {
				NaiveBayesianClassification(data_Values, I);
			}
			
			StringBuilder sb = new StringBuilder();
			for (ArrayList<String> arrayList : data_Values) {
				for (String s : arrayList) {
					sb.append(s);
					sb.append('\t');
				}
				sb.append('\n');
			}
			String Data_Value = sb.toString();
			System.out.println(Data_Value);
			// Always close files.
			bufferedRead.close();

			/*
			 * // ***************** Code to traverse the tree *****************
			 */
			/*
			 * Integer search_counter = 0; Integer two_two_count = 0; Integer
			 * two_four_count = 0; Integer four_two_count = 0; Integer
			 * four_four_count = 0; Integer twos_majority_count = 0; Integer
			 * fours_majority_count = 0;
			 * 
			 * 
			 * System.out.println(
			 * "\n\n***************CONFUSION MATRIX***************\n");
			 * 
			 * System.out.print("Total = " + Total_Count + "\t");
			 * System.out.print("Predicted 2's\t"); System.out.print(
			 * "Predicted 4's\n"); System.out.print("Actual 2's\t");
			 * System.out.print(two_two_count + "\t\t");
			 * System.out.print(two_four_count + "\n"); System.out.print(
			 * "Predicted 4's\t"); System.out.print(four_two_count + "\t\t");
			 * System.out.print(four_four_count);
			 * 
			 * System.out.println(
			 * "\n\n***************ACCURACY***************\n\n" + "Accuracy = "
			 * + 100 * Accuracy + "%");
			 * 
			 * buffer_write_output3.write(
			 * "\n\n***************CONFUSION MATRIX***************\n");
			 * 
			 * buffer_write_output3.write("Total = " + Total_Count + "\t\t\t");
			 * buffer_write_output3.write("Predicted 2's\t\t\t");
			 * buffer_write_output3.write("Predicted 4's\n");
			 * buffer_write_output3.write("Actual 2's\t\t\t\t");
			 * buffer_write_output3.write(two_two_count + "\t\t\t\t\t\t");
			 * buffer_write_output3.write(two_four_count + "\n");
			 * buffer_write_output3.write("Predicted 4's\t\t\t");
			 * buffer_write_output3.write(four_two_count + "\t\t\t\t\t\t");
			 * buffer_write_output3.write(four_four_count+ "\t");
			 * 
			 * buffer_write_output3.write(
			 * "\n\n***************ACCURACY***************\n\n" + "Accuracy = "
			 * + 100 * Accuracy + "%");
			 * 
			 * 
			 * buffer_write_output1.close(); absolute_write_output1.close();
			 * buffer_write_output2.close(); absolute_write_output2.close();
			 * buffer_write_output3.close(); absolute_write_output3.close();
			 */
		}

		catch (

		FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {

		}

	}

}

// Parent_attr_ID holds the value of the attribute number from the arff
// (dataset)//
// Parent_index holds the index of the parent node in the tree //
/*
 * public static double Calculate_data_Entropy(ArrayList<ArrayList<String>>
 * data_Values) {
 * 
 * // System.out.println(data_Values.size()); double twos_ct = 0.0; double
 * fours_ct = 0.0;
 * 
 * if (data_Values.size() < 1) return 0;
 * 
 * for (int j = 0; j < data_Values.size(); j++) {
 * 
 * if (data_Values.get(j).get(9).equals("2")) { //
 * System.out.print("SUCCESS\n"); twos_ct++; } else if
 * (data_Values.get(j).get(9).equals("4")) { // System.out.print("SUCCESS\n");
 * fours_ct++; } }
 * 
 * double twos_prob = twos_ct / data_Values.size(); double four_prob = fours_ct
 * / data_Values.size();
 * 
 * if (twos_prob == 0) twos_prob = 1; if (four_prob == 0) four_prob = 1;
 * 
 * // System.out.println("twos_ct " + twos_ct); System.out.println( //
 * "fours_ct " + fours_ct); System.out.println("twos_prob " + // twos_prob);
 * System.out.println("four_prob " + four_prob);
 * 
 * // System.out.println("\n" + "twos_prob " + twos_prob + "\n"); //
 * System.out.println("\n" + "fours_prob " + four_prob + "\n");
 * 
 * double Entropy_overall = -1 (twos_prob * Math.log(twos_prob) / Math.log(2) +
 * four_prob * Math.log(four_prob) / Math.log(2)); // System.out.println(
 * "Entropy_overall " + Entropy_overall);
 * 
 * return Entropy_overall;
 * 
 * }
 * 
 * public static double Calculate_data_Attribute(ArrayList<ArrayList<String>>
 * data_Values, int Attr_ID) {
 * 
 * StringBuilder sb = new StringBuilder(); for (ArrayList<String> arrayList:
 * data_Values) { for (String s: arrayList) { sb.append(s); sb.append('\t'); }
 * sb.append('\n'); } String yourInfo = sb.toString();
 * System.out.print(yourInfo);
 * 
 * 
 * 
 * 
 * if (data_Values.size() < 1) return 0;
 * 
 * double zero_ct = 0; double one_ct = 0; double two_ct = 0;
 * 
 * ArrayList<ArrayList<String>> zeros_list = new ArrayList<ArrayList<String>>();
 * ArrayList<ArrayList<String>> ones_list = new ArrayList<ArrayList<String>>();
 * ArrayList<ArrayList<String>> twos_list = new ArrayList<ArrayList<String>>();
 * 
 * for (int j = 0; j < data_Values.size(); j++) {
 * 
 * if (data_Values.get(j).get(Attr_ID).equals("0")) { zeros_list.add(new
 * ArrayList<String>(data_Values.get(j))); zero_ct++;
 * 
 * } else if (data_Values.get(j).get(Attr_ID).equals("1")) { ones_list.add(new
 * ArrayList<String>(data_Values.get(j))); one_ct++; } else if
 * (data_Values.get(j).get(Attr_ID).equals("2")) { twos_list.add(new
 * ArrayList<String>(data_Values.get(j))); two_ct++; }
 * 
 * }
 * 
 * double Entropy_zero = Calculate_data_Entropy(zeros_list); double Entropy_one
 * = Calculate_data_Entropy(ones_list); double Entropy_two =
 * Calculate_data_Entropy(twos_list);
 * 
 * double zero_prob = zero_ct / (zero_ct + one_ct + two_ct); double one_prob =
 * one_ct / (zero_ct + one_ct + two_ct); double two_prob = two_ct / (zero_ct +
 * one_ct + two_ct);
 * 
 * if (zero_prob == 0.0) zero_prob = 1.0; if (one_prob == 0.0) one_prob = 1.0;
 * if (two_prob == 0.0) two_prob = 1.0;
 * 
 * System.out.println( "Entropy_zero  " + Entropy_zero + "Entropy_one  " +
 * Entropy_one + "Entropy_two  " + Entropy_two);
 * 
 * double Entropy_Attribute = zero_prob * Entropy_zero + one_prob * Entropy_one
 * + two_prob * Entropy_two;
 * 
 * System.out.print(Entropy_Attribute); System.out.print("\n");
 * System.out.print("\n");
 * 
 * return Entropy_Attribute;
 * 
 * }
 * 
 * private static String createIndent(int depth) { StringBuilder sb = new
 * StringBuilder(); for (int i = 0; i < depth - 1; i++) { sb.append(" | "); }
 * return sb.toString();
 * 
 * }
 * 
 * }
 */
