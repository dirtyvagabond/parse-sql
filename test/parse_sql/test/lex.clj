(ns parse-sql.test.lex
  (:use [parse-sql.lex])
  (:use [clojure.test]))


(deftest test-lex
  (let [lexed (lex "select * from places where locality=\"los angeles\"")
        tokens (map :token lexed)]
    (is (= (count lexed) 8))
    (is (= tokens ["select" "*" "from" "places" "where" "locality" "=" "\"los angeles\""]))))
