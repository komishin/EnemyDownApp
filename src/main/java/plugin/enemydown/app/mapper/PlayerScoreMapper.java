package plugin.enemydown.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import plugin.enemydown.app.mapper.data.GameConfig;
import plugin.enemydown.app.mapper.data.PlayerScore;

import java.util.List;

@Mapper
public interface PlayerScoreMapper {
    @Select("select * from player_score order by id asc")
    List<PlayerScore> selectPlayerScoreList();
}
