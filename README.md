# This is heavily under construction and won't probably be finished

# mwo-parse-export
Export/Import Strings are a new feature in MWO. Mech builds can be saved and shared. A lot of people use resources like [smurfy's mechlab](https://mwo.smurfy-net.de/mechlab) to build and share their mechs, though. Since MWO now supports importing mechs, I thought it'd be great to see how the strings are created and how to parse them and maybe even write a library so that existing tools can also import from/export to MWO.

## Explanation
### Export Strings in MWO
* The strings consist of the mech id (first 4-6 places, for example `Avb3000`) and
* a list of parts with armor and items `20p20q40|2@|2@|2@r20s20t20u20v20w2030:0`


* Each part consists of 2 digits for armor and IDs for the items, each with a `|` prefix
  * Digits are in base 64 from '0' to 'o' on the ASCII table (basically `x.charCodeAt(0) - "0".charCodeAt(0)`). The ids are also in "least significant digit first notation", so `>1` <=> `1>` = `1*64^1 + >*64^0` = `78`
* a character is used to map armor and ids to the given part 
  * `p` Center Torso
  * `q` Right Torso
  * `r` Left Torso
  * `s` Left Arm
  * `t` Right Arm
  * `u` Left Leg
  * `v` Right Leg
  * `w` Head
  *  the rear armor is represented by a string of six places at the end of the string representing right, left and center torso



## Example`s
* `>1p` represents 78 armor in the center torso
* `>1|2@r` represents 78 armor in the left torso with an LRM 5.
* `20w2030:0` represents 2 armor in the head, 2 armor in the right torso, 3 in the left and ten (`:0`) in the center torso 
### Some complete example strings
* `Ab300000p00q00r00s00t00u00v00w000000` Empty centurion CN9-AH 
* `Ab300000p00q00r10s00t00u00v00w000000` CN9-AH with 1 armor in left arm
* `Ab300000p00q00|2@r00s00t00u00v00w000000` CN9-AH with an LRM 5 launcher in the left torso
* `Ab300220p20q20|2@|2@r20s20t20u20v20w000000` CN9-AH with two lrm 5s an 2 armor in each part.
* `Af182000p00q81r00s00t00u00v00w000000` BLR-1G with 72 armor in the left torso

TODO How are omnipods represented?

### Bugs

...

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
