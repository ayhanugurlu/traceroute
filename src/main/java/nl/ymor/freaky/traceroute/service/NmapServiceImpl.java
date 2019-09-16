package nl.ymor.freaky.traceroute.service;

import nl.ymor.freaky.traceroute.service.dto.NmapResponseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NmapServiceImpl implements NmapService {


    private static final Pattern PATTERN_NMAP = Pattern.compile("^(\\d+)\\s+([0-9.]+)\\sms\\s+(.+)$");

    private static final Pattern IPADDRESS_PATTERN = Pattern.compile(
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

    private static final Pattern RTT_PATTERN = Pattern.compile("(\\d+\\.\\d+)");

    private static final Pattern ORDER_PATTERN = Pattern.compile("(\\d+)");


    private final static String NMAP_COMMAND = "%s/nmap -Pn --traceroute -p %s %s";

    @Override
    public List<NmapResponseDTO> getTraceRouteResult(String nmapLocation, String url, String port) throws IOException {
        List<NmapResponseDTO> nmapResponseDTOS = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        String command = String.format(NMAP_COMMAND, nmapLocation, port, url);
        System.out.println(command);
        Process proc = rt.exec(command);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            Matcher matcher = PATTERN_NMAP.matcher(s);
            if (matcher.find()) {
                NmapResponseDTO nmapResponseDTO = parseLine(s);
                if (nmapResponseDTO != null) {
                    nmapResponseDTOS.add(nmapResponseDTO);
                }
            }

        }

        // Read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        return nmapResponseDTOS;
    }

    private NmapResponseDTO parseLine(String input) {

        NmapResponseDTO nmapResponseDTO = new NmapResponseDTO();
        nmapResponseDTO.setOriginalString(input);
        Matcher orderMatcher = ORDER_PATTERN.matcher(input);
        if (orderMatcher.find()) {
            nmapResponseDTO.setRtt(orderMatcher.group());
        }

        Matcher rttMatcher = RTT_PATTERN.matcher(input);
        if (rttMatcher.find()) {
            nmapResponseDTO.setRtt(rttMatcher.group());
        }


        Matcher ipMatcher = IPADDRESS_PATTERN.matcher(input);
        if (ipMatcher.find()) {
            nmapResponseDTO.setIp(ipMatcher.group());
        }


        /*
        String[] elements = input.split(" ");

        List<String> parsedElements = Arrays.asList(elements).stream().filter(s -> s.length() != 0).collect(Collectors.toList());

        if (parsedElements.size() >= 4) {
            nmapResponseDTO.setOrder(parsedElements.get(0));
            nmapResponseDTO.setRtt(parsedElements.get(1));
            nmapResponseDTO.setIp(parsedElements.get(3));

        }
        if (parsedElements.size() == 5) {
            nmapResponseDTO.setHost(parsedElements.get(3));
            nmapResponseDTO.setIp(parsedElements.get(4));
        }
        */


        return nmapResponseDTO;

    }
}
