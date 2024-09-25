public class CompanyProfileNotFoundException extends RuntimeException {
    public CompanyProfileNotFoundException(String companyId, String profileCode) {
        super("Company Profile not found for Company ID: " + companyId + " and Profile Code: " + profileCode);
    }
}

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CompanyProfileNotFoundException.class)
    public ResponseEntity<String> handleCompanyProfileNotFound(CompanyProfileNotFoundException ex) {
        logger.error("Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        logger.error("Database Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Unexpected Error: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}

@Service
public class CompanyProfileService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyProfileService.class);

    @Autowired
    private CompanyProfileMapper companyProfileMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentEntryMethodMapper paymentEntryMethodMapper;

    @Autowired
    private TierMapper tierMapper;

    public CompanyProfileResponse getCompanyProfile(String companyId, String profileCode) {
        try {
            // Fetch basic information
            CompanyProfileResponse profile = companyProfileMapper.getBasicInfo(companyId, profileCode);
            if (profile == null) {
                logger.warn("No company profile found for Company ID: {}, Profile Code: {}", companyId, profileCode);
                throw new CompanyProfileNotFoundException(companyId, profileCode);
            }
            logger.info("Successfully fetched basic company profile for Company ID: {}, Profile Code: {}", companyId, profileCode);

            // Fetch additional details only when required (Lazy load)
            profile.setAccount(Optional.of(accountMapper.getAccounts(companyId, profileCode)));
            profile.setPaymentEntryMethod(Optional.of(paymentEntryMethodMapper.getPaymentEntryMethods(companyId, profileCode)));

            // Fetch tier details with pagination for large datasets
            List<Tier> tiers = tierMapper.getTiersAndPanels(companyId, profileCode, 0, 50); // Example with limit of 50
            profile.setTier(Optional.of(tiers));

            logger.info("Successfully fetched full company profile for Company ID: {}, Profile Code: {}", companyId, profileCode);

            return profile;
        } catch (Exception ex) {
            logger.error("Error fetching company profile for Company ID: {}, Profile Code: {}. Error: {}", companyId, profileCode, ex.getMessage());
            throw new DatabaseException("Unable to fetch company profile due to a database error.", ex);
        }
    }
}

@RestController
@RequestMapping("/api/company-profile")
public class CompanyProfileController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyProfileController.class);

    @Autowired
    private CompanyProfileService companyProfileService;

    @GetMapping("/{companyId}/{profileCode}")
    public ResponseEntity<CompanyProfileResponse> getCompanyProfile(
            @PathVariable String companyId, @PathVariable String profileCode,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit) {

        logger.info("Received request for company profile: Company ID: {}, Profile Code: {}", companyId, profileCode);

        // The service call may throw an exception, but it will be handled by @RestControllerAdvice
        CompanyProfileResponse response = companyProfileService.getCompanyProfile(companyId, profileCode);

        logger.info("Successfully processed request for Company ID: {}, Profile Code: {}", companyId, profileCode);
        return ResponseEntity.ok(response);
    }
}
