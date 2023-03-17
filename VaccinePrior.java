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
        LocalDate dateBirthDayDate = null;

        LocalDate firstDayVaccine = LocalDate.of(2564, 6, 1);
        LocalDate lastDayVaccine = LocalDate.of(2564, 8, 31);

        boolean eligibleFlag = false;
        String status = "";

        // Input gender
        // while (true) {
        // System.out.println("What is your gender (male/female)?");
        // gender = scan.next();

        // if ("male".equals(gender) || "female".equals(gender)) {
        // break;
        // } else {
        // System.out.println("Invalid gender!");
        // continue;
        // }
        // }

        // Input Birth Day
        while (true) {
            try {
                System.out.println("Please enter your birth day (dd-MM-yyyy)");
                dateBirthDayDate = LocalDate.parse(scan.next(), dateFormatInput);
                birthday = dateBirthDayDate.format(dateFormatOutput);
                break;
            } catch (Exception e) {
                System.out.println("Invalid birth day!");
            }
        }

        // System.out.println("Infomation");
        // System.out.println("Gender : " + gender);
        // System.out.println("Birth day : " + birthday);

        int dayBirthDay = dateBirthDayDate.getDayOfMonth();
        // System.out.println("Day Value : " + day);

        Month monthBirthDay = dateBirthDayDate.getMonth();
        int monthBirthDayValue = monthBirthDay.getValue();
        // System.out.println("Month Value : " + monthBirthDay);

        Year yearBirthDay = Year.from(dateBirthDayDate);
        int yearBirthDayValue = yearBirthDay.getValue();
        // System.out.println("Year value: " + yearValue);

        int age = (lastDayVaccine.getYear()) - yearBirthDayValue;
        System.out.println("Age : " + age);

        // little guys
        // if (age < 0) {
        // System.out.println("Are you kidding me?");
        // } else if (age == 0) {
        // for (int tempM = 6; tempM <= 8; tempM++) {
        // if (tempM - monthBirthDayValue >= 6) {
        // System.out.println("Vaccine " + tempM);
        // break;
        // }
        // }
        // }

        // age more than 65 years old
        if (age <= 65) {
            int tempM = 6 + (12 - monthBirthDayValue);
            if (tempM >= 10 && age == 65) {
                eligibleFlag = true;
                status = "Y";
                firstDayVaccine = LocalDate.of(2564, monthBirthDayValue, dayBirthDay);
            } else {
                eligibleFlag = false;
                status = "N";
            }
        } else {
            eligibleFlag = true;
            status = "Y";
        }

        String fdv = firstDayVaccine.format(dateFormatOutput);
        String ldv = lastDayVaccine.format(dateFormatOutput);

        // Display status
        if (eligibleFlag == true) {
            System.out.println("╔════════════════════════════╗");
            System.out.println("║ Eligible Flag : " + status + "          ║");
            System.out.println("║ Start Date : " + fdv + "   ║");
            System.out.println("║ End date : " + ldv + "     ║");
            System.out.println("╚════════════════════════════╝");
        } else {
            int tempY = 65 - age;
            int yearGetVaccine = 2564 + tempY;
            System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Eligible Flag : " + status + "                                                 ║");
            System.out.println("║ You can't get vaccine. You will 65 years old on " + dayBirthDay + " " + monthBirthDay
                    + " " + yearGetVaccine + "   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        }

    }
}
