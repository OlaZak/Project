package dao;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.*;
import java.net.HttpURLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;

public class DAO {

    private static final String LOGIN = "admin@b4959285";
    private static final String PASSWORD = "e148b97e73";


    public static String getBase64() {
        return Base64
                .getEncoder()
                .encodeToString(
                        (LOGIN + ":" + PASSWORD)
                                .getBytes()
                );
    }

    public static String getRest(String request) {
        String res = "";
        try {
            URL url = new URL(request);

            String encoding = getBase64();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }

    public static ArrayList<JsonNode> getNodeListFromNode(JsonNode rows, String key) {
        JsonNode meta;
        ArrayList<JsonNode> resNode = new ArrayList<>();
        Iterator<JsonNode> iterRows = rows.elements();
        while (iterRows.hasNext()) {
            meta = iterRows.next();
            resNode.add(meta.get(key));
        }
        return resNode;
    }

    public static ArrayList<String> parseP(String json) throws IOException {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        String key = "created";
        ArrayList<JsonNode> createDatesVal;

        JsonNode rootNode = mapper.readTree(json);
        JsonNode rows = rootNode.get("rows");
        createDatesVal = DAO.getNodeListFromNode(rows, key);
        ArrayList<String> res = new ArrayList<>();
        for (JsonNode rowsValue : createDatesVal) {
            res.add(rowsValue.toString().replaceAll("^\"|\"$", ""));
        }
        return res;
    }

    public static ArrayList<Order> getAll() {
        ArrayList<Order> products = new ArrayList<>();
        String query = "SELECT * FROM db.olya_orders";
        return getOrdersFromDB(products, query);
    }

    public static ArrayList<Order> getOrdersFromDB(ArrayList<Order> products, String query) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getString("orderId"));
                order.setName(rs.getString("name"));
                order.setDescription(rs.getString("description"));
                order.setMoment(rs.getString("moment"));
                order.setSum(rs.getString("sum"));
                order.setCounterparty_uuid(rs.getString("counterparty_uuid"));
                products.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void TransferDataFromExcel() throws ClassNotFoundException, SQLException {
        Row.MissingCellPolicy xRow = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
            con.setAutoCommit(false);
            PreparedStatement ps = null;
            File excel = new File("C:/AzureTables.xlsx");
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(excel));
            //ps = addCounterpartyToDatabase(con, ps, myExcelBook);
            //ps = addOrdersToDatabase(xRow, con, ps, myExcelBook);
            con.commit();
            ps.close();
            con.close();
            System.out.println("Success import excel to mysql table");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement addCounterpartyToDatabase(Connection con, PreparedStatement ps, XSSFWorkbook myExcelBook) throws SQLException {
        XSSFSheet myExcelSheet = myExcelBook.getSheet("counterparty");
        XSSFRow row = myExcelSheet.getRow(0);
        System.out.println(myExcelSheet.getLastRowNum());
        System.out.println("Name: " + myExcelSheet.getSheetName());
        for (int i = 1; i <= myExcelSheet.getLastRowNum(); i++) {
            row = myExcelSheet.getRow(i);
            String clientId = row.getCell(0).getStringCellValue();
            String name = row.getCell(1).getStringCellValue();
            String sql = "INSERT INTO olya_clients (clientId, name) VALUES( ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, clientId);
            ps.setString(2, name);
            ps.execute();
        }
        return ps;
    }

    public static PreparedStatement addOrdersToDatabase(Row.MissingCellPolicy xRow, Connection con, PreparedStatement ps, XSSFWorkbook myExcelBook) throws SQLException {
        XSSFSheet myExcelSheet1 = myExcelBook.getSheet("orders");
        XSSFRow row1 = myExcelSheet1.getRow(0);
        System.out.println(myExcelSheet1.getLastRowNum());
        System.out.println("Name: " + myExcelSheet1.getSheetName());
        for (int j = 1; j <= myExcelSheet1.getLastRowNum(); j++) {
            row1 = myExcelSheet1.getRow(j);
            String sql = "INSERT INTO olya_orders (orderId, name,description,moment,sum,counterparty_uuid) VALUES( ?, ?, ?, ?, ?, ?)";
            if (row1.getCell(2) == null) {
                Cell cell = row1.getCell(2, xRow.RETURN_BLANK_AS_NULL);
                String description = String.valueOf(cell);
                System.out.println(description);
                String orderId = row1.getCell(0).getStringCellValue();
                String name = row1.getCell(1).getStringCellValue();
                String moment = row1.getCell(3).getStringCellValue();
                String sum = row1.getCell(4).getStringCellValue();
                String counterparty_uuid = row1.getCell(5).getStringCellValue();

                ps = con.prepareStatement(sql);
                ps.setString(1, orderId);
                ps.setString(2, name);
                ps.setString(3, description);
                ps.setString(4, moment);
                ps.setString(5, sum);
                ps.setString(6, counterparty_uuid);
                ps.execute();
            }
        }
        return ps;
    }

    public static void addNewDataIfExist() throws ClassNotFoundException, SQLException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
            String jsonString = DAO.getRest("https://online.moysklad.ru/api/remap/1.1/entity/customerorder");
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT moment FROM db.olya_orders;");
            HashSet<String> fromDB = new HashSet<>();
            while (resultSet.next()) {
                fromDB.add(resultSet.getString("moment"));
            }
            for (String s : DAO.parseP(jsonString)) {
                if (fromDB.contains(s)) {
                    System.out.println("already exist");

                } else {
                    int a = (int) (Math.random() * 999999999);
                    Statement statement1 = con.createStatement();
                    statement1.executeUpdate(" INSERT INTO db.olya_orders (orderId,moment) VALUE ('" + a + "','" + s + "' )");
                    break;
                }
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
