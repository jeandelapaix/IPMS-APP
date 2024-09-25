public class CompanyProfileResponse {
    private String companyId;
    private String profileCode;
    private String companyName;
    private String profileName;
    private String approvalStructureType;
    private String profileStatus;
    private List<String> paymentEntryMethod;
    private List<Account> account;
    private List<String> paymentCategory;
    private String currency;
    private List<Tier> tier;
    
    // Getters and Setters
}

public class Account {
    private String accountNumber;
    private String bankId;
    
    // Getters and Setters
}

public class Tier {
    private int id;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private List<Panel> panel;
    
    // Getters and Setters
}

public class Panel {
    private int id;
    private int approvalOrder;
    private Group group;
    
    // Getters and Setters
}

public class Group {
    private int id;
    private int orderInPanel;
    private int approvalsRequired;
    
    // Getters and Setters
}

@Mapper
public interface CompanyProfileMapper {

    @Select("SELECT CD.COMP_ID AS companyId, CD.COMP_NAME AS companyName, CP.PROFILE_CODE AS profileCode, " +
            "CPD.PROFILE_NAME AS profileName, CPD.APR_STRUCTURE_TYPE AS approvalStructureType, " +
            "CPD.NUMBER_OF_APPROVERS AS noOfApprovers, CP.STATUS AS profileStatus " +
            "FROM MCESSERV.COMPANY_DETAILS CD " +
            "INNER JOIN MCESSERV.COMP_APP CA ON CD.ACTIVE_COMP_PKREF = CA.MCES_COMPANY " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE CP ON CA.CMAP_PK = CP.CMAP_PKREF " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE_DETAILS CPD ON CP.PYMT_PROF_PK = CPD.ACTIVE_PYMT_PROF_PKREF " +
            "WHERE CD.COMP_ID = #{companyId} AND CP.PROFILE_CODE = #{profileCode} AND CP.STATUS = 'ACTIVE'")
    @Results({
        @Result(column = "companyId", property = "companyId"),
        @Result(column = "companyName", property = "companyName"),
        @Result(column = "profileCode", property = "profileCode"),
        @Result(column = "profileName", property = "profileName"),
        @Result(column = "approvalStructureType", property = "approvalStructureType"),
        @Result(column = "noOfApprovers", property = "noOfApprovers"),
        @Result(column = "profileStatus", property = "profileStatus")
    })
    CompanyProfileResponse getBasicInfo(@Param("companyId") String companyId, @Param("profileCode") String profileCode);
}

@Mapper
public interface AccountMapper {

    @Select("SELECT MR.MCES_ACCOUNT_NUMBER AS accountNumber, MR.MCES_BANK_ID AS bankId " +
            "FROM MCESSERV.COMP_APP CA " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE CP ON CA.CMAP_PK = CP.CMAP_PKREF " +
            "LEFT JOIN MCESSERV.COMP_RESOURCE CR ON CA.MCES_COMPANY = CR.ACTIVE_COMP_PKREF " +
            "LEFT JOIN MCESSERV.MCES_RESOURCE MR ON CR.RESOURCE_PK = MR.RESOURCE_PK " +
            "WHERE CA.MCES_COMPANY = (SELECT ACTIVE_COMP_PKREF FROM MCESSERV.COMPANY_DETAILS WHERE COMP_ID = #{companyId}) " +
            "AND CP.PROFILE_CODE = #{profileCode}")
    @Results({
        @Result(column = "accountNumber", property = "accountNumber"),
        @Result(column = "bankId", property = "bankId")
    })
    List<Account> getAccounts(@Param("companyId") String companyId, @Param("profileCode") String profileCode);
}


@Mapper
public interface PaymentEntryMethodMapper {

    @Select("SELECT LISTAGG(DISTINCT MCPPP.ENTRY_METHOD, ', ') WITHIN GROUP (ORDER BY MCPPP.ENTRY_METHOD) AS paymentEntryMethod " +
            "FROM MCESSERV.COMP_PAYMENT_PROFILE_PYMTENTRY MCPPP " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE CP ON CP.PYMT_PROF_PK = MCPPP.active_pymt_prof_pkref " +
            "WHERE CP.PROFILE_CODE = #{profileCode} AND CP.CMAP_PKREF = " +
            "(SELECT CA.CMAP_PK FROM MCESSERV.COMP_APP CA WHERE CA.MCES_COMPANY = " +
            "(SELECT ACTIVE_COMP_PKREF FROM MCESSERV.COMPANY_DETAILS WHERE COMP_ID = #{companyId})) " +
            "GROUP BY CP.CMAP_PKREF")
    @Results({
        @Result(column = "paymentEntryMethod", property = "paymentEntryMethod")
    })
    List<String> getPaymentEntryMethods(@Param("companyId") String companyId, @Param("profileCode") String profileCode);
}

@Mapper
public interface TierMapper {

    @Select("SELECT TIER.TIER_ID_PKREF AS tierId, TIER.MIN_AMOUNT AS minAmount, TIER.MAX_AMOUNT AS maxAmount, " +
            "PANEL.PANEL_ID_PKREF AS panelId, PANEL.PANEL_APPROVAL_ORDER AS approvalOrder, " +
            "APP_GROUP.USERGRP_PK AS groupId, APP_GROUP.NO_OF_APPROVAL_REQUIRED AS approvalsRequired " +
            "FROM MCESSERV.COMP_PAYMENT_PROFILE_TIER TIER " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE_PANEL PANEL ON PANEL.TIER_ID_PKREF = TIER.TIER_ID_PKREF " +
            "LEFT JOIN MCESSERV.COMP_PAYMENT_PROFILE_PANEL_APPGRP APP_GROUP ON PANEL.PANEL_ID_PKREF = APP_GROUP.PANEL_ID_PKREF " +
            "INNER JOIN MCESSERV.COMP_PAYMENT_PROFILE CP ON CP.PYMT_PROF_PK = TIER.ACTIVE_PYMT_PROF_PKREF " +
            "WHERE CP.PROFILE_CODE = #{profileCode} AND CP.CMAP_PKREF = " +
            "(SELECT CA.CMAP_PK FROM MCESSERV.COMP_APP CA WHERE CA.MCES_COMPANY = " +
            "(SELECT ACTIVE_COMP_PKREF FROM MCESSERV.COMPANY_DETAILS WHERE COMP_ID = #{companyId}))")
    @Results({
        @Result(column = "tierId", property = "id"),
        @Result(column = "minAmount", property = "minAmount"),
        @Result(column = "maxAmount", property = "maxAmount"),
        @Result(column = "panelId", property = "panel.id"),
        @Result(column = "approvalOrder", property = "panel.approvalOrder"),
        @Result(column = "groupId", property = "panel.group.id"),
        @Result(column = "approvalsRequired", property = "panel.group.approvalsRequired")
    })
    List<Tier> getTiersAndPanels(@Param("companyId") String companyId, @Param("profileCode") String profileCode);
}

@Service
public class CompanyProfileService {

    @Autowired
    private CompanyProfileMapper companyProfileMapper;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private PaymentEntryMethodMapper paymentEntryMethodMapper;
    
    @Autowired
    private TierMapper tierMapper;
    
    public CompanyProfileResponse getCompanyProfile(String companyId, String profileCode) {
        CompanyProfileResponse profile = companyProfileMapper.getBasicInfo(companyId, profileCode);
        
        // Fetch additional details
        profile.setAccount(accountMapper.getAccounts(companyId, profileCode));
        profile.setPaymentEntryMethod(paymentEntryMethodMapper.getPaymentEntryMethods(companyId, profileCode));
        profile.setTier(tierMapper.getTiersAndPanels(companyId, profileCode));
        
        // Handle other fields like paymentCategory and currency (if needed)
        
        return profile;
    }
}

@RestController
@RequestMapping("/api/company-profile")
public class CompanyProfileController {

    @Autowired
    private CompanyProfileService companyProfileService;

    @GetMapping("/{companyId}/{profileCode}")
    public ResponseEntity<CompanyProfileResponse> getCompanyProfile(
            @PathVariable String companyId, @PathVariable String profileCode) {
        
        CompanyProfileResponse response = companyProfileService.getCompanyProfile(companyId, profileCode);
        return ResponseEntity.ok(response);
    }
}

