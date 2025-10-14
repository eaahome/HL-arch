#!/usr/bin/perl

use strict;
use UUID qw(uuid);


open F, "posts.txt";

print "COPY \"post\" (id, author_user_id, \"text\") FROM stdin;\n";

my @users = ('user1', 'user2', 
'f9dca338-a686-4610-94b5-60f90d0f7c43',
'f17d50b3-5f2e-4986-8879-332ac94f4e7b',
'825c981f-a4a6-490c-b959-e44dea33d021',
'54e77aba-296c-4ca1-94b1-6964ed41117c'
);

foreach my $text (<F>) {
	#print $l;
	chomp $text;
	my $uuid = uuid();
	my $author_user_id;
	my $user_idx = rand (scalar(@users));
	$author_user_id = @users[$user_idx];
	
	#print "F=$f, S=$s, SEX=$sex, DATE=$date, CITY=$city\n";
	print "$uuid\t$author_user_id\t$text\n";
}

close F;

print "\\.\n";

