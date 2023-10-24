import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BozoEdit {

    public static ArrayList<String> text = new ArrayList<>();
    public static boolean loaded = false;
    public static boolean stated = false;
    public static int pos = 0;
    public static String fileName;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String command;
        String state;
        String[] commandSplit;
        while(true){
            while(!stated){
                System.out.println("Enter state(edit/exit): ");
                state = in.nextLine();
                switch(state){
                    case "edit":
                        stated = true;
                        break;
                    case "exit":
                        System.exit(0);
                        in.close();
                        break;
                    default:
                        System.err.println("Command not found");
                }
            }
            System.out.print("Enter filename: ");
            fileName = in.nextLine();
            try {
                load();
            } catch (Exception e) {
                System.out.println(e);
            }
            while(loaded){
                try {
                    command = in.nextLine();
                    commandSplit = command.split(" ", 2);
                    switch(commandSplit[0]){
                        case "unload":
                            unload();
                            break;
                        case "save":
                            save();
                            break;
                        case "hop":
                            hop(Integer.parseInt(commandSplit[1]));
                            break;
                        case "write":
                            write(commandSplit[1]);
                            break;
                        case "clear":
                            clear();
                            break;
                        case "delete":
                            delete();
                            break;
                        case "concat":
                            concat(commandSplit[1]);
                            break;
                        case "add":
                            addLine(Integer.parseInt(commandSplit[1]));
                            break;
                        case "addlast":
                            addLineLast();
                            break;
                        case "copy":
                            copy(Integer.parseInt(commandSplit[1]));
                            break;
                        case "cut":
                            cut(Integer.parseInt(commandSplit[1]));
                            break;
                        case "peek":
                            peek();
                            break;
                        case "print":
                            print();
                            break;
                        default:
                            System.out.println("command not found");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("argument not found");
                } catch (NumberFormatException e) {
                    System.err.println("invalid argument");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public static void load() throws Exception{
        try {
            File data = new File(fileName);
            Scanner reader = new Scanner(data);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                text.add(line);
            }
            reader.close();
            loaded = true;
            if(text.size() == 0){
                text.add("");
            }
            System.out.println("file loaded successfully");
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            fileName = "";
            loaded = false;
            stated = false;
        }
    }

    public static void unload() throws Exception{
        text.clear();
        pos = 0;
        loaded = false;
        stated = false;
    }

    public static void save() throws Exception{
        try {
            FileWriter writer = new FileWriter(fileName);
            for(int i = 0;i < text.size();i++){
                writer.write(text.get(i));
                if((i+1) < text.size()){
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            System.err.println("error occured");
        }
    }

    public static void hop(int hops) throws Exception{
        if(hops > text.size() || hops < 1){
            System.err.println("line not found");
        } else {
            pos = (hops - 1);
        }
    }

    public static void write(String data) throws Exception{
        text.set(pos, data);
    }

    public static void clear() throws Exception{
        text.set(pos, "");
    }

    public static void delete() throws Exception{
        if(text.size() > 1){
            text.remove(pos);
            if(pos > 0){
                pos--;
            }
        } else {
            System.err.println("text require at least 1 line");
        }
    }

    public static void concat(String data) throws Exception{
        text.set(pos, text.get(pos) + data);
    }

    public static void addLine(int newPos) throws Exception{
        if(newPos >= 0){
            text.add(newPos, "");
        } else {
            System.err.println("invalid index");
        }
    }

    public static void addLineLast() throws Exception{
        text.add("");
    }

    public static void copy(int destPos) throws Exception{
        if(destPos > text.size()){
            System.err.println("invalid index");
        } else {
            text.set((destPos - 1), text.get(pos));
        }
    }

    public static void cut(int destPos) throws Exception{
        if((destPos - 1) == pos){
            System.err.println("cannot cut to the same line as pointer");
        } else {
            if(destPos > text.size()){
                System.err.println("invalid index");
            } else {
                text.set((destPos - 1), text.get(pos));
                text.set(pos, "");
            }
        }
    }

    public static void peek() throws Exception{
        System.out.println("<" + (pos + 1) + "> " + text.get(pos));
    }

    public static void print() throws Exception{
        System.out.println("============================ TEXT START ============================");
        for(int i = 0;i < text.size();i++){
            System.out.println("<" + (i+1) + "> " + text.get(i));
        }
        System.out.println("============================= TEXT END =============================");
    }

}
