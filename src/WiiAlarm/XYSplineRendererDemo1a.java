package WiiAlarm;

import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class XYSplineRendererDemo1a extends ApplicationFrame
{
    public static BufferedReader getFile(File file) throws FileNotFoundException{
        FileReader fr = new FileReader(file);
        BufferedReader in = new BufferedReader(fr);
        return in;
    } 
    
    public XYSplineRendererDemo1a(String s) throws FileNotFoundException, IOException
    {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(600, 400));
        getContentPane().add(jpanel);
        
        
    }

    public static JPanel createDemoPanel() throws FileNotFoundException, IOException
    {
        NumberAxis numberaxis = new NumberAxis("Время");
        numberaxis.setAutoRangeIncludesZero(true);
        NumberAxis numberaxis1 = new NumberAxis("Отклонение");
        numberaxis1.setAutoRangeIncludesZero(false);
        XYSplineRenderer xysplinerenderer = new XYSplineRenderer();
        XYPlot xyplot = new XYPlot(createSampleData(), numberaxis, numberaxis1, xysplinerenderer);
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
        JFreeChart jfreechart = new JFreeChart("График активности во время сна", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        ChartUtilities.applyCurrentTheme(jfreechart);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        return chartpanel;
    }

    private static XYDataset createSampleData() throws IOException 
    {   
        BufferedReader in=null;
        try {
        in = XYSplineRendererDemo1a.getFile(Values.filetxt);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);}

        String s;
        XYSeries xyseries = new XYSeries("График"+" "+Values.filetxt.getName());
        while((s = in.readLine()) != null){
            String t[] = s.split(" ");
            xyseries.add(Double.parseDouble(t[0]), Double.parseDouble(t[1]));
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        
        return xyseriescollection;
    }

    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        XYSplineRendererDemo1a xysplinerendererdemo1a = new XYSplineRendererDemo1a("Graph");
        xysplinerendererdemo1a.pack();
        RefineryUtilities.centerFrameOnScreen(xysplinerendererdemo1a);
        xysplinerendererdemo1a.setVisible(true);
    }
}