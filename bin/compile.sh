#!/bin/bash

root=`git rev-parse --show-toplevel`
cat "$root/assign_dir.txt"|while read dirname titlename
do
	pandoc -i "$root/oo/${titlename}.html" -o "$root/$dirname/README.md"
done

