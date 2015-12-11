#!/bin/bash
mkdir keys
for i in 1 2 3 4 5 6
do
	#ssh vm$i ssh-keygen
	scp vm$i:/home/im2ag/.ssh/id_rsa.pub keys/m$i.pub
done
for i in keys/*
do
	cat $i >> authorized_keys
done 
for i in 1 2 3 4 5 6 
do
	scp authorized_keys vm$i:/home/im2ag/.ssh/authorized_keys
done 
