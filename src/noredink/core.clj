(ns noredink.core
  (require [clojure.data.csv :as csv]))

(defn read-csv-file [filename cols]
  (with-open [file (clojure.java.io/reader filename :encoding "ISO-8859-1")]
    (map #(zipmap cols %) (doall (csv/read-csv file)))))

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

(defn get-usage
  "return seq of usage data"
  []
  (rest
    (read-csv-file
      "usage.csv"
      [:student_id :question_id
       :assigned_hours_ago :answered_hours_ago])))

(defn get-strand-ids
  [questions]
  (map :strand_id questions))

(defn standards-for-strand-id
  [questions strand-id]
  (map :strand_id (filter #(= strand-id (:strand_id %)))))

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