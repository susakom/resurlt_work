#!/bin/bash

. /etc/os-release
echo "Operating System: $NAME"
echo "Version: $VERSION"
echo "------------"
echo "Root users "

 awk -F: '/\/bin\/bash/ {print $1}' /etc/passwd
 
echo "-------------"
echo "open ports from firewall d"
 sudo firewall-cmd  --list-ports
echo "----------------------"
echo "Ports  casual"
netstat -tuln | grep LISTEN
echo "-----------------------"
