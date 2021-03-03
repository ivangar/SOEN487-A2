package albums.client;

import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientDriver {

    public static AlbumClient albumClient;


    public static void main(String[] args){
        albumClient = new AlbumClient();
        printMainMenu();
    }

    private static void printMainMenu(){
        Scanner sc = new Scanner(System.in);
        int choose=0;
        boolean correct = true;

        System.out.println("\nWelcome to the Albums Service!\n Created by Kiho, Ivan and Febrian");
        System.out.println("1. Album Service");
        System.out.println("2. Quit Console");

        do {
            System.out.print("Please select the service you would like to use: ");
            try {
                choose = sc.nextInt();
                switch(choose){
                    case 1:
                        System.out.println("You have selected the Album Service option!");
                        albumsDriver();

                        break;
                    case 2:
                        System.out.println("You have selected the Quit option. The program is terminating...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input. Select again.");
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please put a correct number. Try again.");
            }

            if(choose == 1)
                correct=false;

        }while(correct);


    }

    private static void albumsDriver(){
        Scanner sc = new Scanner(System.in);
        int choose;

        do {
            choose = AlbumsMenu(sc);

            switch(choose){
                case 1:
                    displayAlbums();
                    break;
                case 2:
                    retrieveAlbum(sc);
                    break;
                case 3:
                    addOrUpdateAlbum(true, false, sc);
                    break;
                case 4:
                    addOrUpdateAlbum(false, true, sc);
                    break;
                case 5:
                    deleteAlbum(sc);
                    break;
                case 6:
                    System.out.println("Exiting albums driver.");
                    break;
                default:
                    System.out.println("You chose an invalid option.");
            }

        }while(choose != 6);

        printMainMenu();
    }

    private static int AlbumsMenu(Scanner sc){
        System.out.println("\n\nMenu (Verb Lists) :");
        System.out.println("1. List all Albums");
        System.out.println("2. Return the specific album info");
        System.out.println("3. Add a new album to the collection");
        System.out.println("4. Update existing album info");
        System.out.println("5. Delete an existing album");
        System.out.println("6. Return to main menu");
        System.out.println();

        System.out.print("Please choose one of the above numbers : ");
        try{
            int choose = sc.nextInt();
            System.out.println();
            return choose;
        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("Please enter a number. Try again.");
            return 0;
        }
    }

    private static void displayAlbums(){
        albumClient.showAll();
    }

    private static void retrieveAlbum(Scanner sc){
        String ISRC;
        String title;

        System.out.println("Please enter the ISRC and title of the album.");
        try{
            System.out.print("ISRC: ");
            ISRC = sc.next();
            sc.nextLine();

            System.out.print("Title: ");
            title = sc.nextLine();
            title = URLEncoder.encode(title);

            albumClient.getAlbums(ISRC, title);

        }catch(InputMismatchException e){
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void addOrUpdateAlbum(boolean add, boolean update, Scanner sc){
        String ISRC;
        String title;
        String description;
        int year;
        String artist;

        System.out.println("Please enter the information of the album.");
        System.out.println("(Must have a valid ISRC, Title, Year and Artist)");
        try {
            System.out.print("ISRC: ");
            ISRC = sc.next();
            sc.nextLine();

            System.out.print("Title: ");
            title = sc.nextLine();
            title = URLEncoder.encode(title);

            System.out.print("Description: ");
            description = sc.nextLine();
            description = URLEncoder.encode(description);

            System.out.print("Year: ");
            year = sc.nextInt();
            sc.nextLine();

            System.out.print("Artist: ");
            artist = sc.nextLine();
            artist = URLEncoder.encode(artist);

            if(ISRC.isEmpty() || title.isEmpty() || artist.isEmpty()){
                throw new InputMismatchException("A field is missing an input!");
            }else if(year < 1950){
                throw new InputMismatchException("The year must be greater than or equal to 1950!");
            }
            else if(add) {
                if (description.isEmpty()) {
                    description = null;
                }
                albumClient.addAlbum(ISRC, title, description, artist, year);
            }
            else if(update) {
                if(description.isEmpty()){
                    description = null;
                }
                albumClient.updateAlbum(ISRC, title, description, artist, year);
            }

        }catch(InputMismatchException e){
            System.out.println("You put the wrong information. " + e.getMessage() + " Try again.");
            System.out.println();
        }
    }


    private static void deleteAlbum(Scanner sc){
        String ISRC;

        System.out.println("What album do you want to delete?");
        try{
            System.out.print("ISRC: ");
            ISRC = sc.next();
            albumClient.deleteAlbum(ISRC);

        }catch(InputMismatchException e){
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }


}
