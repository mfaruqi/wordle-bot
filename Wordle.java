import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Wordle {

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("list.txt");
        //readWords(f);
        //getPercentages(readWords(f));
        System.out.println("Finding the best starting word!");
        System.out.println("Calculating letter percentages.");
        System.out.println("The highest percecntage letters were: E = 10.3%, S = 10.3%, A = 9.2%, O = 6.8%, R = 6.4%");
        System.out.println("Finding a word with E, S, A, O, R...");
        System.out.println("Use: AROSE");
        Scanner in = new Scanner(System.in);
        String colors = "";
        ArrayList<String> words = new ArrayList<String>();
        words = readWords(f);
        double[] percentages = getPercentages(words);
        //System.out.println("Before >>>>" + words);
        String currentWord = "arose";
        for(int i = 0; i < 20; i++) {
            //System.out.println("Iteration number " + (i + 1) + " and the current word is " + currentWord);
            System.out.println("Enter the colors of the boxes with spaces: B (grey), Y (yellow),or G (green)");
            colors = in.nextLine();
            colors = colors.trim();
            while (!isCorrectInput(colors)) {
                System.out.println("Enter the colors of the boxes with spaces: B (grey), Y (yellow),or G (green)");
                colors = in.nextLine();
                colors = colors.trim();
            }
            words = removeWords(words, currentWord, colors);
            if (words == null) {
                break;
            }
            if (words.size() == 0) {
                System.out.println("No words found with given instructions!");
                break;
            }
            //currentWord = getBestWord(words, percentages, i);
            //System.out.println(words);
            currentWord = words.get(0);
            System.out.println("Use: " + currentWord.toUpperCase());
        }
        //System.out.println("After >>>>" + words);
    }

    public static ArrayList<String> removeWords(ArrayList<String> words, String word, String colors) {
        if (colors.equals("GGGGG")) {
            System.out.println("YAY!");
            return null;
        }
        ArrayList<String> newWords = new ArrayList<String>();
        ArrayList<String> wordsPossible = new ArrayList<String>();
        boolean keepGoing = true;
        for(int i = 0; i < colors.length(); i++) {
            if (colors.charAt(i) == 'B') {
                for (int j = 0; j < word.length(); j++) {
                    if (j != i) {
                        if ((word.charAt(j) == word.charAt(i)) && (colors.charAt(j) == 'G' || colors.charAt(j) == 'Y')) {
                            keepGoing = false;
                            for (int k = 0; k < words.size(); k++) {
                                if (words.get(k).charAt(i) == word.charAt(i)) {
                                    //System.out.println("This word contains " + word.charAt(i) + " at the wrong spot so we are removing it.");
                                    //System.out.println(words.get(j));
                                    words.remove(k);
                                    k--;
                                    //System.out.println(words.size() + " size");
                                }
                            }
                        }
                    }
                }
                if (keepGoing) {
                    for (int j = 0; j < words.size(); j++) {
                        if (words.get(j).contains(String.valueOf(word.charAt(i)))) {
                            //System.out.println("This word does not contain " + word.charAt(i) + " so we are adding this.");
                            //System.out.println(words.get(j));
                            words.remove(j);
                            j--;
                            //System.out.println(words.size() + " size");
                        }
                    }
                }
            }
            else if (colors.charAt(i) == 'Y') {
                //System.out.println("heree");
                for (int j = 0; j < words.size(); j++) {
                    if (words.get(j).charAt(i) == word.charAt(i) || !words.get(j).contains(String.valueOf(word.charAt(i)))) {
                        //System.out.println("This word contains " + word.charAt(i) + " at the wrong spot so we are removing it.");
                        //System.out.println(words.get(j));
                        words.remove(j);
                        j--;
                        //System.out.println(words.size() + " size");
                    }
                }
            }
            else {
                for (int j = 0; j < words.size(); j++) {
                    if (words.get(j).charAt(i) != word.charAt(i)) {
                        //System.out.println("This word does not contain " + word.charAt(i) + " at the right spot so we are removing it.");
                        //System.out.println(words.get(j));
                        words.remove(j);
                        j--;
                        //System.out.println(words.size() + " size");
                    }
                }
            }
        }
        return words;
    }
    public static ArrayList<String> readWords(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);
        ArrayList<String> words = new ArrayList<String>();
        String line = "";
        while(in.hasNextLine()) {
            line = in.nextLine();
            words.add(line);
        }
        return words;
    }
    public static double[] getPercentages(ArrayList<String> words) {
        double[] count = new double[26];
        int total = 0;
        char c = '\0';
        for (int i = 0; i < words.size(); i++) {
            for(int j = 0; j < 5; j++) {
                c = words.get(i).charAt(j);
                count[(int) c - 97]++;
                total++;
            }
        }
        for(int i = 0; i < count.length; i++) {
            count[i] = (count[i] / total) * 100; 
        }
        for(int i = 0; i < count.length; i++) {
            if (i != count.length - 1) {
                //System.out.printf("%c = %.1f\n", (char) (97 + i), count[i]);
            }
            else {
                //System.out.printf("z = %.1f\n", count[i]);
            }
        }
        return count;
    }
    public static String getBestWord(ArrayList<String> words, double[] wordPercentages, int ii) {
        double max = wordPercentages[(int) words.get(0).charAt(0) - 97] + wordPercentages[(int) words.get(0).charAt(1) - 97] + wordPercentages[(int) words.get(0).charAt(2) - 97] + wordPercentages[(int) words.get(0).charAt(3) - 97] + wordPercentages[(int) words.get(0).charAt(4) - 97];
        String bestWord = words.get(0);
        double temp = 0;
        for (int i = 1; i < words.size(); i++) {
            temp = wordPercentages[(int) words.get(i).charAt(0) - 97] + wordPercentages[(int) words.get(i).charAt(1) - 97] + wordPercentages[(int) words.get(i).charAt(2) - 97] + wordPercentages[(int) words.get(i).charAt(3) - 97] + wordPercentages[(int) words.get(i).charAt(4) - 97];
            if(temp > max) {
                if (ii <= 3 && check(words.get(i))) {
                max = temp;
                bestWord = words.get(i);
                }
                else if (ii > 3) {
                    max = temp;
                    bestWord = words.get(i);
                }
            }
        }
        return bestWord;
    } 
    public static boolean check(String g) {
        for (int i = 0; i < g.length(); i++) {
            for (int j = i + 1; j < g.length(); j++) {
                if (g.charAt(i) == g.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Boolean isCorrectInput(String input) {
        if (input.length() > 5 || input.length() < 5) {
            System.out.println("Input must be 5 charachters long. Please try again.");
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != 'G' && input.charAt(i) != 'Y' && input.charAt(i) != 'B') {
               System.out.println("Your input is not correct. Make sure it is either \'G\', \'Y\', or \'B\'. Please try again."); 
               return false;
            }
        }
        return true;
    }
}


