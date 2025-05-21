package repository;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByUsername(String username);
}
