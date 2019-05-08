package net.frozenchaos.TirNaNog.automation.triggers;

//todo: flesh out
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;

public interface TriggerRepository extends CrudRepository<Trigger, Integer> {
//    @Query("SELECT config FROM ModuleConfig config WHERE config.name = ?1")
//    public Trigger findByName(String name);
//
//    @Query("SELECT config FROM ModuleConfig config WHERE config.ip = ?1")
//    public Trigger findByIp(String ip);
//
//    @Query("SELECT config FROM ModuleConfig config WHERE config.ip != 'localhost'")
//    public List<Trigger> findOtherConfigs();
}
