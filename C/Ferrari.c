//GitHub: HenriqueIni
//https://www.blogcyberini.com/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/*
 * Formata o número complexo na forma "a + bi" e imprime-o
 */
void formatComplex(double realPart, double imPart) {
    if (realPart == 0 && imPart == 0) {
        printf("0\n");
    }
    if (realPart != 0) {
        printf("%f", realPart);
        if (imPart > 0) {
            printf("+%fi", imPart);
        } else if (imPart < 0) {
            printf("%fi", imPart);
        }
    } else {
        printf("%fi", imPart);
    }
    printf("\n");
}
/*
 * Tenta retornar a primeira raiz real positiva da equação do terceiro
 * grau passada como parâmetro.
 * 
 * Se não for possível, retorna uma raiz real qualquer.
 */
double cardanoRealPositive(double a, double b, double c, double d) {
    //normaliza os coeficientes
    double A = b / a;
    double B = c / a;
    double C = d / a;

    //constantes da equação reduzida
    double p = B - A * A / 3;
    double q = C + 2 * A * A * A / 27 - A * B / 3;

    //discriminante
    double delta = q * q / 4 + p * p * p / 27;

    //raiz
    double x;

    if (delta >= 0) {
        double y1 = cbrt(-q / 2 + sqrt(delta)) + cbrt(-q / 2 - sqrt(delta));
        x = y1 - A / 3;
        if (x > 0) {
            return x;
        } else {
            double delta2 = -3 * y1 * y1 - 4 * p;
            if (delta2 >= 0) {
                x = (-y1 + sqrt(delta2)) / 2 - A / 3;
                if (x > 0) {
                    return x;
                } else {
                    return (-y1 - sqrt(delta2)) / 2 - A / 3;
                }
            } else {
                return x;
            }
        }

    } else {
        double rho = sqrt(q * q / 4 + fabs(delta));
        double theta = acos(-q / (2 * rho));
        x = 2 * cbrt(rho) * cos(theta / 3) - A / 3;
        if (x > 0) {
            return x;
        } else {
            x = 2 * cbrt(rho) * cos((theta + 2 * M_PI) / 3) - A / 3;
            if (x > 0) {
                return x;
            } else {
                return 2 * cbrt(rho) * cos((theta + 4 * M_PI) / 3) - A / 3;
            }
        }
    }
}
/*
 * Resolve uma equação biquadrática da forma x^4 + Bx^2 + D = 0.
 * Subtrai o valor subtract das soluções.
 * As soluções são imprimidas no console.
 */ 
void biquad(double B, double D, double subtract) {
    //equação biquadrática: resolve com a substituição y = x^2        
    double delta = B * B - 4 * D;
    if (delta >= 0) {
        //x1 e x2
        double aux1 = (-B + sqrt(delta)) / 2;
        if (aux1 >= 0) {
            printf("x1 = %f\n", sqrt(aux1) - subtract);
            printf("x2 = %f\n", -sqrt(aux1) - subtract);
        } else {
            printf("x1 = ");
            formatComplex(-subtract, sqrt(fabs(aux1)));
            printf("x2 = ");
            formatComplex(-subtract, -sqrt(fabs(aux1)));
        }
        //x3 e x4
        double aux2 = (-B - sqrt(delta)) / 2;
        if (aux2 >= 0) {
            printf("x3 = %f\n", sqrt(aux2) - subtract);
            printf("x4 = %f\n", -sqrt(aux2) - subtract);
        } else {
            printf("x3 = ");
            formatComplex(-subtract, sqrt(fabs(aux2)));
            printf("x4 = ");
            formatComplex(-subtract, -sqrt(fabs(aux2)));
        }
    } else {
        //quando delta é menor que 0, então é necessário lidar com números complexos
        double rho = sqrt(D);
        double theta = atan2(sqrt(fabs(delta)) / 2, -B / 2);

        double reAux = sqrt(rho) * cos(theta / 2);
        double imAux = sqrt(rho) * sin(theta / 2);

        printf("x1 = ");
        formatComplex(reAux - subtract, imAux);
        printf("x2 = ");
        formatComplex(reAux - subtract, -imAux);
        printf("x3 = ");
        formatComplex(-reAux - subtract, imAux);
        printf("x4 = ");
        formatComplex(-reAux - subtract, -imAux);
    }
}
/*
 * Resolve uma equação do quarto grau com coeficientes reais e imprime as raízes.
 * 
 * Se a equação for biquadrática, resolve via substituição y = x^2. Senão,
 * resolve via fórmula de Ferrari
 */
void ferrari(double a, double b, double c, double d, double e) {
    //normaliza os cofiecientes
    double A = b / a;
    double B = c / a;
    double C = d / a;
    double D = e / a;

    //Fórmula de Ferrari            
    //coeficientes da equação reduzida
    double p = B - 3 * A * A / 8;
    double q = A * A * A / 8 - A * B / 2 + C;
    double r = -3 * A * A * A * A / 256 + A * A * B / 16 - A * C / 4 + D;    
    
    if (q == 0) {
        //a equação é biquadrática: y^4+py^2+r = 0
        //o valor A/4 será subtraído da equação
        return biquad(p, r, A / 4);
    }

    //raiz da equação auxiliar
    double u = cardanoRealPositive(1, 2 * p, p * p - 4 * r, -q * q);
    
    //discriminantes
    double delta1 = -u - 2 * p - 2 * q / sqrt(u);
    double delta2 = -u - 2 * p + 2 * q / sqrt(u);

    //imprime as raízes
    if (delta1 >= 0) {
        printf("x1 = %f\n", -0.25 * A + 0.5 * sqrt(u) + 0.5 * sqrt(delta1));
        printf("x2 = %f\n", -0.25 * A + 0.5 * sqrt(u) - 0.5 * sqrt(delta1));
    } else {
        printf("x1 = ");
        formatComplex(-0.25 * A + 0.5 * sqrt(u), 0.5 * sqrt(fabs(delta1)));
        printf("x2 = ");
        formatComplex(-0.25 * A + 0.5 * sqrt(u), -0.5 * sqrt(fabs(delta1)));
    }
    if (delta2 >= 0) {
        printf("x3 = %f\n", -0.25 * A - 0.5 * sqrt(u) + 0.5 * sqrt(delta2));
        printf("x4 = %f\n", -0.25 * A - 0.5 * sqrt(u) - 0.5 * sqrt(delta2));
    } else {
        printf("x3 = ");
        formatComplex(-0.25 * A - 0.5 * sqrt(u), 0.5 * sqrt(fabs(delta2)));
        printf("x4 = ");
        formatComplex(-0.25 * A - 0.5 * sqrt(u), -0.5 * sqrt(fabs(delta2)));
    }
}

//Código de testes
int main() {
    printf("Coeficientes: 1, 0, 3, 4, 5\n");
    ferrari(1, 0, 3, 4, 5);

    printf("\nCoeficientes: -2,6,14,-54,36\n");
    ferrari(-2, 6, 14, -54, 36);

    printf("\nCoeficientes: -2,6,14,-54,36\n");
    ferrari(-2, 6, 14, -54, 36);

    printf("\nCoeficientes: 2,2,2,2,2\n");
    ferrari(2, 2, 2, 2, 2);

    printf("\nCoeficientes: 2,3,10,2,8\n");
    ferrari(2, 3, 10, 2, 8);

    printf("\nCoeficientes: 1, 0, 3, -4, 5\n");
    ferrari(1, 0, 3, -4, 5);

    printf("\nCoeficientes: -3,6,21,-60, 36\n");
    ferrari(-3, 6, 21, -60, 36);

    printf("\nCoeficientes: 2,2,-44,-32,192\n");
    ferrari(2, 2, -44, -32, 192);

    printf("\nCoeficientes: 1,0,3,0,192\n");
    ferrari(1, 0, 3, 0, 192);

    printf("\nCoeficientes: 1,0,-5,0,4\n");
    ferrari(1, 0, -5, 0, 4);

    printf("\nCoeficientes: 1,2,2,1,1\n");
    ferrari(1, 2, 2, 1, 1);
    return 0;
}
