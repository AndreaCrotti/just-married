# just-married

Website to collect pictures, share information about a wedding and so on and so forth.
Currently hard coded to my specific wedding, but could potentially be easily adaptable or templatable.

## TODO

### Design and features

- [ ] let tests also run on travis, mocking out the HTTP calls for example
- [ ] serve the website behind HTTPS (Heroku's one or another), this actually costs 20$ a month
      with Heroku, even if the certificate itself is free.

- [ ] register a nice DNS name (on [namecheap](https://www.namecheap.com/) or godaddy), possible examples could be:
  - andreaenrica.life
  - andreaenrica.wedding (more expensive)

- [ ] add section about how to get there
- [ ] handle different ways to do the payment, where the "preferrable" might not be stripe since it's the only choice with a commission fee attached to it
  - [ ] postepay coordinates (EUR only)
  - [ ] bank coordinates (EUR/GDP choice)
  - [ ] stripe integration (EUR/GDP choice)

- [ ] add ways to upload images using various integrations: dropbox/google drive/else?
- [ ] add a link to the amazon wish list, which should not be entirely public
      but should still allow to see something
- [ ] add a countdown feature
- [ ] add a list of suggested hotels people can use, with different ranges of prices
- [x] add simple support to [https://gist.github.com/andrewsuzuki/fd9edda14296fd03483b0dbe40ee3a99](google analytics)
- [ ] do English and Italian version of the content (changing currency based on the language as well potentially?)
- [ ] select a nice font from https://fonts.google.com
- [ ] integrate a google maps view of the address
      see: https://github.com/Day8/re-frame/blob/master/docs/Using-Stateful-JS-Components.md
      to do it more correctly
- [ ] add a way to suggest songs to play with https://developer.spotify.com/web-api/code-examples/
- [ ] would be good to find a way to authenticate users, which could be as light as possible:
      An option would be the magic link like Monzo does, just enter your email and authenticate with a magic link
- [ ] package everything in something that can be pushed to the google app store

### Tech details

- [ ] add "fork me on github" to the web page somewhere?? (maybe not the usual visible on top)
- [ ] evaluate twillio integration for more interesting way to interact,
  Works very well to send SMS, but unfortunately can't buy an italian phone number
  so all messages would appear to come from an English phone.

- [ ] add sentry support
  - [x] backend
  - [ ] frontend

- [x] add google analytics to get insight on the usage of the page
- [x] add newrelic support (if free)
- [x] set up logentries to get even more insights
- [ ] register right DNS name and link it with Heroku
- [ ] use https://github.com/kristoferjoseph/flexboxgrid for the alignment
- [ ] check how to get good results on Google, matching all the possible various combinations of names

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
