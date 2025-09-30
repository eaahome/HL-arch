#!/usr/bin/perl

$s = '[{"id":"05f6cffb-4bfc-4537-9d34-69437358de77"';
$s =~ s/^.*?id\":\"(.*)?\".*/$1/;

print "$s\n";