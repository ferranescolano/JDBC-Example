package persistence;

import Main.Marvel;
import exceptions.MarvelException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Place;
import model.SuperHero;
import model.User;

public class MarvelDAO {

    private Connection connection;
    User logeado = new User();

    public List<SuperHero> selectAllHeroes() throws SQLException {

        List<SuperHero> allSuperHeroes = new ArrayList<>();
        Statement st = connection.createStatement();
        String select = "select * from superhero";
        ResultSet rs = st.executeQuery(select);
        while (rs.next()) {
            SuperHero s = new SuperHero();

            allSuperHeroes.add(s);
        }
        rs.close();
        st.close();
        return allSuperHeroes;

    }

    public User loginUser(String userLogin, String passLogin) throws SQLException, MarvelException {
        //Hacemos un select de la bbdd para crear un nuevo objeto, pasándole los Strings de user y password
        String select = "select * from user where username = '" + userLogin + "' and pass = '" + passLogin + "'";
        String selectPlace = "select place from username where username = '" + userLogin + "' and pass = '" + passLogin + "'";
        String selectHero = "select hero from username where username = '" + userLogin + "' and pass = '" + passLogin + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
        User u = new User();

        if (rs.next()) {
            //Le seteamos los valores a este Objeto
            u.setUsername(userLogin);
            u.setPassword(passLogin);
            u.setLevel(rs.getInt("level"));
            u.setHero(getSuperHeroByName(rs.getString("superhero")));
            u.setPlace(getPlaceByName(rs.getString("place")));
            u.setPoints(rs.getInt("points"));

            System.out.println("Welcome, " + userLogin);

        } else {
            System.out.println("There isn't any User with those credentials on the DB");
        }

        rs.close();
        st.close();
        return u;
    }

    public void getGemsOfPlace(Place p) throws SQLException {
    	//Hacemos un simple select de las gemas que haya en la place que nosotros le pasemos
        String select = "select * from Gems where place = '" + p.getName() + "'";

        PreparedStatement ps = connection.prepareStatement(select);
        ResultSet rs = ps.executeQuery(select);

        boolean placeFound = false;

        if (rs.next()) {
        	//Si encuentra valores en la BBDD el booleano será true y nos dirá si hay gema
            placeFound = true;
            System.out.println("There is a gem");
        } else {
            System.out.println("There are no gems here");
        }

    }

    public void deleteUser(User usuario) throws SQLException {
    	//Creamos un delete para eliminar el usuario que le pasamos
        String deleteQuery = "delete from user where username = '" + usuario.getUsername() + "'";
        Statement st = connection.createStatement();
        st.executeUpdate(deleteQuery);
        
        System.out.println("The user has been eliminated");

        st.close();

    }

    public void insertUser(User u) throws SQLException, MarvelException {
        //Si el valor recogido del método existUser nos devuelve true, significa que el usuario ya está en la BBDD
    	//por lo que nos sacará una excepción
        if (existUser(u)) {
            throw new MarvelException("Error: There is a User with the same name on the DB, try another one. ");

        } else {
        	//En su caso contrario
            //Ejecutamos el insert e insertamos al usuario en la bbdd
            String insert = "insert into user values (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(insert);
           //Recogemos los valores del objeto User u para poder pasarlos al insert
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getLevel());
            ps.setString(4, u.getHero().getName());
            ps.setString(5, u.getPlace().getName());
            ps.setInt(6, u.getPoints());

            ps.execute();
            ps.close();

            System.out.println("User Registered");

        }
    }

    public void placeToGo(String variable, User usuario) throws SQLException, IOException {
    	//Le pasamos los datos del objeto usuario del fichero main y el place, que es imposible que sea null
        String update = "update user set place = '" + variable + "' where username = '" + usuario.getUsername() + "'";
        Statement st = connection.createStatement();
        st.executeUpdate(update);
     
        st.close();
        System.out.println("You have moved to: " + variable);

        checkPlace(variable);
    }

    private boolean existUser(User u) throws SQLException {
    	//Método de comprobación de existencia de usuario, para poderlo registrar correctamente
    	//Recogemos el objeto User u para hacerle un get al nombre de usuario para completar la consulta
        String select = "select * from user where username = '" + u.getUsername() + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);

        boolean exists = false;
        if (rs.next()) {
        	//Si nos detecta campos en la BBDD nos devolverá true, lo que significará un registro erróneo
        }
        rs.close();
        st.close();
        return exists;
    }

    public void checkPlace(String place) throws SQLException, IOException {
        //Creamos un objeto place al cual le pasamos nuestro place y nos lo busca por le nombre en la BBDD
        Place p = getPlaceByName(place);
        //Este String vacÃ­o servira para luego mostrar al usuario las rutas disponibles
        String youCanGo = "";
        //Conjuto de if's que concatenan las opciones de movimiento
        if (p.getNorth() != null) {
            youCanGo = youCanGo + " N,";
        }
        if (p.getSouth() != null) {
            youCanGo = youCanGo + " S,";
        }
        if (p.getWest() != null) {
            youCanGo = youCanGo + " W,";
        }
        if (p.getEast() != null) {
            youCanGo = youCanGo + " E,";
        }

        System.out.println(youCanGo);
        //Se vuelve a ejecutar la funciÃ³n que nos permite entrada por teclado, para ir a otro lugar
        Marvel.loginMenu();

    }

    public void showALLHeroes() throws SQLException {
    	 //Hacemos select del heroe y lo metemos en un while para que nos muestre los de toda la bbdd
        String select = "select * from superhero";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
       
        while (rs.next()) {

            System.out.println(rs.getString("name") + " | " + rs.getString("superpower"));
        }

        rs.close();
        st.close();

    }

    public Place getPlaceByName(String defaultplace) throws SQLException {
    	//Hacemos un select la default place
        String select = "select * from place where name = '" + defaultplace + "' ";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
        //Creamos un objeto Place que rellenaremos a continuación
        Place p = new Place();

        if (rs.next()) {
        	//Rellenamos el objeto Place con sus valores correspondientes por si luego
        	//necesitamos acceder a ellos
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));

            p.setNorth(rs.getString("north"));
            p.setSouth(rs.getString("south"));
            p.setEast(rs.getString("east"));
            p.setWest(rs.getString("west"));

        }

        rs.close();
        st.close();
        //Nos devuelve el objeto
        return p;

    }

    public SuperHero getSuperHeroByName(String name) throws MarvelException, SQLException {
    	//Si el método existHero nos devuelve true, significa que hay heroes, y false que no
        if (!existHero(name)) {
        	//Nos salta excepción
            throw new MarvelException("ERROR: There is no SuperHero in the DB with this name");

        }
        //En caso contrario hacemos un select del heroe por su nombre
        String select = "select * from SuperHero where name = '" + name + "' ";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
        SuperHero h = new SuperHero();
        if (rs.next()) {
        	//Para mostrar la información por pantalla de los heroes, rellenamos el objeto h con el nombre y el superpoder
        	//sacados de la base de datos
            h.setName(rs.getString("name"));
            h.setSuperpower(rs.getString("superpower"));
        }
        rs.close();
        st.close();
        return h;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/marvel";
        String user = "root";
        String password = "";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private boolean existHero(String heroe) throws SQLException {
    	//Hacemos un select del String de heroe para checkear si existe en la BBDD
        String select = "select * from superhero where name = '" + heroe + "'";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);

        boolean exists = false;

        if (rs.next()) {
        	//En caso de que encuentre resultados, es decir, que exista, nos devolverá true, el cual recogeremos en otra función
            exists = true;
        }
        rs.close();
        st.close();
        //Nos hace un return del boolean
        return exists;

    }

}
