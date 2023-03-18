import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VaccinePrior {

    static public void checkEligible(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        LocalDate dateBirthDay = LocalDate.of((date.getYear() - 543), date.getMonth(),
                date.getDayOfMonth());

        LocalDate firstDayVaccine = LocalDate.of(2021, 6, 1);
        LocalDate lastDayVaccine = LocalDate.of(2021, 8, 31);

        boolean eligibleFlag = false;

        int dayBirthDay = dateBirthDay.getDayOfMonth();

        Month monthBirthDay = dateBirthDay.getMonth();
        int monthBirthDayValue = monthBirthDay.getValue();

        Year yearBirthDay = Year.from(dateBirthDay);
        int yearBirthDayValue = yearBirthDay.getValue();

        int age = lastDayVaccine.getYear() - yearBirthDayValue;

        // <Display> : Information
        // System.out.println("Infomation");
        // System.out.println("Gender : " + gender);
        // System.out.println("Birth day : " + birthday);
        System.out.println("Age : " + age);

        // Check Eligible
        try {

            // Never born
            if (age < 0) {
                System.out.println("You've got to be kidding me! ");
                return;
            }
            // Age more than 65 years old
            else if (age > 65) {
                eligibleFlag = true;
            }
            // Adult 64 years old -> 65 years old
            else {

                int adultReady = lastDayVaccine.getMonthValue() + (12 - monthBirthDayValue);

                if (age == 65 && adultReady >= 12) {
                    eligibleFlag = true;
                    firstDayVaccine = LocalDate.of(2021, monthBirthDayValue, dayBirthDay);
                }

                // Child 0 - 3 years old
                else if (age >= 0 && age <= 3) {

                    // Baby
                    if (age == 0) {
                        int start = firstDayVaccine.getMonthValue();
                        int stop = lastDayVaccine.getMonthValue();

                        for (int indexMonth = start; indexMonth <= stop; indexMonth++) {
                            if (indexMonth - monthBirthDayValue == 6) {
                                eligibleFlag = true;
                                firstDayVaccine = LocalDate.of(2021, indexMonth, dayBirthDay);
                                break;
                            }
                        }
                    }

                    // Baby 1 year born in December 2563 *********
                    else if (age == 1) {
                        if (monthBirthDayValue == 12) {
                            int lastDayMonth = firstDayVaccine.lengthOfMonth();
                            System.out.println("TTTTTTTT : " + firstDayVaccine);
                            eligibleFlag = true;
                            firstDayVaccine = LocalDate.of(firstDayVaccine.getYear(), firstDayVaccine.getMonthValue(),
                                    lastDayMonth);
                        } else {
                            eligibleFlag = true;
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
                            for (int indexMonth = start; indexMonth <= stop; indexMonth++) {
                                if (indexMonth == monthBirthDayValue) {
                                    eligibleFlag = true;
                                    lastDayVaccine = LocalDate.of(lastDayVaccine.getYear(), indexMonth, dayBirthDay);
                                } else {
                                    eligibleFlag = true;
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        // Call function disPlay
        // Display status
        String fdv = firstDayVaccine.format(dateFormatOutput);
        String ldv = lastDayVaccine.format(dateFormatOutput);
        String status = "";
        // <Display> : Can get Vaccine
        if (eligibleFlag == true) {
            status = "Y";
            System.out.println("╔════════════════════════════╗");
            System.out.println("║ Eligible Flag : " + status + "          ║");
            System.out.println("║ Start Date : " + fdv + "   ║");
            System.out.println("║ End date : " + ldv + "     ║");
            System.out.println("╚════════════════════════════╝");
        }
        // <Display> : Can't get Vaccine!
        else {
            status = "N";
            System.out.println("╔════════════════════════════╗");
            System.out.println("║ Eligible Flag : " + status + "          ║");
            System.out.println("║ Start Date : -             ║ ");
            System.out.println("║ End date : -               ║ ");
            System.out.println("╚════════════════════════════╝");
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        DateTimeFormatter dateFormatInput = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        String gender = "";
        String birthday = "";

        LocalDate dateBirthDay = null;

        // Input gender
        // while (true) {
        // System.out.println("What is your gender (male/female)?");
        // gender = scan.next();

        // if ("male".equals(gender) || "female".equals(gender)) {
        // break;
        // } else {
        // System.out.println("Invalid gender! Please try again.");
        // continue;
        // }
        // }

        // Input Birth Day
        while (true) {
        try {
        System.out.println("Please enter your birth day (dd-MM-yyyy)");
        dateBirthDay = LocalDate.parse(scan.next(), dateFormatInput);

        birthday = dateBirthDay.format(dateFormatOutput);
        break;
        } catch (Exception e) {
        System.out.println("Invalid birth day! Please try again.");
        }
        }
        System.out.println(dateBirthDay);
        checkEligible(dateBirthDay);

        // test
        // LocalDate testDate = LocalDate.of((2564 - 543), 1, 1);
        // int lastDayMonth = testDate.lengthOfMonth();
        // // System.out.println("last day of month : " + lastDayMonth);

        // for (int m = 1; m <= 4; m++) {
        //     testDate = LocalDate.of(2564, m, 1);
        //     lastDayMonth = testDate.lengthOfMonth();

        //     System.out.println();
        //     System.out.println("╔═════════════════════════════════════════════╗");
        //     System.out.println("║                  Month : " + m + "                  ║");
        //     System.out.println("╚═════════════════════════════════════════════╝");

        //     for (int d = 1; d <= lastDayMonth; d++) {
        //         System.out.println("day : " + d);
        //         testDate = LocalDate.of(2564, m, d);
        //         checkEligible(testDate);
        //     }
        // }
    }
}