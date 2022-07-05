# Changelog

**1.2.0-beta**
* Fixed "Cleanse" spell being uncraftable
* Split up `SpellItem`into 3 different spell items for easier spell use
* Spells are now split between "Self Use", "Projectile" or "Both"
* Moved `didSpellSuceeed` method to `SpellHelper` for addons to use
* Moved `SpellHelper` to the API package
* Split up packages in the API for easier finding and formatting
* Moved Spell names into the name of the item to make it faster for use in hotbar in combat when multiple spells are in the hotbar
* Added the spell's type into the tooltip to let it be easier to know what spell it is
* Spell's have different tassel's depending on what type of spell it is
* Updated the book to reflect the new changes from the update
* Update the Crystal Ore texture
* Package refactor for better understanding
* Updated the API documentation for the new features
* Changes the way the sounds work to let there be custom subtitles for user's with hearing troubles

**1.1.2-beta**
* Hotfix for book not working in 1.19 (+ made it the same for 1.18 to make it consistent across versions)

**1.1.1-beta**
* Bumped version number up
* Updated icon
* Added the homepage
* The book now mentions spell failure chances

**1.1.0-beta**
* Tweaked file structure around to make more sense on the development front
* Changed "Dissolve" to be called "Cleanse" for easier understanding
* An amazing book reformatting thanks to NebSpacefarer
* Added a new section to the book titled "Magic Resources" which lists all the magic items in the mod
* Additional functionality for the book to allow for better mod compatibility with addons
* Fixed a crash when opening the Arcanus Novela when Patchouli is not installed
* Tweaked the tooltip for the book when Patchouli is not installed to a better lore
* Removed an unused lang key
* Changed Spell Failure Chance from 30% to 20%
* Updated Dependencies
* Removed NBTCrafting from being included in the jar

**1.0.0-alpha**
* 1.18.2 port
* Changed up the crafting system for spells to a ritual system
* Removed the ability to add multiple spells to one spell sheet
* Changed how Steadfast works (Now MobEffect based)
* Changed how Inward works (Now MobEffect based)
* Marked `SpellItem.java` and `Spell.java` as Deprecated since these will be changed up soon
* Added in a recipe data for people using the API to add spells to be able to add their own spells to the ritual