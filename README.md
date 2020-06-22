# Crawly

## Development

To run integration tests start the mock website:

    $ lein fig

And in a separate shell:

    $ lein test :integration

Unit tests are just

    $ lein test

## Usage

Environment variables:

* `CRAWLY_REQUEST_LOG`: Path to list of requests file
* `CRAWLY_LOG`: Path to log file
## License

Distributed under the MIT License.
