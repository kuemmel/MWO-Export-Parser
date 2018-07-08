# This is heavily under construction

# mwo-parse-export
Export/Import Strings are a new feature in [MWO](https://mwomercs.com/). Mech builds can be shared among mech warriors via short strings containing builds. Before the comunity heavily relied on tools like [smurfy's mechlab](https://mwo.smurfy-net.de/mechlab) to build, share and try new loadouts.

This is a little summer vacation project and won't go anywhere. I hope someone with some experience in javascript sees this and  writes an extension. My goal is to create a mech from an export string and use that to create a JSON object with the required information for the [smurfy api](https://github.com/smurfy/mwo-api-sample)

# Explanation
## Export Strings in MWO
* The strings consist of the mech id (first 4-6 places, for example `Avb3000`) and
* a list of parts with armor and items `20p20q40|2@|2@|2@r20s20t20u20v20w2030:0`


* Each part consists of 2 digits for armor and IDs for the items, each with a `|` prefix
  * Digits are in base 64 from '0' to 'o' on the ASCII table (basically `x.charCodeAt(0) - "0".charCodeAt(0)`). The ids are also in "least significant digit first notation", so `>1` <=> `1>` = `1*64^1 + ">"*64^0` = `78`
  * ID's are also in base 64 and can be checked on the website. `2@` would be `2*1 + ("@".code - "0".cod)*64 = 2 + 1024`
* a character is used to map armor and ids to the given part (since character codes "a" - "o" are used for the base 65 encoding)
  * `p` Center Torso
  * `q` Right Torso
  * `r` Left Torso
  * `s` Left Arm
  * `t` Right Arm
  * `u` Left Leg
  * `v` Right Leg
  * `w` Head
  *  the rear armor is represented by a string of six places at the end representing right, left and center torso

## Example`s
* `90s` -> 9 armor in the left arm
* `01s` -> 63 armor in the left arm
* `>1p` -> 78 armor in the center torso
* `>1|2@r` -> 78 armor in the left torso with an LRM 5.
* `20w2030:0` -> 2 armor in the head, 2 armor in the right torso, 3 in the left and ten (`:0`) in the center torso 
## Some complete example strings
* `Ab300000p00q00r00s00t00u00v00w000000` Empty centurion CN9-AH 
* `Ab300000p00q00r10s00t00u00v00w000000` CN9-AH with 1 armor in left arm
* `Ab300000p00q00|2@r00s00t00u00v00w000000` CN9-AH with an LRM 5 launcher in the left torso
* `Ab300220p20q20|2@|2@r20s20t20u20v20w000000` CN9-AH with two lrm 5s an 2 armor in each part.
* `Af182000p00q81r00s00t00u00v00w000000` BLR-1G with 72 armor in the left torso

TODO How are omnipods represented?

# Bugs

...

# License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
