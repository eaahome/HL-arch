#!/usr/bin/perl

use strict;
use UUID qw(uuid);


open F, "people.short.csv";
open F, "people.v2.csv";

print "COPY \"user\" (id, first_name, second_name, birthdate, sex, city) FROM stdin;\n";

foreach my $l (<F>) {
	#print $l;
	chomp $l;
	my ($f, $rest) = split (/ /, $l, 2);
	my ($s, $date, $city) = split (/,/, $rest);
	
	my $sex = (rand()> 0.5) ? 'M' : 'F';
	my $uuid = uuid();
	
	#print "F=$f, S=$s, SEX=$sex, DATE=$date, CITY=$city\n";
	print "$uuid\t$f\t$s\t$date\t$sex\t$city\n";
}

close F;

print "\\.\n";

