import java.util.Random;
import java.util.ArrayList;

class List {

    public ArrayList<String> availableBots = new ArrayList<>();  // List up the alternative bots
    public ArrayList<String> usedBots = new ArrayList<>();

    public void addBots() {                                      // Adding alternative bots
        availableBots.add("Gemini");
        availableBots.add("Virgo");
        availableBots.add("Pisces");
        availableBots.add("Aquarius");
    }

    public ArrayList<String> getAvailableBots() {
        return availableBots;
    }

    public void moveBotToUsedList(String botName) {
        usedBots.add(botName);
        availableBots.remove(botName);

    }
}


public class Bot {

    private final String[] verbs;

    {
        verbs = new String[]{"climb", "eat", "sing", "box", "sail"};     // Single verbs to choose between, to start the dialog
    }

    private String botAnswer;
    private String foundVerb;
    private boolean foundVerbBoolean = false;

    private final Random random = new Random();
    private final int randomNumb = random.nextInt(4);                   // The number 4 in random.nextInt is for the answer suggestions of the bot

    public Bot(String stringClient, String botName) {

        String[] greet = {"hi", "hey", "hello", "hallo"};               // The conversation can also start with these words
        for (String s : greet) {
            if (stringClient.contains(s)) {
                setBotAnswer("Hello there!");                           // Then the bot will answer if you use one of the greetings over
            }
        }

        if (botAnswer == null) {
            verbFinder(stringClient);
            botToStart(botName);
        }
    }

    public String answerFrBot() {
        return botAnswer;
    }

    public void setBotAnswer(String answerBot) {
        this.botAnswer = answerBot;
    }

    public void botToStart(String botName) {                            // Checking up for which bot has been chosen, and which one to run

        if ("Gemini".equals(botName)) {
            botGemini();
        } else if ("Aquarius".equals(botName)) {
            botAquarius();
        } else if ("Virgo".equals(botName)) {
            botVirgo();
        } else if ("Pisces".equals(botName)) {
            botPisces();
        } else {
            throw new IllegalStateException("Unexpected value: " + botName); // If none of them are choosen, it will be denied
        }


    }

    private void botPisces () {

        if (foundVerbBoolean) {                                              // The bot will answer these sentences if it can read the verb
            String[] array = {
                    foundVerb + "ing " + "is fun!",
                    "Yes let's do " + foundVerb,
                    "I can't " + foundVerb + ", let's do something else",
                    "I want to do " + foundVerb +"ing" + " with you."
            };
            setBotAnswer(array[randomNumb]);
        } else {
            setBotAnswer("Tell me something that makes sense");              // If the bot doesn't recognize the verb, it will say so
        }
    }

    private void botVirgo() {

        if (foundVerbBoolean) {
            String[] array = {
                    foundVerb + "ing " + "is fun!",
                    "Yes let's do " + foundVerb,
                    "I can't " + foundVerb + ", let's do something else",
                    "I want to do " + foundVerb +"ing" + " with you."
            };
            setBotAnswer(array[randomNumb]);
        } else {
            setBotAnswer("Unknown language!");
        }

    }

    private void botAquarius() {

        if (foundVerbBoolean) {
            String[] array = {
                    foundVerb + "ing " + "is fun!",
                    "Yes let's do " + foundVerb,
                    "I can't " + foundVerb + ", let's do something else",
                    "I want to do " + foundVerb +"ing" + " with you."
            };
            setBotAnswer(array[randomNumb]);
        } else {
            setBotAnswer("Sorry didn't catch that, you were trying to say?");
        }

    }

    private void botGemini() {

        if (foundVerbBoolean) {
            String[] array = {
                    foundVerb + "ing " + "is fun!",
                    "Yes let's do " + foundVerb,
                    "I can't " + foundVerb + ", let's do something else",
                    "I want to do " + foundVerb +"ing" + " with you."
            };
            setBotAnswer(array[randomNumb]);
        } else {
            setBotAnswer("That doesn't make any sense");
        }
    }

    public void verbFinder(String stringFrClient) {
        String[] array = stringFrClient.split("[ ,?]");

        for (String verb : verbs) {
            for (String s : array) {
                if (s.equalsIgnoreCase(verb)) {
                    foundVerb = s;
                    foundVerbBoolean = true;
                }
            }
        }
    }
}
