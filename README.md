<img src="assets/icon/icon_shadow.png" width=40%>

# KineticDamage
A Minecraft mod that scales damage and knockback with kinetic energy.\
*This mod is still in development.*

# Configuration
```properties
# KineticDamage config

# Damage multipliers
# These values will be multiplied with the calculated damage values
damage-multiplier-vertical=1.0
damage-multiplier-horizontal=1.0

# Specify the max amount of damage that can be done
# Negative numbers will uncap the damage
damage-max-vertical=100
damage-max-horizontal=-1.0

# Knockback multipliers
# Scales the knockback with attacker's incoming velocity
knockback-multiplier-x=1.0
knockback-multiplier-y=1.0
knockback-multiplier-z=1.0

# Action multipliers
# Scales damage and knockback depending on the attacker's action
action-sprinting-multiplier=1.0
action-swimming-multiplier=1.0
action-sneaking-multiplier=1.0
action-crawling-multiplier=1.0

# Use player fall distance for damage
# The farther the player falls the more damage they will do
# Also, this damage will be added on top of the damage already done by the player's velocity
player-use-fall-distance=true

# Use player head rotation vector for math
# This will use the player's head rotation instead of body velocity to calculate values
# If enabled, the server will always use the players head rotation vector to calculate knockback vectors instead of the velocity vector of the player itself
# In other words, the knockback will always occur in the direction the player is looking, this is not as realistic but it can be very fun.
player-use-head-rotation=true

# Debug
# Logs debug messages to the chat (per player)
debug-chat-log=false
```