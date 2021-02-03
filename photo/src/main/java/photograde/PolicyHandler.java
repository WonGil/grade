package photograde;

import photograde.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PhotoRepository photoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPointAwarded_(@Payload PointAwarded pointAwarded){

        if(pointAwarded.isMe()){
            System.out.println("##### listener  : " + pointAwarded.toJson());
        }
    }

}
