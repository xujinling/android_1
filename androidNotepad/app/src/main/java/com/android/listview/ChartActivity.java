package com.android.listview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


public class ChartActivity extends Activity {

    private LineChartView lineChartView;
    private List<InfoBean> listBean;
    private Map<String,Float> map;
    private String mapKey;
    private float mapValue;
    private List<PointValue> listPoint;
    private List<Line> listLine;
    private Line line;
    private int index=0;
    private LineChartData lineChartData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);
        lineChartView= (LineChartView) findViewById(R.id.chart);
        Intent intent=getIntent();
        listBean= (List<InfoBean>) intent.getSerializableExtra("charts");   //获取list集合中的数据
        map=new TreeMap<>();
        getDatatoMap();
        getChartsValues();
    }

    /**
     * 获取集合里的数据并将时间和花费金额放入map集合中
     */
    private void getDatatoMap(){
        if(listBean!=null){
            for(int i=0;i<listBean.size();i++){
                mapKey=listBean.get(i).getTime();
                mapValue= Float.parseFloat(listBean.get(i).getMoney().toString().trim());
                if(!map.containsKey(mapKey)){
                    map.put(mapKey,mapValue);
                }else{
                    float previous=map.get(mapKey);
                    map.put(mapKey,previous+mapValue);
                }
            }
        }
    }

    /**
     * 获取点和线
     */
    private void getChartsValues(){
        listPoint=new ArrayList<>();
        listLine=new ArrayList<>();
        for(Float value:map.values()){
            listPoint.add(new PointValue(index,value));
            index++;
        }
        line=new Line(listPoint);
        //设置属性
        line.setColor(Color.RED);
        line.setPointColor(Color.YELLOW);
        line.setShape(ValueShape.CIRCLE);
        listLine.add(line);
        lineChartData=new LineChartData(listLine);
        lineChartView.setLineChartData(lineChartData);
    }



}
