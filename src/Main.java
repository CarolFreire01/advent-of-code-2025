import challenges.Day04.ChallengeEight;
import challenges.Day04.ChallengeSeven;
import challenges.Day01.ChallengeOne;
import challenges.Day01.ChallengeTwo;
import challenges.Day03.ChallengeFive;
import challenges.Day03.ChallengeSix;
import challenges.Day02.ChallengeFour;
import challenges.Day02.ChallengeThree;

public class Main {

    public static void main(String[] args) {

        System.out.println("=============== DAY ONE ===============");

        ChallengeOne challengeOne = new ChallengeOne();
        System.out.println("Day One -> Part 1: " + challengeOne.getPassword());

        ChallengeTwo challengeTwo = new ChallengeTwo();
        System.out.println("Day One -> Part 2: " + challengeTwo.getPasswordDuringRotation());

        System.out.println("=============== DAY TWO ===============");

        ChallengeThree challengeThree = new ChallengeThree();
        System.out.println("Day Two -> Part 1: " + challengeThree.getAndCalculateInvalidId());

        ChallengeFour challengeFour = new ChallengeFour();
        System.out.println("Day Two -> Part 2: " + challengeFour.sumInvalidIds());

        System.out.println("=============== DAY THREE ===============");

        ChallengeFive challengeFive = new ChallengeFive();
        System.out.println("Day Three -> Part 1: " + challengeFive.getTotalJoltage());

        ChallengeSix challengeSix = new ChallengeSix();
        System.out.println("Day Three -> Part 2: " + challengeSix.getRightTotalJoltage());

        System.out.println("=============== DAY FOUR ===============");
        ChallengeSeven challengeSeven = new ChallengeSeven();
        System.out.println("Day Four -> Part 1: " + challengeSeven.getRoll());

        ChallengeEight challengeEight = new ChallengeEight();
        System.out.println("Day Four -> Part 2: " + challengeEight.getMoreRoll());
    }
}