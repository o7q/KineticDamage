<img src="assets/icon/icon_shadow.png" width=40%>

# KineticDamage
A Minecraft mod that scales damage and knockback with kinetic energy.

# Configuration
```properties
# KineticDamage config

# Damage multipliers
# These values will be multiplied with the calculated damage values
damage-multiplier-vertical=1.0
damage-multiplier-horizontal=1.0
                    
# Specify the max amount of damage that can be done
# Negative numbers makes damage unlimited
damage-max-vertical=100
damage-max-horizontal=-1.0

# Knockback multipliers
# Scales the knockback with incoming damage velocity
knockback-multiplier-x=1.0
knockback-multiplier-y=1.0
knockback-multiplier-z=1.0
                    
# Action multipliers
# Scales damage and knockback depending on what the attacker's action is
action-sprinting-multiplier=2.0
action-swimming-multiplier=0.5
action-sneaking-multiplier=0.5
action-crawling-multiplier=0.25

# Debug
# Logs debug messages to the chat
debug-chat-log=false
```