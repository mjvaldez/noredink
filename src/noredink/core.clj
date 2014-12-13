(ns noredink.core
  (require [clojure.data.csv :as csv]))

(defn csv-fn [cols]
  (fn [row]
    (let [v (first (csv/read-csv row))]
      (zipmap cols v))))

(defn read-csv-file [filename cols]
  (with-open [file (clojure.java.io/reader filename :encoding "ISO-8859-1")]
    (map (csv-fn cols) (doall (line-seq file)))))

; TODO: reading all into memory because the data is small but could be done lazily
(defn get-questions
  "return seq of questions"
  []
  (rest
    (read-csv-file
      "questions.csv"
      [:strand_id :strand_name
       :standard_id :standard_name
       :question_id :difficulty])))

(defn make-quiz
  "return seq of questions given number to return"
  [num-questions]
  "foo")

; TODO: change to loop until ctrl-c
(defn -main
  "Get number of questions and return quiz."
  []
  (println "Enter number of questions to put in the quiz: ")
  (let [num-questions (read-string (read-line))]
    (if (and (number? num-questions) (> num-questions 0))
      (println "Here you go: \n" (make-quiz num-questions))
      (println "Sorry, I need a positive integer."))))