package com.marcoswolf.repairflow.ui.utils;

public class DocumentoUtils {
    public static boolean isValidCpfOrCnpj(String documento) {
        if (documento == null) return false;

        String numbers = documento.replaceAll("\\D", "");

        if (numbers.length() == 11) {
            return isValidCPF(numbers);
        } else if (numbers.length() == 14) {
            return isValidCNPJ(numbers);
        }

        return false;
    }

    private static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = cpf.charAt(i) - '0';
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int dv1 = 11 - (sum1 % 11);
            dv1 = dv1 > 9 ? 0 : dv1;

            sum2 += dv1 * 2;
            int dv2 = 11 - (sum2 % 11);
            dv2 = dv2 > 9 ? 0 : dv2;

            return cpf.charAt(9) - '0' == dv1 && cpf.charAt(10) - '0' == dv2;

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) return false;

        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum1 = 0, sum2 = 0;

            for (int i = 0; i < 12; i++) {
                int digit = cnpj.charAt(i) - '0';
                sum1 += digit * peso1[i];
                sum2 += digit * peso2[i];
            }

            int dv1 = sum1 % 11;
            dv1 = dv1 < 2 ? 0 : 11 - dv1;

            sum2 += dv1 * peso2[12];
            int dv2 = sum2 % 11;
            dv2 = dv2 < 2 ? 0 : 11 - dv2;

            return cnpj.charAt(12) - '0' == dv1 && cnpj.charAt(13) - '0' == dv2;

        } catch (Exception e) {
            return false;
        }
    }
}
