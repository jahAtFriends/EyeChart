/*
* This code is intended to be copied and pasted into a Processing project.
* Begin copying below this comment and continue all the way to the last line.
* Code is intended to be used in the Eye Chart Lab.
*
*
* (c) 2017 Joel Hammer
* Winchester Thurston School
*/

/**
 *Returns the nth most used element of the given array as a string in all capital letters, ignoring punctuation and given "boring" words.
 *
 *@param  x  An array of strings; each element is considered as a single word.
 *@param boring  An array of "boring" strings which will be ignored in the most used count.
 *@param  n  The desired frequency rank.
 *@return  The nth most common element of the array x (with greatest frequency given by n = 0).
 */
String getMostUsedWord(String[] x, String[] boring, int n)
{

  //If a blank array is passed, return null.
  if (x.length == 0)
    return null;

  //Clone the passed array and sort the clone, eliminating "boring" words.
  String y[] = x.clone();

  for (int i=0; i < y.length; i++)
    y[i] = trim(y[i].replaceAll("[^a-zA-Z\\s]", "").replaceAll("  ", " "));

  y = removeWord(y, "");

  y = sort(scrubStringArray(upperCaseArray(y), boring));

  //Store the the unique words and the number of uses for each word.
  ArrayList<String>  uniqueWords = new ArrayList<String>();
  ArrayList<Integer> numberOfUses = new ArrayList<Integer>();

  //Store the current word being counted and the number of times it has been used.
  String currentWord = y[0];
  int frqCounter = 0;

  //Count the uses of each word in y.
  for (int i = 0; i < y.length; i++) {
    //If the next word is different store the number of usages of the previous word and reset.
    if (currentWord.compareToIgnoreCase(y[i]) != 0) {
      numberOfUses.add(new Integer(frqCounter));
      uniqueWords.add(currentWord);
      currentWord = y[i];
      frqCounter = 0;
    }
    frqCounter++;
  }

  Integer largestFound = new Integer(0);

  //Find the nth largest value of the frequency array.
  for (int i = 0; i <= n; i++) {
    //Reset largest found at each interation of the loop.
    largestFound = new Integer(0);

    //Find the largest frequency in the frequency array.
    for (int j = 0; j<numberOfUses.size(); j++)
      if (numberOfUses.get(j).compareTo(largestFound) > 0)
        largestFound = new Integer(numberOfUses.get(j));

    //If not the nth largest frequency, replace the largest so far with 0 and find the next largest
    if (i < n) {
      numberOfUses.set(numberOfUses.indexOf(largestFound), new Integer(0));
    }
  }
  if (largestFound.intValue() > 1) //If non-boring words that were used more than once remain, return the most used word
    return uniqueWords.get(numberOfUses.indexOf(largestFound));
  else {
    return "";
  }
}

/** 
 * Returns an array equivalent to a given array of strings with the exception
 * of those elements which also appear in a secondary array of strings
 * which are to be "removed" from the first array.
 *
 *@param  x  A String array from which a given set of words is to be removed.
 *@param  y  A String array of "words" to remove from the array x.
 *@returns An array containing all elements of x which do not appear in y.
 *<p>
 *  For the mathematically inclined, this is this is the set-theoretic compliment of y in x.
 */
String[] scrubStringArray(String[] x, String[] y)
{
  //Avoid unnecessary size calculations by storing non-boring words in an arraylist.
  ArrayList<String> scrubArrayList = new ArrayList<String>();
  //Add each word of the passed array to the scrubbed array only if it is not boring.
  boolean wordIsBoring = false;
  for (String a : x) {
    for (String b : y)
      if (a.compareToIgnoreCase(b) == 0)
        wordIsBoring = true;
    if (!wordIsBoring)
      scrubArrayList.add(a);
    wordIsBoring = false;
  }

  //Convert the utility arrayList back to an array and return.
  String scrubbedArray[] = new String[0];
  scrubbedArray = scrubArrayList.toArray(scrubbedArray);
  return scrubbedArray;
}

/**
 * Returns an array of strings, all upper case, which is otherwise identical to the given array.
 *@param  x  A string array.
 *@return  An array identical to x with the exception that all lowercase letters in each element of x
 * have been replaced with upper case letters.
 */
String[] upperCaseArray(String[] x) { //Return an array of strings with only lowercase letters.

  String upperCaseArray[] = new String[x.length];

  for (int i = 0; i < x.length; i++) //Convert the string array to alll upper case.
    upperCaseArray[i] = x[i].toUpperCase();

  return upperCaseArray;
}

/**
 * Returns a String array identical to a given String array with all instances of elements
 * equivalent to a given string removed.
 *@param  word  A string
 *@param  x  A String array from which all elements equivalent to word will be removed.
 *@returns  A String array equivalent to x with all elements themselves equivalent to word removed.
 */
String[] removeWord(String[] x, String word) { //Return an array equivalent to the passed array with all instances of a given string element removed.
  ArrayList<String> y = new ArrayList<String>();

  for (String a : x)
    if (a.compareToIgnoreCase(word) != 0)
      y.add(a);

  String returnString[] = new String[0];
  return y.toArray(returnString);
}