
package ProyectoFinalSD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class BancoB {
    static Connection connectionA = SistemaGlobal.getA();
    static Connection connectionB = SistemaGlobal.getB();
    static Connection connectionC = SistemaGlobal.getC();
    static PreparedStatement stmt1 = null;
    static PreparedStatement stmt2 = null;
    static String nombre="";
    static String apellido="";
    static int dni=0;
    static int clave=0;
    static int saldo=0;
    static Scanner sc= new Scanner(System.in);
    static Statement st1;
    static Statement st2;
    static ResultSet rs1;
    static ResultSet rs2;
    public void init() {
        
        int opcion=0;
        do{
            System.out.println("Se encuentra en el Banco B. Eliga una opción."
                                + "\n\t0:Salir del sistema del banco B."
                                + "\n\t1:Registrarse en el Banco B."
                                + "\n\t2:Retirar del Banco B."
                                + "\n\t3:Depositar en el Banco B."
                                + "\n\t4:Transferencia Interbancaria."
                                + "\n\t5:Estado de la Cuenta.");
            opcion=sc.nextInt();
            switch(opcion){
                case 1: Registrar();
                    break;
                case 2: Retirar();
                    break;
                case 3: Depositar();
                    break;
                case 4: Transferencia();
                    break;
                case 5: EstadoCuenta();
                    break;
            }
        }while(opcion!=0);
        
    }//end:main
    public void Registrar(){
        try {
            //Se capturan los datos
            System.out.println("Ingrese su nombre:");
            nombre=sc.next();
            System.out.println("Ingrese su apellido:");
            apellido=sc.next();
            System.out.println("Ingrese su dni:");
            dni=sc.nextInt();
            System.out.println("Ingrese su clave:");
            clave=sc.nextInt();
            System.out.println("Ingrese su saldo inicial:");
            saldo=sc.nextInt();
            
            //se deshabilita el modo de confirmación automática
            connectionB.setAutoCommit(false);
            //Se preparan las sentencias SQL
            stmt1 = connectionB.prepareStatement("INSERT INTO clientes_b VALUES( ?, ?, ?, ?, ? );");
            
            stmt1.setString(1, nombre);
            stmt1.setString(2, apellido);
            stmt1.setInt(3, dni);
            stmt1.setInt(4, clave);
            stmt1.setInt(5, saldo);
            stmt1.executeUpdate();
           
            //se indica que se deben aplicar los cambios en la base de datos
            connectionB.commit();
            
            System.out.println("----OPERACION EXITOSA en el Banco B----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionB != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionB.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        } 
    }
    public void Retirar(){
        try {
            int money=0;
            //Se capturan los datos
            System.out.println("Ingrese su DNI:");
            dni=sc.nextInt();
            System.out.println("Ingrese su Clave:");
            clave=sc.nextInt();
            System.out.println("Ingrese el monto a retirar:");
            money=sc.nextInt();
            
            st1=connectionB.createStatement();
            rs1=st1.executeQuery("SELECT saldo FROM clientes_b WHERE dni='"+dni+"' AND clave='"+clave+"'");
            rs1.next();
            saldo=rs1.getInt("saldo")-money;
            
            //se deshabilita el modo de confirmación automática
            connectionB.setAutoCommit(false);
            //Se preparan las sentencias SQL
            stmt1 = connectionB.prepareStatement("UPDATE clientes_b SET saldo='"+saldo+"' WHERE dni='"+dni+"' AND clave='"+clave+"'");
            stmt1.executeUpdate();
           
            //se indica que se deben aplicar los cambios en la base de datos
            connectionB.commit();
            
            System.out.println("----OPERACION EXITOSA en el Banco B----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionB != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionB.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        } 
    }
    public void Depositar(){
        try {
            int money=0;
            //Se capturan los datos
            System.out.println("Ingrese su DNI:");
            dni=sc.nextInt();
            System.out.println("Ingrese su Clave:");
            clave=sc.nextInt();
            System.out.println("Ingrese el monto a depositar:");
            money=sc.nextInt();
            
            st1=connectionB.createStatement();
            rs1=st1.executeQuery("SELECT saldo FROM clientes_b WHERE dni='"+dni+"' AND clave='"+clave+"'");
            rs1.next();
            saldo=rs1.getInt("saldo")+money;
            //se deshabilita el modo de confirmación automática
            connectionB.setAutoCommit(false);
            //Se preparan las sentencias SQL
            stmt1 = connectionB.prepareStatement("UPDATE clientes_b SET saldo='"+saldo+"' WHERE dni='"+dni+"'AND clave='"+clave+"'");
           
            //stmt1.setInt(6, saldo);
            stmt1.executeUpdate();
           
            //se indica que se deben aplicar los cambios en la base de datos
            connectionB.commit();
            
            System.out.println("----OPERACION EXITOSA en el Banco B----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionB != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionB.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        } 
    }
    public void Transferencia(){
        try {
            //Se capturan los datos
            System.out.println("Ingrese Banco Destino:");
            String Bank=sc.next();
            System.out.println("Ingrese su DNI de la otra cuenta:");
            int dni2=sc.nextInt();
            System.out.println("Ingrese su DNI:");
            dni=sc.nextInt();
            System.out.println("Ingrese su Clave:");
            clave=sc.nextInt();
            System.out.println("Ingrese el monto a depositar:");
            int moneyDepo=sc.nextInt();
            
            if(Bank.equalsIgnoreCase("A")){
                st1=connectionB.createStatement();
                rs1=st1.executeQuery("SELECT saldo FROM clientes_b WHERE dni='"+dni+"' AND clave='"+clave+"'");
                rs1.next();
                saldo=rs1.getInt("saldo")-moneyDepo;
                connectionB.setAutoCommit(false);
                //Se preparan las sentencias SQL
                stmt1 = connectionB.prepareStatement("UPDATE clientes_b SET saldo='"+saldo+"' WHERE dni='"+dni+"'AND clave='"+clave+"'");
                stmt1.executeUpdate();
                
                st2=connectionA.createStatement();
                rs2=st1.executeQuery("SELECT saldo FROM clientes_a WHERE dni='"+dni2+"'");
                rs2.next();
                int saldo2=rs2.getInt("saldo");
                saldo2=saldo2+moneyDepo;
                        
                stmt2 = connectionA.prepareStatement("UPDATE clientes_a SET saldo='"+saldo2+"' WHERE dni='"+dni2+"'");
                stmt2.executeUpdate();
                
            }else if (Bank.equalsIgnoreCase("B")){
                st1=connectionB.createStatement();
                rs1=st1.executeQuery("SELECT saldo FROM clientes_a WHERE dni='"+dni+"' AND clave='"+clave+"'");
                rs1.next();
                saldo=rs1.getInt("saldo")-moneyDepo;
                connectionB.setAutoCommit(false);
                //Se preparan las sentencias SQL
                stmt1 = connectionB.prepareStatement("UPDATE clientes_a SET saldo='"+saldo+"' WHERE dni='"+dni+"'AND clave='"+clave+"'");
                stmt1.executeUpdate();
                
                //st2=connectionB.createStatement();
                rs2=st1.executeQuery("SELECT saldo FROM clientes_b WHERE dni='"+dni2+"'");
                rs2.next();
                int saldo2=rs2.getInt("saldo");
                saldo2=saldo2+moneyDepo;
                        
                stmt2 = connectionB.prepareStatement("UPDATE clientes_b SET saldo='"+saldo2+"' WHERE dni='"+dni2+"'");
                stmt2.executeUpdate();
            }else if (Bank.equalsIgnoreCase("C")){
                st1=connectionB.createStatement();
                rs1=st1.executeQuery("SELECT saldo FROM clientes_a WHERE dni='"+dni+"' AND clave='"+clave+"'");
                rs1.next();
                saldo=rs1.getInt("saldo")-moneyDepo;
                connectionB.setAutoCommit(false);
                //Se preparan las sentencias SQL
                stmt1 = connectionB.prepareStatement("UPDATE clientes_a SET saldo='"+saldo+"' WHERE dni='"+dni+"'AND clave='"+clave+"'");
                stmt1.executeUpdate();
                
                st2=connectionC.createStatement();
                rs2=st2.executeQuery("SELECT saldo FROM clientes_c WHERE dni='"+dni2+"'");
                rs2.next();
                int saldo2=rs2.getInt("saldo");
                saldo2=saldo2+moneyDepo;
                        
                stmt2 = connectionC.prepareStatement("UPDATE clientes_c SET saldo='"+saldo2+"' WHERE dni='"+dni2+"'");
                stmt2.executeUpdate();
            }
            
            System.out.println("----OPERACION EXITOSA en el Banco A----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionA != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionA.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        }
    }
    public void EstadoCuenta(){
        try {
            //Se capturan los datos
            System.out.println("Ingrese su DNI:");
            dni=sc.nextInt();
            
            //se deshabilita el modo de confirmación automática
            connectionB.setAutoCommit(false);
            //Se preparan las sentencias SQL
            st1=connectionB.createStatement();
            rs1=st1.executeQuery("SELECT * FROM clientes_b WHERE dni='"+dni+"'");
            
            rs1.next();
            System.out.println("INFORMACIÓN DE LA CUENTA\nNombre: "+rs1.getString("nombre")+"\nApellido: "+rs1.getString("apellido")+"\nDNI: "+rs1.getInt("dni")+"\nSaldo: "+rs1.getInt("saldo"));
                        
            System.out.println("----OPERACION EXITOSA en el Banco B----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionB != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionB.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        } 
    }
    public static void EstadoCuenta2(int DNI){
        try {
            
            
            //se deshabilita el modo de confirmación automática
            connectionB.setAutoCommit(false);
            //Se preparan las sentencias SQL
            st1=connectionB.createStatement();
            rs1=st1.executeQuery("SELECT * FROM clientes_b WHERE dni='"+DNI+"'");
            
            rs1.next();
            System.out.println("INFORMACIÓN DE LA CUENTA\nNombre: "+rs1.getString("nombre")+"\nApellido: "+rs1.getString("apellido")+"\nDNI: "+rs1.getInt("dni")+"\nSaldo: "+rs1.getInt("saldo"));
                        
            System.out.println("----OPERACION EXITOSA en el Banco B----");
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connectionB != null) {
                System.out.println("Se restauran los datos");
                try {
                    //deshace todos los cambios realizados en los datos
                    connectionB.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer los cambios" + ex1.getMessage());
                }
            }
        } 
    }
}
