
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VaccinePrior {
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
        checkEligible(dateBirthDay);
        
        // test
        // LocalDate testDate = LocalDate.of((2564 - 543), 2, 1);
        // int lastDayMonth = testDate.lengthOfMonth();
        // // System.out.println("last day of month : " + lastDayMonth);

        // for (int m = 1; m <= 4; m++) {
        // testDate = LocalDate.of(2564, m, 1);
        // System.out.println();
        // System.out.println("╔═════════════════════════════════════════════╗");
        // System.out.println("║ Month : " + m + " ║");
        // System.out.println("╚═════════════════════════════════════════════╝");

        // for (int d = 1; d <= lastDayMonth; d++) {
        // System.out.println("day : " + d);
        // testDate = LocalDate.of(2564, m, d);
        // checkEligible(testDate);
        // }
        // }
    }

    static public void checkEligible(LocalDate date) {

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

            // Adult 64 years old -> 65 years old
            if (age <= 65) {

                int readyGet = lastDayVaccine.getMonthValue() + (12 - monthBirthDayValue);

                if (readyGet >= 12 && age == 65) {
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

                    // Baby 1 years old  born in December 2563
                    else if (age == 1) {
                        if (monthBirthDayValue == 12) {
                            int lastDayMonth = firstDayVaccine.lengthOfMonth();
                            eligibleFlag = true;
                            System.out.println("111111");
                            firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), firstDayVaccine.getMonthValue(),
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
                } else {
                    eligibleFlag = false;
                }
            }

            // Age more than 65 years old
            else {
                eligibleFlag = true;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        // Call function disPlay
        disPlay(dateBirthDay, firstDayVaccine, lastDayVaccine, eligibleFlag);
    }

    static public void disPlay(LocalDate dateBirth, LocalDate fistDay, LocalDate lastDay, boolean eligible) {

        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        LocalDate firstDayVaccine = fistDay;
        LocalDate lastDayVaccine = lastDay;

        LocalDate dateBirthDay = dateBirth;

        boolean eligibleFlag = eligible;

        int dayBirthDay = dateBirthDay.getDayOfMonth();

        Month monthBirthDay = dateBirthDay.getMonth();
        int monthBirthDayValue = monthBirthDay.getValue();

        Year yearBirthDay = Year.from(dateBirthDay);
        int yearBirthDayValue = yearBirthDay.getValue();

        int age = lastDayVaccine.getYear() - yearBirthDayValue;

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
            // Baby Can't get vaccine.
            if (age == 0) {
                int checkGetVaccine = 6 + monthBirthDayValue;
                LocalDate dateBabySixMonth = LocalDate.of(yearBirthDayValue, monthBirthDayValue, dayBirthDay);

                try {
                    // Check 6 months old in 2564
                    if (checkGetVaccine <= 12) {
                        dateBabySixMonth = LocalDate.of(yearBirthDayValue, checkGetVaccine, dayBirthDay);
                    }
                    // Check 6 months old next year
                    else if (checkGetVaccine > 12) {
                        Month convertMonth = Month.of((checkGetVaccine % 12));
                        int nextYear = dateBabySixMonth.getYear() + 1;
                        dateBabySixMonth = LocalDate.of(nextYear, convertMonth, 01);
                        int lastDayMonth = dateBabySixMonth.lengthOfMonth();

                        // Check last day on month?
                        if (dayBirthDay > lastDayMonth) {
                            dateBabySixMonth = LocalDate.of(nextYear, convertMonth, lastDayMonth);
                        } else {
                            dateBabySixMonth = LocalDate.of(nextYear, convertMonth, dayBirthDay);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("CHECK CATCH BABY DISPLAY");
                    System.out.println(dateBabySixMonth);
                }
                // <Display> : 6 months old can't get vaccine.
                int dayBabyGet = dateBabySixMonth.getDayOfMonth();
                Month monthBabyGet = dateBabySixMonth.getMonth();
                int yearBabyGet = dateBabySixMonth.getYear();

                System.out.println("╔════════════════════════════════════════════╗");
                System.out.println("║ Eligible Flag : " + status + "                          ║");
                System.out.println("║ You can't get vaccine.                     ║");
                System.out.println("║ Baby will 6 months old on " + dayBabyGet + " " + monthBabyGet
                        + " " + (yearBabyGet + 543) + " ║");
                System.out.println("╚════════════════════════════════════════════╝");
            }

            // Child can't get vaccine.
            else if (age == 3) {

                System.out.println("╔══════════════════════════════════════════╗");
                System.out.println("║ Eligible Flag : " + status + "                        ║");
                System.out.println("║ You can't get vaccine.                   ║");
                System.out.println("║ You are 3 years old on " + dayBirthDay + " " + monthBirthDay
                        + " " + (2021 + 543) + " ║");
                System.out.println("╚══════════════════════════════════════════╝");
            }

            // Adult can't get Vaccine.
            else {
                int tempY = 65 - age;
                int yearGetVaccine = 2021 + tempY;
                System.out.println("╔══════════════════════════════════════════╗");
                System.out.println("║ Eligible Flag : " + status + "                        ║");
                System.out.println("║ You can't get vaccine.                   ║");
                System.out.println("║ You will 65 years old on " + dayBirthDay + " " + monthBirthDay
                        + " " + (yearGetVaccine + 543) + " ║");
                System.out.println("╚══════════════════════════════════════════╝");
            }

        }
    }
}
