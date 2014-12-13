(ns noredink.core
  (require [clojure.data.csv :as csv]))

; TODO: reading all into memory because the data is small but could be done lazily
; TODO: move csv stuff to separate namespace
; TODO: convert numeric strings to numbers
(defn read-csv-file [filename cols]
  "Return map with keys specified in cols. Assumes number of cols and fields match."
  (with-open [file (clojure.java.io/reader filename :encoding "ISO-8859-1")]
    (map #(zipmap cols %) (doall (csv/read-csv file)))))

(defn get-questions
  "return seq of questions"
  []
  (rest
    (read-csv-file "questions.csv"
      [:strand_id
       :strand_name
       :standard_id
       :standard_name
       :question_id
       :difficulty])))

(defn get-usage
  "return seq of usage data"
  []
  (rest
    (read-csv-file "usage.csv"
      [:student_id
       :question_id
       :assigned_hours_ago
       :answered_hours_ago])))


; Presumably in production we'd have a similarly indexed tables
(defn questions-by-strand-and-standard
  [questions]
  (group-by #(vector (:strand_id %) (:standard_id %)) questions))

(defn get-strands
  "Return vector of strands ids from the question data."
  [questions]
  (into [] (set (map :strand_id questions))))

(defn get-standards-for-strand
  "Return vector of standard ids for the given strand-id."
  [questions strand-id]
  (into [] (set (map :standard_id (filter #(= strand-id (:strand_id %)) questions)))))

(defn standards-by-strand
  "Return map of strands to standards."
  [questions]
  (let [strands (get-strands questions)
        standards (map #(get-standards-for-strand questions %) strands)]
    (zipmap strands standards)))


(defn make-quiz
  "return seq of questions given number to return"
  [num-questions]
  num-questions)

; TODO: change to loop until ctrl-c
(defn -main
  "Get number of questions and return quiz."
  []
  (println "Enter number of questions to put in the quiz: ")
  (let [num-questions (read-string (read-line))]
    (if (and (number? num-questions) (> num-questions 0))
      (println "Here you go: \n" (make-quiz num-questions))
      (println "Sorry, I need a positive integer."))))