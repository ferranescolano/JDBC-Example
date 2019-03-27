/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import exceptions.MarvelException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnectorFactory;
import model.Gems;
import model.Place;
import model.SuperHero;
import model.User;
import persistence.MarvelDAO;


public class Marvel {
//Creamos un usuario null, cuando este no sea null serÃ¡ porque el usuario estÃ¡ logueado.
    //Si acaba en null significa que el usuario ha sido borrado
    private static User usuario = null;
    private static MarvelDAO marveldao;

    public static void main(String[] args) throws SQLException {
        marveldao = new MarvelDAO();

        marveldao.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String[] datos;
            String[] datoslogin;
            do {

                String linea = br.readLine();
                datos = linea.split(" ");

                switch (datos[0].toLowerCase()) {

                    case "r":

                        createUser(datos);

                        break;

                    case "v":

                        showHeroes();

                        break;

                    case "l":

                        login(datos);

                        break;



                    default:
                        System.out.println("Incorrect command");

                }

            } while (datos[0] != "s");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        marveldao.disconnect();
        // desconectas

    }

    public static void showHeroes() {
        try {
            
            //Ejecutamos funciÃ³n de marveldao
            marveldao.showALLHeroes();

        } catch (Exception e) {
        }

    }

    public static void createUser(String[] datos) throws SQLException {

        try {
            //Recogemos las posiciones 1, 2 y 3 del array de datos para sacar el user, password y super heroe
            String username = datos[1];
            String password = datos[2];
            String hero = datos[3];
            //Le asignamos los valores por defecto
            String defaultplace = "New York";
            int defaultlevel = 1;
            int defaultPoints = 0;
            //Creamos un objeto user y lo rellenamos con los valores asignados
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setHero(marveldao.getSuperHeroByName(hero));
            u.setPoints(defaultPoints);
            u.setPlace(marveldao.getPlaceByName(defaultplace));
            u.setLevel(defaultlevel);
            //Ejecutamos funciÃ³n de marveldao
            marveldao.insertUser(u);

        } catch (MarvelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(String[] datos) throws SQLException {
    	//En este método recogeremos password, en caso de que sea igual a la que hay almacenada
    	//en el objeto usuario va a ejecutar la funcion del DAO, en caso de que no sea correcta
    	//nos devuelve un mensaje de error
        String password = datos[1];

        if (!password.equalsIgnoreCase(usuario.getPassword())) {

            System.out.println("The password is incorrect");
        } else {
            marveldao.deleteUser(usuario);
            usuario = null;
        }

    }

    public static void loginMenu() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //En este método recogeremos la entrada por teclado una vez hayamos iniciado sesión
        String[] datos;

        String stringmove = null;
        String variable = null;

        String linea = br.readLine();
        datos = linea.split(" ");

        do {

            switch (datos[0].toLowerCase()) {

                case "d":
                	//Método para borrar usuario
                    delete(datos);
                    break;

                case "n":
                	//En cada uno de los cases, el String stringmove va recibiendo una dirección
                    stringmove = "north";
                   
                    //En una variable tipo String guardamos el el nombre del lugar que esta en la
                    //dirección N/S/E/W del Place donde se encuentra el usuario
                    variable = usuario.getPlace().getNorth();
                    //Este método nos permite pasar al DAO los dos Strings para poder hacer el update de lugar
                    movement(stringmove, variable);

                    break;

                case "s":
                    stringmove = "south";
                    variable = usuario.getPlace().getSouth();
                    movement(stringmove, variable);

                    break;
                case "w":
                    stringmove = "west";
                    variable = usuario.getPlace().getWest();
                    movement(stringmove, variable);

                    break;

                case "e":
                    stringmove = "east";
                    variable = usuario.getPlace().getEast();
                    movement(stringmove, variable);

                    break;

                default:
                    System.out.println("Incorrect command");
            }
        } while (datos[0] == "");

    }

    public static void movement(String stringmove, String variable) throws SQLException, IOException {
        
    	//Si el usuario está logueado y hay algún lugar en la dirección dónde él haya elegido se ejecutara la consulta
        if (usuario != null) {
            if (variable != null) {
                marveldao.placeToGo(variable, usuario);

            } else {
            	//En caso contrario nos informará de que no hay ningún lugar en esa dirección
                System.out.println("There is no place on " + stringmove);
            }
        } else {
            System.out.println("There is no user logged in");
        }
    }

    public static void login(String[] datos) throws SQLException, MarvelException {
        try {
            
            String userLogin = datos[1];
            String passLogin = datos[2];
            //Llenamos nuestro objeto vacÃ­o con los valores del login 
            usuario = marveldao.loginUser(userLogin, passLogin);
            //Mostramos los valores de lugar y dÃ³nde ir basÃ¡ndonos en el usuario
            System.out.println("Place: " + usuario.getPlace().getName());
            System.out.println(usuario.getPlace().getDescription());

            System.out.println("You can go to: ");
            //Recogemos el nombre del lugar donde se encuetra el usuario
            String place = usuario.getPlace().getName();
            //Ejecutamos esta funciÃ³n para que nos muestre las opciones de movimiento disponibles
            marveldao.checkPlace(place);
            //FunciÃ³n para permitir la movilidad y borrado del Usuario
            loginMenu();

        } catch (Exception e) {
        }
    }

}
