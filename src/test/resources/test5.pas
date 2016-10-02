program test5;
begin
   x := 1;
   repeat
     do_something;
     x := x + 1;
   until x = 10;
end.
