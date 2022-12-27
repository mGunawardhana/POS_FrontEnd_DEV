package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 *
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OderDetailsDTO {

    private String order_id;
    private String code;
    private double price;
    private int quantity;
    private double discount;
    private double total;

}

