import CoreData
import Combine

// MARK: - NSManagedObject subclass (no .xcdatamodeld needed)
@objc(FavoriteEntity)
final class FavoriteEntity: NSManagedObject {
    @NSManaged var cocktailId: String
    @NSManaged var name: String
    @NSManaged var imageUrl: String?
    @NSManaged var addedAt: Date
}

// MARK: - Persistence Controller
final class PersistenceController {
    static let shared = PersistenceController()

    let container: NSPersistentContainer

    private init() {
        // Build Core Data model programmatically (avoids .xcdatamodeld in SPM)
        let entity = NSEntityDescription()
        entity.name = "FavoriteEntity"
        entity.managedObjectClassName = "FavoriteEntity"

        func makeAttr(_ name: String, type: NSAttributeType, optional: Bool = false) -> NSAttributeDescription {
            let a = NSAttributeDescription()
            a.name = name; a.attributeType = type; a.isOptional = optional
            return a
        }
        entity.properties = [
            makeAttr("cocktailId", type: .stringAttributeType),
            makeAttr("name",       type: .stringAttributeType),
            makeAttr("imageUrl",   type: .stringAttributeType, optional: true),
            makeAttr("addedAt",    type: .dateAttributeType),
        ]

        let model = NSManagedObjectModel()
        model.entities = [entity]

        container = NSPersistentContainer(name: "MixMaster", managedObjectModel: model)
        container.loadPersistentStores { _, error in
            if let error { print("⚠️ Core Data: \(error)") }
        }
        container.viewContext.automaticallyMergesChangesFromParent = true
    }

    var ctx: NSManagedObjectContext { container.viewContext }

    // MARK: - CRUD

    func addFavorite(_ item: CocktailListItem) {
        guard !isFavorite(id: item.id) else { return }
        let fav = FavoriteEntity(context: ctx)
        fav.cocktailId = item.id
        fav.name       = item.name
        fav.imageUrl   = item.imageUrl?.absoluteString
        fav.addedAt    = Date()
        save()
    }

    func removeFavorite(id: String) {
        let req = fetchRequest(predicate: NSPredicate(format: "cocktailId == %@", id))
        (try? ctx.fetch(req))?.forEach { ctx.delete($0) }
        save()
    }

    func isFavorite(id: String) -> Bool {
        let req = fetchRequest(predicate: NSPredicate(format: "cocktailId == %@", id))
        return (try? ctx.count(for: req)) ?? 0 > 0
    }

    func fetchAllFavorites() -> [CocktailListItem] {
        let req = fetchRequest()
        req.sortDescriptors = [NSSortDescriptor(keyPath: \FavoriteEntity.addedAt, ascending: false)]
        return (try? ctx.fetch(req))?.map { entity in
            CocktailListItem(
                id:       entity.cocktailId,
                name:     entity.name,
                imageUrl: entity.imageUrl.flatMap(URL.init)
            )
        } ?? []
    }

    // MARK: - Private helpers
    private func fetchRequest(predicate: NSPredicate? = nil) -> NSFetchRequest<FavoriteEntity> {
        let req = NSFetchRequest<FavoriteEntity>(entityName: "FavoriteEntity")
        req.predicate = predicate
        return req
    }

    private func save() {
        guard ctx.hasChanges else { return }
        try? ctx.save()
    }
}
