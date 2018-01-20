#!/bin/bash
[ "$1" = "" ]&&echo "\
htmlをmarkdownに変換するだけ
Usage: ./html2markdown.sh filename
"&&exit 1

filename="$1"
pandoc -i "${filename}" -o "${filename%html}md"

