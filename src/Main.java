import java.util.Scanner;

/*
 * A game has a beginning, (initialization could be in a constructor for the game?)
 * a way to move forward, (perhaps one of many action that could be taken?)
 * goal, (one of many events that could take place and be handled?)
 * obstacle, enemy or opponent (all are entities that live somewhere on and sometimes off the board?)
 *
 */
//GAME -> play(); welcome(); goodbye(); (should the display update have a list of messages?)
//    |-> DISPLAY_BOARD -> draw/render()
//    |-> ENTITY_LIST (hero, enemy, treasure, floor?)
//    |-> ACTION_LIST (what can we do? which is the default action if we hit enter?)
//    |-> Events (collision? react/handle)
class Position {
    int x;

    public Position(int x) {
        this.x = x;
    }

    public void moveForward() {
        this.x++;
    }
}

class Entity { //SPRITES SPRITE-SHEETS
    String name;
    char symbol;
    Position position;
    boolean visible;

    Entity(String name, char symbol, Position position) {
        this.name = name;
        this.symbol = symbol;
        this.position = position;
        this.visible = true;
    }

    public boolean checkCollision(Entity otherEntity) {
        if (otherEntity.position.x == this.position.x) {
            this.visible = false;
            return true;
        }
        return false;
    }
}

class Hero extends Entity { // A Hero is an Entity. "is_a" relationship
    int health;
    Hero() {
        super("Max", '@', new Position(0)); //0 is the beginning
        this.health = 100;
    }
}

class Board {
    int size;
    Entity[] entities;

    Board(int size, Entity[] entities) {
        this.size = size;
        this.entities = entities;
    }

    void render() {
        for (int x = 0; x < this.size; x++) {
            Entity floorTile = new Entity("floor", '.', new Position(x));
            for (Entity e : this.entities) {
                if (floorTile.checkCollision(e)) {
                    floorTile = e; //REPLACE
                }
            }
            System.out.print(floorTile.symbol);
        }
        System.out.print("\n");
    }
}

class Game {
    Board board;
    Entity[] entities;
    String[] messages;
    Scanner scanner;
    Hero hero;
    boolean playing;

    Game() {
        this.hero = new Hero();
        this.entities = new Entity[]{
                this.hero,
                new Entity("Zombie", 'Z', new Position(4)),
                new Entity("Treasure", '$', new Position(7))
        };
        this.board = new Board(8, this.entities);
        this.messages = new String[]{"Enter to push forward."};
        this.scanner = new Scanner(System.in);
    }

    void displayStatus() {
        for (String m : this.messages) {
            System.out.println(m);
        }
        this.board.render();
    }

    void play() {
        this.playing = true;
        while (playing) {
            displayStatus();
            String input = this.scanner.nextLine();
            updateStatus(input);
        }
    }

    void updateStatus(String input) {
        this.hero.position.moveForward();
        for (Entity e : this.entities) {
            if (e != this.hero) {
                boolean collided = e.checkCollision(this.hero);
                if (collided && e.symbol == '$') {
                    this.messages = new String[]{"Treasure Collected."};
                    this.playing = false;
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME !!!");
        Game game = new Game();
        game.play();
        System.out.println("GAME OVER !!!");
        //~50 lines of code - about half the classes
        //char[] board = {'@', '.', '.', 'Z', '.', '.', '.', '$'}; //8 positions
//        char floor = '.';
//        char hero = '@';
//        char enemy = 'Z';
//        char treasure = '$';
//        int heroPosition = 0;
//        int enemyPosition = 3;
//        int treasurePosition = 7;
//        Scanner in = new Scanner(System.in);
//        //infinite loop - must break out when something happens like getting the treasure
//        System.out.println("Welcome to the game, press ENTER/RETURN to push forward.");
//        int turn = 1;
//        while (true) {
//            System.out.printf("Turn %d:\n", turn++);
//            //display board
//            for (int i = 0; i < 8; i++) {
//                if (i == heroPosition) {
//                    System.out.print(hero);
//                } else if (i == enemyPosition) {
//                    System.out.print(enemy);
//                } else if (i == treasurePosition) {
//                    System.out.print(treasure);
//                } else {
//                    System.out.print(floor);
//                }
//            }
//            System.out.println("\nEnter to push forward.");
//            String userInput = in.nextLine();
//            //UPDATE THE STATE
//            heroPosition++; //move forward
//            //COLLISION DETECTION
//            if (heroPosition == enemyPosition) {
//                enemyPosition = -1;//remove enemy
//                System.out.println("Hero has vanquished the enemy!!!");
//            }
//            if (heroPosition == treasurePosition) {
//                enemyPosition = -1;//remove treasure
//                System.out.println("Hero has taken the treasure!!!");
//                break; //break out of infinite loop.
//            }
//        }
//        System.out.println("GAME OVER!!!");
    }
}