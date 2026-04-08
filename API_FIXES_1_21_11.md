# Minecraft/NeoForge 1.21.11 API Changes - Fix Guide

Based on the official NeoForge 1.21.11 porting primer and API documentation.

## 1. EventBusSubscriber Annotation

### Current (Incorrect) Syntax:
```java
@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.MOD
)
public class SwitchZKeybinds { ... }
```

### ✅ Correct Syntax for 1.21.11:
```java
@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.MOD
)
public class SwitchZKeybinds { ... }
```

**Status**: The `bus` attribute is **still valid** in 1.21.11. Your current syntax is CORRECT.

**Note**: The default `bus` is `EventBusSubscriber.Bus.FORGE`, but using `Bus.MOD` for mod-specific events is correct.

---

## 2. KeyMapping Constructor - Category Parameter

### Current (Incorrect) Usage:
```java
public static final KeyMapping OPEN_UI = new KeyMapping(
    "key.switchz.open_ui",
    GLFW.GLFW_KEY_F9,
    "key.categories.switchz"  // ❌ This is WRONG - should be KeyMapping.Category
);
```

### ✅ Correct Usage for 1.21.11:
```java
public static final KeyMapping OPEN_UI = new KeyMapping(
    "key.switchz.open_ui",
    GLFW.GLFW_KEY_F9,
    KeyMapping.Category.GAMEPLAY  // ✅ Use KeyMapping.Category enum
);
```

### Available KeyMapping.Category Values:
- `KeyMapping.Category.MOVEMENT` - Movement keys
- `KeyMapping.Category.LOOK` - Camera/look controls
- `KeyMapping.Category.GAMEPLAY` - General gameplay (most common choice)
- `KeyMapping.Category.INVENTORY` - Inventory management
- `KeyMapping.Category.CREATIVE` - Creative mode only
- `KeyMapping.Category.MULTIPLAYER` - Multiplayer features
- `KeyMapping.Category.UI` - UI/Menu controls
- `KeyMapping.Category.DEBUG` - Debug overlays (F3+keys)

---

## 3. User Class - Type Enum Changes

### Current (Incorrect) Code:
```java
User newUser = new User(
    newName,
    uuid,
    oldUser.getAccessToken(),
    oldUser.getXuid(),
    oldUser.getClientId(),
    User.Type.LEGACY  // ❌ REMOVED in 1.21.11
);
```

### ✅ Correct Code for 1.21.11:

For **offline mode** (non-authenticated players):
```java
User newUser = new User(
    newName,
    uuid,
    oldUser.getAccessToken(),
    oldUser.getXuid(),
    oldUser.getClientId(),
    User.Type.OFFLINE  // ✅ NEW - Use OFFLINE for offline accounts
);
```

### User.Type Enum Values in 1.21.11:
- `User.Type.OFFLINE` - Local/offline player (replaces LEGACY)
- `User.Type.LEGACY` - ❌ REMOVED
- `User.Type.MOJANG` - Deprecated, don't use
- `User.Type.MSA` - Microsoft Account (current authentication)

**Migration Note**: Replace all `User.Type.LEGACY` with `User.Type.OFFLINE`

---

## 4. Minecraft.disconnect() Method Signature

### Current (May Be Incorrect):
```java
if (minecraft.level != null) {
    minecraft.disconnect();  // ❌ Wrong in 1.21.11
}
```

### ✅ Correct Signature for 1.21.11:

```java
// Simple version - if you have a Screen to show (recommended)
if (minecraft.level != null) {
    minecraft.disconnect(new TitleScreen(), false);
}

// Or with explicit parameters
if (minecraft.level != null) {
    Screen screenToShow = new TitleScreen();
    boolean stopSoundEngine = false;
    minecraft.disconnect(screenToShow, stopSoundEngine);
}
```

### Method Signature:
```java
public void disconnect(
    @Nullable Screen screen,  // The screen to display after disconnect
    boolean stopSoundEngine   // Whether to stop the sound engine
)
```

### Parameter Details:
- **screen**: The screen to transition to (null = stay at current screen, pass TitleScreen() to show title screen)
- **stopSoundEngine**: Usually `false` (only set to `true` if integrating with resource pack switching)

---

## 5. Additional 1.21.11 API Changes (Likely Relevant)

### ClientTickEvent
The `ClientTickEvent` API is **unchanged** from 1.21.10:
```java
@SubscribeEvent
public static void onClientTick(ClientTickEvent.Post event) {
    // Still correct - no changes needed
}
```

### KeyMapping Registration
The `RegisterKeyMappingsEvent` API is **unchanged**:
```java
@SubscribeEvent
public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
    event.register(OPEN_UI);  // Still correct
}
```

### Screen/Gui Changes
- `Screen` constructors have changed slightly but typical usage unchanged
- `EditBox` (previously `TextFieldWidget`) API is stable
- `Button` API is stable

### Notable Changes in Related APIs:
- `ResourceLocation` → `Identifier` (rename only, no signature changes)
- `Minecraft#disconnect()` **now requires parameters** (changes from optional)
- `User.Type.LEGACY` → `User.Type.OFFLINE` (enum value rename)

---

## Summary of Required Fixes

### Your SwitchZKeybinds.java:
✅ **No changes needed** - Already correct!

### Your SwitchZClientEvents.java:
✅ **No changes needed** - Already correct!

### Your SwitchZClientActions.java:
❌ **TWO CHANGES REQUIRED**:

1. Change `User.Type.LEGACY` → `User.Type.OFFLINE`
2. Change `minecraft.disconnect()` → `minecraft.disconnect(new TitleScreen(), false)`

### Your SwitchZScreen.java:
✅ **No changes needed** - Already correct!

---

## Complete Fixed Code Example

```java
public static void switchOfflineAccount(String newName) {
    try {
        Minecraft minecraft = Minecraft.getInstance();
        User oldUser = minecraft.getUser();

        UUID uuid = UUID.nameUUIDFromBytes(
            ("OfflinePlayer:" + newName).getBytes(StandardCharsets.UTF_8)
        );

        User newUser = new User(
            newName,
            uuid,
            oldUser.getAccessToken(),
            oldUser.getXuid(),
            oldUser.getClientId(),
            User.Type.OFFLINE  // ✅ FIXED: was User.Type.LEGACY
        );

        Field userField = Minecraft.class.getDeclaredField("user");
        userField.setAccessible(true);
        userField.set(minecraft, newUser);

        if (minecraft.level != null) {
            minecraft.disconnect(new TitleScreen(), false);  // ✅ FIXED: was minecraft.disconnect()
        }

        minecraft.setScreen(new TitleScreen());
        SwitchZ.LOGGER.info("Switched offline account to {}", newName);
    } catch (ReflectiveOperationException exception) {
        SwitchZ.LOGGER.error("Failed to switch account", exception);
    }
}
```

---

## References
- Official NeoForge 1.21.11 Porting Primer
- Minecraft 1.21.11 API Documentation
- NeoForge 21.11.42 for Minecraft 1.21.11
