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
    public void wheneverPointAwarded_UpdateStatus(@Payload PointAwarded pointAwarded){

        if(pointAwarded.isMe()){
            java.util.Optional<Photo> photoOptional = photoRepository.findById(pointAwarded.getGradeId());
            Photo photo = photoOptional.get();
            //Photo photo = new Photo();
            photo.setGradeId(pointAwarded.getId());

            photoRepository.save(photo);
            System.out.println("##### listener  : " + pointAwarded.toJson());
        }
    }

}
