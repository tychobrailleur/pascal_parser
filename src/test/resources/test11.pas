program test11;
var
    a, b: ^integer;
    c, d: 1 .. 10;
    file_char: file of char;
type
    person = record
        first_name, last_name : string;
        address: record
            address, town, country: string;
            postal_code: array[1..5] of string;
        end;
        signature: file of byte;
        registered: boolean;
    end;
begin
    writeln('test');
end.
