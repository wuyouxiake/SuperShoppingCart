import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Balance;
public class BalanceDB {

	public static void insert(Balance blc) {
		EntityManager em = postTools.DBUtil.getEmFactory()
				.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.persist(blc);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}

	public static void update(Balance blc) {
		EntityManager em = postTools.DBUtil.getEmFactory()
				.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.merge(blc);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}

	public static void delete(Balance blc) {
		EntityManager em = postTools.DBUtil.getEmFactory()
				.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.remove(em.merge(blc));
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}

}