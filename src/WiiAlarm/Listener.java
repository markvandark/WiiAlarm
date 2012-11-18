/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WiiAlarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import wiiusej.WiiUseApiManager;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.*;

/**
 *
 * @author Inn-nya-
 */
public class Listener implements WiimoteListener {

    private int hours = 0, minutes = 0;
    public Listener(int h, int m) {
        this.hours = h;
        this.minutes = m;
    }
    
    public Calendar correntCalendar(){
        Calendar now = Calendar.getInstance();
        Calendar correntC = Calendar.getInstance();        
        correntC.set( Calendar.HOUR_OF_DAY, hours);
        correntC.set( Calendar.MINUTE, minutes);
        correntC.set( Calendar.SECOND, 0 );
        correntC.set( Calendar.MILLISECOND, 0 );
        if(now.getTime().after(correntC.getTime())){
            correntC.add(Calendar.HOUR_OF_DAY, +24);
        }
        return correntC;
    }
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_hh.mm");
    //SimpleDateFormat timeformat = new SimpleDateFormat("hh.mm.ss");
    //создание файла out_dd.MM.yyyy_hh.mm.txt 
    public PrintWriter pw;
   
    public void printtoFail(PrintWriter p) {
     /*   Calendar temp = correntCalendar();
        Calendar now = Calendar.getInstance();*/
        try {
            pw = new PrintWriter(new File("out_" + formatter.format(new Date()) + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
     /*   while(temp.after(now)){
        pw.println(new Date() + " " + 0 + " " + 0 + " " + 0);
        temp.add(Calendar.SECOND, +2);
        }*/
        
    }

    @Override
    public void onButtonsEvent(WiimoteButtonsEvent wbe) {
        //по нажатию кнопки А отключение wii
        if (wbe.isButtonAPressed()) {
            System.out.println(wbe);
            WiiUseApiManager.shutdown();
            pw.close();
        }
    }

    @Override
    public void onIrEvent(IREvent ire) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //считываение координат x,y,z по событию

    @Override
    public void onMotionSensingEvent(MotionSensingEvent mse) {
        //1й столбец - время, взятие координат и запись в файл
        double vec = ((int)(Math.sqrt(
                Math.pow((double)mse.getRawAcceleration().getX(), 2) 
                + Math.pow((double)mse.getRawAcceleration().getX(), 2) 
                + Math.pow((double)mse.getRawAcceleration().getX(), 2))*100))/100d;
        pw.println(System.currentTimeMillis() +" " + vec);
    }

    @Override
    public void onExpansionEvent(ExpansionEvent ee) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onStatusEvent(StatusEvent se) {
        System.out.println(se);
    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent de) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent nie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent nre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent ghie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent ghre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent ccie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent ccre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
