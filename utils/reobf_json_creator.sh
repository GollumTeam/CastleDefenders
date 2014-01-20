#!/bin/sh
cat joined.srg  | grep  net/minecraft/src/Block/ | grep "FD:" | cut -d" " -f2,3 > Class.fields
cat joined.srg  | grep  net/minecraft/src/Item/ | grep "FD:" | cut -d" " -f2,3 >> Class.fields

rm -r reobf 2> /dev/null
mkdir reobf
FE=reobf/index.json

echo "{" > $FE

while read FIELD
do
	
	key=`echo "$FIELD" | cut -d" " -f1`
	key=`php -r "echo str_replace('/', '|', '$key');"`
	value=`echo "$FIELD" | cut -d" " -f2`
	path=`echo $value | cut -d"/" -f1,2,3,4`
	path=`php -r "echo str_replace('/', '.', '$path');"`
	path=`php -r "echo str_replace('.src.Block', '.block.Block', '$path');"`
	path=`php -r "echo str_replace('.src.Item', '.item.Item', '$path');"`
	fieldName=`echo $value | cut -d"/" -f5`
	att=`cat fields.csv | grep "$fieldName," | cut -d"," -f2`
	
	echo "key=$key | path=$path | fieldName=$fieldName | att=$att"
	
	echo "\t\"$path|$att\" : \"$path|$fieldName\"", >> $FE	
	
done < Class.fields

echo "\t\"none\":\"\"" >> $FE
echo "}" >> $FE
