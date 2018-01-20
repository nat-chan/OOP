#!/bin/bash
[ "$1" = "" ]&&echo "\
manabaで未公開の課題を含めた最新の課題を取ってくるスクリプト
Usage: ./pull_latest_kadai.sh s16xxxxx
"&&exit 1

username="$1"
root=`git rev-parse --show-toplevel`
scp -r "$username"@pentas-compa.coins.tsukuba.ac.jp:/home/prof/maeda/public_html/secure_htdocs/class/16/oo "$root/"
