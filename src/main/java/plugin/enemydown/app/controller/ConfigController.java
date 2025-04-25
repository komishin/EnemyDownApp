package plugin.enemydown.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plugin.enemydown.app.DuplicateConfigException;
import plugin.enemydown.app.mapper.data.GameConfig;
import plugin.enemydown.app.mapper.data.SpawnEnemy;
import plugin.enemydown.app.service.ConfigService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class ConfigController {

    private final ConfigService service;

    public ConfigController(ConfigService service) {
        this.service = service;
    }

    @GetMapping(value = "/configList")
    public List<GameConfig> searchConfigList(){
        return service.searchConfig();
    }

    @GetMapping(value = "/config")
    public GameConfig searchConfig(@RequestParam String difficulty) {
        return service.searchConfig(difficulty);
    }

    @GetMapping(value = "/spawnEnemyList")
    public List<SpawnEnemy> spawnEnemyList(@RequestParam String difficulty){
        return service.searchSpawnEnemyList(difficulty);
    }

    @PostMapping(value = "/config")
    public ResponseEntity<GameConfig> registerConfig(@RequestBody GameConfig config) throws Exception{
        GameConfig registerConfig = service.registerConfig(config);
        return new ResponseEntity<>(registerConfig, HttpStatus.OK);
    }

    @PostMapping(value = "/updateEnemyScore")
    public ResponseEntity<List<SpawnEnemy>> updateEnemyScore(@RequestBody SpawnEnemy enemy){
        List<SpawnEnemy> updatedSpawnEnemyList = service.updateEnemyScore(enemy);
        return new ResponseEntity<>(updatedSpawnEnemyList, HttpStatus.OK);
    }

    @ExceptionHandler(value = DuplicateConfigException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateConfig(
            DuplicateConfigException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
