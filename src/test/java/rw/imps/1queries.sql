SELECT 
    cd.COMP_DETAILS_PK AS companyId,
    cd.COMP_NAME AS companyName,
    cpp.PROFILE_CODE AS profileCode,
    cppd.PROFILE_NAME AS profileName,
    cppd.APR_STRUCTURE_TYPE AS approvalStructureType,
    cpp.STATUS AS profileStatus,
    cppd.AMOUNT_CURRENCY AS currency,
    JSON_ARRAYAGG(cppe.ENTRY_METHOD) AS paymentEntryMethod
FROM 
    MCESSERV.COMPANY_DETAILS cd
JOIN 
    MCESSERV.COMP_APP ca ON ca.ACTIVE_COMP_PKREF = cd.COMP_DETAILS_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE cpp ON cpp.CMAP_PKREF = ca.CMAP_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE_DETAILS cppd ON cppd.ACTIVE_PYMT_PROF_PKREF = cpp.PYMT_PROF_PK
LEFT JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE_PYMTENTRY cppe ON cppe.ACTIVE_PYMT_PROF_PKREF = cpp.PYMT_PROF_PK
WHERE 
    cd.COMP_DETAILS_PK = :companyId
AND 
    cpp.PROFILE_CODE = :profileCode
GROUP BY 
    cd.COMP_DETAILS_PK, cd.COMP_NAME, cpp.PROFILE_CODE, cppd.PROFILE_NAME, cppd.APR_STRUCTURE_TYPE, cpp.STATUS, cppd.AMOUNT_CURRENCY;

SELECT 
    JSON_ARRAYAGG(
        JSON_OBJECT(
            'accountNo', mr.MCES_ACCOUNT_NUMBER,
            'bankId', mr.MCES_BANK_ID
        )
    ) AS accountLst
FROM 
    MCESSERV.COMPANY_DETAILS cd
JOIN 
    MCESSERV.COMP_APP ca ON ca.ACTIVE_COMP_PKREF = cd.COMP_DETAILS_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE cpp ON cpp.CMAP_PKREF = ca.CMAP_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE_ACCT cppa ON cppa.ACTIVE_PYMT_PROF_PKREF = cpp.PYMT_PROF_PK
JOIN 
    MCESSERV.COMP_RESOURE cr ON cr.CRES_PK = cppa.CRES_PKREF
JOIN 
    MCESSERV.MCES_RESOURCE mr ON mr.RESOURCE_PK = cr.RESOURCE_PKREF
WHERE 
    cd.COMP_DETAILS_PK = :companyId
AND 
    cpp.PROFILE_CODE = :profileCode;

SELECT 
    JSON_ARRAYAGG(
        JSON_OBJECT(
            'id', cppr.PYMT_PROF_RANGE_PK,
            'minAmount', cppr.AMT_RANGE_MIN,
            'maxAmount', cppr.AMT_RANGE_MAX
        )
    ) AS range
FROM 
    MCESSERV.COMPANY_DETAILS cd
JOIN 
    MCESSERV.COMP_APP ca ON ca.ACTIVE_COMP_PKREF = cd.COMP_DETAILS_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE cpp ON cpp.CMAP_PKREF = ca.CMAP_PK
JOIN 
    MCESSERV.COMP_PAYMENT_PROFILE_RANGE cppr ON cppr.ACTIVE_PYMT_PROF_PKREF = cpp.PYMT_PROF_PK
WHERE 
    cd.COMP_DETAILS_PK = :companyId
AND 
    cpp.PROFILE_CODE = :profileCode;