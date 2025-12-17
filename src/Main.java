import challenges.DayFour.ChallengeEight;
import challenges.DayFour.ChallengeSeven;
import challenges.DayOne.ChallengeOne;
import challenges.DayOne.ChallengeTwo;
import challenges.DayThree.ChallengeFive;
import challenges.DayThree.ChallengeSix;
import challenges.DayTwo.ChallengeFour;
import challenges.DayTwo.ChallengeThree;

public class Main {
    public static void main(String[] args) {

        System.out.println("=============== Challenge ONE ===============");

        ChallengeOne challengeOne = new ChallengeOne();
        System.out.println("Password: " + challengeOne.getPassword());

        System.out.println("=============== Challenge TWO ===============");

        ChallengeTwo challengeTwo = new ChallengeTwo();
        System.out.println("Password: " + challengeTwo.getPasswordDuringRotation());

        System.out.println("=============== Challenge THREE ===============");

        ChallengeThree challengeThree = new ChallengeThree();
        System.out.println("Total Count: " + challengeThree.getAndCalculateInvalidId());

        System.out.println("=============== Challenge FOUR ===============");
        ChallengeFour challengeFour = new ChallengeFour();
        System.out.println("Total Count: " + challengeFour.sumInvalidIds());

        System.out.println("=============== Challenge FIVE ===============");
        ChallengeFive challengeFive = new ChallengeFive();
        System.out.println("Total Count: " + challengeFive.getTotalJoltage());

        System.out.println("=============== Challenge SIX ===============");
        ChallengeSix challengeSix = new ChallengeSix();
        System.out.println("Total Count: " + challengeSix.getRightTotalJoltage());

        System.out.println("=============== Challenge SEVEN ===============");
        ChallengeSeven challengeSeven = new ChallengeSeven();
        System.out.println("Total Count: " + challengeSeven.getRoll());

        System.out.println("=============== Challenge EIGHT ===============");
        ChallengeEight challengeEight = new ChallengeEight();
        System.out.println("Total Count: " + challengeEight.getMoreRoll());
    }
}