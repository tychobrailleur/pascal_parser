program test15;
procedure printfilename(n,a,e:integer);
var i:integer;
k:integer;
begin
    slowprint(a);
    slowprint(n);
    slowprint(e);
end;

procedure printsize(s:integer);
begin
    if s=0 then
        printesc(412)
    else
        if s=16 then
            printesc(413)
        else
        printesc(414);
end;

procedure printwritewhatsit(s:strnumber; p:halfword);
begin
    printesc(s);
    if mem[p+1].hh.lh<16 then
        printint(mem[p+1].hh.lh)
    else if mem[p+1].hh.lh = 16 then
        printchar(42)
    else
        printchar(45);
end;
begin

end.
