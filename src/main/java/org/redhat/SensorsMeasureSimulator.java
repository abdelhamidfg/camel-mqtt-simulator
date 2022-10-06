package org.redhat;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



//sending measure data 


@Component
public class SensorsMeasureSimulator extends RouteBuilder {

	@Autowired
	private CamelContext camelContext;

	@Override
	public void configure() throws Exception {

	 from("timer:tick?fixedRate=true&period=60000")
	 .setBody(method(this,"genRandomIoTData()"))
	 .marshal().json(JsonLibrary.Jackson).log("sensor.topic.name= {{sensor.topic.name}} {{broker.url}}=  ${body} ")
	.to("paho:{{sensor.topic.name}}/data?brokerUrl={{broker.url}}")
	 ;
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
 

	public Map<String, Map<String, String>> genRandomIoTData(){
        Map<String, Map<String, String>> iotData = new HashMap<String,Map<String, String>>();
        iotData.put("inside", genRandomInsideSensor());
        iotData.put("outside", genRandomOutsideSensor());
        return iotData;
    }

	
	public Map<String, String> genRandomInsideSensor(){
        Map<String, String> iotData = new HashMap<String,String>();
        iotData.put("temperature", ""+getRandomNumber(10,50));
        iotData.put("humidity", ""+getRandomNumber(10,60));
        iotData.put("co2", ""+getRandomNumber(400,1000)); //400 -1000 ppm
        iotData.put("smoke", ""+getRandomNumber(0,1));

        return iotData;
    }
	
	public Map<String, String> genRandomOutsideSensor(){
        Map<String, String> iotData = new HashMap<String,String>();
        iotData.put("temperature", ""+getRandomNumber(10,50));
        iotData.put("humidity", ""+getRandomNumber(10,60));
        iotData.put("pressure", ""+getRandomNumber(800,2000));
        iotData.put("uv", ""+getRandomNumber(1,100)); 
        iotData.put("pm10", ""+getRandomNumber(10,1000));

        return iotData;
    }
}
