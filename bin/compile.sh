#!/bin/bash

root=`git rev-parse --show-toplevel`
cat "$root/assign_dir.txt"|while read dirname titlename
do
	pandoc -i "$root/oo/${titlename}.html" -o "$root/oo/${titlename}.md"
	ln -s "../oo/${titlename}.md" "$root/$dirname/README.md"
done

ln -s stack_and_tree.html
