program test8;
begin
   if true then
       writeln('Hello');
   if true then
       begin
           writeln('Hello');
           writeln('again');
       end;

   if true then
       begin
           writeln('Hello');
           writeln('again');
       end
   else
       writeln('shouldn''t get here');

   if false then
       writeln('wat?')
   else
       begin
           writeln('Hello');
           writeln('again');
       end;
end.
