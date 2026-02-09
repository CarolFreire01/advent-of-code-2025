import challenges.Day04.DayFourPartOne;
import challenges.Day04.DayFourPartTwo;
import challenges.Day01.DayOnePartOne;
import challenges.Day01.DayOnePartTwo;
import challenges.Day03.DayThreePartOne;
import challenges.Day03.DayThreePartTwo;
import challenges.Day02.DayTwoPartOne;
import challenges.Day02.DayTwoPartTwo;
import challenges.Day05.DayFive;
import challenges.Day06.DaySix;
import challenges.Day07.DaySeven;
import challenges.Day08.DayEight;
import challenges.Day09.DayNine;
import challenges.Day10.DayTen;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("=============== DAY ONE ===============");

        DayOnePartOne dayOnePartOne = new DayOnePartOne();
        System.out.println("Day One -> Part 1: " + dayOnePartOne.getPassword());

        DayOnePartTwo dayOnePartTwo = new DayOnePartTwo();
        System.out.println("Day One -> Part 2: " + dayOnePartTwo.getPasswordDuringRotation());

        System.out.println("=============== DAY TWO ===============");

        DayTwoPartTwo dayTwoPartTwo = new DayTwoPartTwo();
        System.out.println("Day Two -> Part 1: " + dayTwoPartTwo.getAndCalculateInvalidId());

        DayTwoPartOne challengeFour = new DayTwoPartOne();
        System.out.println("Day Two -> Part 2: " + challengeFour.sumInvalidIds());

        System.out.println("=============== DAY THREE ===============");

        DayThreePartOne dayThreePartOne = new DayThreePartOne();
        System.out.println("Day Three -> Part 1: " + dayThreePartOne.getTotalJoltage());

        DayThreePartTwo dayThreePartTwo = new DayThreePartTwo();
        System.out.println("Day Three -> Part 2: " + dayThreePartTwo.getRightTotalJoltage());

        System.out.println("=============== DAY FOUR ===============");
        DayFourPartTwo dayFourPartTwo = new DayFourPartTwo();
        System.out.println("Day Four -> Part 1: " + dayFourPartTwo.getRoll());

        DayFourPartOne dayFourPartOne = new DayFourPartOne();
        System.out.println("Day Four -> Part 2: " + dayFourPartOne.getMoreRoll());

        System.out.println("=============== DAY FIVE ===============");

        DayFive dayFive = new DayFive();
        dayFive.selectIngredient();

        System.out.println("=============== DAY SIX ===============");

        DaySix daySix = new DaySix();
        daySix.getCephalopod();

        System.out.println("=============== DAY SEVEN ===============");

        DaySeven daySeven = new DaySeven();
        daySeven.daySeven();

        System.out.println("=============== DAY EIGHT ===============");

        DayEight dayEight = new DayEight();
        dayEight.dayEight();

        System.out.println("=============== DAY NINE ===============");

        DayNine dayNine = new DayNine();
        dayNine.dayNine();

        System.out.println("=============== DAY TEN ===============");

        DayTen dayTen = new DayTen();
        dayTen.dayTen();

    }
}