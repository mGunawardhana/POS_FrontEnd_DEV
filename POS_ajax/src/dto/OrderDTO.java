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

    private String order_id;
    private String order_date;
    private String customer_id;
    private String customer_name;
    private String customer_contact;

}
