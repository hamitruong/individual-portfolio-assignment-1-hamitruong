package TCPclient;

import java.util.Random;
import java.util.ArrayList;

class List {

    public ArrayList<String> availableBots = new ArrayList<>();
    public ArrayList<String> usedBots = new ArrayList<>();

    public void addBots() {
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
        verbs = new String[]{"climb", "eat", "sing", "box", "sail"};
    }

    private String botAnswer;
    private String foundVerb;
    private boolean foundVerbBoolean = false;

    private final Random random = new Random();
    private final int randomNumb = random.nextInt();

    public Bot(String stringClient, String botName) {

        String[] greet = {"hi", "hey", "hello", "hallo"};
        for (String s : greet) {
            if (stringClient.contains(s)) {
                setBotAnswer("Hello there!");
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

    public void botToStart(String botName) {

        if ("Gemini".equals(botName)) {
            botGemini();
        } else if ("Aquarius".equals(botName)) {
            botAquarius();
        } else if ("Virgo".equals(botName)) {
            botVirgo();
        } else if ("Pisces".equals(botName)) {
            botPisces();
        } else {
            throw new IllegalStateException("Unexpected value: " + botName);
        }


    }

    private void botPisces () {

        if (foundVerbBoolean) {
            String[] array = {
                    foundVerb + "ing " + "is fun!",
                    "Yes let's do " + foundVerb,
                    "I can't " + foundVerb + ", let's do something else",
                    "I want to do " + foundVerb +"ing" + " with you."
            };
            setBotAnswer(array[randomNumb]);
        } else {
            setBotAnswer("Tell me something that makes sense");
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