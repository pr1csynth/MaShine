#!/bin/bash

dir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
cd $dir
echo `pwd`
mkdir MaShine releases
cp dist/mashine.jar MaShine
cp -R lib/ MaShine
cp *.vlw MaShine
cp README.md MaShine
tar czvf releases/MaShine_`date +%Y-%m-%d_%H-%M-%S`.tar.gz MaShine
rm -rf MaShine
echo Done