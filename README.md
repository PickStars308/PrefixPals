# PrefixPals

A Fabric mod for Minecraft 1.21.11 that lets you add custom prefixes and suffixes to in-game chat messages. You can
easily edit and customize these chat pals with simple commands, making your server chat more lively and personalized.

# Usage

## Commands

### Set Prefix
Add a custom prefix to your chat messages:

```text
/pp prefix <text>
```

Example:
```text
/pp prefix <green>System.out.println(" <white>
```
![img.png](ModPreview/prefix.png)

### Set Suffix
Add a custom suffix to your chat messages:

```text
/pp suffix <text>
```

Example:
```text
/pp suffix <green> ");
```

![img.png](ModPreview/suffix.png)

### Clear Prefix and Suffix
Remove both prefix and suffix from your chat messages:

```text
/pp clear
```

### View Current Settings
Preview your current prefix and suffix settings:

```text
/pp info
```

### Help
Display all available commands:

```text
/pp help
```

# Effect
![img.png](ModPreview/effect.png)

# Example Usage

To create a colorful admin tag:
```text
/pp prefix <red><bold>[Admin] <reset>
```

To add a fun suffix:
```text
/pp suffix <gray> <italic><small>^_^
```

To use gradient colors:
```text
/pp prefix <gradient:red:yellow:green>Elite <reset>
```



## Formatting

PrefixPals supports MiniMessage formatting for colors and styles. Here are some common examples:

- Colors: `<red>`, `<green>`, `<blue>`, `<yellow>`, `<white>`, etc.
- Styles: `<bold>`, `<italic>`, `<underlined>`, `<strikethrough>`, `<obfuscated>`
- Gradients: `<gradient:red:blue>Text</gradient>` (supports multiple color stops)
- Combined: `<green><bold>Admin<reset>`

# Installation
1. Download the latest release from the [releases page](https://github.com/PickStars308/PrefixPals/releases).
2. Install Fabric Loader for Minecraft 1.21.11 if you haven't already.
3. Place the downloaded mod JAR file into your `mods` folder.
4. Launch Minecraft with the Fabric profile.

# Tips
- PrefixPals works alongside other chat-related mods.
- Your prefix and suffix settings are saved locally, so they persist between server restarts.
- Use `<reset>` to reset formatting to default in your messages.
- For best results, keep your prefixes and suffixes relatively short to avoid cluttering the chat.
