/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alaa.Ezzeldin
 */
public class MainMemory {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new MemoryAllocation().setVisible(true);
            }
        });
    
}
}
