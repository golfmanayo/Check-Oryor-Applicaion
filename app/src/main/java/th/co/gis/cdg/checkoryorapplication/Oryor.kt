package th.co.gis.cdg.checkoryorapplication

object Oryor {

    private val foodPattern: String = """[0-9]{2}-[0-9]-[0-9]{5}-[0-9]-[0-9]{1,4}"""
    private val medicinePattern: String = """[0-9]{0,1}[A-FG-HK-N][0-9]{1,4}/[0-9]{2}"""
    private val cosmeticsPattern: String = """[0-9]{2}-[0-9]-[0-9]{7,10}"""
    private val medicalInsPattern: String = """[ผน].[0-9]{2}/[0-9]{1,4}"""
    private val dangerPattern: String = """วอส.[0-9]{2}/[0-9]{1,4}"""

    fun find(with: String):String {
        val foodRegex = foodPattern.toRegex()
        val medicineRegex = Regex(medicinePattern)
        val cosmeticsRegex = Regex(cosmeticsPattern)
        val medicalInsRegex = Regex(medicalInsPattern)
        val dangerRegex = Regex(dangerPattern)

        val foodMatches = foodRegex.containsMatchIn(input = with)
        val medicineMatches = medicineRegex.containsMatchIn(input = with)
        val cosmeticsMatches = cosmeticsRegex.containsMatchIn(input = with)
        val medicalInsMatches = medicalInsRegex.containsMatchIn(input = with)
        val dangerMatches = dangerRegex.containsMatchIn(input = with)

        if (foodMatches) {
            val value = foodRegex.find(with)?.value
            if (value != null) {
                return value
            }
        }
        if (medicineMatches) {
            val value = medicineRegex.find(with)?.value
            if (value != null) {
                return value
            }
        }
        if (cosmeticsMatches) {
            val value = cosmeticsRegex.find(with)?.value
            if (value != null) {
                return value
            }
        }
        if (medicalInsMatches) {
            val value = medicalInsRegex.find(with)?.value
            if (value != null) {
                return value
            }
        }
        if (dangerMatches) {
            val value = dangerRegex.find(with)?.value
            if (value != null) {
                return value
            }
        }

        return ""
    }
}