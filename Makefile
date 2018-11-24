uberjar:
	lein uberjar

heroku:
	PORT=5000 java -cp target/uberjar/just-married.jar clojure.main -m just-married.api

heroku-clean: uberjar heroku

marco-elisa:
	git push -v --force marco-elisa HEAD:master

preview:
	git push -v --force preview HEAD:master

sloc:
	cloc src/ test/

.PHONY = heroku
