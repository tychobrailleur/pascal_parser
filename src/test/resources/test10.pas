program test10;
var
   a, b: integer;
   c, d: real;
begin
   a := 1;
   b := 2;

   10:if (a = 3) then
      writeln(' a != 1')
   else
      writeln(' a = ', a);

   2:writeln('hello');
   3:for I:=100 downto 1 do
     begin
         4:writeln('value of a: ', I);
     end;
end.
