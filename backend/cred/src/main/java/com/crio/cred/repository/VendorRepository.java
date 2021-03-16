package com.crio.cred.repository;

import com.crio.cred.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Vendor repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
