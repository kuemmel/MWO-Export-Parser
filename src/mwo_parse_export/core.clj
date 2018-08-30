(ns mwo-parse-export.core
  (:gen-class)
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as str]
            [mwo-parse-export.convert :as convert]))

;; TODO parser into own file
;; TODO threading macros (->)
;; TODO JSON structure of
(def ITEM_SEPARATOR \|)
(def PARTS :parts)
(def ARMOR :armor)
(def ITEMS :items)

(def CENTER_TORSO {:place 1 :character \p :short-name "ct" :name "Center Torso"})

(def RIGHT_TORSO {:place 2 :character \q :short-name "rt" :name "Right Torso"})
(def LEFT_TORSO {:place 3 :character \r :short-name "lt" :name "Left Torso"})

(def LEFT_ARM {:place 4 :character \s :short-name "la" :name "Left Arm"})
(def RIGHT_ARM {:place 5 :character \t :short-name "ra" :name "Right Arm"})

(def LEFT_LEG {:place 6 :character \u :short-name "ll" :name "Left Leg"})
(def RIGHT_LEG {:place 7 :character \v :short-name "rl" :name "Right Leg"})

(def HEAD {:place 8 :character \w :short-name "h" :name "Head"})

(def REAR_RIGHT_TORSO {:place 9 :character nil :short-name "rrt" :name "Rear Right Torso"}) ; third place after head
(def REAR_LEFT_TORSO {:place 10 :character nil :short-name "rlt" :name "Rear Left Torso"}) ; second place after head
(def REAR_CENTER_TORSO {:place 11 :character nil :short-name "rct" :name "Rear Center Torso"}) ;last place after head

(def PARTS
  [CENTER_TORSO RIGHT_TORSO LEFT_TORSO LEFT_ARM RIGHT_ARM LEFT_LEG RIGHT_LEG HEAD REAR_CENTER_TORSO REAR_LEFT_TORSO REAR_RIGHT_TORSO])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn get-part-of [part-character]
  (first (filter (fn [part] (= part-character (:character part))) PARTS)))

(defn get-items [item-string]
  (let [item-strings (str/split item-string #"\|")]))

(defn part-string->part [part-string]
  (let [chars (char-array part-string)
        part-character (last chars)
        item-string (subs part-string 2 (- (count part-string) 1))
        armor-value (convert/base64->base10 (first item-string))
        items (get-items (rest item-string))
        part (get-part-of part-character)]
    (assoc part ARMOR armor-value ITEMS items)))

(defn get-rear-armor [rear-string]
  (let [armor-values (map (fn [x] (convert/base64->base10 x)) (re-seq #".{2}" rear-string))
        rrt (assoc REAR_CENTER_TORSO ARMOR (first armor-values))
        rlt (assoc REAR_LEFT_TORSO ARMOR (second armor-values))
        rct (assoc REAR_CENTER_TORSO ARMOR (last armor-values))]
   (list rrt rlt rct)))

;; test data max armor  Af1828X1p41q41rh0sh0tb0ub0v:0w404040
(defn parse-string [mech-string]
  (if (> (count mech-string) 30)
    (let [mech-id (take 6 mech-string)
          part-string (subs mech-string 6)
          part-strings (str/split part-string #"(?<=[p-w])")
          front-parts (map part-string->part (drop-last part-strings))
          rear-parts (get-rear-armor (last part-strings))]
      (assoc {:mech mech-id} PARTS (concat front-parts rear-parts)))
  (throw (IllegalArgumentException. "A minimal string is at least 30 characters long."))))
