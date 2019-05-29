# WordFrequencyFinder
In a given .txt file , returns frequency. Gets various arguments from command line


Input Files: Thr program will receive input from two different files.
  
  • text-file: The text file from which the words are read and counted.

  • directives-file: This file will contain one directive per line. Each directive consists of the
  name of the directive followed by 0 or more arguments, separated by spaces. You may assume
  that only the directives given below will appear in the file, and the specified arguments will
  always appear. The output of the directives will be written to standard output.


  Directives: Here are the possible directives and their meanings.
  
  • load filename:
      Parses the words in the file named filename and construct the frequency linked list.
  
  • print-max N:
      Print the N highest frequency words and their frequencies in the list, one word per line and
      sorted highest to lowest. If N is larger than the number of words in the list, prints the entire
      list.
      
  • print-min N:
      Prints the N lowest frequency words and their frequencies in the list, one word per line and
      sorted highest to lowest. If N is larger than the number of words in the list, prints the entire
      list.

  • print-range N1 N2:
      Prints the words whose frequency is within the range [N1, N2]. Print each word and its
      frequency, one word per line and sorted highest to lowest. If no words are in that range, prints
      ’This range is empty’.

  • print-freq word:
      Prints the frequency of the given word.
  
  • print-nth N:
      Prints the word whose frequency is the N-th highest in the list. For example if N=1 you print
      the word with the highest frequency, if N=2 you prints the second highest, etc.

  • truncate-list N:
      Removes the N lowest frequency words from the list. If N is larger than the number of words
      in the list, clears the entire list.

  Important: The program will be invoked from the command line as: java word-frequency
  <directives-file-name>
