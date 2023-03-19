
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
        while (true) {
            try {
                System.out.println("Please enter your birth day (B.E. only!) ->(yyyy-MM-dd)");
                System.out.print("--> : ");
                dateBirthDay = LocalDate.parse(scan.next(), dateFormatInput);
                break;
            } catch (Exception e) {
                System.out.println("Invalid birth day! Please try again.");
            }
        }
        checkEligible(dateBirthDay, gender);
    }

    static public void checkEligible(LocalDate date, String gender) {

        // Converting B.E -> A.D.
        int conVertYear = date.getYear() - 543;
        LocalDate dateBirthDay = LocalDate.of(conVertYear, date.getMonth(),
                date.getDayOfMonth());

        LocalDate firstDayVaccine = LocalDate.of(2021, 6, 1);
        LocalDate lastDayVaccine = LocalDate.of(2021, 8, 31);

        boolean eligibleFlag = false;

        int dayBirthDay = dateBirthDay.getDayOfMonth();
        int monthBirthDayValue = dateBirthDay.getMonthValue();
        int yearBirthDayValue = dateBirthDay.getYear();

        int age = lastDayVaccine.getYear() - yearBirthDayValue;

        // Check Eligible
        try {

            // Never born
            if (age < 0) {
                System.out.println("**** You've got to be kidding me! You never born **** ");
                return;
            }

            // Adult
            else if (age >= 65) {
                int adultReadyGetV = lastDayVaccine.getMonthValue() + (12 - monthBirthDayValue);

                // Age more than 65 years old
                if (age > 65) {
                    eligibleFlag = true;
                }

                // Adult 64 years old -> 65 years old.
                else if (age == 65 && adultReadyGetV >= 12) {
                    eligibleFlag = true;
                    if (6 <= monthBirthDayValue && monthBirthDayValue <= 8) {
                        firstDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDayValue, dayBirthDay);
                    }
                }
            }

            // Child 0 - 3 years old
            else {

                if (age >= 0 && age <= 3) {

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
                                lastDayVaccine = LocalDate.of(lastDayVaccine.getYear(), monthBirthDayValue, dayBirthDay);
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
            System.out.printf("%-30s\n", "╔═════════════════════════════╗");
            System.out.printf("%-30s║\n", "║ Gender : " + gender);
            System.out.printf("%-30s║\n", "║ Eligible Flag : " + status);
            System.out.printf("%-30s║\n", "║ Start Date : " + dayF + "-" + monthFStr + "-" + yearF);
            System.out.printf("%-30s║\n", "║ End date : " + dayL + "-" + monthLStr + "-" + yearL);
            System.out.printf("%-30s\n", "╚═════════════════════════════╝");
        } else {
            status = "N";
            System.out.printf("%-30s\n", "╔═════════════════════════════╗");
            System.out.printf("%-30s║\n", "║ Gender : " + gender);
            System.out.printf("%-30s║\n", "║ Eligible Flag : " + status);
            System.out.printf("%-30s║\n", "║ Start Date :    -");
            System.out.printf("%-30s║\n", "║ End date :      -");
            System.out.printf("%-30s\n", "╚═════════════════════════════╝");
        }
    }
}
