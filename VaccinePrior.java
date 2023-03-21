
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VaccinePrior {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DateTimeFormatter dateFormatInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateBirthDay = null;
        String gender = "";

        while (true) {
            // Input gender
            while (true) {
                System.out.println("What is your gender (male/female)?");
                System.out.print("Gender --> : ");
                gender = scan.next();

                if ("male".equals(gender) || "female".equals(gender)) {
                    break;
                } else {
                    System.out.println("Invalid gender! Please try again.");
                    System.out.println();
                }
            }
            // Input Birth Day
            while (true) {
                try {
                    System.out.println("Please enter your birth day (B.E. only!) -> (yyyy-MM-dd)");
                    System.out.print("Year-Month-Day --> : ");
                    dateBirthDay = LocalDate.parse(scan.next(), dateFormatInput);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid birth day! Please try again.");
                    System.out.println("(Example : 2490-02-30)");
                    System.out.println();
                }
            }
            checkEligible(dateBirthDay, gender);
        }
    }

    static public void checkEligible(LocalDate date, String gender) {

        // Converting B.E -> A.D.
        int conVertYear = date.getYear() - 543;
        LocalDate dateBirthDay = LocalDate.of(conVertYear, date.getMonth(), date.getDayOfMonth());
        LocalDate firstDayVaccine = LocalDate.of(2021, 6, 1);
        LocalDate lastDayVaccine = LocalDate.of(2021, 8, 31);

        int dayBirthDay = dateBirthDay.getDayOfMonth();
        int monthBirthDayValue = dateBirthDay.getMonthValue();
        int yearBirthDayValue = dateBirthDay.getYear();
        int age = lastDayVaccine.getYear() - yearBirthDayValue;

        boolean eligibleFlag = false;

        // Check Eligible
        try {
            // Adult
            int adultReadyGetV = lastDayVaccine.getMonthValue() + (12 - monthBirthDayValue);
            // Age more than 65 years old
            if (age > 65) {
                eligibleFlag = true;
            }
            // Adult 64 years old -> 65 years old.
            else if (age == 65 && adultReadyGetV >= 12) {
                eligibleFlag = true;
                // check lastDayVaccine
                if (6 <= monthBirthDayValue && monthBirthDayValue <= 8) {
                    firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDayValue, dayBirthDay);
                }
            }
            // Child 0 - 2 years old
            else if (age >= 0 && age <= 3) {

                // Baby born in 2564
                if (age == 0) {
                    int babySixMonth = monthBirthDayValue + 6;
                    if (babySixMonth >= 6 && babySixMonth <= 8) {
                        eligibleFlag = true;
                        firstDayVaccine = LocalDate.of(2021, babySixMonth, dayBirthDay);
                    }
                }
                // 1 years old and baby
                else if (age == 1) {
                    eligibleFlag = true;

                    // Baby born in December 2563
                    int lastDayMonth = firstDayVaccine.lengthOfMonth();
                    if (monthBirthDayValue == 12 && dayBirthDay > lastDayMonth) {
                        firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), firstDayVaccine.getMonthValue(), lastDayMonth);
                    } else if (monthBirthDayValue == 12) {
                        firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), firstDayVaccine.getMonthValue(), dayBirthDay);
                    }

                }
                // 2 years old
                else if (age == 2) {
                    int start = firstDayVaccine.getMonthValue();
                    int stop = lastDayVaccine.getMonthValue();

                    if (monthBirthDayValue >= start) {
                        eligibleFlag = true;
                        if (monthBirthDayValue <= stop) {
                            lastDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDayValue, dayBirthDay);
                        }

                    }

                }
            }
        } catch (Exception e) {
            System.exit(1);
        }

        // Display result
        int dayF = firstDayVaccine.getDayOfMonth();
        Month monthF = firstDayVaccine.getMonth();
        String monthFStr = monthF.toString().substring(0, 3);
        int yearF = firstDayVaccine.getYear() + 543;

        int dayL = lastDayVaccine.getDayOfMonth();
        Month monthL = lastDayVaccine.getMonth();
        String monthLStr = monthL.toString().substring(0, 3);
        int yearL = lastDayVaccine.getYear() + 543;

        String status = "";
        if (eligibleFlag == true) {
            status = "Y";
            System.out.printf("%-30s\n", " -----------------------------");
            System.out.printf("%-30s|\n", "| Gender : " + gender);
            System.out.printf("%-30s|\n", "| Eligible Flag : " + status);
            System.out.printf("%-30s|\n", "| Start Date : " + dayF + "-" + monthFStr + "-" + yearF);
            System.out.printf("%-30s|\n", "| End date : " + dayL + "-" + monthLStr + "-" + yearL);
            System.out.printf("%-30s\n", " -----------------------------");
            System.out.println();
        } else {
            status = "N";
            System.out.printf("%-30s\n", " -----------------------------");
            System.out.printf("%-30s|\n", "| Gender : " + gender);
            System.out.printf("%-30s|\n", "| Eligible Flag : " + status);
            System.out.printf("%-30s|\n", "| Start Date :    -");
            System.out.printf("%-30s|\n", "| End date :      -");
            System.out.printf("%-30s\n", " -----------------------------");
            System.out.println();
        }
    }
}
