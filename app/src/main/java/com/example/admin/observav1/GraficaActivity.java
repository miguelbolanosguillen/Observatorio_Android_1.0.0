package com.example.admin.observav1;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.util.PixelUtils;

import java.util.List;


public class GraficaActivity extends AppCompatActivity {

    public static final int SELECTED_SEGMENT_OFFSET = 50;

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    public PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);

        final float padding = PixelUtils.dpToPix(30);
        pie.getPie().setPadding(padding, padding, padding, padding);

        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if (pie.getPie().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);

                    deselectAll();
                    if (segment != null) {
                        final boolean isSelected = getFormatter(segment).getOffset() != 0;
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
        int[ ] edad = {45, 23, 11, 9};

        s1 = new Segment("s1", 3);
        s2 = new Segment("s2", 1);
        s3 = new Segment("s3", 7);
        s4 = new Segment("s4", 9);

        //sobra,brillo/contraste
        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.3f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter(this, R.xml.pie_segment_formatter1);
        sf1.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter(this, R.xml.pie_segment_formatter2);
        sf2.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter(this, R.xml.pie_segment_formatter3);
        sf3.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter(this, R.xml.pie_segment_formatter4);
        sf4.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf4.getFillPaint().setMaskFilter(emf);

        pie.addSegment(s1, sf1);
        pie.addSegment(s2, sf2);
        pie.addSegment(s3, sf3);
        pie.addSegment(s4, sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupIntroAnimation();
    }

    protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }

    protected void setupIntroAnimation() {

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
    }
}