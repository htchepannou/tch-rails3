package ${package};

import com.tchepannou.rails.core.api.ActiveRecord;
import java.io.Serializable;
import siena.Generator;
import siena.Id;
import siena.Table;

/**
 * Hello world!
 *
 */
@Table ("sample")
public class SampleActiveRecord
    extends ActiveRecord
{
    @Id (Generator.AUTO_INCREMENT)
    public Long id;
    
    //-- ActiveRecord overrodes
    @Override
    public Serializable getId ()
    {
        return id;
    }
}
