package com.acr.mpvconfmaker.data.mpv

enum class MpvOptionSection {
    Playback,
    Video,
    Audio,
    Subtitles,
    Network,
    Profiles,
}

enum class MpvOptionType {
    Boolean,
    Choice,
    Integer,
    Number,
    Float,
    String,
    Text,
    Path,
    StringList,
    List,
}

enum class AndroidSupport {
    OfficialMpv,
    RecommendedAndroid,
    CompatibleAndroid,
    CompatibleAdvanced,
    NotRecommendedAndroid,
    DesktopOnly,
    UnknownAndroid,
}

enum class RiskLevel {
    Low,
    Medium,
    High,
    Experimental,
}

data class MpvOption(
    val key: String,
    val section: MpvOptionSection,
    val type: MpvOptionType,
    val defaultValue: String?,
    val choices: List<String>,
    val minValue: Double?,
    val maxValue: Double?,
    val unit: String?,
    val shortDescription: String,
    val longDescription: String,
    val androidSupport: AndroidSupport,
    val androidNote: String,
    val riskLevel: RiskLevel,
    val sourceUrl: String,
    val sourceAnchor: String,
    val tags: List<String>,
    val example: String,
    val recommendedValues: List<String>,
)
