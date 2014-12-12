(ns noredink.core
  (require [clojure.data.csv :as csv]))

; TODO: reading all into memory because the data is small but could be done lazily
(defn get-questions
  "return seq of questions"
  []
  (with-open
    [question-file (clojure.java.io/reader "questions.csv")]
    (rest (doall (csv/read-csv question-file)))))

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