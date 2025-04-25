package plugin.enemydown.app.service;

import org.springframework.stereotype.Service;
import plugin.enemydown.app.DuplicateConfigException;
import plugin.enemydown.app.mapper.GameConfigMapper;
import plugin.enemydown.app.mapper.data.GameConfig;
import plugin.enemydown.app.mapper.data.SpawnEnemy;

import java.util.List;

@Service
public class ConfigService {

    public final GameConfigMapper mapper;

    public ConfigService(GameConfigMapper mapper) {
        this.mapper = mapper;
    }

    public List<GameConfig> searchConfig(){
        return mapper.selectConfigList();
    }

    public GameConfig searchConfig(String difficulty){
        return mapper.selectConfig(difficulty);
    }

    public List<SpawnEnemy>searchSpawnEnemyList(String difficulty){
        return mapper.selectSpawnEnemyList(difficulty);
    }

    public GameConfig registerConfig(GameConfig config) throws Exception{
        if (searchConfig(config.getDifficulty()) != null){
            throw new DuplicateConfigException("Duplicate Config Error!");
        }
        mapper.insertConfig(config);
        return mapper.selectConfig(config.getDifficulty());
    }

    public List<SpawnEnemy> updateEnemyScore(SpawnEnemy enemy){
        mapper.updateEnemyScore(enemy);
        return mapper.selectSpawnEnemyList(enemy.getDifficulty());
    }
}

