package net.frozenchaos.TirNaNog.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleConfigRepository extends CrudRepository<ModuleConfig, Integer> {
    @Query("SELECT config FROM ModuleConfig config WHERE config.name = ?1")
    public ModuleConfig findByName(String name);

    @Query("SELECT config FROM ModuleConfig config WHERE config.ip = ?1")
    public ModuleConfig findByIp(String ip);

    @Query("SELECT config FROM ModuleConfig config WHERE config.ip != 'localhost'")
    public List<ModuleConfig> findOtherConfigs();
}
