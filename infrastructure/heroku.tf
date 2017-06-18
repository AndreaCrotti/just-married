provider "heroku" {
  email = "${var.email}"
  api_key = "${var.heroku_api_key}"
}

# Create a new Heroku app
resource "heroku_app" "default" {
  name   = "my-cool-app"
  region = "us"

  config_vars {
    FOOBAR = "baz"
  }

  buildpacks = [
    "heroku/go"
  ]
}
