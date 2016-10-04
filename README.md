Pascal parser, good enough for TeX sources.

# Overview

This Pascal parser is written to pretty-print TeX original Pascal
sources for studying its code without relying on Web2C – which proves
surprisingly hard to use.

This parser uses [SableCC](http://www.sablecc.org/) as the parser
generator, as part of a Maven project.  The SableCC Maven plugin is
the version I updated last year to use SableCC 3.7,
cf. https://github.com/tychobrailleur/sablecc-maven-plugin to install.

# How to use

## Build


    $ mvn package

During development, it may be convenient to skip the tests:

    $ mvn clean package -Dmaven.test.skip=true

## Usage

    java -jar target/pascal_parser-0.0.1-SNAPSHOT.jar /path/to/tex.p

To debug, you can also use the `--ast` option to dump the AST:

    java -jar target/pascal_parser-0.0.1-SNAPSHOT.jar src/test/resources/test5.pas  --ast

# Known Limitations

- This is in no way an exhaustive parser for the Pascal language.
- Keywords are case-insensitive in Pascal, which this parser doesn't
  support.  Keywords in TeX sources are lowercase, so this parser only
  accept lowercase.

# License

GPLv3. (c) 2016 Sébastien Le Callonnec.
