program test12;
type
   ASCIIcode = 0..255;
   memoryword = record
               case fourchoices of
                    1:(int:integer);
                    2:(gr:glueratio);
                    3:(hh:twohalves);
                    4:(qqqq:fourquarters);
                end;
   wordfile = file of memoryword;
   liststaterecord = record
                       modefield:-203..203;
                       headfield,tailfield:halfword;
                       pgfield,mlfield:integer;
                       auxfield:memoryword;
                     end;
begin
end.
