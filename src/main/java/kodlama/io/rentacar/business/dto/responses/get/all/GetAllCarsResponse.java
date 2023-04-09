package kodlama.io.rentacar.business.dto.responses.get.all;

import kodlama.io.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponse {
    private int id;
    private int modelId;
    private int modelYear;
    private String plate;
    private double dailyPrice;
    private State state;
    //    private String modelName; mesela response'da modelin ismini istersem böyle
    //    private String modelBrandName; mesela response'da modelin marka ismini istersem böyle
}
