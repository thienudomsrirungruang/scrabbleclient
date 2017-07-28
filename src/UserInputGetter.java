import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class UserInputGetter {
    BufferedReader input;
    public UserInputGetter(BufferedReader input){
        this.input = input;
    }
    public String getNextLineWithNoRestrictions(String message) throws IOException{
        String answer = "~";
        while(answer.contains("~")) {
            System.out.print(message);
            answer = input.readLine();
        }
        return answer;
    }
    public String getRestrictedChoice(String message, String[] choices) throws IOException{
        String answer = "~";
        while(!Arrays.asList(choices).contains(answer) || answer.contains("~")){
            System.out.print(message);
            answer = input.readLine();
        }
        return answer;
    }
    public String getRestrictedChoiceIgnoreCase(String message, String[] choices) throws IOException{
        String answer = "~";
        while(!containsIgnoreCase(answer, choices) || answer.contains("~")){
            System.out.print(message);
            answer = input.readLine();
        }
        return answer;
    }
    public boolean containsIgnoreCase(String string, String[] array){
        for(String element : array){
            if(element.equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
}