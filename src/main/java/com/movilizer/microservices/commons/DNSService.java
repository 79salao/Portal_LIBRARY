package com.movilizer.microservices.commons;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53ClientBuilder;
import com.amazonaws.services.route53.model.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DNSService {

    private String AWS_ACCESS_KEY_ID;

    private String AWS_SECRET_KEY_ID;

    private final String ROUT53_HOSTED_ZONE_ID;

    private final String DNS;

    public DNSService() {
        PropertiesConfiguration config = new PropertiesConfiguration();
        try {
            config.load("application.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        AWS_SECRET_KEY_ID = config.getString("aws.accesskey.id");
        AWS_SECRET_KEY_ID = config.getString("aws.secretkey.id");
        ROUT53_HOSTED_ZONE_ID = config.getString("aws.hostedzone.id");
        DNS = config.getString("dns");
    }

    public Map<String, List<String>> getMicroservicesIps() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY_ID);
        AmazonRoute53 route53 = AmazonRoute53ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.EU_WEST_1)
                .build();
        HostedZone hostedZone = route53.getHostedZone(new GetHostedZoneRequest(ROUT53_HOSTED_ZONE_ID)).getHostedZone();
        ListResourceRecordSetsRequest listResourceRecordSetsRequest = new ListResourceRecordSetsRequest()
                .withHostedZoneId(hostedZone.getId());
        ListResourceRecordSetsResult listResourceRecordSetsResult = route53.listResourceRecordSets(listResourceRecordSetsRequest);
        List<ResourceRecordSet> resourceRecordSetList = listResourceRecordSetsResult.getResourceRecordSets();
        Map<String, List<String>> returnMap = new HashMap<>(this.getIpsMap(resourceRecordSetList));
        System.out.println(returnMap);
        return returnMap;
    }

    private Map<String, List<String>> getIpsMap(List<ResourceRecordSet> resourceRecordSetList) {
        Map<String, List<String>> returnMap = new HashMap<>();
        for (ResourceRecordSet resourceRecordSet : resourceRecordSetList) {
            if (resourceRecordSet.getName().equalsIgnoreCase(DNS)) {
                List<String> educationList = new ArrayList<>();
                List<String> employeeList = new ArrayList<>();
                List<String> entitiesList = new ArrayList<>();
                List<String> experienceList = new ArrayList<>();
                List<String> fileList = new ArrayList<>();
                List<String> monitoringList = new ArrayList<>();
                List<String> projectList = new ArrayList<>();
                List<String> registerList = new ArrayList<>();
                List<String> categoriesList = new ArrayList<>();
                List<String> skillsList = new ArrayList<>();
                List<String> userskillsList = new ArrayList<>();
                List<String> dynamicpropertiesList = new ArrayList<>();
                List<String> proxyList = new ArrayList<>();
                List<String> authenticationList = new ArrayList<>();
                for (ResourceRecord resourceRecord : resourceRecordSet.getResourceRecords()) {
                    educationList.add("http://" + resourceRecord.getValue() + ":9010");
                    employeeList.add("http://" + resourceRecord.getValue() + ":9011");
                    entitiesList.add("http://" + resourceRecord.getValue() + ":9012");
                    experienceList.add("http://" + resourceRecord.getValue() + ":9013");
                    fileList.add("http://" + resourceRecord.getValue() + ":9014");
                    monitoringList.add("http://" + resourceRecord.getValue() + ":9015");
                    projectList.add("http://" + resourceRecord.getValue() + ":9016");
                    registerList.add("http://" + resourceRecord.getValue() + ":9017");
                    categoriesList.add("http://" + resourceRecord.getValue() + ":9018");
                    skillsList.add("http://" + resourceRecord.getValue() + ":9019");
                    userskillsList.add("http://" + resourceRecord.getValue() + ":9020");
                    dynamicpropertiesList.add("http://" + resourceRecord.getValue() + ":9021");
                    authenticationList.add("http://" + resourceRecord.getValue() + ":9012");
                    proxyList.add("http://" + resourceRecord.getValue() + ":9023");
                }
                returnMap.put("education", educationList);
                returnMap.put("employees", employeeList);
                returnMap.put("entities", entitiesList);
                returnMap.put("experiences", experienceList);
                returnMap.put("file", fileList);
                returnMap.put("monitoring", monitoringList);
                returnMap.put("projects", projectList);
                returnMap.put("register", registerList);
                returnMap.put("categories", categoriesList);
                returnMap.put("skills", skillsList);
                returnMap.put("userskills", userskillsList);
                returnMap.put("dynamicproperties", dynamicpropertiesList);
                returnMap.put("authentication", authenticationList);
                returnMap.put("proxy", proxyList);
            }
        }
        return returnMap;
    }

}
