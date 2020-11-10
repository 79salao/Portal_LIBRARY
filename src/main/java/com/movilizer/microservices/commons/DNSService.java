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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DNSService {

    private final String AWS_ACCESS_KEY_ID;

    private final String AWS_SECRET_KEY_ID;

    private final String ROUT53_HOSTED_ZONE_ID;

    public DNSService() {
        PropertiesConfiguration config = new PropertiesConfiguration();
        try {
            config.load("application.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        this.AWS_ACCESS_KEY_ID = config.getString("aws.accesskey.id");

        this.AWS_SECRET_KEY_ID = config.getString("aws.secretkey.id");

        this.ROUT53_HOSTED_ZONE_ID = config.getString("aws.hostedzone.id");
    }

    public Map<String, List<String>> getMicroservicesIps() {
        Map<String, List<String>> returnMap = new HashMap<>();
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
        for (ResourceRecordSet resourceRecordSet : resourceRecordSetList) {
            String microserviceEndpoint = resourceRecordSet.getName();
            String microserviceName = microserviceEndpoint.split("-")[1];
            if (microserviceEndpoint.split("-")[0].equals("ep")) {
                List<String> microserviceIps = new ArrayList<>();
                for (ResourceRecord resourceRecord : resourceRecordSet.getResourceRecords()) {
                    microserviceIps.add(resourceRecord.getValue());
                }
                if (returnMap.get(microserviceName) != null) {
                    List<String> oldList = returnMap.get(microserviceName);
                    microserviceIps = Stream.concat(oldList.stream(), microserviceIps.stream())
                            .collect(Collectors.toList());
                }
                returnMap.put(microserviceName, microserviceIps);
            }
        }
        System.out.println(returnMap);
        return returnMap;
    }


}
