(ns mwo-parse-export.convert
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as str]))

(defn base64-digit->base10 [digit]
  "Convert a single digit from base 64 (values \\0 to \\o on the ASCII table) to an integer from 0 - 64."
  (- (if (char? digit) (int digit) (int (first (char-array digit)))) (int \0)))

(defn base64->base10 [num]
  "Convert a base 64 encoded, least significant leading number to a most significant leading base 10 number."
  (let [BASE 64
        chars   (char-array num)
        indices (range 0 (count chars))
        convert-digit-and-zip (fn [digit range-digit]
                                (vector (base64-digit->base10 digit) range-digit))]
    (reduce (fn [number digit-with-place] (+ number
                                             (* (first digit-with-place)
                                                (math/expt BASE (second digit-with-place)))))
            0
            (map convert-digit-and-zip chars indices))))

(defn base10->baseN-digit
  ([base]
   "curried version that returns a converter to base <base>."
   (fn [x] (base10->baseN-digit x base)))
  ([num base]
  "convert a base 10 number (0-base) to a digit on the ascii table: (base10->baseN-digit 63 64) -> \\o"
  (if (< num base)
    (char (+ num (int \0)))
    (throw (IllegalArgumentException. "Number is greater than the base, can't be a single digit.")))))

(defn base10->baseN
  ([base]
   (fn [x] (base10->baseN x base)))
  ([num base]
   "Convert a base 10 value to base 64 (values \\0 to \\o)."
   (map (base10->baseN-digit base)
        ((fn [rest result-list]
           (if (< rest base)
             (conj result-list rest)
             (recur (/ rest base)
                    (conj result-list (mod rest base)))))
         num '[]))))

(defn base10->base64 [num]
  (str/join ((base10->baseN 64) num)))

