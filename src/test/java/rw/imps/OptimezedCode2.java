public class CompanyProfileResponse {
    private String companyId;
    private String profileCode;
    private String companyName;
    private String profileName;
    private String approvalStructureType;
    private String profileStatus;
    private String currency;

    // Lazy-loaded fields
    private Optional<List<String>> paymentEntryMethod = Optional.empty();
    private Optional<List<Account>> account = Optional.empty();
    private Optional<List<String>> paymentCategory = Optional.empty();
    private Optional<List<Tier>> tier = Optional.empty();

    // Getters and Setters
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
    
    public CompanyProfileResponse getCompanyProfile(String companyId, String profileCode, int offset, int limit) {
        // Fetch basic information
        CompanyProfileResponse profile = companyProfileMapper.getBasicInfo(companyId, profileCode);

        // Lazy load additional details
        loadAccountDetails(profile, companyId, profileCode);
        loadPaymentEntryMethods(profile, companyId, profileCode);
        loadTierDetails(profile, companyId, profileCode, offset, limit);
        
        return profile;
    }
    
    private void loadAccountDetails(CompanyProfileResponse profile, String companyId, String profileCode) {
        if (profile.getAccount().isEmpty()) {
            profile.setAccount(Optional.of(accountMapper.getAccounts(companyId, profileCode)));
        }
    }
    
    private void loadPaymentEntryMethods(CompanyProfileResponse profile, String companyId, String profileCode) {
        if (profile.getPaymentEntryMethod().isEmpty()) {
            profile.setPaymentEntryMethod(Optional.of(paymentEntryMethodMapper.getPaymentEntryMethods(companyId, profileCode)));
        }
    }
    
    private void loadTierDetails(CompanyProfileResponse profile, String companyId, String profileCode, int offset, int limit) {
        if (profile.getTier().isEmpty()) {
            List<Tier> tiers = tierMapper.getTiersAndPanels(companyId, profileCode, offset, limit);
            profile.setTier(Optional.of(tiers));
        }
    }
}
@RestController
@RequestMapping("/api/company-profile")
public class CompanyProfileController {

    @Autowired
    private CompanyProfileService companyProfileService;

    @GetMapping("/{companyId}/{profileCode}")
    public ResponseEntity<CompanyProfileResponse> getCompanyProfile(
            @PathVariable String companyId, @PathVariable String profileCode,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit) {
        
        CompanyProfileResponse response = companyProfileService.getCompanyProfile(companyId, profileCode, offset, limit);
        return ResponseEntity.ok(response);
    }
}
