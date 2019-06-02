# Crawly

> An Angel who did not so much Fall as Saunter Vaguely Downwards


## Development

Start a repl using either boot or leiningen

    $ boot dev
    $ lein repl

Servers to test the scrapers against can be started and stopped

    crawly.dev=> (start-server! th/basic)
    crawly.dev=> (stop-server! th/basic)

## Usage

## Examples

### Wikipedia Bibliography Crawler

Setting file cache level to `:aggressive` makes crawly store all responses to the file system, and will perform a lookup in the cache prior to sending requests. This mainly helps in the development phase so you don't spam the servers too much testing the code.

    (crawly/set-cache-level! :aggressive)

## License

Distributed under the MIT License.
