(ns parse-sql.lex)

;; http://regexadvice.com/forums/thread/29185.aspx
(def REGEX
  #"(?:(['\"])(?:\\\\|\\\1|(?!\1).|\1\1)*\1|(?:(?<!\d)-)?\d+(?:\.\d+)?(?:[eE]-?\d+)?|\.\.|(?:\w+\.)*\w+|[<>=|]{2}|\S)")

(defn digit? [c]
  (re-find #"[0-9]" c))

(defn letter? [c]
  (re-find #"[a-zA-Z]" c))

(defn asymbol? [c]
  (re-find #"[\(\)\[\]!\.+-><=\?*]" c))

(defn astring? [c]
  (re-find #"[\"']" c))

(defn what-type [token]
  (let [c (subs token 0 1)]
    (cond
      (letter? c)  :word
      (digit? c)   :number
      (asymbol? c) :symbol
      (astring? c) :string
      :else (throw (IllegalArgumentException. (str "Don't know type of token:" token))))))

(defn typer [token]
  {:token token
   :type (what-type token)})

(defn lex [sql-str]
  (map typer (map first (re-seq REGEX sql-str))))

(comment
  (lex "select * from places where locality=\"los angeles\"")
)