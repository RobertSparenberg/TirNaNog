package net.frozenchaos.TirNaNog.data;

public interface ModuleConfigEventListener {
    void onModuleConfigSave(ModuleConfig saved);
    void onModuleConfigRemove(ModuleConfig deleted);
}
