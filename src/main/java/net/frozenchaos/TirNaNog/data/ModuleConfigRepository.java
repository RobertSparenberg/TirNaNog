package net.frozenchaos.TirNaNog.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleConfigRepository extends CrudRepository<ModuleConfig, Integer> {
    public ModuleConfig findByName(String name);
    public ModuleConfig findByIp(String ip);

    @Query("SELECT config FROM ModuleConfig config WHERE config.ip != 'localhost'")
    public List<ModuleConfig> findOtherConfigs();
}
