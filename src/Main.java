import exception.Nullvalueexception;
import model.Beer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Nullvalueexception {

        Beer beer = new Beer(1,"Jupiler",3.5,"Pils");
        System.out.println(beer);
    }
}