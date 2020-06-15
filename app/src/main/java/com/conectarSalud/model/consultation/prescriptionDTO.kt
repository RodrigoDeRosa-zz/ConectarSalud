package com.conectarSalud.model.consultation

data class prescriptionDTO (
    val date: String,
    val patientFirstName: String,
    val patientLastName: String,
    val patientPlan: String,
    val patientId: String,
    val doctorFirstName: String,
    val doctorLastName: String,
    val doctorLicence: String,
    val doctorSpecialities: Array<String>,
    val prescriptionText: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as prescriptionDTO

        if (date != other.date) return false
        if (patientFirstName != other.patientFirstName) return false
        if (patientLastName != other.patientLastName) return false
        if (patientPlan != other.patientPlan) return false
        if (patientId != other.patientId) return false
        if (doctorFirstName != other.doctorFirstName) return false
        if (doctorLastName != other.doctorLastName) return false
        if (doctorLicence != other.doctorLicence) return false
        if (!doctorSpecialities.contentEquals(other.doctorSpecialities)) return false
        if (prescriptionText != other.prescriptionText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + patientFirstName.hashCode()
        result = 31 * result + patientLastName.hashCode()
        result = 31 * result + patientPlan.hashCode()
        result = 31 * result + patientId.hashCode()
        result = 31 * result + doctorFirstName.hashCode()
        result = 31 * result + doctorLastName.hashCode()
        result = 31 * result + doctorLicence.hashCode()
        result = 31 * result + doctorSpecialities.contentHashCode()
        result = 31 * result + (prescriptionText?.hashCode() ?: 0)
        return result
    }

}