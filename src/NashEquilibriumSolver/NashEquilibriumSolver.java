/**@author Daniel Artuso
 * Nash Equilibrium Solver
 */
package NashEquilibriumSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NashEquilibriumSolver {

    private Scanner scanner = new Scanner(System.in);
    private Firm firm1 = new Firm();
    private Firm firm2 = new Firm();
    private String choice1;
    private String choice2;
    private ArrayList<Boolean> NE = new ArrayList<>(4);

    public static void main(String[] args) {
        NashEquilibriumSolver solve = new NashEquilibriumSolver();
        solve.setupInfo();
        solve.getChoice();
        solve.setBest();
        solve.printOutput();
        solve.findNE();
        solve.pressAnyKeyToContinue();
    }

    private void setupInfo() {
        System.out.println("What is the first choice for the two firms:");
        choice1 = scanner.nextLine();
        System.out.println("What is the second choice for the two firms:");
        choice2 = scanner.nextLine();
        System.out.println("What should the side firm be called: ");
        firm1.setName(scanner.nextLine());
        System.out.println("What should the top firm be called: ");
        firm2.setName(scanner.nextLine());
    }

    private void getChoice() {
        int choice = 0;
        System.out.println("Enter payoffs in format - 1,1 | Where the left is for the side firm.");
        ArrayList<String> squares = new ArrayList<>(4);
        squares.addAll(0,Arrays.asList("top left", "top right", "bottom left", "bottom right"));
        for (int i = 0; i < squares.size(); i++) {
            String string = squares.get(i);
            System.out.println("Enter the " + string + " payoff:");
            String payouts = scanner.next();
            String[] payoutarray = payouts.split(",");
            firm1.addChoice(choice, payoutarray[0]);
            firm2.addChoice(choice, payoutarray[1]);
            choice++;
        }
    }

    private void setBest() {
        //Pick out best case for other firms choice

        //If firm 1 chooses Option 1
        if (firm2.getChoiceInt(0) > firm2.getChoiceInt(1)) {
            firm2.setNE(0);
        } else {
            firm2.setNE(1);
        }

        //If firm 1 chooses Option 2
        if (firm2.getChoiceInt(2) > firm2.getChoiceInt(3)) {
            firm2.setNE(2);
        } else {
            firm2.setNE(3);
        }

        //If firm 2 chooses Option 1
        if (firm1.getChoiceInt(0) > firm1.getChoiceInt(2)) {
            firm1.setNE(0);
        } else {
            firm1.setNE(2);
        }

        //If firm 2 chooses Option 2
        if (firm1.getChoiceInt(1) > firm1.getChoiceInt(3)) {
            firm1.setNE(1);
        } else {
            firm1.setNE(3);
        }
    }

    private void findNE() {
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            NE.add(i, false);
        }
        for (int i = 0; i < 4; i++) {
            if (firm1.isChoiceNE(i) && firm2.isChoiceNE(i)) {
                counter++;
                NE.set(i, true);
                System.out.println("There is a Nash Equilibrium at ");
                String  location = new String();
                switch (i) {
                    case 0:
                        location = new String(choice1 + "," + choice1);
                        break;
                    case 1:
                        location = new String(choice1 + "," + choice2);
                        break;
                    case 2:
                        location = new String(choice2 + "," + choice1);
                        break;
                    case 3:
                        location = new String(choice2 + "," + choice2);
                        break;
                }
                System.out.println(location);
            }
        }
        if (counter == 0) {
            System.out.println("There is no Nash Equilibirum in this graph.");
        }
    }


    private void printOutput() {
        System.out.println("\t" + firm1.getName());
        System.out.println(choice1 + "\t\t" + choice2);
        char[] divider = new char[25];
        Arrays.fill(divider, '=');
        System.out.println(divider);
        for (int i = 0; i < 3; i++) {
            String firstCol = new String("= " + firm1.getChoice(i).toString() + "," + firm2.getChoice(i).toString());
            System.out.print(firstCol + "\t=\t");
            i++;
            String secCol = new String(firm1.getChoice(i).toString() + "," + firm2.getChoice(i).toString() + "\t=");
            System.out.print(secCol + "\t");
            if (i == 1) {
                System.out.println(choice1 + "\t" + firm2.getName());
            } else {
                System.out.println(choice2);
            }
            System.out.println(divider);
        }
    }
    private void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
}

class Firm{
    private String name;
    private ArrayList<Payoff> choices = new ArrayList<>(4);

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Payoff getChoice(int index) {
        return choices.get(index);
    }

    public int getChoiceInt(int index) {
        return choices.get(index).valuePayout();
    }
    public boolean isChoiceNE(int index) {
        return choices.get(index).isNE;
    }

    public void addChoice(int whichchoice, String payout ) {
        Payoff payoff = new Payoff();
        payoff.setPayout(Integer.parseInt(payout));
        choices.add(whichchoice, payoff);
    }
    public void setNE(int index) {
        choices.get(index).setNE();
    }
}


class Payoff{
    int payout;
    boolean isNE = false;
    public void setPayout(int payout) {
        this.payout = payout;
    }

    public int valuePayout() {
        return payout;
    }

    public void setNE() {
        isNE = true;
    }
    @Override
    public String toString() {
        if (isNE) {
            String payoffstr = new String("(" + payout + ")");
            return payoffstr;
        }else {
            return Integer.toString(payout);
        }
    }
}