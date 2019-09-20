package th.co.gis.cdg.checkoryorapplication

class Oryor {

    private val foodPattern: String = "/[0-9]{2}-[0-9]-[0-9]{5}-[0-9]-[0-9]{1,4}/g"
    private val medicinePattern: String = "/[0-9]{0,1}[ABCDEFGHKLMN][0-9]{1,4}\\/[0-9]{2}/g"
    private val cosmeticsPattern: String = "/[0-9]{2}-[0-9]-[0-9]{7,10}/g"
    private val medicalInsPattern: String = "/[ผน]\\.[0-9]{2}/[0-9]{1,4}/g"
    private val dangerPattern: String = "/วอส\\.[0-9]{2}/[0-9]{1,4}/g"

    fun find(with: String):String {
        val foodRegex = Regex(foodPattern)
        val medicineRegex = Regex(medicinePattern)
        val cosmeticsRegex = Regex(cosmeticsPattern)
        val medicalInsRegex = Regex(medicalInsPattern)
        val dangerRegex = Regex(dangerPattern)

        val foodMatches = foodRegex.matches(input = with)
        val medicineMatches = medicineRegex.matches(input = with)
        val cosmeticsMatches = cosmeticsRegex.matches(input = with)
        val medicalInsMatches = medicalInsRegex.matches(input = with)
        val dangerMatches = dangerRegex.matches(input = with)

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