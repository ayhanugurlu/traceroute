package nl.ymor.freaky.traceroute.service;

import nl.ymor.freaky.traceroute.service.dto.NmapResponseDTO;

import java.io.IOException;
import java.util.List;

public interface NmapService {

    List<NmapResponseDTO> getTraceRouteResult(String nmapLocation,String url,String port) throws IOException;

}
