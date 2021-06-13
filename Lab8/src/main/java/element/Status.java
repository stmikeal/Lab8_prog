package element;

import java.io.Serializable;

/**
 * Перечисление статус.
 *
 * @author mike
 */
public enum Status implements Serializable {
    FIRED,
    RECOMMENDED_FOR_PROMOTION,
    REGULAR;
}
