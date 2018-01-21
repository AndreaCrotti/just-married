uberjar:
	lein uberjar

heroku:
	PORT=5000 java -cp target/uberjar/just-married.jar clojure.main -m just-married.api

heroku-clean: uberjar heroku

preview:
	git push -v --force preview HEAD:master

.PHONY = heroku
