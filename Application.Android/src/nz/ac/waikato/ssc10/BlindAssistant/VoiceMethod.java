package nz.ac.waikato.ssc10.BlindAssistant;

import java.util.Map;

/**
 * A function that can be used to invoke voice methods.
 */
public interface VoiceMethod {
    void invoke(BlindAssistant assistant, Map<String, String> arguments);
}