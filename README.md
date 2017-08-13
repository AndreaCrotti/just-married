# just-married

Website to collect pictures, share information about a wedding and so on and so forth.
Currently hard coded to my specific wedding, but could potentially be easily adaptable or templatable.

## DB schema

- invited (every person invited):
  + age
  + name
  + dietary requirements
  + lunch/dinner flags (or in the family this one?)

- family (collection of invited people, sharing contact details)
  + O2M: invited
  + contact person
  + phone
  + email address
  + should be notified flag?
  + requires accommodation?

- table of events, collecting all the interactions such as:
  + rvsp yes
  + rvsp no
  + email sent
  + sms sent
  + song suggested

  How do we know who did what? Need some kind of basic authentication to keep track properly.

## Pre production check list

- [ ] make sure the google maps key is restricted access [to avoid quota thefts](https://console.developers.google.com/apis/credentials/key/226?authuser=0&project=getting-married-1499546104310&pli=1) 

## Font choice

Nice possible fonts to use:

- https://fonts.google.com/specimen/Courgette
- https://fonts.google.com/specimen/Dancing+Script
- https://fonts.google.com/specimen/Abril+Fatface

## Graphics

Getting ideas from various places:

- https://www.behance.net/

## Upload feature

Could use [Picasa WEB developer API](https://developers.google.com/picasa-web/) 
For clojure see [clj-dropbox ](https://github.com/aria42/clj-dropbox) instead.

## License

Copyright © 2017 Andrea Crotti

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
