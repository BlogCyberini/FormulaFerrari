//GitHub: HenriqueIni
//https://www.blogcyberini.com/
import java.util.LinkedList;
import java.util.List;

public class Ferrari {
    
    /**
     * Resolve uma equação do quarto grau com coeficientes reais e retorna uma
     * lista com as raízes.
     * 
     * Se a equação for biquadrática, resolve via substituição y = x^2. Senão,
     * resolve via fórmula de Ferrari
     * 
     * @param a coeficiente do termo quártico
     * @param b coeficiente do termo cúbico
     * @param c coeficiente do termo quadrático
     * @param d coeficiente do termo linear
     * @param e coeficiente do termo independente
     * @throws IllegalArgumentException se o coeficiente do termo quártico for zero
     * @return uma lista com as raízes em forma de String
     */
    public static List<String> ferrari(double a, double b, double c, double d, double e) {
        if (a == 0) {
            throw new IllegalArgumentException("a == 0");
        }
        //normaliza os cofiecintes
        double A = b / a;
        double B = c / a;
        double C = d / a;
        double D = e / a;

        //lista com os resultados
        List<String> results = new LinkedList<String>();

        //Fórmula de Ferrari            
        //coeficientes da equação reduzida
        double p = B - 3 * A * A / 8;
        double q = A * A * A / 8 - A * B / 2 + C;
        double r = -3 * A * A * A * A / 256 + A * A * B / 16 - A * C / 4 + D;
        
        if(q == 0){
            //a equação é biquadrática: y^4+py^2+r = 0
            //o valor A/4 será subtraído da equação
            return biquad(p, r, A / 4);
        }
        
        //raiz da equação auxiliar
        double u = cardanoRealPositive(1, 2 * p, p * p - 4 * r, -q * q);

        //discriminantes
        double delta1 = -u - 2 * p - 2 * q / Math.sqrt(u);
        double delta2 = -u - 2 * p + 2 * q / Math.sqrt(u);

        //adiciona as raízes à lista de resultados
        if (delta1 >= 0) {
            results.add(String.valueOf(-A / 4 + Math.sqrt(u) / 2 + Math.sqrt(delta1) / 2));
            results.add(String.valueOf(-A / 4 + Math.sqrt(u) / 2 - Math.sqrt(delta1) / 2));
        } else {
            results.add(formatComplex(-A / 4 + Math.sqrt(u) / 2, Math.sqrt(Math.abs(delta1)) / 2));
            results.add(formatComplex(-A / 4 + Math.sqrt(u) / 2, -Math.sqrt(Math.abs(delta1)) / 2));
        }
        if (delta2 >= 0) {
            results.add(String.valueOf(-A / 4 - Math.sqrt(u) / 2 + Math.sqrt(delta2) / 2));
            results.add(String.valueOf(-A / 4 - Math.sqrt(u) / 2 - Math.sqrt(delta2) / 2));
        } else {
            results.add(formatComplex(-A / 4 - Math.sqrt(u) / 2, Math.sqrt(Math.abs(delta2)) / 2));
            results.add(formatComplex(-A / 4 - Math.sqrt(u) / 2, -Math.sqrt(Math.abs(delta2)) / 2));
        }
        return results;
    }
    
    /**
     * Resolve uma equação biquadrática da forma x^4 + Bx^2 + D = 0.
     * Subtrai o valor subtract das soluções.
     * 
     * @param B coeficiente do termo quadrático
     * @param D coeficiente do termo independente
     * @param subtract valor que será subtraído das raízes
     * @return raízes da equação menos o valor subtract
     */
    private static List<String> biquad(double B, double D, double subtract) {
        //equação biquadrática: resolve com a substituição y = x^2
        List<String> results = new LinkedList<String>();        
        double delta = B * B - 4 * D;
        if (delta >= 0) {
            //x1 e x2
            double aux1 = (-B + Math.sqrt(delta)) / 2;
            if (aux1 >= 0) {
                results.add(String.valueOf(Math.sqrt(aux1) - subtract));
                results.add(String.valueOf(-Math.sqrt(aux1) - subtract));
            } else {
                results.add(formatComplex(-subtract, Math.sqrt(Math.abs(aux1))));
                results.add(formatComplex(-subtract, -Math.sqrt(Math.abs(aux1))));
            }
            //x3 e x4
            double aux2 = (-B - Math.sqrt(delta)) / 2;
            if (aux2 >= 0) {
                results.add(String.valueOf(Math.sqrt(aux2) - subtract));
                results.add(String.valueOf(-Math.sqrt(aux2) - subtract));
            } else {
                results.add(formatComplex(-subtract, Math.sqrt(Math.abs(aux2))));
                results.add(formatComplex(-subtract, -Math.sqrt(Math.abs(aux2))));
            }
        } else {
            //quando delta < 0, então é necessário lidar com números complexos
            double rho = Math.sqrt(D);
            double theta = Math.atan2(Math.sqrt(Math.abs(delta)) / 2, -B / 2);

            double reAux = Math.sqrt(rho) * Math.cos(theta / 2);
            double imAux = Math.sqrt(rho) * Math.sin(theta / 2);

            results.add(formatComplex(reAux - subtract, imAux));
            results.add(formatComplex(reAux - subtract, -imAux));
            results.add(formatComplex(-reAux - subtract, imAux));
            results.add(formatComplex(-reAux - subtract, -imAux));
        }
        return results;
    }
    
    /**
     * Tenta retornar a primeira raiz real positiva da equação do terceiro
     * grau passada como parâmetro.
     *  
     * Se não for possível, retorna uma raiz real qualquer.
     * 
     * @param a coeficiente do termo cúbico
     * @param b coeficiente do termo quadrático
     * @param c coeficiente do termo linear
     * @param d coeficiente do termo independente
     * @throws IllegalArgumentException se o coeficiente do termo cúbico for zero
     * @return a primeira raiz real positiva da equação, ou qualquer raiz
     */
    private static double cardanoRealPositive(double a, double b, double c, double d) {
        if (a == 0) {
            throw new IllegalArgumentException("a == 0");
        }
        //normaliza os coeficientes
        double A = b / a;
        double B = c / a;
        double C = d / a;
        
        //constantes da equação reduzida
        double p = B - A * A / 3.0;
        double q = C + 2 * A * A * A / 27.0 - A * B / 3.0;
        
        //discriminante
        double delta = q * q / 4.0 + p * p * p / 27.0;
        
        //raiz
        double x;

        if (delta >= 0) {
            double y1 = Math.cbrt(-q / 2.0 + Math.sqrt(delta)) + Math.cbrt(-q / 2.0 - Math.sqrt(delta));
            x = y1 - A / 3.0;
            if(x > 0){
                return x;
            }else{                
                double delta2 = -3.0 * y1 * y1 - 4.0 * p;
                if (delta2 >= 0) {
                    x = (-y1 + Math.sqrt(delta2)) / 2.0 - A / 3.0;
                    if(x > 0){
                        return x;
                    }else{
                        return (-y1 - Math.sqrt(delta2)) / 2.0 - A / 3.0;                        
                    }                    
                }else{
                    return x;
                }
            }
            
        } else {
            double rho = Math.sqrt(q * q / 4.0 + Math.abs(delta));
            double theta = Math.acos(-q / (2.0 * rho));
            x = 2.0 * Math.cbrt(rho) * Math.cos(theta / 3.0) - A / 3.0;
            if(x > 0){
                return x;
            }else{
                x = 2.0 * Math.cbrt(rho) * Math.cos((theta + 2.0 * Math.PI) / 3.0) - A / 3.0;
                if(x > 0){
                    return x;
                }else{
                    return 2.0 * Math.cbrt(rho) * Math.cos((theta + 4.0 * Math.PI) / 3.0) - A / 3.0;
                }
            }
        }
    }

    /**
     * Formata o número complexo na forma "a + bi"
     *
     * @param realPart parte real do número complexo
     * @param imPart parte imaginária do número complexo
     * @return número complexo formatado
     */
    private static String formatComplex(double realPart, double imPart) {
        if (realPart == 0 && imPart == 0) {
            return "0";
        }
        String number = "";
        if (realPart != 0) {
            number += realPart;
            if (imPart > 0) {
                number += "+" + imPart + "i";
            } else if (imPart < 0) {
                number += imPart + "i";
            }
        } else {
            number += imPart + "i";
        }
        return number;
    }
    
    //Método de testes
    public static void main(String[] args) {
        System.out.println("Coeficientes: 1, 0, 3, 4, 5");
        List<String> roots = ferrari(1, 0, 3, 4, 5);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: -2,6,14,-54,36");
        roots = ferrari(-2,6,14,-54,36);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 2,2,2,2,2");
        roots = ferrari(2,2,2,2,2);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 2,3,10,2,8");
        roots = ferrari(2,3,10,2,8);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 1, 0, 3, -4, 5");
        roots = ferrari(1, 0, 3, -4, 5);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: -3,6,21,-60, 36");
        roots = ferrari(-3,6,21,-60, 36);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 2,2,-44,-32,192");
        roots = ferrari(2,2,-44,-32,192);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 1,0,3,0,192");
        roots = ferrari(1,0,3,0,192);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 1,0,-5,0,4");
        roots = ferrari(1,0,-5,0,4);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
        
        System.out.println("\nCoeficientes: 1,2,2,1,1");
        roots = ferrari(1,2,2,1,1);
        for(int i = 0; i < roots.size(); i++){
            System.out.println("y"+(i+1)+"="+roots.get(i));
        }
    }
}
