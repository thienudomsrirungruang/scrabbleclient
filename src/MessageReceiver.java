import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageReceiver extends Thread{
    private BufferedReader br;
    private PrintWriter pw;
    private UserInputGetter inputGetter;
    public MessageReceiver(BufferedReader br, PrintWriter pw, UserInputGetter inputGetter) {
        this.br = br;
        this.pw = pw;
        this.inputGetter = inputGetter;
    }
    public void run(){
        try{
            while(!isInterrupted()){
                try{
                    int lines = Integer.parseInt(br.readLine());
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < lines; i++){
                        sb.append(br.readLine());
                        if(i < lines - 1){
                            sb.append("\r\n");
                        }
                    }
                    getAndProcessMessage(sb.toString());
                }catch(NumberFormatException e){}
            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void getAndProcessMessage(String message) throws IOException{
        String[] instruction = message.split("[~]+");
        if(instruction[0].equalsIgnoreCase("Request")){
            if(instruction[1].equalsIgnoreCase("GetInput")) { // layout: Request~GetInput~[message]
                if(instruction.length == 2) {
                    String output = inputGetter.getNextLineWithNoRestrictions("");
                    pw.println(output);
                    pw.flush();
                }else{
                    String output = inputGetter.getNextLineWithNoRestrictions(instruction[2]);
                    pw.println(output);
                    pw.flush();
                }
            }else if(instruction[1].equalsIgnoreCase("MakeChoice")) { // layout: Request~MakeChoice~<ignore case (true/false)>~<message>~<choice 1>~[choice 2...]
                String[] choices = new String[instruction.length - 3];
                for(int i = 0; i < instruction.length - 3; i++){
                    choices[i] = instruction[i + 3];
                }
                if(instruction[2].equalsIgnoreCase("false")) {
                    String output = inputGetter.getRestrictedChoice(instruction[3], choices);
                    pw.println(output);
                    pw.flush();
                }else{
                    String output = inputGetter.getRestrictedChoiceIgnoreCase(instruction[3], choices);
                    pw.println(output);
                    pw.flush();
                }
            }
        }
        else if(instruction[0].equalsIgnoreCase("Command")){
            if(instruction[1].equalsIgnoreCase("Print")) { // layout: Command~print~<message>
                System.out.print(instruction[2]);
            }
        }
    }
}