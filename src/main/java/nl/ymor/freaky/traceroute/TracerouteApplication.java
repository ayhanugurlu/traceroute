package nl.ymor.freaky.traceroute;


import nl.ymor.freaky.traceroute.service.NmapService;
import nl.ymor.freaky.traceroute.service.NmapServiceImpl;
import nl.ymor.freaky.traceroute.service.dto.NmapResponseDTO;

import java.io.IOException;
import java.util.List;

public class TracerouteApplication {

    NmapService nmapService = new NmapServiceImpl();


    public static void main(String[] args) throws IOException {
        NmapService nmapService = new NmapServiceImpl();
        List<NmapResponseDTO> nmapResponseDTOS = nmapService.getTraceRouteResult(args[0],args[1],args[2]);
        nmapResponseDTOS.forEach(nmapResponseDTO -> System.out.println(nmapResponseDTO));


    }

}
