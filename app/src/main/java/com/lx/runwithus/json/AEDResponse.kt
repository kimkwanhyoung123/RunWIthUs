package com.lx.runwithus.json


import com.google.gson.annotations.SerializedName

data class AEDResponse(
    @SerializedName("tbEmgcAedInfo")
    val tbEmgcAEDInfo: TbEmgcAEDInfo
) {
    data class TbEmgcAEDInfo(
        @SerializedName("list_total_count")
        val listTotalCount: Int, // 6614
        @SerializedName("RESULT")
        val result: RESULT,
        @SerializedName("row")
        val row: List<Row>
    ) {
        data class RESULT(
            @SerializedName("CODE")
            val code: String, // INFO-000
            @SerializedName("MESSAGE")
            val message: String // 정상 처리되었습니다
        )

        data class Row(
            @SerializedName("BUILDADDRESS")
            val buildaddress: String, // 서울특별시 마포구 성암로 330, DMC첨단산업센터 (상암동)
            @SerializedName("BUILDPLACE")
            val buildplace: String, // 1층 중앙로비
            @SerializedName("CLERKTEL")
            val clerktel: String, // 02-3153-7119
            @SerializedName("MANAGER")
            val manager: String, // 김규태
            @SerializedName("MANAGERTEL")
            val managertel: String, // 02-3153-7119
            @SerializedName("MFG")
            val mfg: String, // 메디아나
            @SerializedName("MODEL")
            val model: String, // HeartOn A10
            @SerializedName("ORG")
            val org: String, // DMC첨단산업센터
            @SerializedName("WGS84LAT")
            val wgs84lat: String, // 37.5847261816
            @SerializedName("WGS84LON")
            val wgs84lon: String, // 126.8858949500
            @SerializedName("ZIPCODE1")
            val zipcode1: String, // 039
            @SerializedName("ZIPCODE2")
            val zipcode2: String // 20
        )
    }
}