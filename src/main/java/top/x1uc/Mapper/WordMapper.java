package top.x1uc.Mapper;




import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface WordMapper {
    Set<String> getAll();
    
    int insert(@Param("word") String word);
    
}
