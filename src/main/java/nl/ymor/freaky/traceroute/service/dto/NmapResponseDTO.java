package nl.ymor.freaky.traceroute.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
public class NmapResponseDTO {
    private String order;
    private String rtt;
    private String ip;
    private String originalString;

}
