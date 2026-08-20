package resolveclaimsni.management.resolvestrategy.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import resolveclaimsni.management.resolvestrategy.data.model.ServiceModel
import java.time.LocalTime

class ServiceRepository {
    private val services = listOf(
        service(1, "Strategic Planning Session", "Turn complex ambitions into a focused, measurable growth roadmap.", 180.0, "Strategic Planning", 90, "1552664730-d307ca884978"),
        service(2, "Organisational Audit", "Reveal structural gaps, duplicated effort and opportunities for stronger accountability.", 240.0, "Business Optimisation", 120, "1556761175-b413da4baf72"),
        service(3, "Business Process Review", "Map critical workflows and design practical improvements that reduce friction.", 160.0, "Business Optimisation", 75, "1454165804606-c3d57bc86b40"),
        service(4, "KPI Framework Design", "Connect daily work to strategic outcomes with a balanced set of indicators.", 195.0, "Performance", 90, "1551288049-bebda4e38f71"),
        service(5, "Leadership Alignment", "Create a shared leadership agenda and strengthen high-stakes decision making.", 210.0, "People & Leadership", 90, "1521737711867-e3b97375f902"),
        service(6, "Change Management Plan", "Prepare leaders and teams to adopt change with clarity and momentum.", 185.0, "People & Leadership", 90, "1542744173-8e7e53415bb0"),
        service(7, "Workforce Strategy", "Shape roles, capabilities and capacity around the organisation you want to become.", 220.0, "People & Leadership", 105, "1522071820081-009f0129c71c"),
        service(8, "Operational Efficiency Sprint", "A concentrated review that identifies rapid, responsible performance gains.", 275.0, "Business Optimisation", 150, "1531497865144-0464ef8fb9a9"),
        service(9, "Growth Strategy Review", "Stress-test market choices and focus investment on the strongest opportunities.", 200.0, "Strategic Planning", 90, "1460925895917-afdab827c52f"),
        service(10, "Governance & Accountability", "Clarify ownership, reporting and controls without unnecessary bureaucracy.", 175.0, "Performance", 75, "1507679799987-c73779587ccf"),
        service(11, "Customer Journey Redesign", "Align teams and processes around the moments that matter most to customers.", 190.0, "Business Optimisation", 90, "1556761175-5973dc0f32e7"),
        service(12, "Executive Advisory Call", "Focused, confidential guidance for an immediate organisational challenge.", 95.0, "Strategic Planning", 45, "1560472354-b33ff0c44a43"),
    )

    fun observeAll(): Flow<List<ServiceModel>> = flowOf(services)

    fun observeById(id: Int): Flow<ServiceModel?> = flowOf(getById(id))

    fun getById(id: Int): ServiceModel? = services.firstOrNull { service -> service.id == id }

    private fun service(
        id: Int,
        name: String,
        description: String,
        price: Double,
        category: String,
        duration: Int,
        imageId: String,
    ) = ServiceModel(
        id = id,
        name = name,
        description = description,
        price = price,
        availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(13, 30), LocalTime.of(16, 0)),
        imageUrl = "https://images.unsplash.com/photo-$imageId?w=1200",
        category = category,
        durationMinutes = duration,
        features = listOf(
            "Expert discovery and analysis",
            "Clear recommendations and priorities",
            "Practical action plan and follow-up",
        ),
    )
}
