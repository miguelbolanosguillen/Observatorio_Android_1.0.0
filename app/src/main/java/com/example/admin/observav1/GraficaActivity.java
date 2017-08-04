package com.example.admin.observav1;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.util.PixelUtils;

import java.util.List;

import static com.example.admin.observav1.ConfiguracionFragment.a_Tit_Cam;
import static com.example.admin.observav1.ConfiguracionFragment.iConta;
import static com.example.admin.observav1.ConfiguracionFragment.it;
import static com.example.admin.observav1.ConfiguracionFragment.v_Col_Ren;
import static com.example.admin.observav1.ConfiguracionFragment.v_tabla;


public class GraficaActivity extends AppCompatActivity {

    public static final int SELECTED_SEGMENT_OFFSET = 50;

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    public PieChart pie;
    private int nRenCons = 0;
    private Segment s1;
    private String sN;      // para quitar las comas a los números
    private int nColg;      // Columna a Graficar
    private int nReng = 4;   // Número de datos a presentar en la gráfica.
    private Float[] aValg;  // Arreglo de valores a graficar
    private String[] aTitg;
//    private Segment s2;
//    private Segment s3;
//    private Segment s4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.GraficaPastel);

        final float padding = PixelUtils.dpToPix(30);
        pie.getPie().setPadding(padding, padding, padding, padding);
//      ------------------------------------------------------------------------------
        pie.getLegend().setVisible(true);
        pie.getLegend().setTableModel(new DynamicTableModel(2, 4));//1 columna 4 renglones
        pie.getLegend().setWidth(PixelUtils.dpToPix(300), SizeMode.ABSOLUTE);
        pie.getLegend().setHeight(PixelUtils.dpToPix(200), SizeMode.ABSOLUTE);
        pie.getLegend().setSize(new Size(10,SizeMode.ABSOLUTE,10,SizeMode.ABSOLUTE));

        //pie.getLegend().setSize(SizeMode.FILL);

//      --------------------------------------------------------------------
        //    pie.setBackgroundColor(getResources().getColor(R.color.rojito)); // se vuelve loca la grafica al refrescarse o rotar la pantalla


        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if (pie.getPie().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);


                    if (segment != null) {
                        final boolean isSelected = getFormatter(segment).getOffset() != 0;
                        deselectAll();
                        setSelected(segment, !isSelected);
                        pie.redraw();
                    }
                }
                return false;
            }

            private SegmentFormatter getFormatter(Segment segment) {
                return pie.getFormatter(segment, PieRenderer.class);
            }

            private void deselectAll() {
                List<Segment> segments = pie.getRegistry().getSeriesList();
                for (Segment segment : segments) {
                    setSelected(segment, false);
                }
            }

            private void setSelected(Segment segment, boolean isSelected) {
                SegmentFormatter f = getFormatter(segment);
                if (isSelected) {
                    f.setOffset(SELECTED_SEGMENT_OFFSET);
                } else {
                    f.setOffset(0);
                }
            }
        });

        donutSizeSeekBar = (SeekBar) findViewById(R.id.donutSizeSeekBar);
        donutSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress() / 100f,
                        PieRenderer.DonutMode.PERCENT);
                pie.redraw();
                updateDonutText();
            }
        });

        donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        updateDonutText();

        m_grafica();

    }

    public void m_grafica() {
        //sombra,brillo/contraste
        EmbossMaskFilter emf = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.3f, 10, 8.2f);
        int xml = 0;
        m_los_mayores();
        nReng = 5;

        if (nReng > nRenCons)
            nReng = nRenCons;

        for (int i = 0; i <= nReng; i++) {  // Solo grafíca 5 datos
            s1 = new Segment(aTitg[i] + "\n" + aValg[i].toString(), aValg[i]);
            if (i == 0) {
                xml = R.xml.pie_segment_formatter1;
            } else if (i == 1) {
                xml = R.xml.pie_segment_formatter2;
            } else if (i % 2 == 0) {
                xml = R.xml.pie_segment_formatter3;
            } else if (i % 3 == 0) {
                xml = R.xml.pie_segment_formatter4;
            } else if (i % 5 == 0) {
                xml = R.xml.pie_segment_formatter5;
            } else if (i%7==0) {
                xml = R.xml.pie_segment_formatter7;
            }else{
                xml = R.xml.pie_segment_formatter2;
            }

            SegmentFormatter sf1 = new SegmentFormatter(this, xml);
            sf1.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLUE);
            sf1.getFillPaint().setMaskFilter(emf);
            sf1.getLabelPaint().setColor(Color.BLACK);
            sf1.getLabelPaint().setLinearText(true);
            pie.addSegment(s1, sf1);
        }

//        for (int i = 0; i <= nReng; i++) {  // Solo grafíca 5 datos
//            sN = v_tabla[iConta][nColg][i];
//            sN = sN.replace(",", "");
//            s1 = new Segment(v_tabla[iConta][1][i] + "\n" + v_tabla[iConta][nColg][i], Float.parseFloat(sN));
//            switch (i) {
//                case 0:
//                    xml = R.xml.pie_segment_formatter1;
//                    break;
//                case 1:
//                    xml = R.xml.pie_segment_formatter2;
//                    break;
//                case 2:
//                    xml = R.xml.pie_segment_formatter3;
//                    break;
//                case 3:
//                    xml = R.xml.pie_segment_formatter4;
//                    break;
//                case 4:
//                    xml = R.xml.pie_segment_formatter1;
//                    break;
//            }
//            SegmentFormatter sf1 = new SegmentFormatter(this, xml);
//            sf1.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLUE);
//            sf1.getFillPaint().setMaskFilter(emf);
//            sf1.getLabelPaint().setColor(Color.BLACK);
//            sf1.getLabelPaint().setLinearText(true);
//            pie.addSegment(s1, sf1);
//        }


        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.BLUE);
        pie.getRenderer(PieRenderer.class).setDonutSize(0, PieRenderer.DonutMode.PERCENT);
        updateDonutText();
        pie.redraw();
    }

    public void m_los_mayores() {

        float fVal = 0, fAux;
        String sAux;
        nColg = 2; // 2 Columna del reporte en plazas sería No de Plazas, en Edo Ejer sería el aprobado
        // Renglones (v_Col_Ren[iConta][1]) - 1;
        nRenCons = (v_Col_Ren[iConta][1]) - 1; // Aqui se guardan los renglones de cada consulta - 1 para no tomar en cuenta el total

        aValg = new Float[nRenCons];
        aTitg = new String[nRenCons];
//      Log.d("nRenCons",nRenCons+ " Renglones:"+v_tabla[iConta][1].length+ " aValg.length"+aValg.length);
        for (int i = 0; i < nRenCons; i++) {   // Hago una copia como viene
            sN = v_tabla[iConta][nColg][i];
            sN = sN.replace(",", "");
            fVal = Float.parseFloat(sN);
            aValg[i] = fVal;
            aTitg[i] = v_tabla[iConta][1][i];
        }
        // ordeno el arreglo de mayor a menor
        for (int i = 0; i < nRenCons; i++) {
            for (int j = i + 1; j < nRenCons; j++) {
                if (aValg[i] < aValg[j]) {
                    fAux = aValg[i];
                    sAux = aTitg[i];
                    // Cambio los importes
                    aValg[i] = aValg[j];
                    aValg[j] = fAux;
                    // Cambio las descripciones
                    aTitg[i] = aTitg[j];
                    aTitg[j] = sAux;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Anima_la_Grafica();
    }

    protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }

    protected void Anima_la_Grafica() {

        final PieRenderer renderer = pie.getRenderer(PieRenderer.class);
        // start with a zero degrees pie:

        renderer.setExtentDegs(0);
        // animate a scale value from a starting val of 0 to a final value of 1:
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // use an animation pattern that begins and ends slowly:
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = valueAnimator.getAnimatedFraction();
//                scalingSeries1.setScale(scale);
//                scalingSeries2.setScale(scale);
                renderer.setExtentDegs(360 * scale);
                pie.redraw();
            }
        });


        // the animation will run for 1.5 seconds:
        animator.setDuration(1500);
        animator.start();
        pie.redraw();
    }
}