package repository

import domain.Item;
import org.hibernate.Session
import org.springframework.data.jpa.repository.JpaRepository;
import repository.IItemsRepository;
import java.util.*

class ItemsRepository: IItemsRepository{
    private val session: Session;
    constructor(connectionURL: String, username: String, password: String){
        val properties = Properties()
        properties["hibernate.connection.driver_class"] = "org.postgresql.Driver"
        properties["hibernate.connection.url"] = connectionURL
        properties["hibernate.connection.username"] = username
        properties["hibernate.connection.password"] = password
        properties["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"

        val sessionFactory = org.hibernate.cfg.Configuration()
            .addAnnotatedClass(Item::class.java)
            .addProperties(properties)
            .buildSessionFactory()

        session = sessionFactory.openSession()
    }
    override fun findByID(id: Int): Item?{
        return session.get(Item::class.java, id);
    }

    override fun findAll(): List < Item >?{
        return session.createQuery("from EntityName").list() as List<Item>?;
    }

    override fun save(item: Item){
        session.save(item);
    }

    override fun update(item: Item){
        session.update(item);
    }


}