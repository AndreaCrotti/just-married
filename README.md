# just-married

Website to collect pictures, share information about a wedding and so on and so forth.

## Usage

## TODO

- [ ] let tests also run on travis, mocking out the HTTP calls for example
- [ ] serve the website behind HTTPS (Heroku's one or another)
- [ ] register a nice DNS name
- [ ] handle different ways to do the payment, where the "preferrable" might not be stripe since it's the only choice with a commission fee attached to it
  - [ ] postepay coordinates (EUR only)
  - [ ] bank coordinates (EUR/GDP choice)
  - [ ] stripe integration (EUR/GDP choice)

- [ ] add ways to upload images using various integrations: dropbox/google drive/else?
- [ ] add a link to the amazon wish list, which should not be entirely public
      but should still allow to see something
- [ ] add "fork me on github" to the web page somewhere??
- [ ] evaluate the fully static page hosted page?
      (might be easier but not as much fun potentially)

## Upload feature

Could use [Picasa WEB developer API](https://developers.google.com/picasa-web/) 
For clojure see [clj-dropbox ](https://github.com/aria42/clj-dropbox) instead.

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
