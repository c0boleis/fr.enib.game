#!/bin/sh
echo bonjour
x=0
y=0

for var in $(ls *.jpg)
do 
largeur=`identify -format '%w' $var`
hauteur=`identify -format '%h' $var` 
echo $largeur
echo $hauteur

if [ "$largeur" -lt 24 ]
then 
	x=16
elif [ "$largeur" -ge 24 ]&&[ "$largeur" -lt 48 ]
then 
	x=32
elif [ "$largeur" -ge 48 ]&&[ "$largeur" -lt 96 ]
then 
	x=64
elif [ "$largeur" -ge 96 ]&&[ "$largeur" -lt 192 ]
then 
	x=128
elif [ "$largeur" -ge 192 ]&&[ "$largeur" -lt 384 ]
then 
	x=256
elif [ "$largeur" -ge 384 ]&&[ "$largeur" -lt 768 ]
then 
	x=512
elif [ "$largeur" -ge 768 ]&&[ "$largeur" -lt 1536 ]
then 
	x=1024
elif [ "$largeur" -ge 1536 ]&&[ "$largeur" -lt 3072 ]
then 
	x=2048
elif [ "$largeur" -ge 3072 ]&&[ "$largeur" -lt 6144 ]
then 
	x=4096
elif [ "$largeur" -ge 6144 ]&&[ "$largeur" -lt 8192 ]
then 
	x=8192
fi


if [ "$hauteur" -lt 24 ]
then 
	y=16
elif [ "$hauteur" -ge 24 ]&&[ "$hauteur" -lt 48 ]
then 
	y=32 
elif [ "$hauteur" -ge 48 ]&&[ "$hauteur" -lt 96 ]
then 
	y=64
elif [ "$hauteur" -ge 96 ]&&[ "$hauteur" -lt 192 ]
then 
	y=128
elif [ "$hauteur" -ge 192 ]&&[ "$hauteur" -lt 384 ]
then 
	y=256
elif [ "$hauteur" -ge 384 ]&&[ "$hauteur" -lt 768 ]
then 
	y=512
elif [ "$hauteur" -ge 768 ]&&[ "$hauteur" -lt 1536 ]
then 
	y=1024
elif [ "$hauteur" -ge 1536 ]&&[ "$hauteur" -lt 3072 ]
then 
	y=2048
elif [ "$hauteur" -ge 3072 ]&&[ "$hauteur" -lt 6144 ]
then 
	y=4096
elif [ "$hauteur" -ge 6144 ]&&[ "$hauteur" -lt 8192 ]
then 
	y=8192
fi

convert $var -resize "$x"x"$y"\! $var
 

done


exit 0
#convert abbe-david-pipriac.jpg -resize 256x512\! abbe-david-pipriac.jpg


