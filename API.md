# API

## Getting Started
1. Add Dependency to Gradle
    ```gradle
    maven {
        name = "SorceryCraft"
        url "https://maven.concern.i.ng/releases"
    }
    dependencies {
        modApi "net.zestyblaze:Sorcery:LATEST"
        {LATEST = Latest mod version available on Curseforge for target version}
    }
    ```
2. Add Dependency to ```fabric.mod.json```
    ```json
    {
        "depends": {
            "sorcerycraft": "LATEST"
        }
    }
    ```
   `{LATEST = Latest mod version available on Curseforge for target version}`
3. Create a class extending ```net.zestyblaze.sorcerycraft.api.Spell```
    ```java
    public class ExampleSpell extends Spell {
       public ExampleSpell(Identifier id, int level) {
           super(id, level, SpellType);
       }

       @Override
       public void execute(Entity target, Entity source, Entity attacker) {
           // OPTIONAL
           // Called when ExampleSpell hits an entity
           // Override this if you want the spell to affect entities
       }

       @Override
       public void execute(World world, BlockHitResult hitResult) {
           // OPTIONAL
           // Called when ExampleSpell hits a block
           // Override this if you want the spell to affect blocks
       }
   
       @Overide
       public void execute(World world, ItemStack source, Entity caster) {
           // OPTIONAL
           // Called when ExampleSpell is used from self spell item
           // Override this if you want the spell to not summon projectile and only be used on caster
       }    

       @Override
       public int getMaxLevel() {
           // REQUIRED
           // Return ExampleSpell's maximum level
           return 2;
       }

       @Override
       public Text getTranslation() {
           // OPTIONAL
           // Return a custom display name for ExampleSpell
           // Override this only if you want a custom display name
           return new LiteralText("Example " + (getLevel() + 1));
       }
    }
    ```
   SpellType is a new enum system that lets you automatically define what kind of spell you want yours to be. There are three types:
   - `SpellType.SELF`: A spell that applies an effect to the caster of the spell only
   - `SpellType.PROJECTILE`: A spell that shoots a projectile which executes a spell on hitting a player or block
   - `SpellType.BOTH`: A spell that acts as a projectile spell on right click, or a self spell on shift right click
   You define your SpellType in the constructor at the end by using one of the above fields
4. Register the spell in your ModInitializer
    ```java
    public class ExampleMod implements ModInitializer {
        public static final Identifier EXAMPLE_SPELL = SpellRegistry.register(new Identifier("modid", "example_spell"), ExampleSpell.class);
    }
    ```
5. Add Spell Translation (skip this if you have overridden ```Spell.getTranslation()``)
    ```json
    {
        "spell.modid.example_spell": "Example"
    }
    ```
6. If you want to add the spell to the ritual circle function, here is an example:
   ```json
    {
        "type": "sorcerycraft:ritual_recipe",
        "cost": [
          {
            "item": "[item_cost]"
          },
          {
            "item": "sorcerycraft:spell"
          }
        ],
        "result": {
            "item": "sorcerycraft:spell",
            "data": {
              "Spells": [
                {
                   "id": "[modid:id_of_spell]",
                   "level": [0]
                }        
              ]      
            }          
        },
        "xpCost": [10]   
    }
    ```
Anything in square brackets will need to be replaced, here are some notes about this
- `cost` is a list of items or tags that will act as the cost for making a spell (Usually have the "sorcerycraft:spell" here)
- The `id` is the id of your custom spell here. It will be the mod id of your mod and the id you registered your spell with
- `level` is the level of the spell that the ritual will give, starting from 0. (So 0 = Level 1 spell, 1 = Level 2 Spell etc)
- `xpCost` is the number of levels your spell will cost to conduct the ritual

## Useful Methods
- ```(non-static) Spell.getLevel()```
- ```(non-static) Spell.getID()```
- ```(static) SpellRegistry.register()```

## API Stability
APIs are only guaranteed to be stable in the ```net.zestyblaze.sorcerycraft.api``` package, it is unrecommended to rely on any SorceryCraft classes outside this package and only if they aren't marked with `@Deprecated`

## Spell Levels
Spell levels are 0-indexed, if you have a level 1 Example Spell, ```Spell.getLevel()``` wil return 0, and if it is level 2 ```Spell.getLevel()``` wil return 1, ```Spell.getMaxSpell()``` should be the maximum-acceptable value of ```Spell.getLevel() + 1```, so if Example Spell has levels 1-2, ```Spell.getMaxLevel()``` should return 2.
