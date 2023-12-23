import java.util.*;
import java.io.*;

//useful website https://www.javacodeexamples.com/check-password-strength-in-java-example/668

class Main {

  public static void main(String[] args) {
    // initialize
    Scanner scan = new Scanner(System.in);

    int point = 0;
    int length = 0;
    int countNum = 0;
    int countUpper = 0;
    int countLower = 0;
    int countSpecial = 0;

    clearScreen();

    System.out.print("Enter your password: ");
    String password = scan.nextLine();
    System.out.println();

    length = password.length();
    // System.out.println(length);


    for (char letter : password.toCharArray()) {
      // check for lowercase, uppercase, numbers, special characters
      if (letter <= 57 && letter >= 48) {
        point += 10; // number 0-9 is from 48 to 57
        countNum++;
      } else if (letter <= 90 && letter >= 65) {
        point += 5; // uppercase A-Z from 65-90
        countUpper++;
      } else if (letter <= 122 && letter >= 97) {
        point++; // lowercase a-z from 97-122
        countLower++;
      } else if (letter == 32) {
        // space can't be use in password
        System.out.println(colorize("YOU CAN'T USE SPACE IN THE PASSWORD\n Please try again!", ConsoleColors.RED));
        System.exit(0);
      } else {
        point += 15; // special characters
        countSpecial++;
      }
    }


    boolean match = false;
    boolean match2 = false;
    boolean match3 = false;

    // check common dictionary words
    File passList = new File("Dictionary.txt");
    try (Scanner myScan = new Scanner(passList)) {
      scan.useDelimiter("\n");
      while (myScan.hasNext()) {
        if (password.indexOf(myScan.next()) != -1) {
          match = true;
          break;
        }
      }
    } catch (IOException mem) {
      mem.printStackTrace();
    }

    // check sequences
    File passList2 = new File("Sequences.txt");
    try (Scanner myScan = new Scanner(passList2)) {
      scan.useDelimiter("\n");
      while (myScan.hasNext()) {
        if (password.indexOf(myScan.next()) != -1) {
          match2 = true;
          break;
        }
      }
    } catch (IOException mem) {
      mem.printStackTrace();
    }

    // check sequences
    File passList3 = new File("Password.txt");
    try (Scanner myScan = new Scanner(passList3)) {
      scan.useDelimiter("\n");
      while (myScan.hasNext()) {
        if (password.indexOf(myScan.next()) != -1) {
          match3 = true;
          break;
        }
      }
    } catch (IOException mem) {
      mem.printStackTrace();
    }

    // length calculation add point if length is long
    point = point + (3 * length);

    // print results
    if (countNum >= 3) {
      System.out.println(colorize("You have at least 3 numbers", ConsoleColors.GREEN));
    } else {
      System.out.println(colorize("You have less than 3 numbers", ConsoleColors.RED));
    }

    if (countUpper >= 1) {
      System.out.println(colorize("You have at least 1 Uppercase", ConsoleColors.GREEN));
    } else {
      System.out.println(colorize("Please put at least 1 Uppercase", ConsoleColors.RED));
    }

    if (countLower >= 5) {
      System.out.println(colorize("You have at least 5 Lowercase", ConsoleColors.GREEN));
    } else {
      System.out.println(colorize("Please put more lowercase characters", ConsoleColors.RED));
    }

    if (countSpecial >= 1) {
      System.out.println(colorize("You have at least 1 special character", ConsoleColors.GREEN));
    } else {
      System.out.println(colorize("Recommended that you use at least one special charcater", ConsoleColors.RED));
    }

    if (match) {
      System.out.println(colorize("Your password is common in the dictionary", ConsoleColors.RED));
      point -= length;
    } else {
      System.out.println(colorize("Your password is not common in the dictionary", ConsoleColors.GREEN));
    }

    if (match2) {
      System.out.println(colorize("Your password is in sequence order or have repeated numbers", ConsoleColors.RED));
      point -= (2 * length);
    } else {
      System.out.println(colorize("No seqences order or repeated numbers had been found", ConsoleColors.GREEN));
    }

    if (match3) {
      System.out.println(colorize("Your password is listed in the top common password", ConsoleColors.RED));
      point -= ((countNum * 10) + (countUpper * 5) + (countLower) + (countSpecial * 15));
    } else {
      System.out.println(colorize("Your password is unique", ConsoleColors.GREEN));
    }

    calculateScore(point);

    scan.close();
  }

  public static String colorize(String text, String color) {
    return color + text + ConsoleColors.RESET;
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void calculateScore(int point) {
    // prints strength
    String strength = "";

    if (point < 0) {
      point = 0;
      strength = colorize("Is that even a password???", ConsoleColors.RED);
    } else if (point < 30) {
      strength = colorize("TOO SIMPLE", ConsoleColors.RED);
    } else if (point < 50) {
      strength = colorize("Weak", ConsoleColors.RED);
    } else if (point < 90) {
      strength = colorize("Fair", ConsoleColors.GREEN_BRIGHT);
    } else if (point < 120) {
      strength = colorize("Strong", ConsoleColors.GREEN);
    } else {
      strength = colorize("Incredible", ConsoleColors.BLUE);
    }
    System.out.format("\nTotal strength score: %s\nStrength Level: %s",
        colorize(String.valueOf(point), ConsoleColors.BLUE), strength);
  }

}