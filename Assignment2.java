import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.System;

class Assignment2 {
  
  public static ArrayList<String> deleteStopwords(String input, String stopwords) {
    //Creates File and Scanner objects so that the files can be read
    File inputWords = new File(input);;
    File stopWords = new File(stopwords);
    Scanner inputWordsReader;
    Scanner stopWordsReader;
    try {
    inputWordsReader = new Scanner(inputWords, "UTF-8");
    stopWordsReader = new Scanner(stopWords, "UTF-8");
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      return null;
    }
    //Puts all words from the input file into an ArrayList
    ArrayList<String> words = new ArrayList<String>();
    while (inputWordsReader.hasNextLine()) {
      String next = inputWordsReader.nextLine();
      //Removes punctuation except the apostrophe from each line of the file
      next = next.replaceAll("[\\p{P}&&[^\u0027]]", "");
      //Removes a tab character that was spotted in the file and appearing in the words ArrayList
      next = next.replaceAll("\\t","");
      for (String word : next.split(" ")) {
        words.add(word);
      }
    }
    //Creates an arraylist of all stop words and then iterates through each stopword and checks if that stop word is in the words arraylist
    ArrayList<String> stopWordsArray = new ArrayList<String>();
    while (stopWordsReader.hasNext()) {
      stopWordsArray.add(stopWordsReader.next());
    }
    for (String word : stopWordsArray) {
      while (words.contains(word)) {
        words.remove(word);
      }
    }
    //Deletes empty elements in words arraylist
    while (words.contains("")) {words.remove("");}
    inputWordsReader.close();
    stopWordsReader.close();
    return words;
  }

  public static void insertionSort(ArrayList<String> listOfWords) {
    for (int i = 1; i< listOfWords.size(); i++) {
      String item = listOfWords.get(i);
      int j = i - 1;
      while (j>=0 && listOfWords.get(j).compareTo(item) > 0) {
        listOfWords.set(j+1, listOfWords.get(j));
        j--;
      }
      listOfWords.set(j+1, item);
    }
  }

  private static void merge(ArrayList<String> left_arr, ArrayList<String> right_arr, ArrayList<String> arr, int left_size, int right_size) {
    int i=0, l=0, r=0;
    while ((l<left_size) && (r < right_size)) {
      if (left_arr.get(l).compareTo(right_arr.get(r)) < 0) {
          arr.set(i++, left_arr.get(l++));
      } else {
          arr.set(i++, right_arr.get(r++));
      } 
  }    
  while (l < left_size) {
    arr.set(i++, left_arr.get(l++));
  }          
  while (r < right_size) {
    arr.set(i++, right_arr.get(r++));
  }
  }

  public static void mergeSort(ArrayList<String> listOfWords) {
    if (listOfWords.size() < 2) {return;}

    int mid = listOfWords.size() / 2;
    ArrayList<String> left_arr = new ArrayList<String>(mid);
    ArrayList<String> right_arr = new ArrayList<String>(listOfWords.size()-mid);

    for (int i=0; i<listOfWords.size(); i++) {
        if (i<mid) {
            left_arr.add(listOfWords.get(i));
        } else {
            right_arr.add(listOfWords.get(i));
        }
    }
    mergeSort(left_arr);
    mergeSort(right_arr);
    merge(left_arr, right_arr, listOfWords, mid, listOfWords.size()-mid);
  }
  
  private static int mergeSortSwaps = 0;

  private static void mergeSwapCounter(ArrayList<String> left_arr, ArrayList<String> right_arr, ArrayList<String> arr, int left_size, int right_size) {
    int i=0, l=0, r=0;
    while ((l<left_size) && (r < right_size)) {
      if (left_arr.get(l).compareTo(right_arr.get(r)) < 0) {
          arr.set(i++, left_arr.get(l++));
      } else {
          arr.set(i++, right_arr.get(r++));
          mergeSortSwaps++;
      } 
  }    
  while (l < left_size) {
    arr.set(i++, left_arr.get(l++));
  }          
  while (r < right_size) {
    arr.set(i++, right_arr.get(r++));
  }
  }

  private static void mergeSortSwapCounter(ArrayList<String> listOfWords) {
    if (listOfWords.size() < 2) {return;}

    int mid = listOfWords.size() / 2;
    ArrayList<String> left_arr = new ArrayList<String>(mid);
    ArrayList<String> right_arr = new ArrayList<String>(listOfWords.size()-mid);

    for (int i=0; i<listOfWords.size(); i++) {
        if (i<mid) {
            left_arr.add(listOfWords.get(i));
        } else {
            right_arr.add(listOfWords.get(i));
        }
    }
    mergeSortSwapCounter(left_arr);
    mergeSortSwapCounter(right_arr);
    mergeSwapCounter(left_arr, right_arr, listOfWords, mid, listOfWords.size()-mid);
  }

  public static void performanceChecker(ArrayList<String> listOfWords) {
    ArrayList<String> listOfWordsCopy = new ArrayList<String>();
    listOfWordsCopy.addAll(listOfWords);
    ArrayList<String> first100 = new ArrayList<String>();
    ArrayList<String> first200 = new ArrayList<String>();
    ArrayList<String> first500 = new ArrayList<String>();
    for (int i=0; i<500;i++) {
      if (i<100) {first100.add(listOfWords.get(i));}
      if (i<200) {first200.add(listOfWords.get(i));}
      first500.add(listOfWords.get(i));
    }

    long start = System.nanoTime();
    insertionSort(first100);
    long end = System.nanoTime();
    System.out.println("Time taken to sort 100 words with insertion sort: " + (end - start) + " nanoseconds.");
    start = System.nanoTime();
    insertionSort(first200);
    end = System.nanoTime();
    System.out.println("Time taken to sort 200 words with insertion sort: " + (end - start) + " nanoseconds.");
    start = System.nanoTime();
    insertionSort(first500);
    end = System.nanoTime();
    System.out.println("Time taken to sort 500 words with insertion sort: " + (end - start) + " nanoseconds.");

    int swaps = 0;
    for (int i = 1; i< listOfWordsCopy.size(); i++) {
      String item = listOfWordsCopy.get(i);
      int j = i - 1;
      while (j>=0 && listOfWordsCopy.get(j).compareTo(item) > 0) {
        listOfWordsCopy.set(j+1, listOfWordsCopy.get(j));
        j--;
        swaps++;
      }
      listOfWordsCopy.set(j+1, item);
    }
    System.out.println(swaps + " number of swaps occurred in insertion sort.");

    ArrayList<String> listOfWordsSecondCopy = new ArrayList<String>();
    listOfWordsSecondCopy.addAll(listOfWords);
    first100 = new ArrayList<String>();
    first200 = new ArrayList<String>();
    first500 = new ArrayList<String>();
    for (int i=0; i<500;i++) {
      if (i<100) {first100.add(listOfWords.get(i));}
      if (i<200) {first200.add(listOfWords.get(i));}
      first500.add(listOfWords.get(i));
    }

    start = System.nanoTime();
    mergeSort(first100);
    end = System.nanoTime();
    System.out.println("Time taken to sort 100 words with merge sort: " + (end - start) + " nanoseconds.");
    start = System.nanoTime();
    mergeSort(first200);
    end = System.nanoTime();
    System.out.println("Time taken to sort 200 words with merge sort: " + (end - start) + " nanoseconds.");
    start = System.nanoTime();
    mergeSort(first500);
    end = System.nanoTime();
    System.out.println("Time taken to sort 500 words with merge sort: " + (end - start) + " nanoseconds.");
    
    mergeSortSwaps = 0;
    mergeSortSwapCounter(listOfWordsSecondCopy);
    System.out.println(mergeSortSwaps + " number of swaps occurred in merge sort.");
  }

  public static void main(String[] args) {
    ArrayList<String> words = deleteStopwords("Input.txt", "stopwords.txt");
    System.out.println(words.toString());
    insertionSort(words);
    System.out.println(words.toString());
    ArrayList<String> words2 = deleteStopwords("Input.txt", "stopwords.txt");
    mergeSort(words2);
    System.out.println(words2.toString());
    ArrayList<String> words3 = deleteStopwords("Input.txt", "stopwords.txt");
    performanceChecker(words3);
  }
}
