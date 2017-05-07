#!/bin/bash

ctags -R src/
vim `find src/main/resources/ -type f`
