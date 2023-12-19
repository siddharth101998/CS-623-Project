import java.sql.*;

public class main {

    public static void main(String[] args) {
        Connection conn = null;// Declaring Connection variable outside try-catch block
        Statement stmt1 = null;// Declaring Statement variable outside try-catch block

        try {
        	//Load the jdbc driver into the code 
            Class.forName("org.postgresql.Driver");
            //using connection , create a connection with database 
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Sidh@rth07");
            
            //for Atomicity - set the autocommit property to false so we can commit the whole transaction or rollback if any error occurs in the transaction.
            conn.setAutoCommit(false);
            //for isolation we set the Transaction isolation mode to Serializable which is the highest level of isolation
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //lets define the statement using connection
            stmt1 = conn.createStatement();
            // Your code for database operations here
            
            //TRANSACTION 1
          //  String updateString= "ALTER TABLE stock ADD CONSTRAINT fk_PROD_ID FOREIGN KEY (prod_id) REFERENCES product(prod_id) ON DELETE CASCADE;";
            String updateString1 = "DELETE FROM product WHERE prod_id='p1'";
            stmt1.executeUpdate(updateString1);
 
            
            //TRANSACTION 2
           // String updateString= "ALTER TABLE stock ADD CONSTRAINT fk_depid FOREIGN KEY (dep_id) REFERENCES depot(dep_id) ON DELETE CASCADE;";
            String updateString2= "DELETE FROM depot WHERE dep_id='d1'";
            stmt1.executeUpdate(updateString2);
            
          //  stmt1.executeUpdate(updateString2);
            
//            //TRANSACTION 3
//            String updateString3= "UPDATE product SET prod_id='pp1' WHERE prod_id='p1'";
//            
//            
//          //TRANSACTION 4
//            String updateString4= "UPDATE product SET dep_id='dd1' WHERE dep_id='d1'";
//            
//            
//          //TRANSACTION 5
//            String updateString5= "INSERT INTO product(prod_id,pname,price) VALUES('p100','cd',5)";
//            String updateString6="INSERT INTO stock(prod_id,dep_id,quantity) VALUES('p100','d2',50)";
//          	stmt1.executeUpdate(updateString5);
//            stmt1.executeUpdate(updateString6);
//            
//          //TRANSACTION 6
//          //TRANSACTION 5
//            String updateString7= "INSERT INTO depot(dep_id,addr,volume) VALUES('d100','Chicago',100)";
//            String updateString8="INSERT INTO stock(prod_id,dep_id,quantity) VALUES('p1','d100',100)";
//            stmt1.executeUpdate(updateString7);
//            stmt1.executeUpdate(updateString8);
            
            
            //if the transaction occurs succesfully then commit the updates to database
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("An exception was thrown");
            //In case of an error in transaction the catch block is executed which rollbacks the transaction implementation.
            try {
            	//there are chances for some reason the connection has not been established or failed , 
            	//so we check the that its not null before closing or rollback 
                if (conn != null) {
                    conn.rollback();
                }
                //similarly to the connection, if for some reason the statement was not created and failed, we check the null property before closing it
                if (stmt1 != null) {
                    stmt1.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (stmt1 != null) {
                    stmt1.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



