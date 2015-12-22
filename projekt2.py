import numpy

ALPHA = 2.926271062443501


def function_value(x):

    return x + numpy.log(x) - 4

def derivative_function_value(x):

    return 1 + (1/x)

def simple_iteration(x):

    return 4 - numpy.log(x)

def calculate_simple_iterations(x, iterations_limit=10, precision=0.0):

    start_point = x
    result = None
    i = 0

    for i in range(0, iterations_limit):
        result = simple_iteration(start_point)
        if result < 0:
            print("Metoda nie jest zbieżna dla danego punktu początkowego.")
            return None
        elif numpy.fabs(result - ALPHA) < precision:
            break
        start_point = result

    print("Rozwiazanie dla metody iteracji prostych znaleziono po {0} iteracjach.".format(i+1))
    print("Błąd metody: {0}".format(numpy.fabs(result-ALPHA)))
    return result

def secant_method(x0, x1):

    return x1 - ((function_value(x1) * (x1 - x0)) / (function_value(x1) - function_value(x0)))

def calculate_secant_iterations(x0, x1, iterations_limit=10, precision=0.0):

    result = None
    x_k_min_1 = x0
    x_k = x1
    i = 0

    for i in range(0, iterations_limit):
        result = secant_method(x_k_min_1, x_k)
        x_k_min_1 = x_k
        x_k = result
        if x_k == x_k_min_1:
            break
        elif numpy.fabs(result - ALPHA) < precision:
            break

    print("Rozwiazanie dla metody siecznych znaleziono po {0} iteracjach.".format(i+1))
    print("Błąd metody: {0}".format(numpy.fabs(result-ALPHA)))
    return result

def tangent_method(x):

    return x - (function_value(x) / derivative_function_value(x))

def calculate_tangent_iterations(x, iterations_limit=10, precision=0.0):

    start_point = x
    result = None
    i = 0
    for i in range(0, iterations_limit):
        result = tangent_method(start_point)
        start_point = result
        if result < 0:
            print("Metoda nie jest zbieżna dla danego punktu początkowego.\n"
                  " W iteracji nr {0} x_i przyjmuję wartość {1},"
                  "dla której równanie x-(f(x)/f'(x)) przyjmuję wartość ujemną({2}),\n"
                  " więc wynik następnej iteracji będzie nieokreślony\n"
                  " w zbiorze liczb rzeczywistych".format(i+1, start_point, result))
            return None
        elif numpy.fabs(result - ALPHA) < precision:
            break

    print("Rozwiazanie dla metody stycznych znaleziono po {0} iteracjach.".format(i+1))
    print("Błąd metody: {0}".format(numpy.fabs(result-ALPHA)))
    return result


def parse_user_provided_float(label, check_x_condition=True, check_iteration_condition=False):
    
    val = None
    while True:
        try:
            val = float(input("Podaj wartość {0}:".format(label)))

            valid = True
            if check_x_condition and val <= 0:
                print("Wartość {0} musi być większa od zera,"
                      " ponieważ funkcja:\n x + log(x) - 4 = 0\n"
                      " jest określona tylko dla liczb rzeczywistych"
                      " większych od 0.".format(label))
                valid = False
            if check_iteration_condition and numpy.log(val) >= 4:
                print("Dla punktu startowego metody iteracji prostych musi byc spełniony warunek"
                      "ln(x) < 4".format(label))
                valid = False
            if not valid:
                continue
        except ValueError:
            print("Wpisz poprawną wartość {0}.".format(label))
            continue
        else:
            break

    return val

def parse_user_provided_int(label):
   
    val = None
    while True:
        try:
            val = int(input("Podaj wartość {0}:".format(label)))
            if val <= 0:
                print("Wartość {0} musi bc większa od zera.".format(label))
                continue
        except ValueError:
            print("Wpisz poprawną wartość {0}.".format(label))
            continue
        else:
            break

    return val

if __name__ == '__main__':
    PRECISION = parse_user_provided_float("zadanej precyzji (np. 0.001, 1e-05)", check_x_condition=False)
    ITERATIONS_LIMIT = parse_user_provided_int("maksymalnej liczby iteracji")
    X_SIMPLE_ITERATIONS = parse_user_provided_float("punktu startowego metody iteracji prostych",
                                                    check_iteration_condition=True)
    print("Wynik metody iteracji prostych {0}".format(calculate_simple_iterations(X_SIMPLE_ITERATIONS, ITERATIONS_LIMIT,
                                                                                  PRECISION)))

    X_TANGENT = parse_user_provided_float("punktu startowego metody stycznych")
    print("Wynik metody stycznych: {0}".format(calculate_tangent_iterations(X_TANGENT, ITERATIONS_LIMIT, PRECISION)))

    X0_SECANT = parse_user_provided_float("punktu startowego x0 dla metody siecznych")
    X1_SECANT = X0_SECANT
    while X1_SECANT == X0_SECANT:
        X1_SECANT = parse_user_provided_float("punktu startowego x1 dla metody siecznych")
        if X1_SECANT == X0_SECANT:
            print("Wartości x0 i x1 dla metody siecznych nie mogą być równe.")

    print("Wynik metody siecznych {0}".format(calculate_secant_iterations(X0_SECANT, X1_SECANT, ITERATIONS_LIMIT,
                                                                          PRECISION)))
