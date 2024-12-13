program FactorialCalculator;

var
    num: integer;
    result: longint;
    i: integer;

begin
    // Prompt the user for input
    Write('Enter a number: ');
    ReadLn(num);

    // Initialize the result to 1
    result := 1;

    // Calculate the factorial
    for i := 1 to num do
        result := result * i;

    // Print the result
    WriteLn('The factorial of ', num, ' is ', result);
end.
