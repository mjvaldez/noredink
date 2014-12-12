(ns noredink.core)

(defn get-questions [num-questions]
  "return seq of questions given number to return"
  num-questions)

; TODO: change to loop until ctrl-c
(defn -main
  "Get number of questions and return quiz."
  []
  (println "Enter number of questions to put in the quiz: ")
  (let [num-questions (read-string (read-line))]
    (if (and (number? num-questions) (> num-questions 0))
      (println "Here you go: \n" (get-questions num-questions))
      (println "Sorry, I need a positive integer."))))

