# Examples
The following file contains a tutorial for the API.

## The Main class
```java 
import com.kyleposluns.menu.inventorymenu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    private static ExamplePlugin instance;

    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        this.menuManager = new MenuManager(this);
        this.menuManager.addMenu(new ExampleMenu(menuManager));
        //register listeners
    }

    @Override
    public void onDisable() {
        this.menuManager.dispose();
        this.menuManager = null;
    }

    public static ExamplePlugin get() {
        return instance;
    }
}
```



## The Menu class

```java
import com.kyleposluns.menu.inventorymenu.InventoryMenuAPI;
import com.kyleposluns.menu.inventorymenu.InventoryMenuTemplateBuilder;
import com.kyleposluns.menu.inventorymenu.Menu;
import com.kyleposluns.menu.inventorymenu.MenuManager;
import org.bukkit.Material;

public class ExampleMenu extends Menu {

    public ExampleMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public InventoryMenuTemplateBuilder getMenuBuilder() {
        return InventoryMenuAPI.menu()
                .title("Example Menu")
                .displayName("Example Menu")
                .displayIcon(Material.DIAMOND_AXE)
                .glowing(true)
                .description("This is an example menu!")
                .component(InventoryMenuAPI.item()
                    .displayIcon(Material.OBSIDIAN)
                    .displayName("Example Component")
                    .displayNumber(21)
                    //players can only access it if they are level 5 or above!    
                    .accessController(p -> p.getLevel() >= 5)
                    .onClick(event -> event.getPlayer().sendMessage("This is how you handle clicks!")));
                
    }
}
```

