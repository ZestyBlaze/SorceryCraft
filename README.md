# SorceryCraft - Dispelled
Cast Spells in Minecraft!

This mod currently supports Minecraft 1.18.

## What are Spells?
Spells are found throughout the world in chests. When you pick up a Spell you will "discover" it, once you "discover" a spell you can apply it to a blank or existing spell in the Casting Table. You can cast spells by right-clicking. There is also a 30% chance (by default) that the spell will rebound and hit you instead, unless the "Steadfast" spell is applied.

## Crafting
#### Blank Spell
<table>
    <tr>
        <td></td>
        <td>Lapis Lazuli</td>
        <td></td>
    </tr>
    <tr>
        <td>Lapis Lazuli</td>
        <td>Paper</td>
        <td>Lapis Lazuli</td>
    </tr>
    <tr>
        <td></td>
        <td>Lapis Lazuli</td>
        <td></td>
    </tr>
</table>

#### Crystalised Redstone
<table>
    <tr>
        <td>Redstone</td>
        <td>Crystal</td>
    </tr>
</table>

#### Crystalised Redstone Block
<table>
    <tr>
        <td>Redstone</td>
        <td>Redstone</td>
        <td>Redstone</td>
    </tr>
    <tr>
        <td>Redstone</td>
        <td>Crystal</td>
        <td>Redstone</td>
    </tr>
    <tr>
        <td>Redstone</td>
        <td>Redstone</td>
        <td>Redstone</td>
    </tr>
</table>

#### Hallowed Iron
<table>
    <tr>
        <td>Iron Ingot</td>
        <td>Iron Ingot</td>
        <td>Iron Ingot</td>
    </tr>
    <tr>
        <td>Iron Ingot</td>
        <td>Crystal</td>
        <td>Iron Ingot</td>
    </tr>
    <tr>
        <td>Iron Ingot</td>
        <td>Iron Ingot</td>
        <td>Iron Ingot</td>
    </tr>
</table>

## Spells
| Spell | Max Level | Description |
| --- | --- | --- |
| Flame | 2 | Sets target on fire. |
| Damage | 2 | Damages target. |
| Heal | 2 | Heals target. |
| Dissolve | 1 | Removes target's status effects. |
| Levitate | 2 | Gives target the Levitation effect. |
| Steadfast | 1 | Prevents the spell from failing. This spell does nothing on its own. |
| Teleport | 3 | Teleports the target to a random location. |
| Inward | 1 | Causes the spell to target the caster. If the spell fails instead of rebounding, it will just do nothing. This spell does nothing on its own. |
| Cooling | 1 | Extinguish the target if they are on fire. |
| Lightning | 1 | Strikes the target with lightning. |



## ```/spell``` Command - NOT YET IMPLEMENTED AGAIN FOR 1.18
This command requires OP permissions.

#### ```/spell forget <player>```
This command clears all known spells from the given player.

#### ```/spell forget <player> <spell-id>```
This command clears the specified spell from the given player.

#### ```/spell list <player>```
This lists all the spells the given player knows.

#### ```/spell discover <player>```
This adds all spells to the specified player's discovered spells list.

#### ```/spell discover <player> <spell-id> <level>```
This adds the specified spell to the specified player's discovered spells list.

#### ```/spell apply <player> <spell-id> <level>```
This adds the specified spell to the item in the specified player's main hand.

#### ```/spell remove <player> <spell-id>```
This removes the specified spell from the item in the specified player's main hand.

## Licenses
[Code License](CODE_LICENSE)

[Art License](ART_LICENSE)

## API
[View API](API.md)

## Changelog
[View Changelog](CHANGELOG.md)