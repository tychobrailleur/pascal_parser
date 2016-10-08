program test13;
type
   ASCIIcode = 0..255;
   memoryword = record
               case fourchoices of
                    1:(int:integer);
                    2:(gr:glueratio);
                    3:(hh:twohalves);
                    4:(qqqq:fourquarters);
                end;
var
   i: integer;
   xord: array[1..255] of integer;
   xchr: array[ASCIIcode]of char;
   eqtb:array[1..6106]of memoryword;
begin
    for i:=0 to 31 do xchr[i]:=' ';
    for i:=127 to 255 do xchr[i]:=' ';
    for i:=0 to 255 do xord[chr(i)]:=127;
    for i :=128 to 255 do xord[xchr[i]]:=i;
    eqtb[3412].hh.rh:=0;
end.
