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
    String,
    StringList,
}

enum class AndroidSupport {
    OfficialMpv,
    RecommendedAndroid,
    CompatibleAdvanced,
    NotRecommendedAndroid,
    DesktopOnly,
}

enum class RiskLevel {
    Low,
    Medium,
    High,
}

data class MpvOption(
    val key: String,
    val section: MpvOptionSection,
    val type: MpvOptionType,
    val defaultValue: String?,
    val choices: List<String>,
    val shortDescription: String,
    val longDescription: String,
    val androidSupport: AndroidSupport,
    val androidNote: String,
    val riskLevel: RiskLevel,
    val sourceUrl: String,
    val sourceAnchor: String,
    val example: String,
    val recommendedValues: List<String>,
)
