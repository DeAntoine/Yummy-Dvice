package yummy_dvice.com;

public class Category {

    String name;
    String number;

    public Category(String name, String number){

        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
