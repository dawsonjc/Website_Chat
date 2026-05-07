package com.brewery.web.model.event;

// ============================================================================
// EVENT TYPES ENUM
// ============================================================================

public enum EventType {

    // ========================================================================
    // SOCIAL - Friend & Connection Events
    // ========================================================================
    FRIEND_REQUEST_RECEIVED("friend_request_received", "Friend Requests"),
    FRIEND_REQUEST_ACCEPTED("friend_request_accepted", "Friend Requests"),
    FRIEND_REQUEST_DECLINED("friend_request_declined", "Friend Requests"),
    FRIEND_REMOVED("friend_removed", "Friends"),
    FRIEND_SUGGESTION("friend_suggestion", "Suggestions"),

    FOLLOW_REQUEST_RECEIVED("follow_request_received", "Follow Requests"),
    FOLLOW_REQUEST_ACCEPTED("follow_request_accepted", "Follow Requests"),
    NEW_FOLLOWER("new_follower", "Followers"),
    USER_YOU_FOLLOW_JOINED("user_you_follow_joined", "Followers"),
    FOLLOWING_BACK("following_back", "Followers"),

    // ========================================================================
    // CONTENT INTERACTION - Posts, Comments, Reactions
    // ========================================================================
    POST_LIKED("post_liked", "Likes"),
    POST_LOVED("post_loved", "Reactions"),
    POST_REACTION("post_reaction", "Reactions"),

    COMMENT_ON_POST("comment_on_post", "Comments"),
    COMMENT_ON_COMMENT("comment_on_comment", "Comments"),
    COMMENT_REPLY("comment_reply", "Comments"),
    COMMENT_LIKED("comment_liked", "Likes"),
    COMMENT_MENTION("comment_mention", "Mentions"),

    POST_SHARED("post_shared", "Shares"),
    POST_REPOSTED("post_reposted", "Reposts"),
    POST_QUOTED("post_quoted", "Quotes"),

    POST_MENTION("post_mention", "Mentions"),
    POST_TAG("post_tag", "Tags"),
    PHOTO_TAG("photo_tag", "Tags"),
    VIDEO_TAG("video_tag", "Tags"),

    // ========================================================================
    // ENGAGEMENT - Milestones & Achievements
    // ========================================================================
    POST_MILESTONE_LIKES("post_milestone_likes", "Milestones"),      // 100, 1k, 10k likes
    POST_MILESTONE_VIEWS("post_milestone_views", "Milestones"),      // 1k, 10k, 100k views
    POST_MILESTONE_SHARES("post_milestone_shares", "Milestones"),
    POST_TRENDING("post_trending", "Trending"),
    POST_FEATURED("post_featured", "Featured"),

    FOLLOWER_MILESTONE("follower_milestone", "Milestones"),          // 100, 1k, 10k followers
    PROFILE_VIEW_MILESTONE("profile_view_milestone", "Milestones"),

    ACHIEVEMENT_UNLOCKED("achievement_unlocked", "Achievements"),
    BADGE_EARNED("badge_earned", "Badges"),
    LEVEL_UP("level_up", "Progress"),
    STREAK_MILESTONE("streak_milestone", "Streaks"),

    // ========================================================================
    // MESSAGING - Direct Messages & Chats
    // ========================================================================
    NEW_MESSAGE("new_message", "Messages"),
    MESSAGE_REACTION("message_reaction", "Messages"),
    MISSED_CALL("missed_call", "Calls"),
    MISSED_VIDEO_CALL("missed_video_call", "Calls"),
    VOICEMAIL_RECEIVED("voicemail_received", "Voicemail"),

    GROUP_MESSAGE("group_message", "Group Chats"),
    GROUP_MENTION("group_mention", "Group Chats"),
    ADDED_TO_GROUP("added_to_group", "Group Chats"),
    REMOVED_FROM_GROUP("removed_from_group", "Group Chats"),
    GROUP_ROLE_CHANGED("group_role_changed", "Group Chats"),

    // ========================================================================
    // GROUPS & COMMUNITIES
    // ========================================================================
    GROUP_INVITE("group_invite", "Group Invites"),
    GROUP_JOIN_REQUEST("group_join_request", "Group Requests"),
    GROUP_REQUEST_APPROVED("group_request_approved", "Groups"),
    GROUP_REQUEST_DECLINED("group_request_declined", "Groups"),

    NEW_GROUP_POST("new_group_post", "Group Activity"),
    GROUP_POST_APPROVED("group_post_approved", "Group Moderation"),
    GROUP_POST_REJECTED("group_post_rejected", "Group Moderation"),

    GROUP_EVENT_CREATED("group_event_created", "Group Events"),
    GROUP_EVENT_UPDATED("group_event_updated", "Group Events"),
    GROUP_EVENT_CANCELLED("group_event_cancelled", "Group Events"),
    GROUP_EVENT_REMINDER("group_event_reminder", "Group Events"),

    PROMOTED_TO_ADMIN("promoted_to_admin", "Group Roles"),
    PROMOTED_TO_MODERATOR("promoted_to_moderator", "Group Roles"),
    DEMOTED_FROM_ROLE("demoted_from_role", "Group Roles"),

    // ========================================================================
    // EVENTS & CALENDAR
    // ========================================================================
    EVENT_INVITE("event_invite", "Event Invites"),
    EVENT_REMINDER("event_reminder", "Event Reminders"),
    EVENT_STARTING_SOON("event_starting_soon", "Events"),
    EVENT_CANCELLED("event_cancelled", "Events"),
    EVENT_RESCHEDULED("event_rescheduled", "Events"),
    EVENT_UPDATED("event_updated", "Events"),

    RSVP_YES("rsvp_yes", "RSVPs"),
    RSVP_MAYBE("rsvp_maybe", "RSVPs"),
    RSVP_NO("rsvp_no", "RSVPs"),

    EVENT_ATTENDEE_UPDATE("event_attendee_update", "Event Activity"),
    EVENT_PHOTO_ADDED("event_photo_added", "Event Photos"),

    // ========================================================================
    // CONTENT UPDATES - From People/Pages You Follow
    // ========================================================================
    NEW_POST_FROM_FRIEND("new_post_from_friend", "Friend Activity"),
    NEW_POST_FROM_PAGE("new_post_from_page", "Page Updates"),
    NEW_POST_FROM_GROUP("new_post_from_group", "Group Activity"),

    LIVE_VIDEO_STARTED("live_video_started", "Live"),
    LIVE_STREAM_STARTED("live_stream_started", "Live"),
    LIVE_VIDEO_ENDING_SOON("live_video_ending_soon", "Live"),

    NEW_STORY_FROM_FRIEND("new_story_from_friend", "Stories"),
    STORY_REPLY("story_reply", "Stories"),
    STORY_REACTION("story_reaction", "Stories"),
    STORY_MENTION("story_mention", "Stories"),

    // ========================================================================
    // PAGES & BUSINESS
    // ========================================================================
    PAGE_LIKE("page_like", "Page Activity"),
    PAGE_FOLLOW("page_follow", "Page Activity"),
    PAGE_REVIEW("page_review", "Reviews"),
    PAGE_RATING("page_rating", "Reviews"),
    PAGE_MENTION("page_mention", "Mentions"),

    NEW_PAGE_POST("new_page_post", "Page Updates"),
    PAGE_PROMOTION_STARTED("page_promotion_started", "Promotions"),
    PAGE_ROLE_ASSIGNED("page_role_assigned", "Page Management"),

    // ========================================================================
    // MARKETPLACE & TRANSACTIONS
    // ========================================================================
    ITEM_SOLD("item_sold", "Sales"),
    ITEM_PURCHASED("item_purchased", "Purchases"),
    OFFER_RECEIVED("offer_received", "Offers"),
    OFFER_ACCEPTED("offer_accepted", "Offers"),
    OFFER_DECLINED("offer_declined", "Offers"),
    OFFER_COUNTER("offer_counter", "Offers"),

    PAYMENT_RECEIVED("payment_received", "Payments"),
    PAYMENT_SENT("payment_sent", "Payments"),
    PAYMENT_FAILED("payment_failed", "Payments"),
    REFUND_PROCESSED("refund_processed", "Refunds"),

    ORDER_CONFIRMED("order_confirmed", "Orders"),
    ORDER_SHIPPED("order_shipped", "Orders"),
    ORDER_DELIVERED("order_delivered", "Orders"),
    ORDER_CANCELLED("order_cancelled", "Orders"),

    PRODUCT_BACK_IN_STOCK("product_back_in_stock", "Shopping"),
    PRICE_DROP("price_drop", "Shopping"),
    SAVED_ITEM_ON_SALE("saved_item_on_sale", "Shopping"),

    // ========================================================================
    // GAMING & COMPETITIONS
    // ========================================================================
    GAME_INVITE("game_invite", "Game Invites"),
    GAME_REQUEST("game_request", "Game Requests"),
    TURN_NOTIFICATION("turn_notification", "Games"),
    GAME_COMPLETED("game_completed", "Games"),

    CHALLENGE_RECEIVED("challenge_received", "Challenges"),
    CHALLENGE_ACCEPTED("challenge_accepted", "Challenges"),
    CHALLENGE_COMPLETED("challenge_completed", "Challenges"),
    CHALLENGE_WON("challenge_won", "Challenges"),
    CHALLENGE_LOST("challenge_lost", "Challenges"),

    LEADERBOARD_POSITION("leaderboard_position", "Leaderboards"),
    HIGH_SCORE_BEATEN("high_score_beaten", "Scores"),

    // ========================================================================
    // MODERATION & SAFETY
    // ========================================================================
    CONTENT_REPORTED("content_reported", "Reports"),
    CONTENT_REMOVED("content_removed", "Moderation"),
    CONTENT_RESTORED("content_restored", "Moderation"),
    CONTENT_WARNING("content_warning", "Warnings"),

    ACCOUNT_WARNING("account_warning", "Account"),
    ACCOUNT_SUSPENDED("account_suspended", "Account"),
    ACCOUNT_RESTRICTED("account_restricted", "Account"),
    ACCOUNT_BAN("account_ban", "Account"),

    APPEAL_APPROVED("appeal_approved", "Appeals"),
    APPEAL_DENIED("appeal_denied", "Appeals"),

    BLOCKED_BY_USER("blocked_by_user", "Blocking"),
    UNBLOCKED_BY_USER("unblocked_by_user", "Blocking"),

    // ========================================================================
    // ACCOUNT & SECURITY
    // ========================================================================
    LOGIN_NEW_DEVICE("login_new_device", "Security"),
    LOGIN_NEW_LOCATION("login_new_location", "Security"),
    SUSPICIOUS_LOGIN("suspicious_login", "Security"),
    PASSWORD_CHANGED("password_changed", "Security"),
    EMAIL_CHANGED("email_changed", "Security"),
    TWO_FACTOR_ENABLED("two_factor_enabled", "Security"),
    TWO_FACTOR_DISABLED("two_factor_disabled", "Security"),

    VERIFICATION_APPROVED("verification_approved", "Verification"),
    VERIFICATION_DENIED("verification_denied", "Verification"),

    PRIVACY_SETTING_CHANGED("privacy_setting_changed", "Privacy"),
    DATA_DOWNLOAD_READY("data_download_ready", "Data"),

    // ========================================================================
    // SUBSCRIPTIONS & MEMBERSHIPS
    // ========================================================================
    SUBSCRIPTION_STARTED("subscription_started", "Subscriptions"),
    SUBSCRIPTION_RENEWED("subscription_renewed", "Subscriptions"),
    SUBSCRIPTION_EXPIRING("subscription_expiring", "Subscriptions"),
    SUBSCRIPTION_EXPIRED("subscription_expired", "Subscriptions"),
    SUBSCRIPTION_CANCELLED("subscription_cancelled", "Subscriptions"),

    MEMBERSHIP_UPGRADED("membership_upgraded", "Membership"),
    MEMBERSHIP_DOWNGRADED("membership_downgraded", "Membership"),

    TRIAL_STARTED("trial_started", "Trials"),
    TRIAL_ENDING_SOON("trial_ending_soon", "Trials"),
    TRIAL_EXPIRED("trial_expired", "Trials"),

    // ========================================================================
    // CONTENT CREATION & PUBLISHING
    // ========================================================================
    POST_SCHEDULED("post_scheduled", "Publishing"),
    POST_PUBLISHED("post_published", "Publishing"),
    POST_FAILED_TO_PUBLISH("post_failed_to_publish", "Publishing"),

    VIDEO_PROCESSING("video_processing", "Media"),
    VIDEO_READY("video_ready", "Media"),
    VIDEO_PROCESSING_FAILED("video_processing_failed", "Media"),

    DRAFT_AUTO_SAVED("draft_auto_saved", "Drafts"),
    DRAFT_EXPIRING("draft_expiring", "Drafts"),

    COPYRIGHT_CLAIM("copyright_claim", "Copyright"),
    COPYRIGHT_STRIKE("copyright_strike", "Copyright"),

    // ========================================================================
    // RECOMMENDATIONS & SUGGESTIONS
    // ========================================================================
    FRIEND_SUGGESTION_GENERAL("friend_suggestion_general", "Suggestions"),
    GROUP_SUGGESTION("group_suggestion", "Suggestions"),
    PAGE_SUGGESTION("page_suggestion", "Suggestions"),
    EVENT_SUGGESTION("event_suggestion", "Suggestions"),
    CONTENT_SUGGESTION("content_suggestion", "Suggestions"),

    PEOPLE_YOU_MAY_KNOW("people_you_may_know", "Suggestions"),
    SIMILAR_INTERESTS("similar_interests", "Suggestions"),

    // ========================================================================
    // REMINDERS & TASKS
    // ========================================================================
    REMINDER_DUE("reminder_due", "Reminders"),
    TASK_DUE("task_due", "Tasks"),
    TASK_OVERDUE("task_overdue", "Tasks"),
    TASK_ASSIGNED("task_assigned", "Tasks"),
    TASK_COMPLETED("task_completed", "Tasks"),

    BIRTHDAY_REMINDER("birthday_reminder", "Birthdays"),
    ANNIVERSARY_REMINDER("anniversary_reminder", "Anniversaries"),

    // ========================================================================
    // PROMOTIONS & MARKETING
    // ========================================================================
    SPECIAL_OFFER("special_offer", "Offers"),
    COUPON_AVAILABLE("coupon_available", "Coupons"),
    COUPON_EXPIRING("coupon_expiring", "Coupons"),

    PROMOTION_STARTED("promotion_started", "Promotions"),
    FLASH_SALE("flash_sale", "Sales"),

    REFERRAL_BONUS_EARNED("referral_bonus_earned", "Referrals"),
    FRIEND_JOINED_VIA_REFERRAL("friend_joined_via_referral", "Referrals"),

    // ========================================================================
    // SYSTEM & PLATFORM
    // ========================================================================
    SYSTEM_ANNOUNCEMENT("system_announcement", "System"),
    MAINTENANCE_SCHEDULED("maintenance_scheduled", "System"),
    FEATURE_ANNOUNCEMENT("feature_announcement", "Updates"),
    POLICY_UPDATE("policy_update", "Updates"),
    TERMS_OF_SERVICE_UPDATE("terms_of_service_update", "Updates"),

    APP_UPDATE_AVAILABLE("app_update_available", "Updates"),

    SURVEY_INVITATION("survey_invitation", "Surveys"),
    FEEDBACK_REQUEST("feedback_request", "Feedback"),

    // ========================================================================
    // HEALTH & WELLNESS (for fitness/health apps)
    // ========================================================================
    WORKOUT_REMINDER("workout_reminder", "Fitness"),
    WORKOUT_COMPLETED_BY_FRIEND("workout_completed_by_friend", "Fitness"),
    GOAL_ACHIEVED("goal_achieved", "Goals"),
    GOAL_MILESTONE("goal_milestone", "Goals"),
    DAILY_GOAL_REMINDER("daily_goal_reminder", "Goals"),

    HYDRATION_REMINDER("hydration_reminder", "Health"),
    MEDICATION_REMINDER("medication_reminder", "Health"),
    SLEEP_REMINDER("sleep_reminder", "Health"),

    // ========================================================================
    // LEARNING & EDUCATION
    // ========================================================================
    COURSE_ENROLLMENT("course_enrollment", "Education"),
    COURSE_COMPLETED("course_completed", "Education"),
    LESSON_AVAILABLE("lesson_available", "Education"),
    ASSIGNMENT_DUE("assignment_due", "Education"),
    ASSIGNMENT_GRADED("assignment_graded", "Education"),
    CERTIFICATE_EARNED("certificate_earned", "Education"),

    QUIZ_RESULT("quiz_result", "Quizzes"),
    TEST_REMINDER("test_reminder", "Tests"),

    // ========================================================================
    // PROFESSIONAL & NETWORKING
    // ========================================================================
    CONNECTION_REQUEST("connection_request", "Connections"),
    PROFILE_VIEWED("profile_viewed", "Profile"),
    ENDORSEMENT_RECEIVED("endorsement_received", "Endorsements"),
    SKILL_VERIFIED("skill_verified", "Skills"),

    JOB_POSTED("job_posted", "Jobs"),
    JOB_APPLICATION_VIEWED("job_application_viewed", "Jobs"),
    JOB_APPLICATION_STATUS("job_application_status", "Jobs"),

    RECOMMENDATION_RECEIVED("recommendation_received", "Recommendations"),

    // ========================================================================
    // WEATHER & LOCATION
    // ========================================================================
    WEATHER_ALERT("weather_alert", "Weather"),
    SEVERE_WEATHER_WARNING("severe_weather_warning", "Weather"),

    FRIEND_NEARBY("friend_nearby", "Location"),
    CHECK_IN_NEARBY("check_in_nearby", "Location"),

    // ========================================================================
    // CUSTOM & GENERIC
    // ========================================================================
    CUSTOM_NOTIFICATION("custom_notification", "Custom"),
    GENERAL_ALERT("general_alert", "Alerts"),
    INFO_MESSAGE("info_message", "Info");

    private final String code;
    private final String category;

    EventType(String code, String category) {
        this.code = code;
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public static EventType fromCode(String code) {
        for (EventType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + code);
    }
}
