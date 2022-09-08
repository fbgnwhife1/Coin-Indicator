package rabbitmqbitsocketconsumer.consumer.DTO;

import lombok.Data;

@Data
public class BSI_RBIDto {
    private String market;
    private Double score;
    private Boolean go;
}
