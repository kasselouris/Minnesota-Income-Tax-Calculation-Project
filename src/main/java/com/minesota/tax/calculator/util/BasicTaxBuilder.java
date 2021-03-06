package com.minesota.tax.calculator.util;

import com.minesota.tax.calculator.model.TaxCategory;
import com.minesota.tax.calculator.model.enumeration.FamilyStatusEnum;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * This class holds the logic for the taxpayer's tax calculations based on his family status and income.
 * Each tax payer pays a basic tax based on this income category ( upper and lower limits ) and then an
 * addional tax that is a percentage of his total income.
 * <p>
 * Formula generally looks like this:
 * basic tax = minTax + incomeBasedTaxPercentage * ( income - taxCategoryLowerLimit )
 * <br>
 * For example:
 * 4746.76 + 7.85% * (income  -71680)
 */
public class BasicTaxBuilder {

    private static final Map<FamilyStatusEnum, List<TaxCategory>> taxCategories = new EnumMap<>(FamilyStatusEnum.class); // enum map

    static {
        taxCategories.put(
                FamilyStatusEnum.MARRIED_FILLING_JOINTLY,
                Arrays.asList(
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_JOINTLY, 0, 0.0535, new int[]{0, 36080}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_JOINTLY, 1930.28, 0.0705, new int[]{36080, 90000}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_JOINTLY, 5731.64, 0.0705, new int[]{90000, 143350}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_JOINTLY, 9492.82, 0.0785, new int[]{143350, 254240}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_JOINTLY, 18197.69, 0.0985, new int[]{254240, -1})));

        taxCategories.put(
                FamilyStatusEnum.MARRIED_FILLING_SEPARATELY,
                Arrays.asList(
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_SEPARATELY, 0, 0.0535, new int[]{0, 18040}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_SEPARATELY, 695.14, 0.0705, new int[]{18040, 71680}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_SEPARATELY, 4746.76, 0.0785, new int[]{71680, 90000}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_SEPARATELY, 6164.88, 0.0785, new int[]{90000, 127120}),
                        new TaxCategory(FamilyStatusEnum.MARRIED_FILLING_SEPARATELY, 9098.80, 0.0985, new int[]{127120, -1})));

        taxCategories.put(
                FamilyStatusEnum.SINGLE,
                Arrays.asList(
                        new TaxCategory(FamilyStatusEnum.SINGLE, 0, 0.0535, new int[]{0, 24680}),
                        new TaxCategory(FamilyStatusEnum.SINGLE, 1320.38, 0.0705, new int[]{24680, 81080}),
                        new TaxCategory(FamilyStatusEnum.SINGLE, 5296.58, 0.0785, new int[]{81080, 90000}),
                        new TaxCategory(FamilyStatusEnum.SINGLE, 59996.80, 0.0785, new int[]{90000, 152540}),
                        new TaxCategory(FamilyStatusEnum.SINGLE, 10906.19, 0.0985, new int[]{152540, -1})));

        taxCategories.put(
                FamilyStatusEnum.HEAD_OF_HOUSEHOLD,
                Arrays.asList(
                        new TaxCategory(FamilyStatusEnum.HEAD_OF_HOUSEHOLD, 0, 0.0535, new int[]{0, 30390}),
                        new TaxCategory(FamilyStatusEnum.HEAD_OF_HOUSEHOLD, 1625.87, 0.0705, new int[]{30390, 90000}),
                        new TaxCategory(FamilyStatusEnum.HEAD_OF_HOUSEHOLD, 5828.38, 0.0705, new int[]{90000, 122110}),
                        new TaxCategory(FamilyStatusEnum.HEAD_OF_HOUSEHOLD, 8092.13, 0.0785, new int[]{122110, 203390}),
                        new TaxCategory(FamilyStatusEnum.HEAD_OF_HOUSEHOLD, 14472.61, 0.0985, new int[]{203390, -1})));

    }

    private BasicTaxBuilder() {
        throw new IllegalStateException("Utility class. This class can not be initialized.");
    }

    /**
     * Calculates the taxpayer's basic tax based on his family status and income. Mechanism
     * described here: http://www.revenue.state.mn.us/Forms_and_Instructions/it_algorithm_14.pdf
     *
     * @param familyStatus the taxpayer's family status ( in small letters and divided by spaces )
     * @param income       the taxpayer's income
     * @return the taxpayer's basic tax
     */
    public static Double getBasicTaxBy(String familyStatus, double income) {

        return taxCategories.get(FamilyStatusEnum.fromValue(familyStatus)).stream()
                .filter(taxCategory ->
                        income >= taxCategory.getLimits()[0] &&
                                (income <= taxCategory.getLimits()[1] || taxCategory.getLimits()[1] == -1))
                .findFirst()
                .map(taxCategory -> taxCategory.getMinTax() + taxCategory.getIncomeBasedTaxPercentage() * (income - taxCategory.getLimits()[0]))
                .orElse((double) 0);

    }
}