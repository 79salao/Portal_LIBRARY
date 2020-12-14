package com.movilizer.microservices.commons;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53ClientBuilder;
import com.amazonaws.services.route53.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DNSService {

    public Map<String, List<String>> getMicroservicesIps(String AWS_ACCESS_KEY_ID, String AWS_SECRET_KEY_ID, String ROUT53_HOSTED_ZONE_ID, String DNS) {
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
        List<String> cassandra = new ArrayList<>();
        educationList.add("http://" + "54.78.9.131" + ":9010");
        employeeList.add("http://" + "54.78.9.131" + ":9011");
        entitiesList.add("http://" + "54.78.9.131" + ":9012");
        experienceList.add("http://" + "54.78.9.131" + ":9013");
        fileList.add("http://" + "54.78.9.131" + ":9014");
        monitoringList.add("http://" + "54.78.9.131" + ":9015");
        projectList.add("http://" + "54.78.9.131" + ":9016");
        registerList.add("http://" + "54.78.9.131" + ":9017");
        categoriesList.add("http://" + "54.78.9.131" + ":9018");
        skillsList.add("http://" + "54.78.9.131" + ":9019");
        userskillsList.add("http://" + "54.78.9.131" + ":9020");
        dynamicpropertiesList.add("http://" + "54.78.9.131" + ":9021");
        authenticationList.add("http://" + "54.78.9.131" + ":9012");
        proxyList.add("http://" + "54.78.9.131" + ":9023");
        cassandra.add("http://" + "54.194.79.132");
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
        return returnMap;
    }

}
