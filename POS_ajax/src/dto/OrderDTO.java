package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 * <p>
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDTO {

    private String orderId;
    private String iCode;
    private String itemName;
    private double price;
    private int Qty;
    private double total;

}
