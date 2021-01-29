package ram.ramires.currency3;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import ram.ramires.currency3.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FragmentChart extends Fragment implements OnChartValueSelectedListener, OnChartGestureListener {
    List<Float> rate1;
    ViewModelCurrency viewModel;
    LineChart mChart;
    ArrayList<Entry> coast= new ArrayList<>();
    ArrayList<String> dates=new ArrayList<>();
    XAxis xAxis;
    Entry eSelected=new Entry();

    @Override
    public void onResume() {
        super.onResume();
        viewModel.historicalDatas();
    }
    @Override
    public void onPause() {
        viewModel.observableDate.set("");
        viewModel.observableCoast.set("");

        super.onPause();
        xAxis.removeAllLimitLines();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModelCurrency.class);
        View view=inflater.inflate(R.layout.fragment_chart, container, false);

        mChart=(LineChart)view.findViewById(R.id.line_Chart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.getDescription().setEnabled(false);

        mChart.getAxisRight().setEnabled(false);

        mChart.animateX(1000);

        xAxis=mChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        viewModel.liveDataSet.observe(getViewLifecycleOwner(), new Observer<LinkedHashMap<String, Float>>() {
            @Override
            public void onChanged(LinkedHashMap<String, Float> set1) {

                //datas
                List<String> dates1=new ArrayList<>(set1.keySet());
                rate1=new ArrayList<>(set1.values());
                coast.clear();
                dates.clear();
                for (int i=0;i<rate1.size();i++){
                    coast.add(new Entry(i, (float) rate1.get(i)));
                }
                setData(coast);

                for (String str:dates1){
                    String day=str.substring(8);
                    String month=str.substring(5,7);
                    String year=str.substring(0,4);
                    dates.add(day+"."+month+"."+year);
                }

                xAxis.removeAllLimitLines();
                xAxis.setValueFormatter(new MyXAxisValueFormater(dates));

                int x=dates1.size()-1;
                String lastDate=dates1.get(x);
                double i=Math.round(set1.get(lastDate) * Math.pow(10, 5)) / Math.pow(10, 5);
                viewModel.observableCoast.set(String.valueOf(i));
            }
        });
        return view;
    }

    private void setData (ArrayList<Entry> entries){
        LineDataSet set2;
        set2=new LineDataSet(entries, "coast ");
        set2.setColor(Color.GREEN);
        set2.setValueTextSize(15f);
        set2.setValueTextColor(Color.BLACK);

        LineData data =new LineData(set2);
        mChart.setData(data);
        mChart.invalidate();
        mChart.notifyDataSetChanged();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.d(MainActivity.LOG,"onValueSelected y="+e.getY()+"; x="+e.getX());
        eSelected=e;

    }
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(MainActivity.LOG, "onChartGestureEnd "+lastPerformedGesture.name());

        if (lastPerformedGesture.name().equals("SINGLE_TAP")) {
            Log.d(MainActivity.LOG, "onChartGestureEnd x=" + eSelected.getX() + " Y " + eSelected.getY());

            LimitLine vertical=new LimitLine(eSelected.getX(), eSelected.getY()+"");
            vertical.setLineColor(Color.BLACK);
            vertical.setLineWidth(1f);
            vertical.enableDashedLine(10f,10f,0);
            if (eSelected.getX()>6){
                vertical.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
            } else vertical.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            vertical.setTextSize(30f);

            WeakReference<LimitLine> weakReference=new WeakReference<>(vertical);
            vertical=null;

            xAxis.removeAllLimitLines();
            xAxis.addLimitLine(weakReference.get());
            double i=Math.round(eSelected.getY() * Math.pow(10, 5)) / Math.pow(10, 5);
            viewModel.observableCoast.set(String.valueOf(i));
            viewModel.observableDate.set(dates.get((int) eSelected.getX()));
        }

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(MainActivity.LOG,"onChartGestureStart" );

    }
    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.d(MainActivity.LOG,"onChartSingleTapped y="+me.getY()+"; x="+me.getX());

    }

    @Override
    public void onChartLongPressed(MotionEvent e) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.d(MainActivity.LOG,"onChartDoubleTapped " );

    }


    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d(MainActivity.LOG," onChartFling" );

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.d(MainActivity.LOG,"onChartScale " );

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d(MainActivity.LOG," onChartTranslate" );

    }

    class MyXAxisValueFormater extends ValueFormatter {
        private String [] mValues;
        public MyXAxisValueFormater(ArrayList<String> values){
            mValues=values.toArray(new String[values.size()]);
        }
        @Override
        public String getFormattedValue(float value) {
            return mValues[(int) value].substring(0,5);
        }
    }
}