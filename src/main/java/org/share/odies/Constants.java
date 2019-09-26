package org.share.odies;

import org.share.odies.api.JedisRepository;

public interface Constants {
    String API = JedisRepository.class.getName();


    String[] SAVEMETHODS = new String[]{"save","saveAll"};
    String[] FETCHMETHODS = new String[]{"findAll","findById"};
    String[] LOCKMETHODS = new String[]{"lock","unlock"};
    String[] METRICSMETHODS = new String[]{"count","exists"};
    String[] DELETEMETHODS = new String[]{"delete","deleteAll"};

}
