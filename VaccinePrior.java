
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VaccinePrior {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        DateTimeFormatter dateFormatInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // DateTimeFormatter dateFormatOutput =
        // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        String gender = "";
        // String birthday = "";

        LocalDate dateBirthDay = null;

        // Input gender
        while (true) {
            System.out.println("What is your gender (male/female)?");
            System.out.print("--> : ");
            gender = scan.next();

            if ("male".equals(gender) || "female".equals(gender)) {
                break;
            } else {
                System.out.println("Invalid gender! Please try again.");
            }
        }

        // Input Birth Day
        // while (true) {
        // try {
        // System.out.println("Please enter your birth day (B.E. only!) ->
        // (yyyy-MM-dd)");
        // System.out.print("--> : ");
        // dateBirthDay = LocalDate.parse(scan.next(), dateFormatInput);

        // // birthday = dateBirthDay.format(dateFormatOutput);
        // break;
        // } catch (Exception e) {
        // System.out.println("Invalid birth day! Please try again.");
        // }
        // }
        // checkEligible(dateBirthDay, gender);

         // test
         System.out.println("Enter year for testing ( 'B.E.' only!)");
         int y = scan.nextInt();
         System.out.println("Enter month testing (quarterly : 1, 4, 7, 10)");
         int x = scan.nextInt();
         LocalDate testDate = null;
         int years = y - 543;
         System.out.println("Year (main) : " + years + " Input : " + y);
 
         for (int m = x; m <= x + 2; m++) {
             testDate = LocalDate.of(years, m, 1);
             System.out.println("Before send (main) : " + testDate);
             int lastDayMonth = testDate.lengthOfMonth();
             System.out.println();
             System.out.println("╔════════════════════════════════════╗");
             System.out.println("║ Month : " + m + " || Day : " + lastDayMonth);
             System.out.println("╚════════════════════════════════════╝");
 
             for (int d = 1; d <= lastDayMonth; d++) {
                 System.out.println("day : " + d);
                 testDate = LocalDate.of(years, m, d);
                 System.out.println("send paremeter (main) : " + testDate);
                 checkEligible(testDate, gender);
             }
         }

    }

    static public void checkEligible(LocalDate date, String gender) {

        // DateTimeFormatter dateFormatOutput =
        // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        // Don't Forget year - 543 *************************************************
        int conVertYear = date.getYear(); // Don't have -543 for testing version only
        LocalDate dateBirthDay = LocalDate.of(conVertYear, date.getMonth(),
                date.getDayOfMonth());

        // System.out.println("Last day in function : " + dateBirthDay.lengthOfMonth());
        // System.out.println("Year in Function : " + dateBirthDay.getYear());

        LocalDate firstDayVaccine = LocalDate.of(2021, 6, 1);
        LocalDate lastDayVaccine = LocalDate.of(2021, 8, 31);

        boolean eligibleFlag = false;

        int dayBirthDay = dateBirthDay.getDayOfMonth();

        Month monthBirthDay = dateBirthDay.getMonth();
        int monthBirthDayValue = monthBirthDay.getValue();

        Year yearBirthDay = Year.from(dateBirthDay);
        int yearBirthDayValue = yearBirthDay.getValue();

        int age = lastDayVaccine.getYear() - yearBirthDayValue;

        // System.out.println("Age : " + age);

        // Check Eligible
        try {

            // Never born
            if (age < 0) {
                System.out.println("**** You've got to be kidding me! You never born **** ");
                return;
            }
            // Age more than 65 years old
            else if (age > 65) {
                eligibleFlag = true;
            }
            // Age less than 65 years old
            else {
                // Adult 64 years old -> 65 years old
                int adultReadyGetV = lastDayVaccine.getMonthValue() + (12 - monthBirthDayValue);

                if (age == 65 && adultReadyGetV >= 12) {
                    eligibleFlag = true;
                    if (6 <= monthBirthDayValue && monthBirthDayValue <= 8) {
                        firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDayValue, dayBirthDay);
                    }
                }

                // Child 0 - 3 years old
                else if (age >= 0 && age <= 3) {

                    // Baby born in 2564
                    if (age == 0) {
                        int babySixMonth = monthBirthDayValue + 6;

                        if (babySixMonth >= 6 && babySixMonth <= 8) {
                            eligibleFlag = true;
                            firstDayVaccine = LocalDate.of(2021, babySixMonth, dayBirthDay);
                        }
                    }

                    // Baby born in December 2563
                    else if (age == 1) {
                        int lastDayMonth = firstDayVaccine.lengthOfMonth();
                        // check last day?
                        if (monthBirthDayValue == 12 && dayBirthDay > lastDayMonth) {
                            eligibleFlag = true;
                            firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(),
                                    firstDayVaccine.getMonthValue(), lastDayMonth);
                        } else if (monthBirthDayValue == 12) {
                            eligibleFlag = true;
                            firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(),
                                    firstDayVaccine.getMonthValue(), dayBirthDay);
                        }
                    }

                    // 2 years old
                    else if (age == 2) {
                        eligibleFlag = true;
                    }

                    // 2 years old -> 3 years old
                    else if (age == 3) {
                        int start = firstDayVaccine.getMonthValue();
                        int stop = lastDayVaccine.getMonthValue();

                        if (monthBirthDayValue >= start) {
                            eligibleFlag = true;
                            if (monthBirthDayValue <= stop) {
                                lastDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDay, dayBirthDay);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Display to console
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
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║ Gender : " + gender + "            ");
            System.out.println("║ Eligible Flag : " + status + "             ║");
            System.out.println("║ Start Date :   " + dayF + "-" + monthFStr + "-" + yearF + "    ║");
            System.out.println("║ End date :     " + dayL + "-" + monthLStr + "-" + yearL + "    ║");
            System.out.println("╚═══════════════════════════════╝");
        } else {
            status = "N";
            System.out.println("╔════════════════════════════╗");
            System.out.println("║ Gender : " + gender + "             ");
            System.out.println("║ Eligible Flag : " + status + "          ║");
            System.out.println("║ Start Date :  -            ║");
            System.out.println("║ End date :  -              ║");
            System.out.println("╚════════════════════════════╝");
        }
    }
}
