;; WARNING
;; The profiles.clj file is used for local environment variables, such as database credentials.
;; This file is listed in .gitignore and will be excluded from version control by Git.

{:profiles/dev  {:env {:database-url "mongodb://127.0.0.1/emmerce_dev"}}
 :profiles/test {:env {:database-url "mongodb://127.0.0.1/emmerce_test"}}}
