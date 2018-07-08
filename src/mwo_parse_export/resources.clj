(ns mwo-parse-export.resources
  (:require [clojure.data.json :as json]
            [clojure.string :as str]))

;; Reads and provides the resources to the program

(def RESOURCES "resources/")
(def WEAPONS (str RESOURCES "weapons.json"))
(def MODULES (str RESOURCES "modules.json"))
(def MECHS (str RESOURCES "mechs.json"))
(def OMNIPODS (str RESOURCES "omnipods.json"))
(def AMMO (str RESOURCES "ammo.json"))

(def weapons (load-json-resource WEAPONS))
(def modules (load-json-resource MODULES))
(def mechs (load-json-resource mechs))
(def omnipods (load-json-resource omnipods))
(def ammo (load-json-resource ammo))

(defn load-json-resource [resource]
  (-> resource slurp json/read-str))
