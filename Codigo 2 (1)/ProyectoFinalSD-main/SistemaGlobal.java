
package ProyectoFinalSD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class SistemaGlobal {
    static Connection connectionA = DataBaseBancoA.getConnection();
    static Connection connectionB = DataBaseBancoB.getConnection();
    static Connection connectionC = DataBaseBancoC.getConnection();
    static PreparedStatement stmt1 = null;
    static PreparedStatement stmt2 = null;
    
    static int dni=0;
    
    static Scanner sc= new Scanner(System.in);
    static Statement st;
    static ResultSet rs;
    
    public static void main(String[] args) {
        int opcion=0;
        do{
            System.out.println("Eliga su Banco.\n\t0:Salir del Sistema.\n\t1:Banco A.\n\t2:Banco B.\n\t3:Banco C.\n\t4:Cuentas/Cliente.");
            opcion=sc.nextInt();
            switch(opcion){
                case 1: Banco_A();
                    break;
                case 2: Banco_B();
                    break;
                case 3: Banco_C();
                    break;
                case 4: CuentasXCliente();
                    break;
            }
        }while(opcion!=0);
        
    }//end:main
    public static Connection getA(){
        return connectionA;
    }
    public static Connection getB(){
        return connectionB;
    }
    public static Connection getC(){
        return connectionC;
    }
    
    public static void Banco_A() {
        BancoA bank = new BancoA();
        bank.init();
    }
    public static void Banco_B() {
        BancoB bank = new BancoB();
        bank.init();
    }
    public static void Banco_C() {
        BancoC bank = new BancoC();
        bank.init();
    }
    public static void CuentasXCliente(){
        System.out.println("Ingrese DNI del Usuario.");
        dni=sc.nextInt();
        System.out.println("-----BANCO A-----");
        BancoA.EstadoCuenta2(dni);
        System.out.println("-----BANCO B-----");
        BancoB.EstadoCuenta2(dni);
        System.out.println("-----BANCO C-----");
        BancoC.EstadoCuenta2(dni);
    }
            

}
