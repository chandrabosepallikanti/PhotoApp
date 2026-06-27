package com.app.photoapp.constants;

public final class AppConstants {
    private AppConstants() {}

    public static final String PREFS_NAME = "photo_app_prefs";
    public static final String KEY_FIRST_LAUNCH = "is_first_launch";
    public static final String KEY_SELECTED_TEMPLATE_URL = "selected_template_url";

    public static final String TERMS_URL = "https://www.example.com/terms";
    public static final String PRIVACY_POLICY_TEXT =
            "Privacy Policy\n\n" +
            "Last Updated: June 2026\n\n" +

            "1. Information We Collect\n" +
            "We collect information you provide directly while using the application. " +
            "This includes images selected from your device or captured using the camera.\n\n" +

            "2. How We Use Your Information\n" +
            "We use the information solely to provide, maintain, and improve the application's features. " +
            "All image processing is performed locally on your device.\n\n" +

            "3. Data Storage\n" +
            "All data is stored locally on your device. " +
            "We do not upload, collect, or store your personal data on external servers.\n\n" +

            "4. Permissions\n" +
            "The application may request Camera and Storage permissions to enable its core functionality. " +
            "These permissions are used only for the intended features of the app.\n\n" +

            "5. Contact Us\n" +
            "If you have any questions regarding this Privacy Policy, please contact the developer.\n\n" +

            "6. Changes to This Policy\n" +
            "We may update this Privacy Policy from time to time. " +
            "Any changes will be reflected by updating the 'Last Updated' date.\n\n" +

            "7. Copyright Notice\n" +
            "© 2026 Chandrabose Pallikanti. All Rights Reserved.\n" +
            "This application and its contents are protected under applicable copyright laws.\n\n" +

            "Developed with Java for Android🩷.";

    public static final String PLAY_STORE_URL = "market://details?id=com.app.photoapp";
    public static final String PLAY_STORE_WEB_URL = "https://play.google.com/store/apps/details?id=com.app.photoapp";

    public static final int SPLASH_TIMER_MS = 10000;
    public static final int PROCESSING_TIMER_MS = 10000;

    public static final String EXTRA_TEMPLATE_URL = "extra_template_url";
    public static final String EXTRA_CROPPED_IMAGE_PATH = "extra_cropped_image_path";
    public static final String EXTRA_SAVED_IMAGE_PATH = "extra_saved_image_path";

    public static final int REQUEST_CAMERA_PERMISSION = 100;
    public static final int REQUEST_STORAGE_PERMISSION = 101;
    public static final int REQUEST_IMAGE_PICK = 200;
    public static final int REQUEST_IMAGE_CAPTURE = 201;
    public static final int REQUEST_CROP = 202;
}
