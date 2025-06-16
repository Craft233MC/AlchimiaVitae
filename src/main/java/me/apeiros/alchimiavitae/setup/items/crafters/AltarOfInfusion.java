package me.apeiros.alchimiavitae.setup.items.crafters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class AltarOfInfusion extends AbstractCrafter<Infusion> {
    private static int layer = 3;

    // Slot where the tool is placed
    private static final int TOOL_SLOT = 10;

    public AltarOfInfusion(ItemGroup ig, DivineAltar divineAltar) {
        super(ig, AlchimiaItems.ALTAR_OF_INFUSION, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR, new ItemStack[]{
                AlchimiaItems.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, AlchimiaItems.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, AlchimiaItems.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
        });

        // Add recipe to Divine Altar
        divineAltar.newRecipe(AlchimiaItems.ALTAR_OF_INFUSION, this.getRecipe());
    }

    // {{{ Set up effects
    @Override
    protected void newInstanceEffects(World w, Location l) {
        // End rod particles
        w.spawnParticle(Particle.END_ROD, l, 100, 0.5, 0.5, 0.5);
    }
    // }}}

    // {{{ Set up recipes
    // {{{ Add all recipes
    @Override
    protected void addDefaultRecipes() {
        // {{{ Prepare
        // Get plugin instance and config
        AlchimiaVitae instance = AlchimiaVitae.i();
        Configuration cfg = instance.getConfig();

        // Get config values
        boolean destructiveCritsEnabled = cfg.getBoolean("options.infusions.infusion-destructivecrits");
        boolean phantomCritsEnabled     = cfg.getBoolean("options.infusions.infusion-phantomcrits");
        boolean trueAimEnabled          = cfg.getBoolean("options.infusions.infusion-trueaim");
        boolean forcefulEnabled         = cfg.getBoolean("options.infusions.infusion-forceful");
        boolean volatileEnabled         = cfg.getBoolean("options.infusions.infusion-volatile");
        boolean healingEnabled          = cfg.getBoolean("options.infusions.infusion-healing");
        boolean autoReplantEnabled      = cfg.getBoolean("options.infusions.infusion-autoreplant");
        boolean totemStorageEnabled     = cfg.getBoolean("options.infusions.infusion-totemstorage");
        boolean knockbackEnabled        = cfg.getBoolean("options.infusions.infusion-knockback");

        // Create placeholder items
        CustomItemStack validMelee      = new CustomItemStack(Material.DIAMOND_SWORD, "&a&o一把斧头或剑, ",
            "&a&o可以是金、铁、钻石材质的。");
        CustomItemStack validRanged     = new CustomItemStack(Material.BOW, "&a&o一把弓或弩。");
        CustomItemStack validHoe        = new CustomItemStack(Material.DIAMOND_HOE, "&a&o一把锄头，",
            "&a&o可以是金、铁、钻石材质的。");
        CustomItemStack validChestplate = new CustomItemStack(Material.DIAMOND_CHESTPLATE, "&a&o一件胸甲，",
            "&a&o可以是金、铁、钻石、下界合金材质的。");
        CustomItemStack validFishingRod = new CustomItemStack(Material.FISHING_ROD, "&a&o一把钓竿。");

        // Get ItemGroup and RecipeType
        ItemGroup ig = AlchimiaUtils.ItemGroups.INFUSIONS;
        RecipeType rt = AlchimiaUtils.RecipeTypes.INFUSION_ALTAR;
        // }}}

        // {{{ Melee weapons
        if (destructiveCritsEnabled) {
            this.newRecipe(ig, rt,
                // Out
                Infusion.DESTRUCTIVE_CRITS,

                // In
                new ItemStack[] {
                        new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                        AlchimiaItems.DARKSTEEL, validMelee, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                        new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
                }
            );
        }

        if (phantomCritsEnabled) {
            this.newRecipe(ig, rt,
                Infusion.PHANTOM_CRITS,

                new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                    AlchimiaItems.DARKSTEEL, validMelee, SlimefunItems.HARDENED_GLASS,
                    new ItemStack(Material.PHANTOM_MEMBRANE), AlchimiaItems.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
                }
            );
        }
        // }}}

        // {{{ Ranged weapons
        if (forcefulEnabled) {
            this.newRecipe(ig, rt,
                Infusion.FORCEFUL,

                new ItemStack[] {
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                    SlimefunItems.INFUSED_MAGNET, validRanged, SlimefunItems.STEEL_THRUSTER,
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
                }
            );
        }

        if (healingEnabled) {
            this.newRecipe(ig, rt,
                Infusion.HEALING,

                new ItemStack[] {
                    AlchimiaItems.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                    AlchimiaItems.ILLUMIUM, validRanged, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
                }
            );
        }

        if (trueAimEnabled) {
            this.newRecipe(ig, rt,
                Infusion.TRUE_AIM,

                new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                    AlchimiaItems.DARKSTEEL, validRanged, AlchimiaItems.EXP_CRYSTAL,
                    new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.STEEL_THRUSTER
                }
            );
        }

        if (volatileEnabled) {
            this.newRecipe(ig, rt,
                Infusion.VOLATILITY,

                new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                    AlchimiaItems.DARKSTEEL, validRanged, SlimefunItems.LAVA_GENERATOR_2,
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
                }
            );
        }
        // }}}

        // {{{ Chestplate
        if (totemStorageEnabled) {
            this.newRecipe(ig, rt,
                Infusion.TOTEM_BATTERY,

                new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL, AlchimiaItems.CONDENSED_SOUL, AlchimiaItems.BENEVOLENT_BREW,
                    AlchimiaItems.ILLUMIUM, validChestplate, AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
                }
            );
        }
        // }}}

        // {{{ Fishing rod
        if (knockbackEnabled) {
            this.newRecipe(ig, rt,
                Infusion.KNOCKBACK,

                new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND, new ItemStack(Material.STICKY_PISTON), AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.GRANDPAS_WALKING_STICK, validFishingRod, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK, SlimefunItems.TALISMAN_WHIRLWIND
                }
            );
        }
        // }}}

        // {{{ Hoe
        if (autoReplantEnabled) {
            this.newRecipe(ig, rt,
                Infusion.AUTO_REPLANT,

                new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), AlchimiaItems.LIGHT_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                    AlchimiaItems.ILLUMIUM, validHoe, SlimefunItems.FLUID_PUMP,
                    new ItemStack(Material.BONE_BLOCK), AlchimiaItems.LIGHT_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
                }
            );
        }
        // }}}
    }
    // }}}

    // {{{ Add a new recipe
    @Override
    public void newRecipe(@Nonnull ItemGroup ig, @Nonnull RecipeType rt, @Nonnull Infusion output, @Nonnull ItemStack... input) {
        ItemStack[] newInput = new ItemStack[8];

        // Add outer items (items excluding middle slot) to new recipe
        int i = 0;
        int newI = 0;
        for (ItemStack stack : input) {
            // 0 1 2
            // 3 4 5
            // 6 7 8
            // Exclude middle item
            if (i != 4) {
                newInput[newI] = stack;
                newI++;
            }

            i++;
        }

        // Add the recipe to the map
        this.recipes.put(output, newInput);

        // Register guide item
        new SlimefunItem(ig, output.guideItem(), rt, input).register(AlchimiaVitae.i());
    }
    // }}}
    // }}}

    // {{{ Begin crafting
    @Override
    protected void craft(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull Player p) {
        // {{{ Getting input
        ItemStack[] input = new ItemStack[8];

        int index = 0;
        for (int i : IN_SLOTS) {
            if (i != TOOL_SLOT) {
                input[index] = menu.getItemInSlot(i);
                index++;
            }
        }

        // Get expected infusion
        Infusion infusion = this.recipes.get(input);

        // Make sure the recipe is valid
        if (infusion == null) {
            p.sendMessage(AlchimiaUtils.format("<red>无效的注入配方！"));
            return;
        }
        // }}}

        // {{{ Checks
        // Get the tool
        ItemStack tool = menu.getItemInSlot(TOOL_SLOT);
        ItemMeta meta = tool.getItemMeta();

        // Make sure there is a tool
        if (tool == null || meta == null || tool.getType().equals(Material.AIR)) {
            p.sendMessage(AlchimiaUtils.format("<red>没有物品可以注入！"));
            return;
        }

        // Make sure the tool is valid
        if (!Infusion.ANY.canApply(tool)) {
            p.sendMessage(AlchimiaUtils.format("<red>你不能注入该物品！"));
            return;
        }

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Make sure the tool is not already infused
        if (Infusion.ANY.has(pdc)) {
            p.sendMessage(AlchimiaUtils.format("<red>该物品已有注入！"));
            return;
        }

        // Make sure the infusion is applicable to the tool
        if (!infusion.canApply(tool)) {
            p.sendMessage(AlchimiaUtils.format("<red>你不能向物品应用该注入！"));
            return;
        }
        // }}}

        // {{{ Infuse
        infusion.apply(pdc);

        // Add lore
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

        lore.add("");
        lore.add(AlchimiaUtils.format("<gray>注入："));

        // Infusion name to lore
        lore.add(AlchimiaUtils.format("<dark_gray>› " + infusion.lore()));

        // Set lore and meta
        meta.setLore(lore);
        tool.setItemMeta(meta);
        // }}}

        // Finish crafting
        this.finish(b.getWorld(), b.getLocation().add(0.5, 0.5, 0.5), menu, infusion);
    }
    // }}}

    // {{{ Finish crafting
    @Override
    protected void finish(World w, Location l, BlockMenu menu, Infusion infusion) {
        // Get item
        ItemStack tool = menu.getItemInSlot(TOOL_SLOT).clone();

        // Consume items
        for (int slot : IN_SLOTS) {
            menu.consumeItem(slot, 1);
        }

        // Schedule task
        AlchimiaVitae.getScheduler().runAtLocationTimer(l, wrappedTask -> {
            if (layer == 3) {
                // Pre-craft
                w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1, 1);
                w.playSound(l, Sound.BLOCK_BEACON_POWER_SELECT, 1.5F, 1);
                w.spawnParticle(Particle.FLASH, l, 2, 0.1, 0.1, 0.1);

                // Decrease layer
                layer--;
            } else if (layer == 2) {
                // Pre-craft
                w.playSound(l, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
                w.playSound(l, Sound.BLOCK_CONDUIT_ATTACK_TARGET, 0.5F, 1);
                w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                w.playSound(l, Sound.ITEM_TOTEM_USE, 0.1F, 1);
                w.playSound(l, Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
                w.playSound(l, Sound.BLOCK_LODESTONE_PLACE, 1.5F, 1);
                w.spawnParticle(Particle.FLASH, l, 2, 0.1, 0.1, 0.1);

                // Decrease layer
                layer--;
            } else if (layer == 1) {
                // Pre-craft
                w.playSound(l, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                w.playSound(l, Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1.5F, 1);
                w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                w.playSound(l, Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
                w.playSound(l, Sound.ITEM_TOTEM_USE, 0.3F, 1);
                w.spawnParticle(Particle.FLASH, l, 2, 0.1, 0.1, 0.1);

                // Decrease layer
                layer--;
            } else {
                // Output the item
                if (menu.fits(tool, OUT_SLOTS)) {
                    menu.pushItem(tool, OUT_SLOTS);
                } else {
                    // Drop if it doesn't fit
                    w.dropItemNaturally(l.add(0, 0.5, 0), tool);
                }

                // Post-craft
                w.strikeLightningEffect(l.add(0, 0.5, 0));
                w.playSound(l, Sound.ITEM_TRIDENT_THUNDER, 0.5F, 1);
                w.playSound(l, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                w.playSound(l, Sound.ITEM_TOTEM_USE, 0.5F, 1);
                w.spawnParticle(Particle.END_ROD, l, 5, 0, 8, 0);
                w.spawnParticle(Particle.PORTAL, l, 300, 2, 2, 2);

                // Cancel runnable
                wrappedTask.cancel();
            }
        }, 1, 30);
    }
    // }}}

    // {{{ Infusions
    public enum Infusion {

        // {{{ Enum values
        // {{{ Melee weapons
        DESTRUCTIVE_CRITS(
                "infusion_destructivecrits",
                "<red><bold>毁灭暴击",

                new SlimefunItemStack("AV_DESTRUCTIVE_CRITS_INFUSION", Material.TNT, "&c&l毁灭暴击",
                        "&4暴击时有小概率给你的对手施加",
                        "&4负面效果，并对护甲造成额外伤害。")),

        PHANTOM_CRITS (
                "infusion_phantomcrits",
                "<aqua>幻影暴击",

                new SlimefunItemStack("AV_PHANTOM_CRITS_INFUSION", Material.PHANTOM_MEMBRANE, "&b幻影暴击",
                        "&7暴击时有小概率造成无视护甲的额外伤害。")),
        // }}}

        // {{{ Ranged weapons
        FORCEFUL(
                "infusion_forceful",
                "<dark_green>强力",

                new SlimefunItemStack("AV_FORCEFUL_INFUSION", Material.PISTON, "&2强力",
                        "&2可以射出更远且伤害更高的箭。")),

        HEALING(
                "infusion_healing",
                "<red>治疗",

                new SlimefunItemStack("AV_HEALING_INFUSION", Material.REDSTONE, "&c治疗",
                        "&c命中目标时将进行治疗。")),

        TRUE_AIM(
                "infusion_trueaim",
                "<light_purple>真正的瞄准",

                new SlimefunItemStack("AV_TRUE_AIM_INFUSION", Material.SHULKER_SHELL, "&d真正的瞄准",
                        "&5可以射出不受重力影响的箭。")),

        VOLATILITY(
                "infusion_volatile",
                "<dark_red><bold>挥发",

                new SlimefunItemStack("AV_VOLATILE_INFUSION", Material.FIRE_CHARGE, "&4&l挥发",
                        "&c可以发射火球。")),
        // }}}

        // {{{ Chestplate
        TOTEM_BATTERY(
                "infusion_totemstorage",
                "<gold><bold>图腾电池",

                new SlimefunItemStack("AV_TOTEM_BATTERY_INFUSION", Material.TOTEM_OF_UNDYING, "&6&l图腾电池",
                        "&6最多可以存储8个不死图腾。",
                        "&e手持不死图腾并&7&lShift+右键点击&e",
                        "&e以存入。")),
        // }}}

        // {{{ Fishing rod
        KNOCKBACK(
                "infusion_knockback",
                "<green>击退",

                new SlimefunItemStack("AV_KNOCKBACK_INFUSION", Material.SLIME_BALL, "&a击退",
                        "&a将目标击退而不是拉近。")),
        // }}}

        // {{{ Hoe
        AUTO_REPLANT(
                "infusion_autoreplant",
                "<green>自动补种",

                new SlimefunItemStack("AV_AUTO_REPLANT_INFUSION", Material.WHEAT, "&a自动补种",
                        "&2收获作物时会自动补种。")),
        // }}}

        // Dummy value for checking if an item is infusable in general
        ANY("infusion_dummy_any", "", null);
        // }}}

        // {{{ Fields & constructors
        private final NamespacedKey key;
        private final String lore;
        private final SlimefunItemStack guideItem;

        Infusion(String key, String lore, SlimefunItemStack guideItem) {
            this.key = AbstractAddon.createKey(key);
            this.lore = lore;
            this.guideItem = guideItem;
        }

        // Get the key
        public NamespacedKey key() {
            return this.key;
        }

        // Get the lore
        public String lore() {
            return this.lore;
        }

        // Get the guide item
        public SlimefunItemStack guideItem() {
            return this.guideItem;
        }
        // }}}

        // {{{ Check if an infusion can be applied to a tool
        public boolean canApply(@Nonnull ItemStack tool) {
            Material mat = tool.getType();

            return switch (mat) {
                // Melee weapons
                case GOLDEN_AXE,
                     IRON_AXE,
                     DIAMOND_AXE,
                     NETHERITE_AXE,
                     GOLDEN_SWORD,
                     IRON_SWORD,
                     DIAMOND_SWORD,
                     NETHERITE_SWORD -> AlchimiaUtils.equalsAny(this, ANY, DESTRUCTIVE_CRITS, PHANTOM_CRITS);

                // Ranged weapons
                case BOW, CROSSBOW -> AlchimiaUtils.equalsAny(this, ANY, FORCEFUL, HEALING, TRUE_AIM, VOLATILITY);

                // Chestplates
                case GOLDEN_CHESTPLATE,
                     IRON_CHESTPLATE,
                     DIAMOND_CHESTPLATE,
                     NETHERITE_CHESTPLATE -> AlchimiaUtils.equalsAny(this, ANY, TOTEM_BATTERY);

                // Fishing rod
                case FISHING_ROD -> AlchimiaUtils.equalsAny(this, ANY, KNOCKBACK);

                // Hoes
                case GOLDEN_HOE,
                     IRON_HOE,
                     DIAMOND_HOE,
                     NETHERITE_HOE -> AlchimiaUtils.equalsAny(this, ANY, AUTO_REPLANT);

                default -> false;
            };
        }
        // }}}

        // {{{ Check and apply infusions
        public boolean has(@Nonnull PersistentDataContainer pdc) {
            if (this == TOTEM_BATTERY) {
                if (pdc.has(this.key(), PersistentDataType.INTEGER)) return true;
            } else {
                if (pdc.has(this.key(), PersistentDataType.BYTE)) return true;
            }

            return false;
        }

        public void apply(@Nonnull PersistentDataContainer pdc) {
            if (this == TOTEM_BATTERY) {
                pdc.set(this.key(), PersistentDataType.INTEGER, 0);
            } else {
                pdc.set(this.key(), PersistentDataType.BYTE, (byte) 1);
            }
        }
        // }}}

        // {{{ Battery of Totems-specific methods
        // Get the number of totems stored
        public int getTotems(@Nonnull PersistentDataContainer pdc) {
            // Make sure the infusion is the Battery of Totems
            if (this != TOTEM_BATTERY)
                return -1;

            return pdc.get(this.key(), PersistentDataType.INTEGER);
        }

        // Set the number of totems stored
        public void setTotems(@Nonnull PersistentDataContainer pdc, int n) {
            // Make sure the infusion is the Battery of Totems
            if (this != TOTEM_BATTERY)
                return;

            pdc.set(this.key(), PersistentDataType.INTEGER, n);
        }
        // }}}

    }
    // }}}

}

